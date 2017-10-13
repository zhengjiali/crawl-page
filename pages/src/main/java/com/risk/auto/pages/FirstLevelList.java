package com.risk.auto.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FirstLevelList{
	
	@FindBy(id="lastUpdateTimeStart")
	private WebElement lastUpdateTimeStart;
	
	@FindBy(id="lastUpdateTimeEnd")
	private WebElement lastUpdateTimeEnd;
	
	@FindBy(name="status")
	private WebElement status;
	
	@FindBy(name="createType")
	private WebElement createType;
		
	@FindBy(className="search_btn")
	private WebElement searchBtn;
	
	@FindBy(name="nameCn")
	private WebElement nameCn;
	
	@FindBy(name="nameEn")
	private WebElement nameEn;
	
	@FindBy(id="time_sort")
	private WebElement sortBtn;
	
	@FindBy(id="submit_all")
	private WebElement bulkSubmit;
	
	@FindBy(id="retract_all")
	private WebElement bulkRetract;
	
	@FindBy(id="delete_all")
	private WebElement bulkDelete;
	
	@FindBy(className="btn_create")
	private WebElement createBtn;
	
	@FindBy(tagName="tbody")
	private WebElement items;
	@FindBy(className="dialog")
	private WebElement dialog;
	@FindBy(className="dialog_bd")
	private WebElement confirmDialog;
	@FindBy(className="dialog_tip_sucess")
	private WebElement sucessTip;
	@FindBy(className="dialog_tip_fail")
	private WebElement failTip;
	@FindBy(className="dialog_close")
	private WebElement closeDialog;
	
	@FindBy(className="notedssss")
	private WebElement test;
	
	private Boolean isExist(WebElement ele){
		try{
			ele.getTagName();
			return true;
		}catch(Exception e){
			return false;
		}finally{}
	}
	
	/*public void test(){
		if(this.isExist(this.sucessTip))
			util.log("exist");
		else
			util.log("nooooo");

	}*/
	
	public void closeDialog(){
		this.closeDialog.click();
	}

	private WebElement certainDialog(){
		return this.confirmDialog.findElement(By.xpath("//div[2]/button[2]"));
	}
	static int i=0;
	/**
	 * When submit a confirmDialog,it will display a tipDialog:
	 * if confirmDialog:click submitBtn;
	 * if tipDialog:return the result;
	 * @return if displayFailed:
	 * 				return false
	 */
	public Boolean submitDialog(WebDriver driver,String root_path){
		i=i+1;
		if(this.isExist(this.confirmDialog)){
			util.screenShot(driver, root_path, "diag-click"+i);
			new WebDriverWait(driver,2).until(ExpectedConditions.elementToBeClickable(this.certainDialog())).click();;
			if(this.isExist(this.dialog)){
				this.submitDialog(driver,root_path);
				}
		}else if(this.isExist(this.sucessTip)){
			util.screenShot(driver, root_path, "diag-succ"+i);
			this.closeDialog();
			return true;
		}else if(this.isExist(this.failTip)){
			util.screenShot(driver, root_path, "diag-fail"+i);
			this.closeDialog();
			return false;
		} 
		return true;	
	}
	
	public void cancelDialog(){
		this.dialog.findElement(By.xpath("//div[2]/button[1]")).click();
	}
	
	/** 
	 * 
	 *  @param line of the item(1,2,3...10)
	 *  @return The item's English Name.
	 */
	public String getNameEn(int line){
		return this.items.findElement(By.xpath("//tr["+line+"]/td[2]/div[1]")).getText();
	}
	
	/**
	 * 
	 * @param line of the item(1,2,3...10)
	 * @return The item's Chinese Name.
	 */
	public String getNameCn(int line){
		return this.items.findElement(By.xpath("//tr["+line+"]/td[3]/div[1]")).getText();
	}
	
	/**
	 * 
	 * @param line of the item(1,2,3...10)
	 * @return The dataType of the item.
	 */
	public String getdataType(int line){
		return this.items.findElement(By.xpath("//tr["+line+"]/td[4]")).getText();
	}
	
	/**
	 * 
	 * @param line of the item(1,2,3...10)
	 * @return The zones of the item.
	 */
	public String getzones(int line){
		return this.items.findElement(By.xpath("//tr["+line+"]/td[5]/div[1]")).getText();
	}
	
	/**
	 * 
	 * @param line of the item(1,2,3...10)
	 * @return The createType of the item.
	 */
	public String getCreateType(int line){
		return this.items.findElement(By.xpath("//tr["+line+"]/td[6]")).getText();
	}
	
	/**
	 * 
	 * @param line of the item(1,2,3...10)
	 * @return The lastUpdateTime of the item.
	 */
	public String getUpdateTime(int line){
		return this.items.findElement(By.xpath("//tr["+line+"]/td[7]")).getText();
	}
	
	/**
	 * 
	 * @param line of the item(1,2,3...10)
	 * @return The status of the item.
	 */
	public String getStatus(int line){
		return this.items.findElement(By.xpath("//tr["+line+"]/td[9]")).getText();
	}
	
	private WebElement opButtn(int line,String op){
		util.log("+++++In opButtn");
		int i=0;
		if(op == "测试")
			i=1;
		else if (op == "撤回"|| op == "提交" || op == "应用")
			i=2;
		else if(op == "编辑")
			i=3;
		else if(op == "删除")
			i=4;
		if(this.isExist(this.items))
			return this.items.findElement(By.xpath("//tr["+line+"]/td[10]/a["+i+"]"));
		else
			return null;
	}
	/**
	 * @param currentDriver
	 * @param line of the item(1,2,3...10)
	 * @param op One operation(测试|应用,撤回,提交|编辑|删除） of item
	 * @return If currentPageJumped and displaySucceed:
	 * 				return true;
	 * 		   if displayFaild:
	 * 				return false;	
	 */
	public Boolean opItem(WebDriver driver,int line,String op,String path){
		String title1 = driver.getTitle();
		WebElement btn=this.opButtn(line, op);
		if(btn==null)
			return false;
		btn.click();
		util.screenShot(driver, path, "AfterClick");
		if(!driver.getTitle().equals(title1)){
			return true;
			}
		else if(this.submitDialog(driver,path)){
			return true;
		}
		else return false;
	}
	
	
	/**
	 * Select the Item for bulk operation
	 * @param line of the item(1,2,3...10)
	 */
	public void selectItem(int line){
		this.items.findElement(By.xpath("//tr["+line+"]/td[1]/input")).click();
	}
	
	public void createDate(){
		this.createBtn.click();
	}
	
	public void searchByNameCn(String s){
		this.nameCn.sendKeys(s);
		this.searchBtn.click();
	}
	public void searchByNameEn(String s){
		this.nameEn.sendKeys(s);
		this.searchBtn.click();
	}
	/**
	 * 
	 * @param s 可编辑|已提交|使用中
	 */
	public void searchByStatus(String s){
		Integer id = 0;
		if(s.equals("可编辑"))
			id=1;
		else if(s.equals("已提交"))
			id=2;
			else if(s.equals("使用中"))
				id=3;
		this.status.findElement(By.xpath("//option[@value='"+id+"']")).click();
		this.searchBtn.click();
	}
	
	public void searchByCreateType(String s){
		Integer id = 0;
		if(s == "点选")
			id=1;
		else if(s == "java")
			id=2;
		else if(s == "sql")
			id=3;
		this.createType.findElement(By.xpath("//option[@value='"+id+"']")).click();
		this.searchBtn.click();
	}
	
	public void searchByTimeStart(String s){
		this.lastUpdateTimeStart.sendKeys(s);
		this.searchBtn.click();
	}
	
	public void searchByTimeEnd(String s){
		this.lastUpdateTimeEnd.clear();
		this.lastUpdateTimeEnd.sendKeys(s);
		this.searchBtn.click();
	}
	
	public void bulkOpSubmit(){
		this.bulkSubmit.click();
	}
	public void bulkOpDelete(){
		this.bulkDelete.click();
	}
	public void bulkOpRetract(){
		this.bulkRetract.click();
	}
}
