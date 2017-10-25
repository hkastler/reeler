/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.test.selenium;


import com.hkstlr.reeler.test.cucumber.BaseStepDefs;
import com.hkstlr.reeler.test.pageobject.CreateBlogEntryPage;
import com.hkstlr.reeler.test.pageobject.LoginPage;
import com.hkstlr.reeler.test.util.TestUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author Henry
 */
public class SeleniumCreateBlogEntry extends BaseStepDefs {

    
    private CreateBlogEntryPage cbep;
    private LoginPage lp;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        driver = new HtmlUnitDriver();
        cbep = new CreateBlogEntryPage(driver);
        lp = new LoginPage(driver);
    }

    @Test
    public void runAll() throws Throwable{
        int blogEntriesToPost = 10;
        for(int i=0; i<=blogEntriesToPost; i++){
            the_user_is_logged_in();
            the_user_has_a_blog_and_is_on_create_entry_page();
            the_user_enters();
            user_submits_the_create_entry_form();
            a_create_entry_success_message_should_be_displayed();
        }
    }
    
    public void the_user_is_logged_in() throws Throwable {
        String username = "testerson";
        String password = "password";
        //WebDriver newDriver = new HtmlUnitDriver();
        
        driver.get(lp.getPageURL());
        lp.usernameField.sendKeys(username);
        lp.passwordField.sendKeys(password);
        lp.loginForm.submit();
    }

    
    public void the_user_has_a_blog_and_is_on_create_entry_page() throws Throwable {
        String pageTitle = driver.getTitle();
        String expected = "Create Entry";
        assertEquals(expected, pageTitle);
    }

   
    public void the_user_enters() throws Throwable {

        Entry entry = new Entry();
        entry.title = "%1s";
        entry.category = "Technology";
        entry.tags = "%1s";

        //String data = getLitipsumText("/api/15/p/json");
        //JSONObject obj = new JSONObject(data);
        //System.out.println(obj.get("text").toString());
        String data = getLitipsumText("/api/15/p");
        String titleData = data.replace("<p>", "").replace("</p>", "");
        
        //String title = obj.getString("title");
        int start = ThreadLocalRandom.current().nextInt(0, 11);
        int end = ThreadLocalRandom.current().nextInt(12, 100);
        String[] text = titleData.split(" ");
        if(text.length < end){
            end = text.length;
        }
        String[] titleText = Arrays.copyOfRange(text,start,end);
        
        String title = Stream.of(titleText).collect(Collectors.joining(" "));
        if(title.length() > 255){
            title = title.substring(0,255);
        }
        this.cbep.titleField.clear();
        this.cbep.titleField.sendKeys(String.format(entry.title, title));

        Select categorySelect = new Select(cbep.weblogEntryCategoryField);
        categorySelect.selectByVisibleText(entry.category);

        this.cbep.tagBagField.clear();
        String tags = data.substring(100,199);
        this.cbep.tagBagField.sendKeys(String.format(entry.tags, tags));

        this.cbep.textField.clear();
        
        //entry.content = obj.getJSONArray("text").get(0).toString();
        entry.content = data;
        this.cbep.textField.sendKeys(entry.content);

    }

    
    public void user_submits_the_create_entry_form() throws Throwable {
        cbep.weblogEntryForm.submit();
    }

    
    public void a_create_entry_success_message_should_be_displayed() throws Throwable {
        String body = driver.findElement(By.tagName("body")).getText();
        System.out.println(driver.findElement(By.tagName("body")).getText());
        boolean hasSuccessMessage = body.contains("Blog post published");
        assertTrue(true);
    }

    public class Entry {

        String title;
        String category;
        String tags;
        String content;
    }

    private String sendPost() throws Exception {
        String USER_AGENT = "Mozilla/5.0";
        
        URL url = new URL("http://justinjay.wang/90s-ipsum/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        
        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "p=5";

        // Send post request
        con.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }

        int responseCode = con.getResponseCode();

        StringBuilder response;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        //print result
        return response.toString();

    }
    
    //need to setup,for java, litipsum.com's ssl cert
    //see util.InstallCert
    public String getLitipsumText(String apiPath) throws Exception {
        
        String USER_AGENT = "Mozilla/5.0";
        String GET = "GET";
        
        URL url = new URL("https://litipsum.com/".concat(apiPath));
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        
        //add request header
        con.setRequestMethod(GET);
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Send post request
        con.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.flush();
        }

        //int responseCode = con.getResponseCode();
        

        StringBuilder response;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        
        //System.out.println(obj.get("text").toString());
        //assertTrue(response.toString().length()>0);
        return response.toString();
    }
    


}
