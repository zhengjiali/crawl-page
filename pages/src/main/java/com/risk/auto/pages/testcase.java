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
import org.openqa.selenium.Dimension;
import com.risk.auto.pages.util;


public class testcase {
	
	static WebDriver driver;
	String uri="http://risk.bat.tcredit.com";
	Date date = new Date();
	DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	String time = format.format(date);
	String root = "../pic/"+time;
	ArrayList<HashMap<String, String>> pages;
	FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
	
	public void login(){
		driver.get(uri+"/riskData/trics/login");
//		driver.get(uri+"/risk/trics/login");
		LoginPage loginPage=PageFactory.initElements(driver, LoginPage.class);
		loginPage.login("admin1", "123456", false);
		new WebDriverWait(driver,4).until(ExpectedConditions.titleContains(""));
		util.screenShot(driver, root, "login");
//		Assert.assertEquals(driver.getTitle(), "DB数据管理");

	}
	
	@Test(groups="testFirstLevelList")
	public class FirstLevelListTest{
		private String path_b="/riskData/trics/data/toDataPage?funCode=201";
		private String root_path = root+"/fistlevellist";
		@BeforeClass
		public void setUp() throws IOException{
			System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			util.new_dir(root_path);
//			driver.manage().window().maximize();
			driver.manage().window().setSize(new Dimension(1420,800));
			login();
			driver.get(uri+path_b);
		}
		/*@Test
		public void bulkStart(){
			driver.get("http://risk.bat.tcredit.com/risk/trics/plan/toQuery");
			String title=driver.getTitle();
			RiskFirstList firstlist = PageFactory.initElements(driver, RiskFirstList.class);
			ArrayList<WebElement> elements=firstlist.getStatus();
			int l=elements.size();
			for(int i=1;i<l;i+=1){
				String pre= firstlist.getStatus().get(i).getText();
				util.log(pre);
				firstlist.searchByStatus(i+1);
				util.screenShot(driver, root_path, "xxxx"+i);
//				firstlist.traverseItemTools(driver, root_path, i, 1, title+pre);
			}
//			firstlist.traverseMainTools(driver,root_path);
//			firstlist.traverseItemTools(driver, root_path+"/已预置", "已预置", 1,"http://risk.bat.tcredit.com/risk/trics/planRun/toQuery");
//			firstlist.traverseItemTools(driver, root_path+"/已启用", "已启用", 1,"http://risk.bat.tcredit.com/risk/trics/planRun/toQuery");
		}*/
		
			
		@Test(priority=2)
		public void searchStatus(){
			driver.get(uri+path_b);
			driver.navigate().refresh();
			FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
			firstlistpage.searchByStatus("已提交");
			Assert.assertEquals(firstlistpage.getStatus(5), "已提交");
			util.screenShot(driver, root_path, "searchStatus");	
		}
		
		@Test(priority=4)
		public void searchTimeStart(){
			driver.get(uri+path_b);
			FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
			firstlistpage.searchByTimeStart("2017-08-08");
			util.screenShot(driver, root_path, "searchTimeStart");
		}
		
		@Test(priority=3)
		public void searchTimeEnd(){
			driver.get(uri+path_b);
			FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
			firstlistpage.searchByTimeEnd("2017-08-18");
			util.screenShot(driver, root_path, "searchTimeEnd");
		}
		
		@Test(priority=5)
		public void bulkSubmit(){
			driver.get(uri+path_b);
			FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
			firstlistpage.selectItem(1);
			firstlistpage.selectItem(2);
			firstlistpage.selectItem(3);
			firstlistpage.selectItem(4);
			firstlistpage.selectItem(2);
			firstlistpage.submitAll(driver,root_path);
		}
		
		@Test(priority=6)
		public void bulkSubmitNull(){
			driver.get(uri+path_b);
			FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
			firstlistpage.submitAll(driver, root_path);
		}
		
