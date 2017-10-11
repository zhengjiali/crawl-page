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
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import com.risk.auto.pages.util;


public class testcase {
	
	static WebDriver driver;
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
		driver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);
		util.new_dir(root_path);
	}
	
	@Test(priority=0)
	public void login(){
		driver.get(uri+"/riskData/trics/login");
		LoginPage loginPage=PageFactory.initElements(driver, LoginPage.class);
		loginPage.login("admin1", "123456", false);
		new WebDriverWait(driver,4).until(ExpectedConditions.not(ExpectedConditions.titleContains("登陆")));
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenshot, new File(root_path+"/"+"login.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		Assert.assertEquals(driver.getTitle(), "DB数据管理");

	}
	
	@Test(priority=2)
	public void Search(){
		FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
		firstlistpage.searchByStatus("已提交");
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenshot, new File(root_path+"/"+"Search.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
	@Test(priority=1)
	public void SearchTimeStart(){
		FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
		firstlistpage.searchByTimeStart("2017-08-08");
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenshot, new File(root_path+"/"+"SearchTimeStart.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
	@Test(priority=3)
	public void SearchTimeEnd(){
		FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
		firstlistpage.searchByTimeEnd("2017-08-18");
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenshot, new File(root_path+"/"+"SearchTimeEnd.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
	@AfterClass
	public void tearDown(){
		driver.close();
	}
	
}
