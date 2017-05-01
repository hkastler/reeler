/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.pages;

import com.hkstlr.reeler.app.control.PageBean;
import com.hkstlr.reeler.app.control.Paginator;
import com.hkstlr.reeler.weblogger.weblogs.boundary.Weblogger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.control.DateFormatter;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogBookmark;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogHitCount;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;

/**
 *
 * @author henry.kastler
 */
@ManagedBean
@RequestScoped
public class WeblogBean extends PageBean {

    private static final Logger log = Logger.getLogger(WeblogBean.class.getName());    

    @EJB
    Weblogger weblogger;

    @ManagedProperty(value = "#{param.handle}")
    private String handle;

    @ManagedProperty(value = "#{param.categoryName}")
    private String categoryName;

    @ManagedProperty(value = "#{param.dateString}")
    private String dateString;

    private String defaultOutcome;

    private Date date;

    private List<WeblogEntry> viewEntries;

    private List<WeblogBookmark> bookmarks;


    private Weblog weblog;

    private WeblogHitCount weblogHitCount;

    public WeblogBean() {
        super();
    }

    @PostConstruct
    public void init() {
        
        if(dateString == null){
            dateString = "";
        }
        
        if (pageNum == null) {
            pageNum = 1;
        }
        
        try {
            this.weblog = getWeblogByHandle(handle);
            if(this.weblog != null){
                int numberOfEntries = getEntryCount(weblog);

                if (pageSize == null) {
                    pageSize = weblog.getEntryDisplayCount();
                }
                paginator = new Paginator(pageSize,pageNum,numberOfEntries);
            }
            
        } catch (Exception e) {
          log.log(Level.WARNING,"WeblogBean.init()",e);
        }

    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public Weblog getWeblog() {
        return weblog;
    }

    public void setWeblog(Weblog weblog) {
        this.weblog = weblog;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<WeblogEntry> getViewEntries() {
        return viewEntries;
    }

    public void setViewEntries(List<WeblogEntry> viewEntries) {
        this.viewEntries = viewEntries;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }    

    public String getDefaultOutcome() {
        return defaultOutcome;
    }

    public void setDefaultOutcome(String defaultOutcome) {
        this.defaultOutcome = defaultOutcome;
    }

    public List<WeblogBookmark> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<WeblogBookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public WeblogHitCount getWeblogHitCount() {
        return weblogHitCount;
    }

    public void setWeblogHitCount(WeblogHitCount weblogHitCount) {
        this.weblogHitCount = weblogHitCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Weblog getWeblogByHandle(String handle) {

        if (handle == null) {
            try{
                WeblogEntry weblogEntry = weblogger.getWeblogEntryManager().findByPinnedToMain().get(0);
                return weblogEntry.getWebsite();
            }catch(Exception e){
                log.log(Level.FINER,null,e);
                Weblog fallback = new Weblog();
                return fallback;
            }
        }

        Weblog lweblog = null;
        try {
            lweblog = weblogger.getWeblogManager().getWeblogByHandle(handle);
            List<WeblogBookmark> weblogBookmarks = weblogger.getWeblogBookmarkManager().getBookmarksForWeblog(weblog);
            setBookmarks(weblogBookmarks);
        } catch (WebloggerException ex) {
            log.log(Level.SEVERE, null, ex);
        }

        return lweblog;
    }

    public WeblogEntry getLatestWeblogEntryForWeblog() {

        if (this.weblog.getWeblogEntries() != null && !this.weblog.getWeblogEntries().isEmpty()) {
            return this.weblog.getWeblogEntries().get(this.weblog.getWeblogEntries().size() - 1);
        }

        WeblogEntry weblogEntry = weblogger.getWeblogEntryManager().getLatestEntryForWeblog(weblog);
        if (weblogEntry == null) {
            weblogEntry = new WeblogEntry();
            weblogEntry.setText("not found");
        }
        return weblogEntry;
    }

    public int getEntryCount(Weblog weblog) {
        return weblogger.getWeblogEntryManager().getWeblogEntryCountForWeblog(weblog);
    }

    public void categoryViewAction() {

        if (this.categoryName == null) {
            this.categoryName = "Technology";
        }
        this.viewEntries = weblogger.getWeblogEntryManager()
                .getWeblogEntriesByCategoryNameAndWeblog(categoryName, weblog);

    }

    public DateFormat localeDateFormat() {
        return DateFormatter.localeDefaultDateFormat(weblog.getLocaleInstance());
    }

    public void dateViewAction() {

        String dateToGet = this.dateString;

        Integer year = Integer.parseInt(dateString.substring(0, 4));
        Integer month = Integer.parseInt(dateString.substring(4, 6));
        Integer startDate = 1;

        if (dateString.length() == 8) {
            startDate = Integer.parseInt(dateString.substring(6, 8));
        }

        Calendar calendar = weblog.getCalendarInstance();
        calendar.set(year, month - 1, startDate, 0, 0, 0);
        this.date = new Date(calendar.getTimeInMillis());
        this.viewEntries = weblogger.getWeblogEntryManager()
                .getWeblogEntriesByDateAndWeblog(dateToGet, weblog);

    }

    public void weblogViewAction() {
        int[] range = new int[2];
        range[0] = getPaginator().getPageFirstItem()-1;
        range[1] = getPaginator().getPageLastItem()-1;
        this.viewEntries = weblogger.getWeblogEntryManager().getPaginatedEntries(weblog,range);
    }

}
