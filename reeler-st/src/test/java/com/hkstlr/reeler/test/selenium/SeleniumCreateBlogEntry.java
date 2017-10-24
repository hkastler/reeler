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
        for(int i=0; i<=50; i++){
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

        String data = getLitipsumText();
        JSONObject obj = new JSONObject(data);
        //System.out.println(obj.get("text").toString());
        
        this.cbep.titleField.clear();
        this.cbep.titleField.sendKeys(String.format(entry.title, obj.getString("title")));

        Select categorySelect = new Select(cbep.weblogEntryCategoryField);
        categorySelect.selectByVisibleText(entry.category);

        this.cbep.tagBagField.clear();
        this.cbep.tagBagField.sendKeys(String.format(entry.tags, obj.getString("title")));

        this.cbep.textField.clear();
        
        entry.content = obj.getJSONArray("text").get(0).toString();
        this.cbep.textField.sendKeys(entry.content);

    }

    
    public void user_submits_the_create_entry_form() throws Throwable {
        cbep.weblogEntryForm.submit();
    }

    
    public void a_create_entry_success_message_should_be_displayed() throws Throwable {
        String body = driver.findElement(By.tagName("body")).getText();
        //System.out.println(driver.findElement(By.tagName("body")).getText());
        boolean hasSuccessMessage = body.contains("Blog post published");
        assertTrue(hasSuccessMessage);
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
        
        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "p=5";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        return response.toString();

    }
    
    public String getLitipsumText() throws Exception {
        String USER_AGENT = "Mozilla/5.0";
        
        URL url = new URL("https://litipsum.com/api/15/p/json");
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        
        //add reuqest header
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        //System.out.println("\nSending 'POST' request to URL : " + url);
       
        //System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //System.out.println();
        //JsonObjectBuilder builder = Json.createObjectBuilder();
        //JsonParser jp = Json.createParser(in);
        //JsonObject json = (JsonObject) jp.parse();
        //print result
        //JSONObject obj = new JSONObject(response.toString());
        //System.out.println(obj.get("text").toString());
        //assertTrue(response.toString().length()>0);
        return response.toString();
    }
    


}
