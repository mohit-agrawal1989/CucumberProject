package utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.FindsByXPath;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class  DriverPage extends DriverTestCase {
	
	protected WebDriver driver;

	public DriverPage(WebDriver webdriver) {
		driver = webdriver;
	}
	

	public void Log(String logMsg){
		System.out.println(logMsg);
	}
	
	public WebDriver getWebDriver() 
	{
		return driver;
	}

	//Handle locator type
	public By ByLocator(String locator) 
	{
		By result = null;

		if (locator.startsWith("//")) 
		{ result = By.xpath(locator); }
		else if (locator.startsWith("css=")) 
		{ result = By.cssSelector(locator.replace("css=", "")); } 
		else if (locator.startsWith("name=")) 
		{ result = By.name(locator.replace("name=", ""));
		} else if (locator.startsWith("link=")) 
		{ result = By.linkText(locator.replace("link=", "")); } 
		else 
		{ result = By.id(locator); }
		return result;
	}

	//Assert element present
	public Boolean isElementPresent(String locator) 
	{
		Boolean result = false;
		try 
		{
			
			getWebDriver().findElement(ByLocator(locator));
			result = true;
		} 
		catch (Exception ex) { }
		return result;
	}

    public void dragAndDrop(String loc_SourceElement, String loc_TargetElement)
    {
     Actions act = new Actions(getWebDriver());
     WebElement SourceElement = getWebDriver().findElement(ByLocator(loc_SourceElement)); 
     WebElement TargetElement = getWebDriver().findElement(ByLocator(loc_TargetElement));
     act.clickAndHold(SourceElement).moveToElement(TargetElement).pause(2000).release(TargetElement).build().perform();
    }

	public void moveToElement(String loc)
	{
		Actions act = new Actions(getWebDriver());
		act.moveToElement(getWebDriver().findElement(By.xpath(loc))).build().perform();
	}

	public void WaitForElementPresent(String locator, int timeout) {

		for (int i = 0; i < timeout; i++) {
			if (isElementPresent(locator)) {
				break;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void WaitForElementEnabled(String locator, int timeout) {

		for (int i = 0; i < timeout; i++) {
			if (isElementPresent(locator)) {
				if (getWebDriver().findElement(ByLocator(locator)).isEnabled()) {
					break;
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void WaitForElementNotEnabled(String locator, int timeout) {

		for (int i = 0; i < timeout; i++) {
			if (isElementPresent(locator)) {
				if (!getWebDriver().findElement(ByLocator(locator)).isEnabled()) {
					break;
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void WaitForElementVisible(String locator, int timeout) {

		for (int i = 0; i < timeout; i++) {
			if (isElementPresent(locator)) {
				if (getWebDriver().findElement(ByLocator(locator)).isDisplayed()) {
					break;
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void WaitForElementNotVisible(String locator, int timeout) {

		for (int i = 0; i < timeout; i++) {
			if (isElementPresent(locator)) {
				if (!getWebDriver().findElement(ByLocator(locator)).isDisplayed()) {
					break;
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void mouseOver(String locator){		
		this.WaitForElementPresent(locator, 20);		
		// find Assignments menu
		WebElement el = getWebDriver().findElement(ByLocator(locator));
		
		//build and perform the mouseOver with Advanced User Interactions API
		Actions builder = new Actions(getWebDriver());    
		builder.moveToElement(el).build().perform();
	}
	  public void dragAndDrop(String loc_SourceElement, String loc_TargetElement, String DashbaordTab)
	    {
	     Actions act = new Actions(getWebDriver());
	     WebElement SourceElement = getWebDriver().findElement(ByLocator(loc_SourceElement)); 
	     WebElement TargetElement = getWebDriver().findElement(ByLocator(loc_TargetElement));
	     WebElement SupportElement = getWebDriver().findElement(ByLocator(DashbaordTab));
	     act.clickAndHold(SourceElement).moveToElement(SupportElement).pause(2000).moveToElement(TargetElement).pause(2000).release(TargetElement).build().perform();
	    }
	public void clickOn(String locator)
	{
		moveToElement(locator);
		this.WaitForElementPresent(locator, 20);
		Assert.assertTrue(isElementPresent(locator));
		WebElement el = getWebDriver().findElement(ByLocator(locator));			
		el.click();
	}
	
	public void doubleClick(String locator)
	{		
		this.WaitForElementPresent(locator, 20);
		Assert.assertTrue(isElementPresent(locator));
		WebElement el = getWebDriver().findElement(ByLocator(locator));	
		Actions action = new Actions(driver);
		action.doubleClick(el).perform();
	}
	public void rightClick(String locator)
	{		
	
		WebElement el = getWebDriver().findElement(ByLocator(locator));	
		Actions action = new Actions(driver);
		action.contextClick(el).build().perform();
	}
	public void sendKeys(String locator, String value){
		
		this.WaitForElementPresent(locator, 20);
		Assert.assertTrue(isElementPresent(locator));
		WebElement el = getWebDriver().findElement(ByLocator(locator));
//		el.clear();
		el.sendKeys(value);
	}
	
	public void sendKeysWebElement(WebElement e, String val) {
		e.clear();
		e.sendKeys(val);
	}
	
	public void selectFrame(String locator){
		
		this.WaitForElementPresent(locator, 20);
		Assert.assertTrue(isElementPresent(locator));
		getWebDriver().switchTo().frame(locator);
		
	}

	public void selectDropDown(String locator, String targetValue){ 
		Assert.assertTrue(isElementPresent(locator));
		this.WaitForElementPresent(locator, 20);
		new Select(getWebDriver().findElement(ByLocator(locator))).selectByVisibleText(targetValue);
		
    }
	
	public void selectDropDownByValue(String locator, String value){ 
		Assert.assertTrue(isElementPresent(locator));
		this.WaitForElementPresent(locator, 20);
		new Select(getWebDriver().findElement(ByLocator(locator))).selectByValue(value);
		
    }
	
	public void selectDropDownByIndex(WebElement locator, int value){ 
		new Select(locator).selectByIndex(value);;
		
    }
	public boolean isTextPresent(String locator, String str){		
		String message = getWebDriver().findElement(ByLocator(locator)).getText();			
		if(message.contains(str)){return true;}
		else {	return false; }
	}
	
	public String getText(String locator){
		WaitForElementPresent(locator, 5);
		String text = getWebDriver().findElement(ByLocator(locator)).getText();	
		return text;
	}
	
	public void scrollIntoView(String locator){
		  
		  WebElement elem = getWebDriver().findElement(ByLocator(locator));
		  ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", new Object[] { elem });
		 }
	
	public static By byLabel(final String label)
	{
	  return new By() {
	    @Override
	    public List<WebElement> findElements(final SearchContext context)
	    {
	      final String xpath =
	         "//*[@id = //label[text() = \"" + label + "\"]/@for]";
	      return ((FindsByXPath) context).findElementsByXPath(xpath);
	    }
	 
	    @Override
	    public WebElement findElement(final SearchContext context)
	    {
	      String xpath =
	        "id(//label[text() = \"" + label + "\"]/@for)";
	      return ((FindsByXPath) context).findElementByXPath(xpath);
	    }
	 
	    @Override
	    public String toString()
	    {
	      return "ByLabel: " + label;
	    }
	  };
	}
	
	
	//generating random string
	public static String generateRandomString(int length) throws Exception {

		StringBuffer buffer = new StringBuffer();
		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

		int charactersLength = characters.length();

		for (int i = 0; i < length; i++) {
			double index = Math.random() * charactersLength;
			buffer.append(characters.charAt((int) index));
		}
		return buffer.toString();
	}
	
	public void checkPageLoader() {
		String loc;
		try {
			for(int i=0;i<100;i++) {
				String str = getWebDriver().findElement(By.xpath("//img[@id='loading-image']/..")).getAttribute("style");
				if(str.equals("display: none;")) {
					Thread.sleep(1000);
					break;
				}
				else if(str.equals("display: block;")) {
					Thread.sleep(1000);
				}
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public WebElement explicitWait(String str) {
		WebDriverWait wait=new WebDriverWait(driver, 20);
		WebElement ele;
		ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(str)));

		return ele;
	}

	public void putWait(int sec){
		try{
			Thread.sleep(sec);
		}
		catch (Exception e){}
	}

	public String getDate(String format){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now.plusDays(1));
	}

	public void uploadFile(){
		sendKeys("//input[@id='preview_custom_file']", getPath()+"/Bylaws1.pdf");
	}
}
