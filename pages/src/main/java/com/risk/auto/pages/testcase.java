package com.risk.auto.pages;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import com.risk.auto.pages.util;
import com.risk.auto.pages.login;
import com.risk.auto.pages.FirstLevelList;

public class testcase {
	
	WebDriver driver;
	String uri="http://risk.bat.tcredit.com";
	Date date = new Date();
	DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	String time = format.format(date);
	String root_path = "../pic/"+time;
	ArrayList<HashMap<String, String>> pages;
	
	
	@BeforeClass
	public void setUp() throws IOException{
		System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
		driver = new ChromeDriver();
		driver.get(uri+"/risk/trics/login");
		driver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);
		util.new_dir(root_path);
		login l=new login();
		l.loginData("admin1","123456",false);
		Assert.assertEquals(driver.getTitle(), "DB数据管理");
	}
	
	@Test
	public void Search(){
		FirstLevelList firstlistpage=new FirstLevelList();
		firstlistpage.searchByStatus("已提交");
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenshot, new File(root_path+"/"+"xxx.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
	@AfterClass
	public void tearDown(){
		driver.close();
	}
	
}
