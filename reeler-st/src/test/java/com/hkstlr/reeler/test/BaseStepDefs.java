package com.hkstlr.reeler.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.hkstlr.reeler.test.util.TestUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class BaseStepDefs {

    protected WebDriver driver;

    @PostConstruct
    public void setUp() {
        driver = new HtmlUnitDriver();
        String myURL;
        myURL = TestUtils.getTestURL();
        //System.out.println("myURL:" + myURL);
    }

    @PreDestroy
    public void tearDown() {
        //System.out.println("tearDown in BaseStepDefs");
        driver.close();
    }
}
