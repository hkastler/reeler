package com.hkstlr.reeler.test;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty","html:target/cucumber" ,
                           "json:target/cucumber.json" ,
                           "junit:target/cucumber.xml"}
                            , features = {
                                "src/test/resources/com/hkstlr/reeler/test/1_first_account_creation.feature",
                                "src/test/resources/com/hkstlr/reeler/test/2_create_blog.feature"
                            })
public class RunSetupTest {
}

