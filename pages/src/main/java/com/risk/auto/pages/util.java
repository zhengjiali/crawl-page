package com.risk.auto.pages;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class util {
	
	static public Boolean debug = true; 
	public static void new_dir(String dir){
		File file = new File(dir);
		if( !file.exists() && !file.isDirectory()){
			file.mkdir();
		}
	}
	public static void log(boolean b){
		if(debug)
			System.out.println(b);
	}
	public static void log(String s) {
		// TODO Auto-generated method stub
		if(debug)
			System.out.println(s);
	}
	
	public static void screenShot(WebDriver driver,String root_path,String kw){
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenshot, new File(root_path+"/"+kw+".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
	}
	
}
