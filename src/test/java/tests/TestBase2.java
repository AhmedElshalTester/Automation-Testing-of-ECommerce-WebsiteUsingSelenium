package tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import utilities.Helper;

public class TestBase2 {

	//Sauce Lab Configuration
	/*public static final String USERNAME=LoadProperties.saucelabsData.getProperty("username");
	public static final String ACCESS_KEY=LoadProperties.saucelabsData.getProperty("accesskey");
	public static final String sauceURL="https://"+USERNAME+":"+ACCESS_KEY
			+LoadProperties.saucelabsData.getProperty("seleniumURL");*/


	public static String BaseURL="https://demo.nopcommerce.com";
	//public static String sauceURL1="https://ahelshal2020:1fa5fca6-4f38-4241-9f21-1ab8e96314ae@ondemand.saucelabs.com:443/wd/hub";

	protected ThreadLocal<RemoteWebDriver> driver=null;

	@BeforeClass
	@Parameters(value= {"browser"})

	public void setUp(@Optional ("chrome") String browser) throws MalformedURLException
	{
		driver=new ThreadLocal<>();
		DesiredCapabilities caps =new DesiredCapabilities();
		caps.setCapability("browserName", browser);
		//Selenium Grid Local
	//driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), caps));
		
		//run Zalenium and docker
		driver.set(new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), caps));
		
		
		//Run on SauceLabs on cloud
		//driver.set(new RemoteWebDriver(new URL(sauceURL1), caps));
		
		getDriver().navigate().to(BaseURL);
	}

	public WebDriver getDriver()
	{
		return driver.get();
	}

	@AfterClass
	public void stopDriver()
	{
		getDriver().quit();
		driver.remove();
	}

	//take screenshot when test case fail and add it in the screenshot folder
	@AfterMethod
	public void screenshotOnFailure(ITestResult result)
	{
		if (result.getStatus()==ITestResult.FAILURE) 
		{
			System.out.println("failed");
			System.out.println("taking screenshot");
			Helper.captureScreenshot(getDriver(), result.getName());
		}
	}


}
