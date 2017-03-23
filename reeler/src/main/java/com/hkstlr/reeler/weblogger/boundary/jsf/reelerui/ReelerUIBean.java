/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.boundary.jsf.reelerui;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.boundary.Weblogger;
import com.hkstlr.reeler.weblogger.entities.Weblog;
import com.hkstlr.reeler.weblogger.entities.WeblogPermission;
import com.hkstlr.reeler.weblogger.users.entities.User;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author henry.kastler
 */
@ManagedBean(name = "reelerUiBean")
@SessionScoped
public class ReelerUIBean implements Serializable {

    private Logger log = Logger.getLogger(ReelerUIBean.class.getName());

    @Inject
    Weblogger weblogger;

    private User user;

    private List<Weblog> userWeblogs;

    private Map<String, String> userWeblogPermissions;

    private Map<String, String> pages = new LinkedHashMap<>();

    private final String path = "/weblogger/reeler-ui";

    private Weblog currentWeblog;

    public ReelerUIBean() {
        setUserFromSession();
        log.fine("reelerUiBean user:" + this.user.getUserName());

    }

    @PostConstruct
    private void init() {
        log.log(Level.INFO, "expected that a session bean would init only once");
        pages.put("entry", "New Entry");
        pages.put("entries", "Entries");
        pages.put("comments", "Comments");
        pages.put("categories", "Categories");
        pages.put("blogrolls", "Blogrolls");
        pages.put("mediafiles", "Media Files");

        setUserWeblogs();
        try {
            setWeblogPermissions();
        } catch (Exception ex) {
            Logger.getLogger(ReelerUIBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get the value of user
     *
     * @return the value of user
     */
    public User getUser() {
        return user;
    }

    public Map<String, String> getUserWeblogPermissions() {
        return userWeblogPermissions;
    }

    public List<Weblog> getUserWeblogs() {
        return userWeblogs;
    }

    public void setUserWeblogs(User user) {

        List<Weblog> ublogs = null;
        List<Weblog> finalUblogs = new ArrayList<>();
        try {

            ublogs = weblogger.getWeblogManager().getUserWeblogs(this.user, true);
        } catch (WebloggerException ex) {
            Logger.getLogger(ReelerUIBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (ublogs == null) {
            ublogs = new ArrayList<>();
        }
        for (Weblog blog : ublogs) {
            log.info("weblog:" + blog.getHandle() + " has " + blog.getWeblogEntries().size() + " entries");
            finalUblogs.add(blog);
        }
        this.userWeblogs = finalUblogs;
    }

    public void setUserWeblogs() {
        //log.fine("getUserWeblog user:" + this.user.getUserName());
        setUserWeblogs(this.user);

    }

    public void setWeblogPermissions(List<Weblog> blogs) {

        Map<String, String> weblogPermissionMap = new HashMap<>();
        StringBuilder permString = new StringBuilder();
        for (Weblog blog : blogs) {
            List<WeblogPermission> perms;
            try {
                perms = weblogger.getWeblogPermissionManager().getWeblogPermissions(blog);
                log.info("perms for blog:" + blog.getHandle() + ":" + perms.size());

                for (WeblogPermission perm : perms) {
                    permString.append(perm.getActions());
                }

            } catch (WebloggerException ex) {
                Logger.getLogger(ReelerUIBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            //log.info("permissions:" + perms.toString());
            weblogPermissionMap.put(blog.getHandle(), permString.toString());
        }

        this.userWeblogPermissions = weblogPermissionMap;
    }

    public void setWeblogPermissions() throws WebloggerException {
        setWeblogPermissions(this.userWeblogs);
    }

    /**
     * Set the value of user
     *
     * @param user new value of user
     */
    public void setUser(User user) {
        this.user = user;
    }

    private void setUserFromSession() {
        this.user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }

    public String getPath() {
        return path;
    }

    public Weblog getCurrentWeblog() {
        return currentWeblog;
    }

    public void setCurrentWeblog(Weblog currentWeblog) {
        this.currentWeblog = currentWeblog;
    }

    public Map<String, String> getPages() {
        return pages;
    }

    public void setPages(Map<String, String> pages) {
        this.pages = pages;
    }

    public void action(Weblog weblog, String page) throws WebloggerException {
        log.info("setting weblog and redirecting...");
        this.currentWeblog = weblog;
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            StringBuilder path = new StringBuilder(context.getRequestContextPath());
            path.append(this.path);
            path.append("/weblog/").append(page).append(".xhtml");
            context.redirect(path.toString());
        } catch (IOException ex) {
            log.severe(ex.getMessage());
        }
    }

}
