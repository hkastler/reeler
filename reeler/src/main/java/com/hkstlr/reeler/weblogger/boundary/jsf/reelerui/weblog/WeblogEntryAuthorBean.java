/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.boundary.jsf.reelerui.weblog;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.boundary.jsf.ReelerUIBean;
import com.hkstlr.reeler.weblogger.boundary.manager.WeblogCategoryManager;
import com.hkstlr.reeler.weblogger.boundary.manager.WeblogEntryManager;
import com.hkstlr.reeler.weblogger.boundary.manager.WeblogManager;
import com.hkstlr.reeler.weblogger.entities.Weblog;
import com.hkstlr.reeler.weblogger.entities.WeblogEntry;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author henry.kastler
 */
@ManagedBean
@RequestScoped
public class WeblogEntryAuthorBean implements Serializable {
    
    
    @Inject
    private Logger log;

    @EJB
    private WeblogEntryManager weblogEntryManager;
    
    @EJB
    private WeblogManager weblogManager;
    
    @EJB
    private WeblogCategoryManager weblogCategoryManager;
    
    @Inject
    private ReelerUIBean reelerUiBean;
    
    private Weblog weblog;

    private WeblogEntry weblogEntry;
    
    private Calendar cal;
    
    
    @ManagedProperty(value = "#{param.weblog}")
    private String handle;
    
    private boolean isFieldValidationDisabled = true;
    
    private Date pubDate = new Date();
    
    private String strDateTimeOfPubDate = new String();
    
    private String enclosureURL = new String();
    
    private String tagString = new String();

    public WeblogEntryAuthorBean() {
    }
    
    @PostConstruct
    private void init(){
        try {
            this.weblog = weblogManager.findByHandle(handle);
            this.cal = Calendar.getInstance(TimeZone.getTimeZone(weblog.getTimeZone()));
        } catch (WebloggerException ex) {
            Logger.getLogger(WeblogEntryAuthorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.weblogEntry = new WeblogEntry(weblog,reelerUiBean.getUser());   
    }

    public Weblog getWeblog() {
        return weblog;
    }

    public void setWeblog(Weblog weblog) {
        this.weblog = weblog;
    }
    
    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public boolean isIsFieldValidationDisabled() {
        return isFieldValidationDisabled;
    }

    public void setIsFieldValidationDisabled(boolean isFieldValidationDisabled) {
        this.isFieldValidationDisabled = isFieldValidationDisabled;
    }
    
    /**
     * Get the value of weblogEntry
     *
     * @return the value of weblogEntry
     */
    public WeblogEntry getWeblogEntry() {
        return weblogEntry;
    }

    /**
     * Set the value of weblogEntry
     *
     * @param weblogEntry new value of weblogEntry
     */
    public void setWeblogEntry(WeblogEntry weblogEntry) {
        this.weblogEntry = weblogEntry;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }
    
     public String getStrDateTimeOfPubDate() {
        return strDateTimeOfPubDate;
    }

    public void setStrDateTimeOfPubDate(String strDateTimeOfPubDate) {
        this.strDateTimeOfPubDate = strDateTimeOfPubDate;
    }
    
    public String getEnclosureURL() {
        return enclosureURL;
    }

    public void setEnclosureURL(String enclosureURL) {
        this.enclosureURL = enclosureURL;
    }

    public String getTagString() {
        return tagString;
    }

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }
    
    public Calendar setCalFromStrPubDate(String strPubDate){
        
        //Timestamp ts = null;
        if(strPubDate.equals("")){
            cal.setTime(new Date());
        }
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date pubDate = df.parse(strPubDate);
            cal.setTime(pubDate);
        } catch (ParseException ex) {
            Logger.getLogger(WeblogEntryAuthorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cal;
    }
    
    public Calendar setCalFromDate(Date dateToSet){
        cal.setTime(dateToSet);
        return cal;
     }
   
    
    private void setupAndSave(){
        String anchor = weblogEntry.getTitle().replace(" ", WeblogEntry.TITLE_SEPARATOR);
        weblogEntry.setAnchor(anchor);
        weblogEntry.setPubTime(setCalFromStrPubDate(strDateTimeOfPubDate));
        weblogEntry.setUpdateTime(setCalFromDate(new Date()));
        weblogEntryManager.save(weblogEntry);
    }
    
    public void saveAsDraft() {
        log.info("draft?");        
        weblogEntry.setStatus(WeblogEntry.PubStatus.DRAFT.toString());
        setupAndSave();
    }

    public void postToWeblog() {
        log.info("posted?");
        weblogEntry.setStatus(WeblogEntry.PubStatus.PUBLISHED.toString());
        setupAndSave();
    }

}
