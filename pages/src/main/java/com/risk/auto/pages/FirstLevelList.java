package com.risk.auto.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.risk.auto.pages.util;

public class FirstLevelList{
	
	@FindBy(id="lastUpdateTimeStart")
	private WebElement lastUpdateTimeStart;
	
	@FindBy(id="lastUpdateTimeEnd")
	private WebElement lastUpdateTimeEnd;
	
	@FindBy(name="status")
	private WebElement status;
		
	@FindBy(className="search_btn")
	private WebElement searchBtn;
	
	public void searchByStatus(String s){
		Integer id = 0;
		if(s=="可编辑")
			id=1;
		else if(s=="已提交")
			id=2;
			else if(s == "使用中")
				id=3;
		util.log("+++++++++++++++++++");
		util.log(id.toString());	
		this.status.findElement(By.xpath("//option[@value='+id+']")).click();
		this.searchBtn.click();
	}
	
	public void searchByTimeStart(String s){
		this.lastUpdateTimeStart.sendKeys(s);
		this.searchBtn.click();
	}
	public void searchByTimeEnd(String s){
		this.lastUpdateTimeEnd.sendKeys(s);
		this.searchBtn.click();
	}
	
}
