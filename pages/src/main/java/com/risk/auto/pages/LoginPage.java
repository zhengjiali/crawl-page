package com.risk.auto.pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;



public class LoginPage {
	
	@FindBy(name="userName")
	private WebElement username;
	
	@FindBy(name="password")
	private WebElement passwd;
	
	@FindBy(name="isExemptLogin")
	private WebElement remeBtn;
	
	@FindBy(className="submit_btn")
	private WebElement loginBtn;

	
	public void login(String name,String pwd,Boolean r){
		this.username.sendKeys(name);
		this.passwd.sendKeys(pwd);
		if(r)
			this.remeBtn.click();
		this.loginBtn.click();
	}
}