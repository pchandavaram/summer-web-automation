package com.busyqa.objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegistrationPageAlt {
	// PAGEFACTORY

	WebDriver driver;

	public RegistrationPageAlt(WebDriver driver) {
		//JAVA
		this.driver = driver;
		//PF		
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@id='input-email']")
	public WebElement tboxEmail;

	public void enterEmail(String value) {
		tboxEmail.sendKeys(value);
	}

}
