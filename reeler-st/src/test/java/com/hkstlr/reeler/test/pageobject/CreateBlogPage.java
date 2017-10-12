/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.test.pageobject;

import com.hkstlr.reeler.test.util.TestUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

/**
 *
 * @author Henry
 */
public class CreateBlogPage extends LoadableComponent<CreateBlogPage>{
    
    public WebDriver driver;
   
    public final String pageURL;

    @FindBy(id = "createWeblogForm")
    public WebElement createWeblogForm;
    
    @FindBy(id = "name")
    public WebElement nameField;
    @FindBy(id = "tagline")
    public WebElement taglineField;
    @FindBy(id = "handle")
    public WebElement handleField;    
    @FindBy(id = "emailAddress")
    public WebElement emailAddressField;    
    @FindBy(id = "localeSelect")
    public WebElement localeField;    
    @FindBy(id = "timeZone")
    public WebElement timezoneField;    

    @FindBy(xpath = "/html/body/main/div/div/div[1]/div/div/div/div/div[2]/div/div[1]/h2")
    public WebElement adminHomePageTitle;
    
    @FindBy(id = "createBlogForm")
    public WebElement createBlogForm;
    
    @FindBy(id = "createBlogLink")
    public WebElement createBlogLink;
    
    @FindBy(xpath = "/html/body/main/div/div/div[1]/div/div/div[1]/h3")
    public WebElement createBlogPanelTitle;
    

    public CreateBlogPage(WebDriver aDriver) {
        this.pageURL = TestUtils.getTestURL()
                .concat("/reeler/weblogger/reeler-ui/weblog/create.xhtml");
                
        driver = aDriver;
        PageFactory.initElements(driver, this);
        driver.get(pageURL);       
    }
    
    @Override
    protected void load() {
        this.driver.get(pageURL);
    }

    @Override
    protected void isLoaded() throws Error {
       
    }


    
}
