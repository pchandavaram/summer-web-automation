package com.busyqa.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegPage {

	WebDriver driver;

	public RegPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement tboxTelephone() {

		return driver.findElement(By.xpath("//input[@id='input-telephone']"));
	}

	public WebElement dynamicElement(String dateValue) {		
		
		return driver.findElement(By.xpath("//input[@id='" + dateValue + "']"));
	}
	
	
	public  static WebElement tboxEmail(WebDriver driver) {

		return driver.findElement(By.xpath("//input[@id='input-email']"));
	}
	


}
