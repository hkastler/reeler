/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.test.pageobject;

import com.hkstlr.reeler.test.util.TestUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

/**
 *
 * @author Henry
 */
public class HomePage extends LoadableComponent<HomePage> {
    private final WebDriver driver;
    private final String pageURL;

    
    public HomePage(WebDriver aDriver) {
        this.pageURL = TestUtils.getTestURL().concat("/reeler/");
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
