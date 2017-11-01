/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.test.selenium;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 *
 * @author Henry
 */
public class ServerAdminClickThruIT {
    
    HtmlUnitDriver wd;
    
    @Before
    public void setUp() throws Exception {
        wd = new HtmlUnitDriver();
        Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
    }
    
    @Test
    public void clickthru() {
        wd.get("http://localhost:8080/reeler/weblogger/login/");
        wd.findElement(By.id("username")).click();
        wd.findElement(By.id("username")).clear();
        wd.findElement(By.id("username")).sendKeys("testerson");
        wd.findElement(By.id("password")).click();
        wd.findElement(By.id("password")).clear();
        wd.findElement(By.id("password")).sendKeys("password");
        wd.findElement(By.id("submit")).click();
        
        wd.get("http://localhost:8080/reeler/weblogger/reeler-ui/");
        wd.findElement(By.id("navbarReeler")).click();
        wd.findElement(By.linkText("Reeler UI")).click();
        wd.findElement(By.linkText("Server Administration")).click();
        wd.findElement(By.linkText("Configuration")).click();
        Assert.assertEquals("Server Configuration", getHeadingText());
        
        wd.findElement(By.linkText("User Admin")).click();
        wd.findElement(By.linkText("Global Comment Manager")).click();
        wd.findElement(By.linkText("Ping Target")).click();
        wd.findElement(By.linkText("Cache Info")).click();
        
    }
    
    private String getHeadingText(){
        String heading = wd.findElement(By.cssSelector(
                "#globalConfig > h2:nth-child(2)")).getText();
        return heading;
    }
    
    @After
    public void tearDown() {
        wd.quit();
    }
    
    public static boolean isAlertPresent(FirefoxDriver wd) {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
