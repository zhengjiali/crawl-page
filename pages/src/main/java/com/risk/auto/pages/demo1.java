package com.risk.auto.pages;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class demo1 {
	WebDriver driver;
	String uname="tester";
	String pwd="123456";
	
	@Test
	public void testLogin() throws IOException{
		System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);
		login(uname,pwd);
	}
	
	public void login(String uname,String pwd) throws IOException{
		try{
			driver.get("https://xueqiu.com/");
			driver.findElement(By.className("nav__login__btn")).click();
			driver.findElement(By.xpath("//*[@id='app']/div[4]/div[1]/div[2]/div[1]/div[1]/div[2]/form/div[1]/input")).sendKeys(uname);
			driver.findElement(By.xpath("//*[@id='app']/div[4]/div[1]/div[2]/div[1]/div[1]/div[2]/form/div[2]/input")).sendKeys(pwd);
			driver.findElement(By.className("modal__login__btn")).click();	
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File("../login.jpg"));
		}
	}
	
	@AfterClass
	public void tearDown(){
		driver.close();
	}

}
