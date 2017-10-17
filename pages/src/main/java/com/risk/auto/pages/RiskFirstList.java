package com.risk.auto.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;

/**
 * 风控端一级列表页
 * @author zhengjiali
 *
 */
public class RiskFirstList {
	
	/*搜索部分*/
	@FindBy(name="productLine")
	private WebElement productLine;
	@FindBy(name="status")
	private WebElement status;
	@FindBy(name="moduleNo")
	private WebElement moduleNo;
	@FindBy(name="nameCn")
	private WebElement nameCn;
	@FindBy(name="version")
	private WebElement version;
	@FindBy(xpath="//button[@type='submit']")
	private WebElement searchBtn;
	
	/*主要操作栏*/
	@FindBy(className="main_tools")
	private WebElement mainTools;
	
	@FindBy(xpath="//tbody")/*列表*/
	private WebElement items;

	@FindBy(linkText="系统提示")/*弹框*/
	private WebElement dialog;
	@FindBy(className="dialog_close")/*关闭弹框按钮*/
	private WebElement closeDialogBtn;
	@FindBy(id="start_all")
	private WebElement startAll;
	/**
	 * Select the Item for bulk operation
	 * @param line of the item(1,2,3...10)
	 */
	public void selectItem(int line){
		this.items.findElement(By.xpath("//tr["+line+"]/td[1]/input")).click();
	}
	
	
	public void searchByNameCn(String s){
		this.nameCn.sendKeys(s);
		this.searchBtn.click();
	}
	
	public void bulkStart(){
		this.startAll.click();
		this.closeDialog();
	}
	
	/**
	 * 搜索：状态
	 * @param s 已预置|已启用|已停用
	 */
	public void searchByStatus(String s){
		Integer id = 0;
		if(s.equals("已预置"))
			id=3;
		else if(s.equals("已启用"))
			id=4;
			else if(s.equals("已停用"))
				id=5;
		this.status.findElement(By.xpath("//option[@value='"+id+"']")).click();
		this.searchBtn.click();
	}
	
	private Boolean isExist(WebElement ele){
		try{
			ele.getTagName();
			util.log("exist true");
			return true;
		}catch(Exception e){
			util.log("exist false");
			return false;
		}finally{}
	}
	
	public void closeDialog(){
		util.log("close dialog");
		this.closeDialogBtn.click();
	}
	/**
	 * 点击ele，截图存储
	 * @param driver
	 * @param ele 操作元素
	 * @param root_path 截图存储的路径
	 * @param kw 截图存储的名称
	 */
	public Boolean executeCmd(WebDriver driver,WebElement ele,String root_path,String kw){
		util.log("execute cmd...");
		String oTitle=driver.getTitle();
		ele.click();
		util.screenShot(driver, root_path,kw);
		if(!driver.getTitle().equals(oTitle)){
			util.log("title is not the same");
			return true;
		}
		else
			this.closeDialog();
		return false;
	}
	/**
	 * 获取ele元素下的所有a标签元素
	 * @param ele
	 * @return
	 */
	public ArrayList<WebElement> getTools(WebElement ele){
		Assert.assertNotNull(ele);
		ArrayList<WebElement> elements=(ArrayList<WebElement>) ele.findElements(By.tagName("a"));
		if(elements.isEmpty())
			return null;
		return elements;
	}
	
	public String getNameEn(int line){
		return this.items.findElement(By.xpath("//tr["+line+"]/td[3]")).getText();
	}
	
	public void getAll(){
		util.log(this.items.getTagName());
		util.log(this.items.getText());
		util.log(this.items.toString());
		WebElement ele=this.items.findElement(By.xpath("//tr[1]"));
		util.log("...____");
		util.log(ele.getText());
		util.log(getNameEn(1));
		
//		util.log(getTools(ele).toString());
	}
	
	/**
	 * 遍历ele元素下所有的a标签,并截图
	 * @param driver
	 * @param root_path 存储截图的目录
	 * @param ele WebElement
	 * @return
	 */
	private Boolean traverse(WebDriver driver,String root_path,WebElement ele){
		ArrayList<WebElement> tools = getTools(ele);
		Assert.assertNotNull(tools);
		String url = driver.getCurrentUrl();
		int length=tools.size();
		
		for(int i = 0;i<length;i++){
			WebElement e=tools.get(i);
			this.executeCmd(driver, e, root_path, "cmd"+i);
			driver.get(url);
		}
		return true;
	} 
	
	private WebElement getItem(int line){
		if(this.isExist(this.items))
			return this.items.findElement(By.xpath("//tr["+line+"]"));
		else return null;
	}
	
	static int numItemTools=0;
	/**
	 * 遍历第line行的item下所有的操作
	 * @param driver
	 * @param root_path
	 * @param status 已预置|已启用|已停用
	 * @param line 1,2,3...
	 * @return
	 */
	public void traverseItemTools(WebDriver driver,String root_path,String status,int line){
		this.searchByStatus(status);
		Assert.assertNotNull(this.getItem(line));
		ArrayList<WebElement> tools = this.getTools(this.getItem(line));
		Assert.assertNotNull(tools);
		int len=tools.size();
		util.log("Item tools "+len);
		for(;this.numItemTools<len;){
			WebElement e = new WebDriverWait(driver,5).until(ExpectedConditions.elementToBeClickable(tools.get(this.numItemTools)));
			if(this.executeCmd(driver,e , root_path, "ItemTools-"+this.numItemTools)){
				util.log("Jumped...");
				driver.navigate().back();
			}
			driver.navigate().refresh();
			this.numItemTools+=1;
			this.traverseItemTools(driver, root_path, status, line);
		}
		numItemTools=0;
	}

	
	/**
	 * 遍历mainTools
	 */
	public void traverseMainTools(WebDriver driver,String root_path){
		String url = driver.getCurrentUrl();
		ArrayList<WebElement> tools = this.getTools(this.mainTools);
		int len=tools.size();
		util.log("the tools "+len);
		for(;this.numItemTools<len;){
			WebElement e = new WebDriverWait(driver,5).until(ExpectedConditions.elementToBeClickable(tools.get(this.numItemTools)));
			if(this.executeCmd(driver,e , root_path, "MainTools-"+this.numItemTools)){
				util.log("Jumped...");
				driver.navigate().back();
			}
			driver.navigate().refresh();
			this.numItemTools+=1;
			this.traverseMainTools(driver, root_path);
		}
		numItemTools=0;
	}
}
