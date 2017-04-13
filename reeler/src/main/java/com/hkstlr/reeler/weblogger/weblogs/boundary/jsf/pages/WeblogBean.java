/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.pages;

import com.hkstlr.reeler.app.control.Paginator;
import com.hkstlr.reeler.weblogger.weblogs.boundary.Weblogger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogManager;
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
public class WeblogBean {

    @EJB
    Weblogger weblogger;

    @ManagedProperty(value = "#{param.handle}")
    private String handle;

    @ManagedProperty(value = "#{param.categoryName}")
    private String categoryName;

    @ManagedProperty(value = "#{param.dateString}")
    private String dateString = new String();

    @ManagedProperty(value = "#{param.pageNum}")
    private Integer pageNum;

    @ManagedProperty(value = "#{param.pageSize}")
    private Integer pageSize;

    private Paginator paginator;
    
    private String defaultOutcome;

    private Date date;

    private List<WeblogEntry> viewEntries;

    private List<WeblogBookmark> bookmarks;

    @Inject
    private Logger log;

    private Weblog weblog;

    private WeblogHitCount weblogHitCount;

    public WeblogBean() {
    }

    @PostConstruct
    public void init() {
        if (pageNum == null) {
            pageNum = 1;
        }
        
        try {
            this.weblog = getWeblogByHandle(handle);
            int numberOfEntries = getEntryCount(weblog);
            log.info("numberOfEntries:" + numberOfEntries + " for weblog " + weblog.getName());
            if (pageSize == null) {
                pageSize = weblog.getEntryDisplayCount();
                log.info("pageSize:" + pageSize);
            }
            paginator = new Paginator(pageSize,pageNum,numberOfEntries);
            //log.log(Level.WARNING,"hello from WeblogBean");
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
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

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
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
            WeblogEntry weblogEntry = weblogger.getWeblogEntryManager().findByPinnedToMain().get(0);
            Weblog weblog = weblogEntry.getWebsite();
            return weblog;
        }

        Weblog weblog = null;
        try {
            weblog = weblogger.getWeblogManager().getWeblogByHandle(handle);
            //List<WeblogEntry> weblogEntries = weblogger.getWeblogEntryManager().getWeblogEntriesForWeblog(weblog);
            //weblog.setWeblogEntries(weblogEntries);
            List<WeblogBookmark> weblogBookmarks = weblogger.getWeblogBookmarkManager().getBookmarksForWeblog(weblog);
            setBookmarks(weblogBookmarks);
        } catch (WebloggerException ex) {
            log.log(Level.SEVERE, null, ex);
        }

        return weblog;
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
        //log.info("date:" + date);
        //log.info("formatted:" + localeDateFormat().format(date));
        this.viewEntries = weblogger.getWeblogEntryManager()
                .getWeblogEntriesByDateAndWeblog(dateToGet, weblog);

    }

    public void weblogViewAction() {
        /*this.viewEntries = weblogger.getWeblogEntryManager()
        .getWeblogEntriesForWeblog(weblog);*/
        int[] range = new int[2];
        range[0] = paginator.getPageFirstItem()-1;
        range[1] = paginator.getPageLastItem()-1;
        this.viewEntries = weblogger.getWeblogEntryManager().getPaginatedEntries(weblog,range);
    }

}
