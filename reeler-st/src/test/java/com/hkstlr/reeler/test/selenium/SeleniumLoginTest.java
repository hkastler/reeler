/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.test.selenium;

/**
 *
 * @author Henry
 */
import com.hkstlr.reeler.test.pageobject.LoginPage;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class SeleniumLoginTest {
  private WebDriver driver;
  private boolean acceptNextAlert = true;
  private final StringBuffer verificationErrors = new StringBuffer();
  LoginPage loginPage;
  
  @Before
  public void setUp() throws Exception {
    driver = new HtmlUnitDriver();
    loginPage = new LoginPage(driver);
    //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testSeleniumLogin() throws Exception {
    loginPage.usernameField.clear();
    loginPage.usernameField.sendKeys("testerson");
    
    loginPage.passwordField.clear();
    loginPage.passwordField.sendKeys("password");
    
    loginPage.loginForm.submit();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

 

  

  
}

