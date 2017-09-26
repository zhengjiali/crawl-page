package com.risk.auto.pages;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import org.testng.Assert;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;



public class crawl {
	
	WebDriver driver;
	String uri="http://risk.bat.tcredit.com";
	Date date = new Date();
	DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	String time = format.format(date);
	String path = "../pic/"+time;
	File file;
	
	
	@BeforeClass
	public void setUp() throws IOException{
		file = new File(path);
		if( !file.exists() && !file.isDirectory()){
			file.mkdir();
		}
		System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
		driver = new ChromeDriver();
		driver.get(uri+"/risk/trics/login");
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		login();
	}
	
	public void login() throws IOException{
		WebElement ElementName = driver.findElement(By.id("user_name"));
		WebElement ElementPwd = driver.findElement(By.id("pwd"));
		ElementName.sendKeys("admin1");
		ElementPwd.sendKeys("123456");
		WebElement ElementLogin = driver.findElement(By.className("submit_btn"));
		ElementLogin.click();
		System.out.println(driver.getTitle());
		new WebDriverWait(driver, 15 ).until(
				ExpectedConditions.presenceOfElementLocated(By.cssSelector("body > div.container > div.clearfix.header > a"))
		);
		driver.manage().window().maximize();
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshot, new File(path+"/planrun.jpg"));
//		Assert.assertEquals(driver.getTitle(), "方案执行");
		
	}
	
	@Test
	public void plan() throws IOException{
		try{
			driver.get(uri+"/risk/trics/plan/toQuery");
			driver.manage().window().maximize();
			Assert.assertEquals(driver.getTitle(), "方案配置");
		}finally{
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(path+"/plan.jpg"));
		}
	}
	
	@Test
	public void rule() throws IOException{
		try{
			driver.get(uri+"/risk/trics/rule/toQuery");
			driver.manage().window().maximize();
			Assert.assertEquals(driver.getTitle(), "规则管理");
		}finally{
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(path+"/rule.jpg"));
		}
	}
	
	@Test
	public void ruleGroup() throws IOException{
		try{
			driver.get(uri+"/risk/trics/ruleGroup/toQuery");
			driver.manage().window().maximize();
			Assert.assertEquals(driver.getTitle(), "规则集管理");
		}finally{
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(path+"/ruleGroup.jpg"));
		}
	}
	
	@Test
	public void model() throws IOException{
		try{
			driver.get(uri+"/risk/trics/model/toQuery");
			driver.manage().window().maximize();
			Assert.assertEquals(driver.getTitle(), "模型管理");
		}finally{
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(path+"/model.jpg"));
		}
	}
	
	@Test
	public void rulePackage() throws IOException{
		try{
			driver.get(uri+"/risk/trics/rulePackage/toElementPackagePage?packageType=1");
			driver.manage().window().maximize();
			Assert.assertEquals(driver.getTitle(), "R包函数管理");
		}finally{
			new WebDriverWait(driver,5).until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='list']/table/tbody/tr[1]/td[2]"))
			);
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(path+"/rulePackage.jpg"));
		}
	}
	
	
	@Test
	public void definedVariable() throws IOException{
		try{
			driver.get(uri+"/risk/trics/definedVariable/toDataDefinePage");
			driver.manage().window().maximize();
			Assert.assertEquals(driver.getTitle(), "自定义数据管理");
		}finally{
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(path+"/definedVariable.jpg"));
		}
	}
	
	@Test
	public void undefinedVariable() throws IOException{
		try{
			driver.get(uri+"/risk/trics/undefinedVariable/toDataUndefinePage");
			driver.manage().window().maximize();
			Assert.assertEquals(driver.getTitle(), "非自定义数据管理");
		}finally{
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(path+"/undefinedVariable.jpg"));
		}
	}
	
	@Test
	public void paramVariable() throws IOException{
		try{
			driver.get(uri+"/risk/trics/paramVariable/toDataParamPage");
			driver.manage().window().maximize();
			Assert.assertEquals(driver.getTitle(), "数据调取参数管理");
		}finally{
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(path+"/paramVariable.jpg"));
		}
	}
	
	
	
	@AfterClass
	public void tearDown(){
		driver.close();
	}
}
