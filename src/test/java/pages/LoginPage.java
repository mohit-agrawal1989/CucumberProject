package pages;

import locators.LoginLocators;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.DriverPage;
import utility.ExecutionLog;

public class LoginPage extends DriverPage{

	private String loc;
	private ExecutionLog log  = new ExecutionLog();
	public LoginPage(WebDriver webdriver) {
		super(webdriver);
	}

	public void enterUserName(String username){
		sendKeys(LoginLocators.username, username);
		log.log("Entered username as :"+username);
	}

	public void enterPassword(String password){
		sendKeys(LoginLocators.password, password);
		log.log("Entered password as :"+password);
	}

	public void clickOnLoginBtn(){
		clickOn(LoginLocators.loginBtn);
		log.log("Click on login button");
	}

	public void verifyLogin(){
		Assert.assertTrue(isElementPresent(LoginLocators.dashboardPage));
		log.log("Verfiy login is successful.");
	}
}
