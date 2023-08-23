package com.busyqa.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {
	WebDriver driver;

	// LOCATOR -->driver.findElement(By.xpath("//input[@id='input-firstname']"))
	// ACTION-->.sendKeys(fName);

	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
	}


	By fName = By.id("input-firstname");
	By lName = By.id("input-lastname");
	By email = By.xpath("//input[@id='input-email']");
	By phone = By.xpath("//input[@id='input-telephone']");
	By password = By.xpath("//input[@id='input-password']");
	By passwordConfirm = By.xpath("//input[@id='input-confirm']");
	By chkboxAgree = By.xpath("//input[@name='agree']");
	By buttonContinue = By.xpath("//input[@value='Continue']");


	public void enterFname(String value) {
		driver.findElement(fName).sendKeys(value);
	}

	public void clearFname() {
		driver.findElement(fName).clear();
	}

	public void getFNameValue() {
		driver.findElement(fName).getAttribute("value");
	}

	public void enterLname(String value) {
		driver.findElement(lName).sendKeys(value);
	}

	public void clearLname() {
		driver.findElement(lName).clear();

	}	

	public void enterEmail(String mail) {
		driver.findElement(email).sendKeys(mail);
	}

	public void enterPhone(String phoneNumber) {
		driver.findElement(phone).sendKeys(phoneNumber);
	}

	public void enterPassword(String pwd) {
		driver.findElement(password).sendKeys(pwd);
	}

	public void enterConfirmPasword(String pwd) {
		driver.findElement(passwordConfirm).sendKeys(pwd);
	}

	public void clickTermsAndConditions() {
		driver.findElement(chkboxAgree).click();
	}

	public void clickContinue() {
		driver.findElement(buttonContinue).click();
	}
	
	
	public void fillRegistrationForm(String firstName,String lastName, String email , String phone, String password) {
		
		enterFname(firstName);
		enterLname(lastName);
		enterEmail(email);
		enterPhone(email);
		enterPassword(email);		
		
	}
	
	
	
	

}
