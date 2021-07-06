package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.LoginPage;
import utility.DriverTestCase;
import utility.PropertyReader;

public class LoginSteps extends DriverTestCase {

	LoginPage login = new LoginPage(getWebDriver());
	PropertyReader prop = new PropertyReader();
	String path = "application.properties";

	@When("^user enter username$")
	public void enterUserName(){
		login.enterUserName(prop.readApplicationFile("Username", path));
	}

    @And("^user enter password$")
    public void userEnterPassword() {
		login.enterPassword(prop.readApplicationFile("Password", path));
    }

	@And("^user click on log in button$")
	public void userClickOnLogInButton() {
		login.clickOnLoginBtn();
	}

	@Then("^verify user is successfully logged in$")
	public void verifyUserIsSuccessfullyLoggedIn() {
		login.verifyLogin();
	}
}