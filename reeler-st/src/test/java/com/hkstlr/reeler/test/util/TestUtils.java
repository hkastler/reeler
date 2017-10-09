package com.hkstlr.reeler.test.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.RandomStringUtils;

public class TestUtils {

    public static String getNewUserEmailAddress(String oldEmail) {

        String randomString = RandomStringUtils.randomAlphanumeric(6).toLowerCase();

        String newEmail = String.format(oldEmail, randomString);

        System.out.println("new email: " + newEmail);

        return newEmail;
    }

    public static String getTestURL() {
        Properties prop = new Properties();
        String testURL = "http://localhost:8080";

        try {
            //load a properties file
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = loader.getResourceAsStream("test.properties");
            prop.load(stream);
            //get the property value and print it out
            //System.out.println("hostName:" + prop.getProperty("hostName"));
            
            StringBuilder newURL = new StringBuilder(prop.getProperty("protocol", "http"));
            newURL.append("://");
            newURL.append(prop.getProperty("hostName"));

            testURL = newURL.toString();
        } catch (IOException ex) {
        }

        return testURL;
    }

}
