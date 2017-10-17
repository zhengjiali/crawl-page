package com.risk.auto.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 数据端新建数据页面：适用于新建DB、新建API、新建混合的第一步；新建传入数据
 * @author zhengjiali
 *
 */
public class NewData {
	
	@FindBy(className="main_hd")
	private WebElement blank;
	@FindBy(name="nameEn")/*英文名输入框*/
	private WebElement nameEn;
	@FindBy(name="nameCn")/*中文名输入框*/
	private WebElement nameCn;
	@FindBy(name="dataType")/*数据类型选择框*/
	private WebElement dataType;
	@FindBy(name="productLine")/*作用域选择框*/
	private WebElement productLine;
	@FindBy(name="simpleDef")/*缺失值*/
	private WebElement simpleDef;
	@FindBy(name="scope")/*取值范围输入框*/
	private WebElement scope;
	@FindBy(name="complexDef")/*数据定义输入框*/
	private WebElement complexDef;
	@FindBy(name="description")/*说明输入框*/
	private WebElement description;
	@FindBy(name="btn_cancel")/*取消按钮*/
	private WebElement cancelBtn;
	@FindBy(name="submit")/*确定|下一步按钮*/
	private WebElement submitBtn;
	@FindBy(id="nameEn-error")/*英文名输入框-错误提*/
	private WebElement nameEnErr;
	@FindBy(id="nameCn-error")/*中文名输入框-错误提示*/
	private WebElement nameCnErr;
	@FindBy(id="dataType-error")/*数据类型选择框-错误提示*/
	private WebElement dataTypeErr;
	@FindBy(id="productLine-error")/*作用域选择框-错误提示*/
	private WebElement productLineErr;
	@FindBy(id="simpleDef-error")/*缺失值-错误提示*/
	private WebElement simpleDefErr;
	@FindBy(id="scope-error")/*取值范围输入框-错误提示*/
	private WebElement scopeErr;
	@FindBy(id="complexDef-error")/*数据定义输入框-错误提示*/
	private WebElement complexDefErr;
	@FindBy(id="description-error")/*说明输入框-错误提示*/
	private WebElement descriptionErr;
	
	public void clickBlank(){
		this.blank.click();
	}
	public void setNameEn(String s){
		this.nameEn.clear();
		this.nameEn.sendKeys(s);
	}
	public void setNameCn(String s){
		this.nameCn.clear();
		this.nameCn.sendKeys(s);
	}
	
	public void setSimpleDef(String s){
		this.simpleDef.clear();
		this.simpleDef.sendKeys(s);
	}
	public void setScope(String s){
		this.scope.clear();
		this.scope.sendKeys(s);
	}
	public void setComplexDef(String s){
		this.complexDef.clear();
		this.complexDef.sendKeys(s);
	}
	public void setDesc(String s){
		this.description.clear();
		this.description.sendKeys(s);
	}
	
	public String getNameEnErr(){
		return this.nameEnErr.getText();
	}
	public String getNameCnErr(){
		return this.nameCnErr.getText();
	}
	
	public String getSimpleDefErr(){
		return this.simpleDefErr.getText();
	}
	public String getScopeErr(){
		return this.scopeErr.getText();
	}
	public String getComplexDefErr(){
		return this.complexDefErr.getText();
	}
	public String getDescErr(){
		return this.descriptionErr.getText();
	}
	static int i=0;
	public Boolean createData(WebDriver driver,String root_path){
		i=i+1;
		String oTitle=driver.getTitle();
		this.submitBtn.click();
		if(!driver.getTitle().equals(oTitle))
			return true;
		else{
			util.screenShot(driver, root_path, "create-err"+i);
			return false;
		}
	}
	public void clickCancel(){
		this.cancelBtn.click();
	}
}
