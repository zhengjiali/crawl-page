package com.risk.auto.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
/**
 * 数据端一级列表页
 * @author zhengjiali
 *
 */
public class FirstLevelList{
	
	@FindBy(id="lastUpdateTimeStart")/*上次修改开始时间选择框*/
	private WebElement lastUpdateTimeStart;
	
	@FindBy(id="lastUpdateTimeEnd")/*上次修改截止时间选择框*/
	private WebElement lastUpdateTimeEnd;
	
	@FindBy(name="status")/*状态选择框*/
	private WebElement status;
	
	@FindBy(name="createType")/*新建方式选择框*/
	private WebElement createType;
		
	@FindBy(className="search_btn")/*搜索按钮*/
	private WebElement searchBtn;
	
	@FindBy(name="nameCn")/*中文名输入框*/
	private WebElement nameCn;
	
	@FindBy(name="nameEn")/*英文名输入框*/
	private WebElement nameEn;
	
	@FindBy(id="time_sort")/*排序*/
	private WebElement sortBtn;
	
	@FindBy(id="submit_all")/*批量提交按钮*/
	private WebElement bulkSubmit;
	
	@FindBy(id="retract_all")/*批量撤回按钮*/
	private WebElement bulkRetract;
	
	@FindBy(id="delete_all")/*批量删除按钮*/
	private WebElement bulkDelete;
	
	@FindBy(className="btn_create")/*单个创建按钮*/
	private WebElement createBtn;
	@FindBy(id="btn_create_batch")/*批量创建按钮*/
	private WebElement createBtnBatch;
	
	@FindBy(tagName="tbody")/*列表*/
	private WebElement items;
	@FindBy(partialLinkText="系统提示")/*弹框*/
	private WebElement dialog;
	@FindBy(className="dialog_confirm")/*确认弹框*/
	private WebElement confirmDialog;
	@FindBy(className="dialog_tip")/*提示弹框*/
	private WebElement tipDialog;
	@FindBy(className="dialog_tip_sucess")/*成功提示*/
	private WebElement sucessTip;
	@FindBy(className="dialog_tip_fail")/*失败提示*/
	private WebElement failTip;
	@FindBy(className="dialog_close")/*关闭弹框按钮*/
	private WebElement closeDialog;
	
	@FindBy(id="attr_form")/*数据属性编辑框*/
	private WebElement attrForm;
	@FindBy(name="scope")/*取值范围*/
	private WebElement attrScope;
	@FindBy(name="complexDef")/*数据定义*/
	private WebElement attrDefined;
	@FindBy(name="description")/*说明*/
	private WebElement attrDesc;
	@FindBy(id="submit_btn")/*确认按钮*/
	private WebElement attrSubmit;
	@FindBy(xpath="//*[@id='attr_form']/div/button[1]")/*取消按钮*/
	private WebElement attrCancel;
	
	
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
	 * @param driver
	 * 		  root_path:截屏存储位置
	 * @return if displayFailed:
	 * 				return false
	 */
	public Boolean submitDialog(WebDriver driver,String root_path){
		i=i+1;
		if(this.isExist(this.tipDialog)){
			if(this.isExist(this.sucessTip)){
				util.screenShot(driver, root_path, "diag-succ"+i);
				this.closeDialog();
				return true;
			}else{
				util.screenShot(driver, root_path, "diag-fail"+i);
				/*若此种错误提示需手动关闭，取消注释；
				 * 当前的错误提示为自动关闭。*/
//				this.closeDialog();
				return false;
			}
		}else if(this.isExist(this.confirmDialog)){
			util.screenShot(driver, root_path, "diag-click"+i);
//			WebElement certainBtn = new WebDriverWait(driver,1).until(ExpectedConditions.elementToBeClickable(this.certainDialog()));
			this.certainDialog().click();
			/*确定按钮不再显示后，再判断当前页面是否还存在弹框：
			 * 若还存在弹框，递归调用处理弹框方法*/
//			new WebDriverWait(driver,2).until(ExpectedConditions.invisibilityOf(this.certainDialog()));
			if(this.isExist(this.dialog) & !this.isExist(this.certainDialog())){
				this.submitDialog(driver,root_path);
			}
		}
		return true;
	}
	
	public void cancelDialog(){
		this.dialog.findElement(By.xpath("//div[2]/button[1]")).click();
	}
	
