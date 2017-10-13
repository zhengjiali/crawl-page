package com.risk.auto.pages;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
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
	FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
	
	
	@BeforeClass
	public void setUp() throws IOException{
		String url="http://risk.bat.tcredit.com/riskData/trics/data/toDataPage?funCode=201";
		System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		util.new_dir(root_path);
		driver.manage().window().maximize();
		login();
		driver.get(url);
		String title = driver.getTitle();
	}
	
/*	@BeforeMethod
	public void initTest(){
		FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
	}
	@AfterMethod
	public void tearTest(){
	}*/
	
	public void login(){
		driver.get(uri+"/riskData/trics/login");
		LoginPage loginPage=PageFactory.initElements(driver, LoginPage.class);
		loginPage.login("admin1", "123456", false);
		new WebDriverWait(driver,4).until(ExpectedConditions.titleContains("数据"));
		util.screenShot(driver, root_path, "login");
		Assert.assertEquals(driver.getTitle(), "DB数据管理");

	}
	
	@Test(/*priority=2*/enabled=false)
	public void searchStatus(){
		driver.navigate().refresh();
		FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
		firstlistpage.searchByStatus("已提交");
		Assert.assertEquals(firstlistpage.getStatus(5), "已提交");
		util.screenShot(driver, root_path, "searchStatus");	
	}
	
	@Test(/*priority=4*/enabled=false)
	public void searchTimeStart(){
		driver.navigate().refresh();
		FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
		firstlistpage.searchByTimeStart("2017-08-08");
		util.screenShot(driver, root_path, "searchTimeStart");
	}
	
	@Test(/*priority=3*/enabled=false)
	public void searchTimeEnd(){
		driver.navigate().refresh();
		FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
		firstlistpage.searchByTimeEnd("2017-08-18");
		util.screenShot(driver, root_path, "searchTimeEnd");
	}
	
	@Test(enabled=false)
	public void bulkSubmit(){
		String s=driver.getTitle();
		driver.navigate().refresh();
		if(driver.getTitle().equals(s))
			util.log("相等");
		else
			util.log("相等");
		Assert.assertEquals(driver.getTitle(), s);
		/*
		FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
		firstlistpage.selectItem(1);
		firstlistpage.selectItem(2);
		firstlistpage.selectItem(3);
		firstlistpage.selectItem(4);
		firstlistpage.selectItem(2);
		firstlistpage.bulkOpSubmit();
		util.screenShot(driver, root_path, "selectItem");*/
	}
	
	@Test(priority=1)
	public void submitItem(){
		FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
		driver.navigate().refresh();
		firstlistpage.searchByStatus("可编辑");
		String name = firstlistpage.getNameEn(1);
		util.log(name);
		util.screenShot(driver, root_path, "submit-step1");
		if(firstlistpage.opItem(driver,1, "提交",root_path)){
			util.screenShot(driver, root_path, "submit-step2");
			driver.navigate().refresh();
			util.log("The operation name is:"+name);
			util.log("The result is:"+firstlistpage.getNameEn(1));
			Assert.assertEquals(firstlistpage.getNameEn(1), name);
			util.screenShot(driver, root_path, "submit-step3");;
		}
		else{
			util.screenShot(driver, root_path, "submit-err");
			Assert.fail();
		}
	}

	
	@AfterClass
	public void tearDown(){
		driver.close();
	}
	
}
