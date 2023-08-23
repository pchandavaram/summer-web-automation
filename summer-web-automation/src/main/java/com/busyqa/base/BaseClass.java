package com.busyqa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.busyqa.data.Constants;
import com.busyqa.utils.ExcelUtil;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseClass {

	public BaseClass() {

	}

	public static String baseUrl = null;;
	public static Properties prop;
	public static String rootPath;
	public static String browser;

	@BeforeSuite
	public void setUp() {
		try {
			rootPath = System.getProperty("user.dir");
			FileInputStream fis = new FileInputStream(rootPath + "/src/main/java/com/busyqa/data/config.properties");
			prop = new Properties();
			prop.load(fis);
			baseUrl = prop.getProperty("base.url");
			browser = prop.getProperty("app.browser");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@AfterSuite
	public void superTearDown() {

		// flush out all utils
		// archive reports
		// clean up any test data in db

	}

	public static void reportInfo(ExtentTest test, String type, String message) {
		switch (type) {

		case "I":
			test.log(LogStatus.INFO, message);
			break;
		case "P":
			test.log(LogStatus.PASS, message);
			break;
		case "F":
			test.log(LogStatus.FAIL, message);
			break;

		}

		//
	}

	public static void takeSnapShot(WebDriver driver, String ssName, ExtentTest test) throws IOException {

		File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(file, new File(rootPath + "\\Reports\\" + ssName + ".png"));
		test.log(LogStatus.INFO, "Snapshot below:" + test.addScreenCapture(ssName + ".png"));

	}

	public static void alterImplicitHelper(WebDriver driver, int timeOut) throws IOException {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeOut));

	}

	public static void alterImplicitWait(WebDriver driver,int timeOut,String xpath) throws IOException {
		
		alterImplicitHelper(driver, timeOut);
		driver.findElement(By.xpath(xpath));
		alterImplicitHelper(driver, 20);
	}

}
