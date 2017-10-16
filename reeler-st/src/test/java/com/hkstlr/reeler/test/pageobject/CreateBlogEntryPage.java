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
public class CreateBlogEntryPage extends LoadableComponent<CreateBlogEntryPage> {
    public WebDriver driver;
   
    public final String pageURL;

    @FindBy(id = "weblogEntryForm")
    public WebElement weblogEntryForm;
    
    @FindBy(id = "title")
    public WebElement titleField;
    @FindBy(id = "weblogEntryCategory")
    public WebElement weblogEntryCategoryField;
    @FindBy(id = "tagBag")
    public WebElement tagBagField;
    @FindBy(id = "text")
    public WebElement textField;        

    @FindBy(xpath = "#nav > li.active")
    public WebElement navTabTitle;
    
    
    @FindBy(xpath = "//*[@id=\"content\"]/div/div[1]")
    public WebElement createEntryPanelTitle;
    

    public CreateBlogEntryPage(WebDriver aDriver) {
        this.pageURL = TestUtils.getTestURL()
                .concat("/reeler/weblogger/reeler-ui/weblog/entry.xhtml");
                
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