	public WebElement getItem(int line){
		return this.items.findElement(By.xpath("//tr["+line+"]"));
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
	
	/**
	 * 单个操作对应的元素
	 * @param line the line of item
	 * @param op One operation(测试|应用,撤回,提交|编辑|删除） of item
	 * @return the current operation WebElement
	 */
	private WebElement opButtn(int line,String op){
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
	 * 对操作进行处理。
	 * @param driver currentDriver
	 * @param path of screenShot
	 * @param oTitle 操作前的页面title
	 * @return If currentPageJumped and displaySucceed: return true;
	 * 		   if displayFaild: return false;
	 */
	public Boolean afterOpItem(WebDriver driver,String path,String oTitle){
		if(!driver.getTitle().equals(oTitle)){
			/*测试|编辑操作跳转页面*/
			util.log("页面跳转......");
			return true;
			}
		else if(isExist(this.dialog)){
			/*提交|删除操作弹出确认操作弹框*/
			util.log("开始处理弹框......");
			return this.submitDialog(driver,path);
		}
		else if(this.isEdit()){
			util.log("开始编辑属性......");
			/*非可编辑状态的item：编辑操作弹出编辑属性弹框*/
			return true;
		}
			return false;
	}
	
	/**
	 * 对单个数据进行操作
	 * @param currentDriver
	 * @param line of the item(1,2,3...10)
	 * @param op One operation(测试|应用,撤回,提交|编辑|删除） of item
	 * @param path the path of screenShot
	 * @return If currentPageJumped and displaySucceed: return true;
	 * 		   if displayFaild: return false;	
	 */
	public Boolean opItem(WebDriver driver,int line,String op,String path){
		String title1 = driver.getTitle();
		WebElement btn=this.opButtn(line, op);
		if(btn==null)
			return false;
		btn.click();
		util.screenShot(driver, path, "opItem--->AfterClick");
		return this.afterOpItem(driver, path, title1);
	}
	
	/**
	 * Select the Item for bulk operation
	 * @param line of the item(1,2,3...10)
	 */
	public void selectItem(int line){
		this.items.findElement(By.xpath("//tr["+line+"]/td[1]/input")).click();
	}
	
	public Boolean clickCreateBtn(WebDriver driver,String path){
		String title = driver.getTitle();
		this.createBtn.click();
		return this.afterOpItem(driver, path, title);
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
	 * 搜索：状态
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
	
	/**
	 * 搜索：创建方式
	 * @param s 点选|java|sql
	 */
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
	
	/**搜索：最后修改的开始时间
	 *@param s format likes "2017-08-18"
	 * */
	public void searchByTimeStart(String s){
		this.lastUpdateTimeStart.sendKeys(s);
		this.searchBtn.click();
	}
	
	/**搜索：最后修改的截止时间
	 *@param s format likes "2017-08-18"
	 * */
	public void searchByTimeEnd(String s){
		this.lastUpdateTimeEnd.clear();
		this.lastUpdateTimeEnd.sendKeys(s);
		this.searchBtn.click();
	}
	
	/**批量提交*/
	public void submitAll(WebDriver driver,String root_path){
		this.bulkSubmit.click();
		this.closeDialog();
	}
	
	/**批量删除*/
	public void deleteAll(WebDriver driver,String root_path){
		this.bulkDelete.click();
		this.submitDialog(driver, root_path);
	}
	
	/**批量撤回*/
	public void retractAll(WebDriver driver,String root_path){
		this.bulkRetract.click();
		this.submitDialog(driver, root_path);
	}
	
	/**是否弹出编辑弹框*/
	public Boolean isEdit(){
		return this.isExist(this.attrForm);
	}
	
	public void editAttrScope(String s){
		this.attrScope.clear();
		this.attrScope.sendKeys(s);
	}
	public void editAttrDefined(String s){
		this.attrDefined.clear();
		this.attrDefined.sendKeys(s);
	}
	public void editAttrDesc(String s){
		this.attrDesc.clear();
		this.attrDesc.sendKeys(s);
	}
	public void editAttrSubmit(WebDriver driver,String root_path){
		this.attrSubmit.click();
		this.submitDialog(driver, root_path);
	}
	public void editAttrCancel(){
		this.attrCancel.click();
	}
	
	
}
