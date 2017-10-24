package com.hkstlr.reeler.test.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty","html:target/cucumber" ,
                           "json:target/cucumber.json" ,
                           "junit:target/cucumber.xml"}
                            , features = {
                                "src/test/resources/com/hkstlr/reeler/test/3_create_blog_entry.feature"
                            })
public class Run02CreateBlogEntryTest {
}

