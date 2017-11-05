/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.test.selenium;

import com.hkstlr.reeler.test.cucumber.BaseStepDefs;
import com.hkstlr.reeler.test.pageobject.CreateBlogEntryPage;
import com.hkstlr.reeler.test.pageobject.LoginPage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.net.ssl.HttpsURLConnection;
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
public class CreateBlogEntryIT extends BaseStepDefs {

    private static final Logger LOG = Logger.getLogger(CreateBlogEntryIT.class.getName());

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
    public void runAll() throws Throwable {
        int blogEntriesToPost = 5;
        the_user_is_logged_in();
        for (int i = 0; i <= blogEntriesToPost; i++) {
            LOG.info(Integer.toString(i));
            the_user_has_a_blog_and_is_on_create_entry_page();
            the_user_enters("litipsumText", blogEntriesToPost);
            user_submits_the_create_entry_form();
            a_create_entry_success_message_should_be_displayed();
        }
    }

    public void the_user_is_logged_in() throws Throwable {
        String username = "testerson";
        String password = "password";
        //WebDriver newDriver = new HtmlUnitDriver();
        LOG.info(lp.getPageURL());
        driver.get(lp.getPageURL());

        //LOG.info(driver.getPageSource());
        lp.usernameField.sendKeys(username);
        lp.passwordField.sendKeys(password);
        lp.loginForm.submit();
    }

    public void the_user_has_a_blog_and_is_on_create_entry_page() throws Throwable {
        String pageTitle = driver.getTitle();
        String expected = "Create Entry";
        assertEquals(expected, pageTitle);
    }

    public void the_user_enters(String textType, int numberOfParagraphs) throws Throwable {

        Entry entry = new Entry();
        entry.title = "%1s";
        entry.category = "Technology";
        entry.tags = "%1s";

        //String data = getLitipsumText("/api/15/p/json");
        //JSONObject obj = new JSONObject(data);
        //System.out.println(obj.get("text").toString());
        //String data = getLitipsumText("/api/15/p");
        String data;
        if (!"litipsumText".equals(textType)) {
            Map<String, String> params = new HashMap<>();
            params.put("p", "5");

            String rawdata = sendPost("http://justinjay.wang/90s-ipsum/", params);
            data = getBodyContent(rawdata);
        } else {
            data = getLitipsumText("/api/15/p");
        }

        String titleData = data.replaceAll("<[^>]*>", "").replaceAll("  ", "");

        //String title = obj.getString("title");
        int start = ThreadLocalRandom.current().nextInt(0, 11);
        int end = ThreadLocalRandom.current().nextInt(12, 100);
        String[] text = titleData.split(" ");
        if (text.length < end) {
            end = text.length;
        }
        String[] titleText = Arrays.copyOfRange(text, start, end);

        String title = Stream.of(titleText).collect(Collectors.joining(" "));
        if (title.length() > 255) {
            title = title.substring(0, 255);
        }
        this.cbep.titleField.clear();
        this.cbep.titleField.sendKeys(String.format(entry.title, title));

        Select categorySelect = new Select(cbep.weblogEntryCategoryField);
        categorySelect.selectByVisibleText(entry.category);

        this.cbep.tagBagField.clear();
        String tags = titleData.substring(100, 199);
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
        assertTrue(hasSuccessMessage);
    }

    public class Entry {

        String title;
        String category;
        String tags;
        String content;
    }

    private String sendPost(String strUrl, Map params) throws Exception {
        String USER_AGENT = "Mozilla/5.0";

        URL url = new URL(strUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        StringBuilder p = new StringBuilder();
        params.forEach((k, v) -> p.append(k).append("=").append(v));

        String urlParameters = p.toString();

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

    public static String getBodyContent(String html) {
        Pattern BODY_PATTERN = Pattern.compile("((?:.(?!<body[^>]*>))+.<body[^>]*>)|(</body\\>.+)", java.util.regex.Pattern.MULTILINE);
        if (html == null) {
            return null;
        }
        //see https://stackoverflow.com/questions/38376584/java-matcher-appendreplacement-and-matcher-appendtail-for-stringbuilder               
        Matcher m = BODY_PATTERN.matcher(html);
        StringBuilder sb = new StringBuilder();
        int pos = 0;
        while (m.find()) {
            sb.append(html, pos, m.start());
            pos = m.end();
            if (m.start(2) >= 0) {                  // check if group 1 matched
                sb.append(html, m.start(2), m.end(2)); // replace with group 1
            }
        }
        sb.append(html, pos, html.length());

        return sb.toString();
    }

}
