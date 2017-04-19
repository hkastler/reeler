/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.boundary.Weblogger;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogPermission;
import com.hkstlr.reeler.weblogger.users.entities.User;
import com.hkstlr.reeler.weblogger.users.entities.UserRole;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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

    private transient Logger log = Logger.getLogger(ReelerUIBean.class.getName());

    @EJB
    private transient Weblogger weblogger;

    private User user;

    private List<Weblog> userWeblogs;

    private Map<String, String> userWeblogPermissions;

    private Map<String, String> pages = new LinkedHashMap<>();

    private static final String PAGE_HOME = "/weblogger/reeler-ui";

    private static final String PATH = PAGE_HOME + "/weblog";

    private Weblog currentWeblog = new Weblog();

    public ReelerUIBean() {
        setUserFromSession();
    }

    @PostConstruct
    public void init() {
        log.log(Level.INFO, "expected that a session bean would init only once");
        pages.put("entry", "Create Entry");
        pages.put("entries", "Entries");
        pages.put("comments", "Comments");
        pages.put("categories", "Categories");
        pages.put("blogrolls", "Blogrolls");
        pages.put("mediafiles", "Media Files");
    }

    /**
     * Get the value of user
     *
     * @return the value of user
     */
    public User getUser() {
        return user;
    }

    public List<UserRole> getUserRolesAsList(Set<UserRole> roles) {
        return new ArrayList<UserRole>(roles);
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

            ublogs = weblogger.getWeblogManager().getUserWeblogs(user, true);
        } catch (WebloggerException ex) {
            Logger.getLogger(ReelerUIBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (ublogs == null) {
            ublogs = new ArrayList<>();
        }
        for (Weblog blog : ublogs) {
            
            finalUblogs.add(blog);
            //refresh the current weblog
            if (currentWeblog != null) {
                if (blog.equals(currentWeblog)) {
                    currentWeblog = blog;
                }
            }
        }
        this.userWeblogs = null;
        this.userWeblogs = finalUblogs;
    }

    public void setUserWeblogs() {
        setUserWeblogs(this.user);
    }

    public void setWeblogPermissions(List<Weblog> blogs) {

        Map<String, String> weblogPermissionMap = new HashMap<>();

        for (Weblog blog : blogs) {
            StringBuilder permString = new StringBuilder();
            List<WeblogPermission> perms;
            try {
                perms = weblogger.getWeblogPermissionManager().getWeblogPermissions(blog);

                for (WeblogPermission perm : perms) {
                    permString.append(perm.getActions());
                }

            } catch (WebloggerException ex) {
                Logger.getLogger(ReelerUIBean.class.getName()).log(Level.SEVERE, null, ex);
            }

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

    public void setUserFromSession() {

        this.user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        user.getPermissions().forEach((t) -> {
            log.info(t.toString());
        });
    }

    public String getPATH() {
        return PATH;
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

    public String getPAGE_HOME() {
        return PAGE_HOME;
    }

    public void action(Weblog weblog, String page) throws WebloggerException {
        log.finer("setting weblog and redirecting...");
        this.currentWeblog = weblog;
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            StringBuilder actionPath = new StringBuilder(context.getRequestContextPath());
            actionPath.append(this.PATH).append("/");
            if ("config".equals(page)) {
                actionPath.append("settings/");
            }
            actionPath.append(page).append(".xhtml");
            context.redirect(actionPath.toString());
        } catch (IOException ex) {
            log.log(Level.SEVERE,"action error",ex);
            
        }
    }

    public void newWeblog() throws WebloggerException {

        this.currentWeblog = new Weblog();
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            StringBuilder createPath = new StringBuilder(context.getRequestContextPath());
            createPath.append(this.PATH);
            createPath.append("/create.xhtml");
            context.redirect(createPath.toString());
        } catch (IOException ex) {
            log.log(Level.SEVERE,"newWeblog",ex);
        }
    }

    public void indexViewAction() {
        try {
            setUserWeblogs();
        } catch (Exception e) {
        }
        try {
            setWeblogPermissions();
        } catch (WebloggerException ex) {
            Logger.getLogger(ReelerUIBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void blogrollsViewAction() {
        setUserWeblogs();
        try {
            setWeblogPermissions();
        } catch (WebloggerException ex) {
            Logger.getLogger(ReelerUIBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
