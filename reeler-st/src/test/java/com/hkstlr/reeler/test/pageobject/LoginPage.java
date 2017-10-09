package com.hkstlr.reeler.test.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class LoginPage extends LoadableComponent<LoginPage> {

    private final WebDriver driver;
    private final String pageURL = "http://localhost:8080/reeler/weblogger/login";

    @FindBy(id = "login")
    public WebElement loginForm;

    @FindBy(id = "username")
    public WebElement usernameField;

    @FindBy(id = "password")
    public WebElement passwordField;

    public LoginPage(WebDriver aDriver) {
        this.driver = aDriver;
        PageFactory.initElements(driver, this);
        this.driver.get(pageURL);
    }

    @Override
    protected void load() {
        this.driver.get(pageURL);
    }

    @Override
    protected void isLoaded() throws Error {
        //do something here
    }

}
