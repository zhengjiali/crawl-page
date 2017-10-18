package com.risk.auto.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
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
	 * @param int id  [0:未选中]
	 */
	public void searchByStatus(int id){
		this.status.findElement(By.xpath("//*[@name='status']/option["+id+"]")).click();
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
	 * 点击ele，页面跳转返回true，不跳转返回false，并截图
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
		else{
			this.closeDialog();
			new WebDriverWait(driver,2).until(ExpectedConditions.invisibilityOfElementWithText(By.linkText("系统提示"), "系统提示"));
		}
		return false;
	}
	
	/**
	 * 获取ele元素下的所有标签为tag的元素
	 * @param ele:WebElement 
	 * @param tag:String tagName
	 * @return Arraylist<WebElement>
	 */
	public ArrayList<WebElement> getElements(WebElement ele,String tag){
		Assert.assertNotNull(ele);
		ArrayList<WebElement> elements=(ArrayList<WebElement>) ele.findElements(By.tagName(tag));
		if(elements.isEmpty())
			return null;
		return elements;
	}
	
	/**
	 * 获取status选择框下所有的可选标签
	 * @return
	 */
	public ArrayList<WebElement> getStatus(){
		return this.getElements(this.status, "option");
	}
	
	public String getNameEn(int line){
		return this.items.findElement(By.xpath("//tr["+line+"]/td[3]")).getText();
	}
	
	
	/**
	 * 遍历ele元素下所有的标签,并截图
	 * @param driver
	 * @param element WebElement
	 * @param tgName 遍历的标签
	 * @param root_path 存储截图的目录
	 * @param kw 截图名称
	 * @return
	 */
	private void traverse(WebDriver driver,WebElement element,String tgName,String root_path,String kw){
		try{
			ArrayList<WebElement> tools = this.getElements(element,tgName);
			int len=tools.size();
			if(len == 0)
				return;
			for(int j=0;j<len;j+=1){
				tools = this.getElements(element,tgName);
				WebElement e = new WebDriverWait(driver,3).until(ExpectedConditions.elementToBeClickable(tools.get(j)));
				if(e.getAttribute("class").contains("disabled"))
					continue;
				String tool=e.getText();
				util.log(tool);
				if(this.executeCmd(driver,e , root_path, kw+tool)){
					util.log("Jumped...");
					driver.navigate().back();
				}
			}
		}catch(Error e){
			util.log("Error....");
			return;
		}catch(StaleElementReferenceException e){
			util.log("StaleElementReferenceException...");
			return;
		}finally{}
	}
	
	public WebElement getItem(int line){
		if(this.isExist(this.items))
			return this.items.findElement(By.xpath("//tbody/tr['"+line+"']"));
		else return null;
	}
	
	/**
	 * 遍历第line行的item下所有的操作
	 * @param driver
	 * @param root_path
	 * @param status 已预置|已启用|已停用
	 * @param line 1,2,3...
	 * @param kw 图片名称
	 * @return
	 */
	public void traverseItemTools(WebDriver driver,String root_path,int id,int line,String kw){
		this.searchByStatus(id);
		try{
			Assert.assertNotNull(this.getItem(line));
			ArrayList<WebElement> tools = this.getElements(this.getItem(line),"a");
			Assert.assertNotNull(tools);
			int len=tools.size();
			for(int i=0;i<len;i+=1){
				util.log(i);
				tools = this.getElements(this.getItem(line),"a");
				WebElement e = new WebDriverWait(driver,3).until(ExpectedConditions.elementToBeClickable(tools.get(i)));
				if(e.getAttribute("class").contains("disabled"))
					continue;
				String tool=e.getText();
				util.log(tool);
				if(this.executeCmd(driver,e , root_path, kw+'-'+tool)){
					util.log("Jumped...");
					driver.navigate().back();
				}
				
			}
		}catch(Error e){
			util.log("Error....");
			return;
		}catch(StaleElementReferenceException e){
			util.log("StaleElementReferenceException...");
			return;
		}catch(NoSuchElementException e){
			util.log("NoSuchElementException...");
			return;
		}finally{}
	}

	
	/**
	 * 遍历mainTools
	 */
	public void traverseMainTools(WebDriver driver,String root_path,String kw){
		
		try{
			ArrayList<WebElement> tools = this.getElements(this.mainTools,"a");
			int len=tools.size();
			if(len == 0)
				return;
			for(int j=0;j<len;j+=1){
				tools = this.getElements(this.mainTools,"a");
				WebElement e = new WebDriverWait(driver,3).until(ExpectedConditions.elementToBeClickable(tools.get(j)));
				if(e.getAttribute("class").contains("disabled"))
					continue;
				String tool=e.getText();
				util.log(tool);
				if(this.executeCmd(driver,e , root_path, kw+"-批量"+tool)){
					util.log("Jumped...");
					driver.navigate().back();
				}
			}
		}catch(Error e){
			util.log("Error....");
			return;
		}catch(StaleElementReferenceException e){
			util.log("StaleElementReferenceException...");
			return;
		}catch(NoSuchElementException e){
			util.log("NoSuchElementException...");
			util.log(e.getMessage());
			return;
		}finally{}

		
	}
	
	/*public void traverseStatus(WebDriver driver,String root_path){
		ArrayList<WebElement> status=this.getElements(this.status, "option");
		for()
	}*/
}
