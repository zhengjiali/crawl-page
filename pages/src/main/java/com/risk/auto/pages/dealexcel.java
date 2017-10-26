package com.risk.auto.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import java.util.Date;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

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
	
	@Parameters("type")
	@BeforeClass
	public void setUp(String type) throws IOException{
		if(type.equals("firefox"))
			driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"),DesiredCapabilities.firefox());
		else if(type.equals("chrome"))
			driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"),DesiredCapabilities.chrome());
		driver.get(uri+"/risk/trics/login");
		driver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);
		new_dir(root_path);
	}
	
	@Parameters({"username","password"})
	@BeforeMethod
	public void login(String username,String password) throws IOException{
		WebElement ElementName = driver.findElement(By.id("user_name"));
		WebElement ElementPwd = driver.findElement(By.id("pwd"));
		ElementName.sendKeys(username);
		ElementPwd.sendKeys(password);
		WebElement ElementLogin = driver.findElement(By.className("submit_btn"));
		ElementLogin.click();
		System.out.println(driver.getTitle());
		new WebDriverWait(driver, 5 ).until(
				ExpectedConditions.presenceOfElementLocated(By.cssSelector("body > div.container > div.clearfix.header > a"))
		);
		currPath = root_path+"/"+username;
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
		dir = dir+"/"+kw;
		driver.get(url);
		driver.manage().window().setSize(new Dimension(1320,800));
		Assert.assertEquals(driver.getTitle(), title);
		util.screenShot(driver, dir, "home");
		RiskFirstList firstlist=PageFactory.initElements(driver, RiskFirstList.class);
		firstlist.traverseMainTools(driver,dir,title);
		ArrayList<WebElement> elements=firstlist.getStatus();
		int l=elements.size();
		for(int i=1;i<l;i+=1){
			String pre= firstlist.getStatus().get(i).getText();
			util.screenShot(driver, dir, title+pre);
			firstlist.traverseItemTools(driver, dir, i+1, 1, title+pre);
		}
		
		
	}
	
	public void clickCreate(String dir,String kw){
		try{
			driver.findElement(By.className("btn_create")).click();
			util.screenShot(driver, dir, kw+"_click_Create");
			driver.navigate().back();
		}catch(NoSuchElementException e){
			return;
		}
	}

	public void clickMainTools(String dir,String kw,String title){
		try{
			WebElement content = driver.findElement(By.className("content"));
			WebElement mainTools = content.findElement(By.className("main_tools"));
		    List<WebElement> tools = mainTools.findElements(By.tagName("a"));
			int n = tools.size();
			for(int i = 0;i < n;i++){
				tools.get(i).click();
				File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(screenshot, new File(dir+"/"+kw+(i+1)+".jpg"));
				try{
					WebElement tips = driver.findElement(By.className("dialog_hd"));
					driver.findElement(By.className("dialog_close")).click();
					new WebDriverWait(driver,4).until(ExpectedConditions.invisibilityOf(tips));
				}catch(Exception e){
					if(driver.getTitle() != title)
						driver.navigate().back();
				}
				}
		}catch(Exception e){
			return;
		}
	}
	
	public void clickItemTools(String dir,String kw,String title){
		try{
			WebElement content = driver.findElement(By.xpath("//*[@id='list']/table/tbody"));
			List<WebElement> items = content.findElements(By.tagName("tr"));
			List<WebElement> tools;
			int n;
			for(int j = 0;j < 3;j++){
//				System.out.println("First Loop ------->"+j);
				tools = items.get(j).findElements(By.tagName("a"));
				n = tools.size();
				for(int i = 0;i < n;i++){
//					System.out.println("Second Loop ------->"+i);
					if(tools.get(i).getAttribute("href") == "#copy")
						continue;
					tools.get(i).click();
					File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
					FileUtils.copyFile(screenshot, new File(dir+"/"+kw+(j+1)+(i+1)+".jpg"));
					try{
						WebElement tips = driver.findElement(By.className("dialog_hd"));
						driver.findElement(By.className("dialog_close")).click();
						new WebDriverWait(driver,4).until(ExpectedConditions.invisibilityOf(tips));
					}catch(Exception e){
						if(driver.getTitle() != title)
							driver.navigate().back();
						new WebDriverWait(driver,4).until(ExpectedConditions.titleIs(title));
						content = driver.findElement(By.xpath("//*[@id='list']/table/tbody"));
						items = content.findElements(By.tagName("tr"));
						tools = items.get(j).findElements(By.tagName("a"));
						n = tools.size();
					}
					}
			}			
		}catch(Exception e){
			return;
		}
	}
		

}
