package com.risk.auto.pages;

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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import org.testng.Assert;
import java.io.File;
import java.io.IOException;



public class crawl {
	
	WebDriver driver;
	String base_url="http://risk.bat.tcredit.com/risk/trics/login";
	
	@BeforeTest
	public void setUp() throws IOException{
		
		System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(base_url);
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
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshot, new File("/Users/tcxy/Documents/workspace/auto_page/pages/src/pic/planrun.jpg"));
//		Assert.assertEquals(driver.getTitle(), "方案执行");
		
	}
	
	@Test
	public void plan() throws IOException{
		try{
			driver.get("http://risk.bat.tcredit.com/risk/trics/plan/toQuery");
			Assert.assertEquals(driver.getTitle(), "方案配置");
		}finally{
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File("/Users/tcxy/Documents/workspace/auto_page/pages/src/pic/plan.jpg"));
		}
	}
	
	@Test
	public void ruleGroup() throws IOException{
		try{
			driver.get("http://risk.bat.tcredit.com/risk/trics/ruleGroup/toQuery");
			Assert.assertEquals(driver.getTitle(), "规则集管理");
		}finally{
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File("/Users/tcxy/Documents/workspace/auto_page/pages/src/pic/ruleGroup.jpg"));
		}
	}
	
	
	@AfterTest
	public void tearDown(){
		driver.close();
	}
}
