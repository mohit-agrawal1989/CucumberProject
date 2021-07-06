package utility;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public abstract class DriverTestCase {

	enum WebBrowser
	{Firefox, IE, Chrome}

	public static WebDriver driver;
	public static PropertyReader propertyReader;
	Random rg = new Random();
	public int n = rg.nextInt(100000);

	public void setup(String filepath) throws Exception{
		String driverType, url, os, type;
		propertyReader = new PropertyReader();
		driverType = propertyReader.readApplicationFile("BROWSER",filepath);
		url = propertyReader.readApplicationFile("URL",filepath);
		os = propertyReader.readApplicationFile("OS",filepath);
		type = propertyReader.readApplicationFile("headless",filepath);

		if(driver == null) {
			//Check if desired browser is Firefox
			if (WebBrowser.Firefox.toString().equalsIgnoreCase(driverType)) {
				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
				driver = new FirefoxDriver(dc);
			}

			//Check if desired browser is Internet Explorer
			else if (WebBrowser.IE.toString().equalsIgnoreCase(driverType)) {
				//Set property for IEDriverServer
				String path = getPath();
				File file = new File(path + "//driver//IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver", file.getAbsolutePath());

				//Accept all SSL Certificates
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

				//Create driver object
				driver = new InternetExplorerDriver();
			}

			//Check if desired browser is Chrome
			else if (WebBrowser.Chrome.toString().equalsIgnoreCase(driverType)) {
				String path = getPath();
				System.setProperty("webdriver.chrome.driver", path + "//drivers//chromedriver");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--no-sandbox");
				options.addArguments("--headless");
				options.addArguments("--disable-gpu");
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--window-size=1325x744");
				if(type.equalsIgnoreCase("yes"))
					driver = new ChromeDriver(options);
				else
					driver = new ChromeDriver();
			}

			//If browser type is not matched, exit from the system
			else {
				String path = getPath();
				System.setProperty("webdriver.chrome.driver", path + "//drivers//chromedriver");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--no-sandbox");
				options.addArguments("--headless");
				options.addArguments("--disable-gpu");
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--window-size=1325x744");
				if(type.equalsIgnoreCase("yes"))
					driver = new ChromeDriver(options);
				else
					driver = new ChromeDriver();
			}
		}

		//Maximize window
		if(os.equalsIgnoreCase("Mac") | os.equalsIgnoreCase("Windows"))
			driver.manage().window().maximize();

		//Delete cookies
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to(url);

		//Handling IE certificate
		if (WebBrowser.IE.toString().equals(driverType)) 
		{ 
			driver.get("javascript:document.getElementById('overridelink').click();");
		}
	}

	public void loadURL(){

	}

	public void exit(){
		driver.quit();
	}

	public WebDriver getWebDriver(){
		return driver;
	}
	
	public int getRandomNumber(){
		return n;
	}

	//Get absolute path
	public String getPath()
	{
		String path ="";		
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		path = absolutePathOfFirstFile.replaceAll("\\\\+", "/");		
		return path;
	}

	public void getScreenshot(String str) throws IOException {

		TakesScreenshot scrShot =((TakesScreenshot)getWebDriver());
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		File file=new File(getPath()+"/Screenshots/"+str);
		FileUtils.copyFile(SrcFile, file);

	}

}
