/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.weblog;

import com.hkstlr.reeler.app.control.StringPool;
import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.control.DateFormatter;
import com.hkstlr.reeler.weblogger.weblogs.control.StringChanger;
import com.hkstlr.reeler.weblogger.weblogs.control.WeblogEntryAnchorBuilder;
import com.hkstlr.reeler.weblogger.weblogs.control.WeblogEntrySearchCriteria;
import com.hkstlr.reeler.weblogger.weblogs.control.jsf.FacesMessageManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment;
import java.io.Serializable;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    
    @Inject
    private WeblogEntryAnchorBuilder anchorBuilder;

    @ManagedProperty(value = "#{reelerUiBean.currentWeblog}")
    private Weblog weblog;
    
    private WeblogEntry weblogEntry;
    
    private Calendar cal;

    //@ManagedProperty(value = "#{param.weblog}")
    private String handle;
    
    @ManagedProperty(value = "#{param.weblog}")
    private String weblogId;

    private boolean isFieldValidationDisabled = true;

    private Date pubDate = new Date();

    private String strDateTimeOfPubDate = StringPool.BLANK;
    
    private String localDateTimeFormat;

    private String enclosureURL = StringPool.BLANK;

    private String tagBag = StringPool.BLANK;
    
    public WeblogEntryAuthorBean() {
        super(WeblogEntry.class);
    }

    @PostConstruct
    private void init() {
        //this.weblog = currentWeblog;//weblogManager.findByHandle(handle);
        if(weblogId != null){
            weblog = weblogger.getWeblogManager().findById(weblogId);
            reelerUiBean.setCurrentWeblog(weblog);
            
        }
        
        this.handle = weblog.getHandle();
        this.cal = Calendar.getInstance(TimeZone.getTimeZone(weblog.getTimeZone()));
        //this.allowCommentsChecked = weblog.isAllowComments();

        if (this.id != null && !this.id.isEmpty()) {
            this.weblogEntry = weblogger.getWeblogEntryManager().findForEdit(id);
            /*this.weblogEntry.getTags().forEach((tag) -> {
            tagBag = tagBag.concat(tag.getName()).concat(" ");
            });*/
            tagBag = this.weblogEntry.getTagsAsString();
            //allowCommentsChecked = this.weblogEntry.isAllowComments();
            
            if(weblogEntry.getPubTime() != null){        
                this.strDateTimeOfPubDate = DateFormatter.sdf.format(new Date(this.weblogEntry.getPubTime().getTimeInMillis()));
            }
            
            this.action = "edit";
            this.actionLabel = "Edit";
        } else {
            
            if(reelerUiBean.getUser()==null){
                reelerUiBean.setUserFromSession();
            }
            this.weblogEntry = new WeblogEntry(weblog, reelerUiBean.getUser());
            weblogEntry.setCommentDays(weblog.getDefaultCommentDays());
            //allowCommentsChecked = weblog.isAllowComments();
            this.action = "create";
            this.actionLabel = "Create";
        }


    }

    public String getWeblogId() {
        return weblogId;
    }

    public void setWeblogId(String weblogId) {
        this.weblogId = weblogId;
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

        if (StringPool.BLANK.equals(strPubDate)) {
            cal.setTime(new Date());
            return cal;
        }
        
        try {
            Date lpubDate = DateFormatter.sdf.parse(strPubDate);
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

    public String getLocalDateTimeFormat() {
        String df = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).toFormat().toString();
        log.info("pattern:" + df);
        return df;
    }

    public String[] getStatuses() {
        String[] statuses = new String[WeblogEntry.PubStatus.values().length];
        int counter = 0;
        for(Enum e : WeblogEntry.PubStatus.values()){
            statuses[counter] = StringChanger.toTitleCase(e.toString());
            counter++;
        }
        return statuses;
    }
    
    public List<WeblogEntry> getRecentEntries(){
        
        WeblogEntrySearchCriteria wesc = new WeblogEntrySearchCriteria();
        wesc.setWeblog(weblog);
        wesc.setMaxResults(10);
        wesc.setSortBy(WeblogEntrySearchCriteria.SortBy.UPDATE_TIME);
        return weblogger.getWeblogEntryManager().getBySearchCriteria(wesc);
         
    }
    
    public List<WeblogEntry> getRecentDrafts(){
        
        WeblogEntrySearchCriteria wesc = new WeblogEntrySearchCriteria();
        wesc.setWeblog(weblog);
        wesc.setMaxResults(10);
        wesc.setSortBy(WeblogEntrySearchCriteria.SortBy.UPDATE_TIME);
        wesc.setStatus(WeblogEntry.PubStatus.DRAFT);
        return weblogger.getWeblogEntryManager().getBySearchCriteria(wesc);
        
    }
    
    public List<WeblogEntry> getPendingEntries(){
        
        WeblogEntrySearchCriteria wesc = new WeblogEntrySearchCriteria();
        wesc.setWeblog(weblog);
        wesc.setMaxResults(10);
        wesc.setSortBy(WeblogEntrySearchCriteria.SortBy.UPDATE_TIME);
        wesc.setStatus(WeblogEntry.PubStatus.PENDING);
        return weblogger.getWeblogEntryManager().getBySearchCriteria(wesc);
        
    }
    
    public List<WeblogEntryComment> getComments(){
        return weblogger.getWeblogEntryCommentManager().getComments(weblogEntry);
    }

    public void saveAsDraft() throws WebloggerException {
        log.info("draft?");
        weblogEntry.setStatus(WeblogEntry.PubStatus.DRAFT.toString());
        setupAndSave("Blog post saved as draft");
    }

    public void publishWeblog() throws WebloggerException {
        log.info("posted?");
        //TODO: need some logic for PENDING and SCHEDULED        
        weblogEntry.setPubTime(setCalFromStrPubDate(strDateTimeOfPubDate));
        weblogEntry.setStatus(WeblogEntry.PubStatus.PUBLISHED.toString());
        setupAndSave("Blog post published");
    }

    public String updateWeblogEntry() throws WebloggerException {
        log.info("updated?");
        //TODO: need some logic for PENDING and SCHEDULED
        weblogEntry.setUpdateTime(setCalFromDate(new Date()));
        setupTagsAndSave();
        FacesMessageManager.addSuccessMessage("weblogEntryForm", "Entry updated");
        return reelerUiBean.getPath() + "/entry.xhtml";
    }

    private void setupAndSave(String facesMsg) throws WebloggerException {
        if(weblogEntry.getAnchor() == null || weblogEntry.getAnchor().isEmpty()){
            String anchor = anchorBuilder.createAnchorBase(weblogEntry);
            weblogEntry.setAnchor(anchor);
        }        
        weblogEntry.setUpdateTime(setCalFromDate(new Date()));        
        setupTagsAndSave();
        FacesMessageManager.addSuccessMessage("authorBeanUpdate", facesMsg);
    }
    
    public void setupTagsAndSave() throws WebloggerException{
        weblogEntry.setTagsAsString(tagBag);
        save(weblogEntry);        
     }
    
    public void save(WeblogEntry weblogEntry) throws WebloggerException {
        weblogger.getWeblogEntryManager().saveWeblogEntry(weblogEntry);
    }
    

}
