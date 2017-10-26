package com.risk.auto.pages;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import com.risk.auto.pages.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class RemoteDriver {
	
	WebDriver driver;
	
	@Parameters("type")
	@BeforeClass
	public void setUp(String type) throws IOException{
		if(type.equals("firefox"))
			driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"),DesiredCapabilities.firefox());
		else if(type.equals("chrome"))
			driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"),DesiredCapabilities.chrome());
		driver.get("http://www.baidu.com");
		driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
	}	
	
	@Test
	public void test1(){
		LoginPage loginPage=PageFactory.initElements(driver, LoginPage.class);
		loginPage.login("admin1", "123456", false);
		util.screenShot(driver, "/Users/zhengjiali/", "login1026");
	}
	
}
