package com.hkstlr.reeler.test.cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;

import com.hkstlr.reeler.test.pageobject.CreateAccountPage;
import com.hkstlr.reeler.test.util.TestUtils;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.support.ui.Select;

public class CreateAccountStepDefs extends BaseStepDefs {

    private CreateAccountPage cap;
    private String newEmail;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        cap = new CreateAccountPage(driver);
    }

    @Given("the user is on create account page")
    public void step1() {
        cap.get();
        String pageTitle = cap.getTitle();
        String expected = "Reeler Blog Engine";
        assertEquals(expected, pageTitle);

    }

    @When("the setup first account screen is displayed")
    public void the_setup_first_account_screen_is_displayed() throws Throwable {
        
        assertNotNull(cap.getSetupFormContainer());
        
        String formTitle = cap.setupFormTitle.getText();
        System.out.println("formTitle:" + formTitle);
        String expected = "Setup First/Admin User";
        
        assertEquals(expected, formTitle);
        assertNotNull(cap.createAccountForm);

    }

    @Given("^user enters \"(.*?)\" in userName field$")
    public void user_enters_in_userName_field(String arg1) throws Throwable {
        cap.getUserNameField().clear();
        cap.getUserNameField().sendKeys(arg1);
    }

    @Given("^user enters \"(.*?)\" in screenName field$")
    public void user_enters_in_screenName_field(String arg1) throws Throwable {
        cap.getScreenNameField().clear();
        cap.getScreenNameField().sendKeys(arg1);
    }

    @Given("^user enters \"(.*?)\" in fullName field$")
    public void user_enters_in_fullName_field(String arg1) throws Throwable {
        cap.getFullNameField().clear();
        cap.getFullNameField().sendKeys(arg1);
    }

    @Given("^user enters \"(.*?)\" in emailAddress field$")
    public void user_enters_in_emailAddress_field(String arg1) throws Throwable {
        cap.getEmailField().clear();
        //get a fresh email address for a username
        //using henrykastler+test%1s@gmail.com as a string template
        newEmail = TestUtils.getNewUserEmailAddress(arg1);
        cap.getEmailField().sendKeys(newEmail);
    }

    @Given("^user enters \"(.*?)\" in passwordText field$")
    public void user_enters_in_passwordText_field(String arg1) throws Throwable {
        cap.getPasswordField().clear();
        cap.getPasswordField().sendKeys(arg1);
    }

    @Given("^user confirms \"(.*?)\" in password confirm field$")
    public void user_confirms_in_password_confirm_field(String arg1) throws Throwable {
        cap.getPasswordConfirmField().clear();
        cap.getPasswordConfirmField().sendKeys(arg1);
    }

    @Given("^user selects en_US in locale field$")
    public void user_selects_en_US_in_locale_field() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //System.out.println(cap.get().driver.getPageSource());
        //got the Select working here
        Select localeSelect = new Select(cap.getLocaleField());
        localeSelect.selectByValue("en_US");
    }

    @Given("^user selects America/Chicago in timezone field$")
    public void user_selects_America_Chicago_in_timezone_field() throws Throwable {
        //WebElement timeZone = cap.get().driver.findElement(By.id("timeZone"));
        Select timeZoneSelect = new Select(cap.getTimezoneField());
        timeZoneSelect.selectByValue("America/Chicago");
    }

    @When("^user submits the form$")
    public void user_submits_the_form() throws Throwable {
        cap.createAccountForm.submit();
    }

    @Then("^a success message should be displayed$")
    public void a_success_message_should_be_displayed() throws Throwable {
        String body = driver.findElement(By.tagName("body")).getText();
        System.out.println("body:" + body);
        boolean hasSuccessMessage = body.contains("User created, please log in");
        assertTrue(hasSuccessMessage);
    }

    @After
    public void tearDown() {
        super.tearDown();
    }
}
