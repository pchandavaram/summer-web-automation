package com.busyqa.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.busyqa.base.BaseClass;
import com.busyqa.data.Constants;
import com.busyqa.objects.RegPage;
import com.busyqa.objects.RegistrationPage;
import com.busyqa.objects.RegistrationPageAlt;
import com.busyqa.utils.DataProv;
import com.busyqa.utils.ExcelUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.bytebuddy.build.Plugin.Factory.UsingReflection.Priority;

public class EcommTest extends BaseClass {

	Logger log = LogManager.getLogger(EcommTest.class);
	WebDriver driver;
	ExtentReports report;
	public ExcelUtil excelRegistration;
	public ExcelUtil excelEnv;

	public EcommTest() {

	}

	@BeforeTest
	public void setUp() {
		try {

			// creating html file for report
			report = new ExtentReports(rootPath + prop.getProperty("report.path"), true);
			log.info("Initiating the Extent reports");
			excelRegistration = new ExcelUtil(rootPath + Constants.PATH_EXCEL, "RegistrationData");
			excelEnv = new ExcelUtil(rootPath + Constants.PATH_EXCEL, "ENV");
			String env = excelEnv.getCellValue(0, 1);///
			log.info("Running in env" + env);
			log.info("Initiating the Ecom Tests");
			

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}

	}

	@AfterTest
	public void tearDown() {

		report.flush();
	}

