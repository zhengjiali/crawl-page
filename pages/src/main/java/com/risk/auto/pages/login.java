package com.risk.auto.pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;



public class login {
	
	@FindBy(id="user_name")
	private WebElement username;
	
	@FindBy(id="pwd")
	private WebElement passwd;
	
	@FindBy(id="is_remember")
	private WebElement remeBtn;
	
	@FindBy(className="submit_btn")
	private WebElement loginBtn;

	
	public void loginData(String name,String pwd,Boolean r){
		this.username.sendKeys(name);
		this.passwd.sendKeys(pwd);
		if(r)
			this.remeBtn.click();
		this.loginBtn.click();
	}
}