package com.risk.auto.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import org.testng.Assert;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class dealexcel {
	WebDriver driver;
	String uri="http://risk.bat.tcredit.com";
	Date date = new Date();
	DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	String time = format.format(date);
	String root_path = "../pic/"+time;
	String currPath;
	File file;
	ArrayList<HashMap<String, String>> pages;
	
	
	@BeforeClass
	public void setUp() throws IOException{
		System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
		driver = new ChromeDriver();
		driver.get(uri+"/risk/trics/login");
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		new_dir(root_path);
	}
	
	@Parameters({"admin1"})
	@BeforeMethod
	public void login(String admin1) throws IOException{
		WebElement ElementName = driver.findElement(By.id("user_name"));
		WebElement ElementPwd = driver.findElement(By.id("pwd"));
		ElementName.sendKeys("admin1");
		ElementPwd.sendKeys(admin1);
		WebElement ElementLogin = driver.findElement(By.className("submit_btn"));
		ElementLogin.click();
		System.out.println(driver.getTitle());
		new WebDriverWait(driver, 15 ).until(
				ExpectedConditions.presenceOfElementLocated(By.cssSelector("body > div.container > div.clearfix.header > a"))
		);
		currPath = root_path+"/"+"admin1";
		new_dir(currPath);
	}
	
	
	@Test
	public void getPages() throws InvalidFormatException, IOException{
		InputStream inp = new FileInputStream("../pages.xlsx");
		Workbook wb = WorkbookFactory.create(inp);
	    Sheet pages = wb.getSheetAt(1);
	    int lastRowNum = pages.getLastRowNum();
	    for(int i = 1;i<=lastRowNum;i++){
	    	Row row = pages.getRow(i);
	    	try{
	    		getPage(uri+row.getCell(2).getStringCellValue(),currPath,row.getCell(1).getStringCellValue(),row.getCell(3).getStringCellValue());
	    	}catch(Error e){
	    		row.createCell(4).setCellValue("Fail");
	    		continue;
	    	}
	    	row.createCell(4).setCellValue("Pass");
	    }
	    FileOutputStream out = null;
	    try{
	    	out = new FileOutputStream("../pages.xlsx");
	    	wb.write(out);
	    }catch(IOException e){
	    	e.printStackTrace();
	    }finally{
	    	try{
	    		out.close();
	    	}catch(IOException e){
	    		e.printStackTrace();
	    	}
	    }
	}
	
	@AfterClass
	public void tearDown(){
		driver.close();
	}
	
	public void new_dir(String dir){
		File file = new File(dir);
		if( !file.exists() && !file.isDirectory()){
			file.mkdir();
		}
	}
	
	public void getPage(String url,String dir,String kw,String title){
		try{
			driver.get(url);
			driver.manage().window().maximize();
			Assert.assertEquals(driver.getTitle(), title);
		}finally{
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(screenshot, new File(dir+"/"+kw+".jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
		
		try{
			clickCreate(dir,kw);
			clickSubmitAll(dir,kw);
			clickRetractAll(dir,kw);
			clickDeleteAll(dir,kw);
		}catch(Error e){
			return;
		}
	}
	
	public void clickCreate(String dir,String kw){
		try{
			driver.findElement(By.className("btn_create")).click();
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(screenshot, new File(dir+"/"+kw+"_click_Create"+".jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			driver.navigate().back();
		}catch(NoSuchElementException e){
			return;
		}
	}
	public void clickSubmitAll(String dir,String kw){
		try{
			driver.findElement(By.id("submit_all")).click();
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(screenshot, new File(dir+"/"+kw+"_click_SubmitAll"+".jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			WebElement tips = driver.findElement(By.className("dialog_hd"));
			new WebDriverWait(driver,5).until(ExpectedConditions.invisibilityOf(tips));
		}catch(Exception e){
			return;
		}
	}
	public void clickRetractAll(String dir,String kw){
		try{
			driver.findElement(By.id("retract_all")).click();
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(screenshot, new File(dir+"/"+kw+"_click_RetractAll"+".jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			WebElement tips = driver.findElement(By.className("dialog_hd"));
			new WebDriverWait(driver,5).until(ExpectedConditions.invisibilityOf(tips));
		}catch(Exception e){
			return;
		}
	}
	public void clickDeleteAll(String dir,String kw){
		try{
			driver.findElement(By.id("delete_all")).click();
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(screenshot, new File(dir+"/"+kw+"_click_DeleteAll"+".jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			WebElement tips = driver.findElement(By.className("dialog_hd"));
			new WebDriverWait(driver,5).until(ExpectedConditions.invisibilityOf(tips));
		}catch(Exception e){
			return;
		}
	}
		

}