	@BeforeMethod
	public void init() {

		// log.info("I started logging");
		// String browser = prop.getProperty("browser");
		try {
		if (browser.equalsIgnoreCase("CH")) {
			log.info("Running tests in Chrome Browser");
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		}
		
		
		if (browser.equalsIgnoreCase("SF")) {
			//BS CODE for SAFARI
		}

		if (browser.equalsIgnoreCase("ED")) {
			WebDriverManager.edgedriver().setup();
			EdgeOptions options = new EdgeOptions();
			options.addArguments("--remote-allow-origins=*");
			driver = new EdgeDriver(options);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		}

		if (browser.equalsIgnoreCase("FF")) {
			System.setProperty("webdriver.gecko.driver",rootPath+"\\Drivers\\geckodriver.exe");
			//FirefoxOptions options = new FirefoxOptions();
			//options.addArguments("--remote-allow-origins=*");
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 0, enabled = true)
	public void titleTest() {
		ExtentTest test = report.startTest("Verify Title of Ecom Website");
		try {

			test.log(LogStatus.INFO, "Navigating to " + baseUrl + Constants.URL_HOME);
			driver.get(baseUrl + Constants.URL_HOME);
			String expectedTitle = "Your Store";
			String actualTitle = driver.getTitle();
			System.out.println(excelRegistration.getCellValue(0, 0));

			// extent Report
			if (actualTitle.equalsIgnoreCase(expectedTitle)) {
				test.log(LogStatus.PASS, "Title has appeared as expected");
				BaseClass.takeSnapShot(driver, "titleTest1", test);
			} else {
				test.log(LogStatus.FAIL, "Title has NOT appeared as expected");
				BaseClass.takeSnapShot(driver, "titleTest2", test);
			}		
			report.endTest(test);

			// testNG Reporting
			assertEquals(actualTitle, expectedTitle);
		} catch (Exception e) {
			test.log(LogStatus.ERROR, e);
			log.error(e);
			e.printStackTrace();
			report.endTest(test);
		}

	}

	@Test(priority = 1, dataProvider = "registrationData", enabled = false)
	public void registrtationTest_bkp(String fName, String lName, String email, String phone, String password) {
		boolean regiterResult = false;
		ExtentTest test = report.startTest("Verify Registration of Ecom Website");
		try {
			driver.get(baseUrl + Constants.URL_REGISTER);
			test.log(LogStatus.INFO, "Navigated to URL and entering Test data");
			driver.findElement(By.xpath("//input[@id='input-firstname']")).sendKeys(fName);
			//using a prop file			
			//driver.findElement(By.xpath(prop.getProperty("xpath.first.name"))).sendKeys(fName);
			driver.findElement(By.xpath("//input[@id='input-lastName']")).sendKeys(lName);
			driver.findElement(By.xpath("//input[@id='input-email']")).sendKeys(email);
			driver.findElement(By.xpath("//input[@id='input-telephone']")).sendKeys(phone);
			driver.findElement(By.xpath("//input[@id='input-password']")).sendKeys(password);
			driver.findElement(By.xpath("//input[@id='input-confirm']")).sendKeys(password);
			driver.findElement(By.xpath("//input[@name='agree']")).click();
			driver.findElement(By.xpath("//input[@value='Continue']")).click();
			BaseClass.takeSnapShot(driver, "rsTest1", test);
			test.log(LogStatus.INFO, "Navigated to URL and entered all test data");
			try {
				String xpath = ".//h1[contains(text(),'Been Created!')]";
				alterImplicitWait(driver, 3, xpath);
				regiterResult = true;
				test.log(LogStatus.PASS, "User registration PASSED");
				BaseClass.takeSnapShot(driver, "rsTest2", test);
			} catch (Exception e) {
				test.log(LogStatus.FAIL, "User registration FAILED");
				BaseClass.takeSnapShot(driver, "rsTest3", test);
				regiterResult = false;
			}		
			assertTrue(regiterResult);
			report.endTest(test);
		} catch (Exception e) {
			test.log(LogStatus.ERROR, e);
			e.printStackTrace();
			report.endTest(test);
		}

	}	
	
	
	
	@Test(priority = 1,enabled = false)
	public void registrtationTest() {
		boolean regiterResult = false;
		ExtentTest test = report.startTest("Verify Registration of Ecom Website");
		RegistrationPage registrationPage = new RegistrationPage(driver);
		RegistrationPageAlt registrationPageAlt = new RegistrationPageAlt(driver);
		RegPage regPage = new RegPage(driver);
		try {
			driver.get(baseUrl + Constants.URL_REGISTER);
			test.log(LogStatus.INFO, "Navigated to URL and entering Test data");
			//Method 1
			registrationPage.enterFname("Page");
			registrationPage.enterLname("Object");
			//Method 2 
			registrationPageAlt.enterEmail("pom@email.com");
			//Method 3
			regPage.tboxTelephone().sendKeys("43856225252");
			RegPage.tboxEmail(driver).sendKeys("Email");;
			
			//registrationPage.fillRegistrationForm("","","","","");
			/*
			driver.findElement(By.xpath("//input[@id='input-email']")).sendKeys("");
			driver.findElement(By.xpath("//input[@id='input-telephone']")).sendKeys("");
			driver.findElement(By.xpath("//input[@id='input-password']")).sendKeys("");
			driver.findElement(By.xpath("//input[@id='input-confirm']")).sendKeys("");
			driver.findElement(By.xpath("//input[@name='agree']")).click();
			driver.findElement(By.xpath("//input[@value='Continue']")).click();
			*/
			
			
			BaseClass.takeSnapShot(driver, "rsTest1", test);
			test.log(LogStatus.INFO, "Navigated to URL and entered all test data");
			try {
				String xpath = ".//h1[contains(text(),'Been Created!')]";
				alterImplicitWait(driver, 3, xpath);
				regiterResult = true;
				test.log(LogStatus.PASS, "User registration PASSED");
				BaseClass.takeSnapShot(driver, "rsTest2", test);
			} catch (Exception e) {
				test.log(LogStatus.FAIL, "User registration FAILED");
				BaseClass.takeSnapShot(driver, "rsTest3", test);
				regiterResult = false;
			}		
			assertTrue(regiterResult);
			report.endTest(test);
		} catch (Exception e) {
			test.log(LogStatus.ERROR, e);
			e.printStackTrace();
			report.endTest(test);
		}

	}	

	@Test(dataProvider = "loginData", priority = 2, enabled = false)
	public void loginTest() {
		boolean loginResult = false;
		ExtentTest test = report.startTest("Verify Login of Ecom Website");
		try {
			reportInfo(test, "I", "Navigating to login url");
			driver.get(baseUrl + Constants.URL_LOGIN);
			BaseClass.takeSnapShot(driver, "loginTest1", test);
			driver.findElement(By.xpath("//input[@id='input-email']")).sendKeys("girish8@company.ca");
			driver.findElement(By.xpath("//input[@id='input-password']")).sendKeys("Password12345");
			BaseClass.takeSnapShot(driver, "loginTest2", test);
			driver.findElement(By.xpath("//input[@value='Login']")).click();

			try {
				driver.findElement(By.linkText("Edit your account information"));
				loginResult = true;
				reportInfo(test, "P", "Login Test passed");
				BaseClass.takeSnapShot(driver, "loginTest3", test);
			} catch (Exception e) {
				loginResult = false;
				reportInfo(test, "F", "Login Test failed");
				BaseClass.takeSnapShot(driver, "loginTest4", test);
			}
			assertTrue(loginResult);
			report.endTest(test);
		} catch (Exception e) {
			e.printStackTrace();
			test.log(LogStatus.ERROR, e);
			report.endTest(test);
		}

	}

	@AfterMethod
	public void quit() {
		driver.quit();

	}

	/* DATA PROVIDERS */
	
	@DataProvider(name = "registrationData")
	public Object[][] resgistrationData() {
		Object[][] data = null;
		try {
			DataProv dataprov = new DataProv();
			data = dataprov.getExcelData("RegistrationData");
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}

	}

	@DataProvider(name = "loginData")
	public Object[][] loginData() {
		Object[][] data = null;
		try {
			DataProv dataprov = new DataProv();
			data = dataprov.getExcelData("LoginData");
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}

	}

}
