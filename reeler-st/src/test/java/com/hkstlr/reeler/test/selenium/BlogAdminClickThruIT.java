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
public class BlogAdminClickThruIT {
    
    HtmlUnitDriver wd;
    
    @Before
    public void setUp() throws Exception {
        wd = new HtmlUnitDriver();//new FirefoxDriver();
        //wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
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
        wd.findElement(By.id("nav-entry")).click();
        
        
        wd.findElement(By.id("nav-entries")).click();
        Assert.assertTrue(getHeadingText().contains("Entries"));
        
        wd.findElement(By.id("nav-comments")).click();
        Assert.assertEquals("Comments", getHeadingText());
        
        wd.findElement(By.id("nav-categories")).click();
        Assert.assertEquals("Categories", getHeadingText());
        
        wd.findElement(By.id("nav-blogrolls")).click();
        Assert.assertEquals("Bookmarks", getHeadingText());
        
        wd.findElement(By.id("nav-mediafiles")).click();
        
        wd.findElement(By.linkText("Preferences")).click();
        
        wd.findElement(By.id("nav-config")).click();
        Assert.assertEquals("Blog Settings", getHeadingText());
        
        wd.findElement(By.id("nav-members")).click();
        
        wd.findElement(By.id("nav-pings")).click();
        
        wd.findElement(By.id("nav-maintenance")).click();
        
    }
    
    private String getHeadingText(){
        String heading = wd.findElement(By.cssSelector(
                ".panel-primary > div:nth-child(2) > h2:nth-child(1)")).getText();
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
