/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.test.cucumber;

import com.hkstlr.reeler.test.pageobject.CreateBlogPage;
import com.hkstlr.reeler.test.pageobject.LoginPage;
import com.hkstlr.reeler.test.util.TestUtils;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author Henry
 */
public class CreateBlogDefs extends BaseStepDefs {
    
    private CreateBlogPage cbp;


    @Before
    @Override
    public void setUp() {
        super.setUp();
        cbp = new CreateBlogPage(driver);
    }

    @Given("^an admin user is logged in$")
    public void an_admin_user_is_logged_in() throws Throwable {
        String username = "testerson";
        String password = "password";
        
        LoginPage lp = new LoginPage(driver);
        driver.get(lp.getPageURL());
        lp.usernameField.sendKeys(username);
        lp.passwordField.sendKeys(password);
        lp.loginForm.submit();
    }

    @When("^the user is on admin home page$")
    public void the_user_is_on_admin_home_page() throws Throwable {
        String pageTitle = driver.getTitle();
        String expected = "Reeler UI";
        assertEquals(expected, pageTitle);
    }

    @Then("^the user clicks on the create blog link$")
    public void the_user_clicks_on_the_create_blog_link() throws Throwable {
        //this doesn't seem to work with jsf h:commandLink
        cbp.createBlogLink.click();
        //so i'm just going to redirect manually
        driver.get(cbp.pageURL);
    }

    @Then("^the create blog form is displayed$")
    public void the_create_blog_form_is_displayed() throws Throwable {
        String expectedTitle = "Create New Blog";
        assertEquals(expectedTitle, cbp.createBlogPanelTitle.getText());
    }

    @Then("^user enters \"([^\"]*)\" in create blog form name field$")
    public void user_enters_in_create_blog_form_name_field(String arg1) throws Throwable {
         cbp.nameField.clear();
         cbp.nameField.sendKeys(arg1);
    }

    @Then("^user enters \"([^\"]*)\" in create blog form tagline field$")
    public void user_enters_in_create_blog_form_tagline_field(String arg1) throws Throwable {
         cbp.taglineField.clear();
         cbp.taglineField.sendKeys(arg1);
    }

    @Then("^user enters \"([^\"]*)\" in create blog form handle field$")
    public void user_enters_in_create_blog_form_handle_field(String arg1) throws Throwable {
        cbp.handleField.clear();
        cbp.handleField.sendKeys(arg1);
    }

    @Then("^user enters \"([^\"]*)\" in the create blog form emailAddress field$")
    public void user_enters_in_the_create_blog_form_emailAddress_field(String arg1) throws Throwable {
       cbp.emailAddressField.clear();
       String newEmail = TestUtils.getNewUserEmailAddress(arg1);
       cbp.emailAddressField.sendKeys(newEmail);
    }

    @Then("^user selects en_US in the create blog form locale field$")
    public void user_selects_en_US_in_the_create_blog_form_locale_field() throws Throwable {
        Select localeSelect = new Select(cbp.localeField);
        localeSelect.selectByValue("en_US");
    }

    @Then("^user selects America/Chicago in the create blog form timezone field$")
    public void user_selects_America_Chicago_in_the_create_blog_form_timezone_field() throws Throwable {
        Select timeZoneSelect = new Select(cbp.timezoneField);
        timeZoneSelect.selectByValue("America/Chicago");
    }

    @Then("^user submits the create blog form$")
    public void user_submits_the_create_blog_form() throws Throwable {
        cbp.createWeblogForm.submit();
    }

    @Then("^a blog created success message should be displayed$")
    public void a_blog_created_success_message_should_be_displayed() throws Throwable {
        String body = driver.findElement(By.tagName("body")).getText();
        boolean hasSuccessMessage = body.contains("Weblog blog Created");
        assertTrue(hasSuccessMessage);
    }

}
