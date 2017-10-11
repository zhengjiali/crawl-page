package com.risk.auto.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FirstLevelList{
	@FindBy(id="lastUpdateTimeStart")
	private WebElement lastUpdateTimeStart;
	
	@FindBy(id="lastUpdateTimeEnd")
	private WebElement lastUpdateTimeEnd;
	
	@FindBy(className="col_6")
	private WebElement status;
		
	@FindBy(className="search_btn")
	private WebElement searchBtn;
	
	public void searchByStatus(String s){
		this.status.findElement(By.linkText(s)).click();
		this.searchBtn.click();
	}
	
	public void searchByTimeStart(String s){
		this.lastUpdateTimeStart.sendKeys(s);
		this.searchBtn.click();
	}
}