		@Test(priority=7)
		public void bulkDelete(){
			driver.get(uri+path_b);
			FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
			firstlistpage.selectItem(1);
			firstlistpage.selectItem(2);
			firstlistpage.selectItem(3);
			firstlistpage.selectItem(4);
			firstlistpage.selectItem(2);
			firstlistpage.deleteAll(driver, root_path);
		}
		
		@Test(priority=1)
		public void submitItem(){
			driver.get(uri+path_b);
			FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
			firstlistpage.searchByStatus("可编辑");
			String name = firstlistpage.getNameEn(1);
			util.log(name);
			util.screenShot(driver, root_path, "submit-step1");
			if(firstlistpage.opItem(driver,1, "提交",root_path)){
				util.screenShot(driver, root_path, "submit-step2");
				driver.navigate().refresh();
				Assert.assertEquals(firstlistpage.getNameEn(1), name);
				util.screenShot(driver, root_path, "submit-step3");
			}
			else{
				util.screenShot(driver, root_path, "submit-err");
				Assert.fail();
			}
		}
		
		@Test(priority=1)
		public void submitAll(){
			driver.get(uri+path_b);
			FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
			firstlistpage.submitAll(driver, root_path);
		}
		
		

		@Test(priority=8)
		public void testItem(){
			driver.get(uri+"/riskData/trics/data/toDataPage?funCode=101");
			FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
			firstlistpage.searchByStatus("可编辑");
			util.screenShot(driver, root_path, "submit-step1");
			if(firstlistpage.opItem(driver,1, "测试",root_path)){
				util.screenShot(driver, root_path, "submit-step2");
				Assert.assertTrue(driver.getTitle().contains("测试"));
			}
			else{
				util.screenShot(driver, root_path, "submit-err");
				Assert.fail();
			}
		}
		
		@Test(priority=1)
		public void editItem_submited(){
			driver.get(uri+"/riskData/trics/data/toDataPage?funCode=101");
			FirstLevelList firstlistpage=PageFactory.initElements(driver, FirstLevelList.class);
			firstlistpage.searchByStatus("已提交");
			String name = firstlistpage.getNameEn(1);
			util.log(name);
			if(firstlistpage.opItem(driver,1, "编辑",root_path)){
				firstlistpage.editAttrScope("abcd");
				firstlistpage.editAttrSubmit(driver,root_path);
				driver.navigate().refresh();
				Assert.assertEquals(firstlistpage.getNameEn(1), name);
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
	
	@Test(groups="testNewData")
	public class NewDataTest{
		private String path_b="/riskData/trics/code/toDataAttrPage?funCode=202";
		private String root_path = root+"/testNewData";
		@BeforeClass
		public void setUp() throws IOException{
			System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
			util.new_dir(root_path);
			driver.manage().window().maximize();
			login();
			driver.get(uri+path_b);
		}
		@AfterClass
		public void tearDown(){
			driver.close();
		}
		
		@Test(priority=1)
		public void submitNull(){
			NewData newdata = PageFactory.initElements(driver, NewData.class);
			Assert.assertFalse(newdata.createData(driver, root_path));
		}
		
		@Test(priority=2)
		public void checkDuplicateEnName(){
			NewData newdata = PageFactory.initElements(driver, NewData.class);
			newdata.setNameEn("abc");
			newdata.clickBlank();
			Assert.assertEquals(newdata.getNameEnErr(), "名字重复");
		}
		@Test(priority=3)
		public void wrongFormatEnName1(){
			NewData newdata = PageFactory.initElements(driver, NewData.class);
			newdata.setNameEn("abc中文");
			newdata.clickBlank();
			Assert.assertEquals(newdata.getNameEnErr(), "须以英文字母开头，且只能包含英文字母、数字或下划线");
		}
		@Test(priority=3)
		public void wrongFormatEnName2(){
			NewData newdata = PageFactory.initElements(driver, NewData.class);
			newdata.setNameEn("zoey ");
			newdata.clickBlank();
			Assert.assertEquals(newdata.getNameEnErr(), "须以英文字母开头，且只能包含英文字母、数字或下划线");
		}
		
	}
	
}
