/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.boundary.jsf.pages;

import com.hkstlr.reeler.weblogger.boundary.Weblogger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.entities.Weblog;
import com.hkstlr.reeler.weblogger.entities.WeblogEntry;

/**
 *
 * @author henry.kastler
 */
@ManagedBean
@RequestScoped
public class WeblogBean {

    @Inject
    Weblogger weblogger;

    @ManagedProperty(value = "#{param.handle}")
    private String handle;

    @ManagedProperty(value = "#{param.categoryName}")
    private String categoryName;
    
    private List<WeblogEntry> categoryEntries;

    @Inject
    private Logger log;

    private Weblog weblog;

    public WeblogBean() {
    }

    @PostConstruct
    public void init() {
        //log.log(Level.WARNING,"hello from WeblogBean");
        try {
            this.weblog = getWeblogByHandle(handle);
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
    
    public String getCategoryName(){
        return categoryName;
    }
    
    public void setCategoryName(String categoryName){
        this.categoryName = categoryName;
    }
    

    public List<WeblogEntry> getCategoryEntries() {
        return categoryEntries;
    }

    public void setCategoryEntries(List<WeblogEntry> categoryEntries) {
        this.categoryEntries = categoryEntries;
    }
    
    

    private Weblog getWeblogByHandle(String handle) {

        if (handle == null) {
            WeblogEntry weblogEntry = weblogger.getWeblogEntryManager().findByPinnedToMain().get(0);
            Weblog weblog = weblogEntry.getWebsite();
            return weblog;
        }
        log.log(Level.FINE, "handle:{0}", handle);
        Weblog weblog = null;
        try {
            weblog = weblogger.getWeblogManager().getWeblogByHandle(handle);
            //lazy hibernate load
            //might need some refactoring here, for larger installations
            //for personal use it should be no problem
            List<WeblogEntry> weblogEntries = weblogger.getWeblogEntryManager().getWeblogEntriesForWeblog(weblog);
            weblog.setWeblogEntries(weblogEntries);
            log.log(Level.FINE, "weblog retrieved:" + weblog.getName());

        } catch (WebloggerException ex) {
            log.log(Level.SEVERE, null, ex);
        }

        return weblog;
    }

    public WeblogEntry getLatestWeblogEntryForWeblog() {

        if (this.weblog.getWeblogEntries() != null && !this.weblog.getWeblogEntries().isEmpty()) {
            log.fine("latestWeblogEntry returning from getWeblogEntries");
            return this.weblog.getWeblogEntries().get(this.weblog.getWeblogEntries().size()-1);
        }
        log.fine("finding top 1 weblogEntry");
        WeblogEntry weblogEntry = weblogger.getWeblogEntryManager().getLatestEntryForWeblog(weblog);
        if (weblogEntry == null) {
            weblogEntry = new WeblogEntry();
            weblogEntry.setText("not found");
        }
        return weblogEntry;
    }
    
    public void categoryViewAction(){
       log.fine("category:" + this.categoryName);
       if(this.categoryName == null){
           this.categoryName = "Technology";
       }
        this.categoryEntries = weblogger.getWeblogEntryManager()
                .getWeblogEntriesByCategoryName(categoryName);
       log.fine("number of categoryEntries:" + this.categoryEntries.size());
    }
}
