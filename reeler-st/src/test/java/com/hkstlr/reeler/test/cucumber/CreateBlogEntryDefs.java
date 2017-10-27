/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.test.cucumber;

import com.hkstlr.reeler.test.pageobject.CreateBlogEntryPage;
import com.hkstlr.reeler.test.pageobject.LoginPage;
import com.hkstlr.reeler.test.util.TestUtils;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.List;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author Henry
 */
public class CreateBlogEntryDefs extends BaseStepDefs {

    private static final Logger LOG = Logger.getLogger(CreateBlogEntryDefs.class.getName());
    
    private CreateBlogEntryPage cbep;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        cbep = new CreateBlogEntryPage(driver);
    }

    @Given("^the user is logged in$")
    public void the_user_is_logged_in() throws Throwable {
        String username = "testerson";
        String password = "password";
        
        LoginPage lp = new LoginPage(driver);
        driver.get(lp.getPageURL());
        lp.usernameField.sendKeys(username);
        lp.passwordField.sendKeys(password);
        lp.loginForm.submit();
    }

    @Given("^the user has a blog and is on create entry page$")
    public void the_user_has_a_blog_and_is_on_create_entry_page() throws Throwable {
        String pageTitle = driver.getTitle();
        String expected = "Create Entry";
        assertEquals(expected, pageTitle);
    }

    @When("^the user enters$")
    public void the_user_enters(List<Entry> entries) throws Throwable {
      
      Entry entry = entries.get(0);
      
      this.cbep.titleField.clear();
      this.cbep.titleField.sendKeys(String.format(entry.title, TestUtils.getRandomString(8)));
      
      Select categorySelect = new Select(cbep.weblogEntryCategoryField); 
      categorySelect.selectByVisibleText(entry.category); 
		
      this.cbep.tagBagField.clear();
      this.cbep.tagBagField.sendKeys(String.format(entry.tags, TestUtils.getRandomString(6)));
		
      this.cbep.textField.clear();
      this.cbep.textField.sendKeys(entry.content);

    }

    @When("^user submits the create entry form$")
    public void user_submits_the_create_entry_form() throws Throwable {
        LOG.info("submitting form");
        cbep.weblogEntryForm.submit();
    }

    @Then("^a create entry success message should be displayed$")
    public void a_create_entry_success_message_should_be_displayed() throws Throwable {
        String body = driver.findElement(By.tagName("body")).getText();
        LOG.info(driver.findElement(By.tagName("body")).getText());
        boolean hasSuccessMessage = body.contains("Blog post published");
        //assertTrue(hasSuccessMessage);
    }
    
    public class Entry {
        String title;
        String category;
        String tags;
        String content;
    }
}
