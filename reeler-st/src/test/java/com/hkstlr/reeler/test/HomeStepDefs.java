/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.test;

import com.hkstlr.reeler.test.pageobject.HomePage;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Henry
 */
public class HomeStepDefs extends BaseStepDefs {

    private HomePage put;

    @Before
    public void setUp() {
        super.setUp();
        put = new HomePage(driver);
    }

    @Given("^the user is on home page$")
    public void the_user_is_on_home_page() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        
    }

    @Then("^a the title should be \"([^\"]*)\"$")
    public void a_the_title_should_be(String arg1) throws Throwable {
        String expected = "rEEler blog engine";
        assertEquals(expected, arg1);
    }
}
