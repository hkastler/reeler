package com.hkstlr.reeler.test.pageobject;

import com.hkstlr.reeler.test.util.TestUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class LoginPage extends LoadableComponent<LoginPage> {

    private static final Logger LOG = Logger.getLogger(LoginPage.class.getName());

    
    
    private final WebDriver driver;
    private final String pageURL = TestUtils.getTestURL() + "/reeler/weblogger/login";

    @FindBy(id = "login")
    public WebElement loginForm;

    @FindBy(id = "username")
    public WebElement usernameField;

    @FindBy(id = "password")
    public WebElement passwordField;

    public LoginPage(WebDriver aDriver) {
        LOG.info(pageURL);
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF); 
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
