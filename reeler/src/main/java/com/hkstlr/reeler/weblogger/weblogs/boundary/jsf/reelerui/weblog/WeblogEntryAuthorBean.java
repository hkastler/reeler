/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.weblog;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.control.jsf.FacesMessageManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryTag;
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
public class WeblogEntryAuthorBean extends AuthorBean<WeblogEntry> implements Serializable {

    @Inject
    private Logger log;

    @ManagedProperty(value = "#{reelerUiBean.currentWeblog}")
    private Weblog weblog;

    private WeblogEntry weblogEntry;

    private Calendar cal;

    //@ManagedProperty(value = "#{param.weblog}")
    private String handle;

    private boolean isFieldValidationDisabled = true;

    private Date pubDate = new Date();

    private String strDateTimeOfPubDate = new String();

    private String enclosureURL = new String();

    private String tagBag = new String();

    public WeblogEntryAuthorBean() {
        super(WeblogEntry.class);
    }

    @PostConstruct
    private void init() {
        //this.weblog = currentWeblog;//weblogManager.findByHandle(handle);
        this.handle = weblog.getHandle();
        this.cal = Calendar.getInstance(TimeZone.getTimeZone(weblog.getTimeZone()));

        if (this.id != null && !this.id.isEmpty()) {
            this.weblogEntry = weblogger.getWeblogEntryManager().findForEdit(id);
            /*this.weblogEntry.getTags().forEach((tag) -> {
            tagBag = tagBag.concat(tag.getName()).concat(" ");
            });*/
            tagBag = this.weblogEntry.getTagsAsString();
            log.fine("tags:" + this.weblogEntry.getTags().size());

            this.action = "edit";
            this.actionLabel = "Edit";
        } else {
            log.info("initing new WeblogEntryfor " + reelerUiBean.getCurrentWeblog().getName());
            this.weblogEntry = new WeblogEntry(weblog, reelerUiBean.getUser());
            weblogEntry.setCommentDays(weblog.getDefaultCommentDays());
        }

        if (this.action == null || this.action.isEmpty()) {
            this.action = "create";
            this.actionLabel = "Create";
        }

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

    public String getTagBag() {
        return tagBag;
    }

    public void setTagBag(String tagBag) {
        this.tagBag = tagBag;
    }

    public Calendar setCalFromStrPubDate(String strPubDate) {

        //Timestamp ts = null;
        if (strPubDate.equals("")) {
            cal.setTime(new Date());
        }
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date lpubDate = df.parse(strPubDate);
            cal.setTime(lpubDate);
        } catch (ParseException ex) {
            Logger.getLogger(WeblogEntryAuthorBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cal;
    }

    public Calendar setCalFromDate(Date dateToSet) {
        cal.setTime(dateToSet);
        return cal;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    private void setupAndSave(String facesMsg) throws WebloggerException {
        String anchor = weblogEntry.getTitle().replace(" ", WeblogEntry.TITLE_SEPARATOR);
        weblogEntry.setAnchor(anchor);
        weblogEntry.setPubTime(setCalFromStrPubDate(strDateTimeOfPubDate));
        weblogEntry.setUpdateTime(setCalFromDate(new Date()));        
        setupTags();
        FacesMessageManager.addSuccessMessage("weblogEntryForm", facesMsg);
    }

    public void saveAsDraft() throws WebloggerException {
        log.info("draft?");
        weblogEntry.setStatus(WeblogEntry.PubStatus.DRAFT.toString());
        setupAndSave("Blog post saved as draft");
    }

    public void postToWeblog() throws WebloggerException {
        log.info("posted?");
        //TODO: need some logic for PENDING and SCHEDULED
        weblogEntry.setStatus(WeblogEntry.PubStatus.PUBLISHED.toString());
        setupAndSave("Blog post published");
    }

    public String updateWeblogEntry() throws WebloggerException {
        log.info("updated?");
        //TODO: need some logic for PENDING and SCHEDULED
        weblogEntry.setUpdateTime(setCalFromDate(new Date()));
        setupTags();
        FacesMessageManager.addSuccessMessage("weblogEntryForm", "Entry updated");
        return reelerUiBean.getPath() + "/weblog/entry.xhtml";
    }

    public void save(WeblogEntry weblogEntry) throws WebloggerException {
        weblogger.getWeblogEntryManager().saveWeblogEntry(weblogEntry);
    }
    
    public void setupTags() throws WebloggerException{
        /*if(action.equals("edit")){
        for(WeblogEntryTag tag: weblogEntry.getTags()){
        log.info("removing tag: " + tag.getName());
        weblogEntry.removeTag(tag);
        }
        }*/
        weblogEntry.setTagsAsString(tagBag);
        log.info("tags:" + weblogEntry.getTags().size());
        save(weblogEntry);
        log.info("tags after save:" + weblogEntry.getTags().size()); 
        //the tagbag has only temp holding names
        //b/c the right query cannot be performed in the converter
        /*for (String tagName : tagBag.split(" ")) {
        log.info("tagBag name:" + tagName);
        WeblogEntryTag jpatag = weblogger.getWeblogEntryTagManager()
        .findByNameAndWeblogEntry(tagName.trim(), weblogEntry);
        if (jpatag == null) {
        WeblogEntryTag tag = new WeblogEntryTag(tagName);
        tag.setCreator(weblogEntry.getCreatorUserName());
        tag.setCreateDate(new Date());
        tag.setWeblog(weblog);
        tag.setWeblogEntry(weblogEntry);
        weblogger.getWeblogEntryTagManager().save(tag);
        jpatag = tag;
        }
        
        weblogEntry.addTag(jpatag);
        
        }
        save(weblogEntry);
        log.info("tags after save:" + weblogEntry.getTags().size());
        
        */
        
        
    }

}
