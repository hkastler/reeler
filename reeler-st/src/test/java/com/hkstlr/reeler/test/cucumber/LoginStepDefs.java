package com.hkstlr.reeler.test.cucumber;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;

import com.hkstlr.reeler.test.pageobject.LoginPage;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginStepDefs extends BaseStepDefs {

    private LoginPage put;

    @Before
    public void setUp() {
        super.setUp();
        put = new LoginPage(driver);
    }

    @Given("^the user is on the login page$")
    public void the_user_is_on_the_login_page() throws Throwable {
        put.get();
    }

    @When("^user enters \"([^\"]*)\" as the email address$")
    public void user_enters_as_the_email_address(String arg1) throws Throwable {
        put.usernameField.clear();
        put.usernameField.sendKeys(arg1);
    }

    @When("^enters \"([^\"]*)\" as the password$")
    public void enters_as_the_password(String arg1) throws Throwable {
        put.passwordField.clear();
        put.passwordField.sendKeys(arg1);
    }

    @When("^submits the form$")
    public void submits_the_form() throws Throwable {
        put.loginForm.submit();
    }

    @Then("^a fail message is displayed$")
    public void a_message_is_displayed() throws Throwable {
        String body = driver.findElement(By.tagName("body")).getText();
        //System.out.println("postLogin body:" + body);
        boolean hasExpectedMessage = body.contains("Login Failed");
        assertTrue(hasExpectedMessage);
    }
    
    @Then("^the user is redirected to the admin$")
    public void the_user_is_redirected_to_the_admin() throws Throwable {
        String reelerUiTxt = driver.findElement(By.className("navbar-brand")).getText();
        System.out.println("postLogin header:" + reelerUiTxt);
        boolean hasExpectedMessage = reelerUiTxt.contains("Reeler UI");
        assertTrue(hasExpectedMessage);
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

}
