package com.hkstlr.reeler.test.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;


import com.hkstlr.reeler.test.util.TestUtils;
import org.openqa.selenium.support.ui.Select;

public class CreateAccountPage extends LoadableComponent<CreateAccountPage> {

    public WebDriver driver;
   
    private final String pageURL = "http://localhost:8080/reeler/";

    public CreateAccountPage(WebDriver aDriver) {
        driver = aDriver;
        PageFactory.initElements(driver, this);
        driver.get(pageURL);
       
    }

    private String prefixId = "";

    @FindBy(id = "profile")
    public WebElement createAccountForm;

    @FindBy(id = "userName")
    private WebElement userNameField;
    @FindBy(id = "screenName")
    private WebElement screenNameField;
    @FindBy(id = "fullName")
    private WebElement fullNameField;
    
    @FindBy(id = "emailAddress")
    private WebElement emailField;
    @FindBy(id = "passwordText")
    private WebElement passwordField;
    @FindBy(id = "passwordConfirm")
    private WebElement passwordConfirmField;

    @FindBy(id = "localeSelect")
    private WebElement localeField;
    
    @FindBy(id = "timeZone")
    private WebElement timezoneField;
    

    @FindBy(xpath = "/html/body/main/div/div/div[1]/div/div/div/div/div[2]/div/div[1]/h2")
    private WebElement formPanelTitle;

    @Override
    protected void load() {
        this.driver.get(pageURL);
    }

    @Override
    protected void isLoaded() throws Error {
        //do something here
    }

    public String getTitle() {
        return this.driver.getTitle();
    }

    public WebElement getFormPanelTitle() {
        return this.formPanelTitle;
    }

    public WebElement getUserNameField() {
        return this.userNameField;
    }

    public void setUserNameField() {
        this.userNameField = driver.findElement(By.id("userName"));
    }

    public WebElement getScreenNameField() {
        return screenNameField;
    }

    public void setScreenNameField() {
        this.screenNameField = driver.findElement(By.id("screenName"));
    }

    public WebElement getFullNameField() {
        return fullNameField;
    }

    public void setFullNameField(WebElement fullNameField) {
        this.fullNameField = driver.findElement(By.id( "fullName"));
    }

    public WebElement getEmailField() {
        return emailField;
    }

    public void setEmailField() {
        this.emailField = driver.findElement(By.id( "emailAddress"));
    }

    public WebElement getPasswordField() {
        return passwordField;
    }

    public void setPasswordField() {
        this.passwordField = driver.findElement(By.id("passwordText"));
    }

    public WebElement getPasswordConfirmField() {
        return passwordConfirmField;
    }

    public void setPasswordConfirmField() {
        this.passwordConfirmField = driver.findElement(By.id("passwordConfirm"));
    }

    public WebElement getLocaleField() {
        return localeField;
    }

    public void setLocaleField() {
        
        this.localeField = driver.findElement(By.id("localeSelect"));
    }

    public WebElement getTimezoneField() {
        return timezoneField;
    }

    public void setTimezoneField() {
        this.timezoneField = driver.findElement(By.id("timeZone"));
    }

    

	

    

    

}
