package com.hkstlr.reeler.test.cucumber;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.hkstlr.reeler.test.util.TestUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class BaseStepDefs {

    protected WebDriver driver;
    private String envURL;

    @PostConstruct
    public void setUp() {
        driver = new HtmlUnitDriver();
        envURL = TestUtils.getTestURL();
        //System.out.println("myURL:" + myURL);
    }

    @PreDestroy
    public void tearDown() {
        //System.out.println("tearDown in BaseStepDefs");
        driver.close();
    }
}
