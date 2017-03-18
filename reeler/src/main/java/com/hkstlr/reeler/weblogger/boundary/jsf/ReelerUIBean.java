/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.boundary.jsf;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.boundary.Weblogger;
import com.hkstlr.reeler.weblogger.entities.Weblog;
import com.hkstlr.reeler.weblogger.entities.WeblogPermission;
import com.hkstlr.reeler.weblogger.users.entities.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author henry.kastler
 */
@ManagedBean(name = "reelerUiBean")
@SessionScoped
public class ReelerUIBean implements Serializable{
    
    private Logger log = Logger.getLogger(ReelerUIBean.class.getName());

    
    @Inject
    Weblogger weblogger;

    private User user;

    private List<Weblog> userWeblogs;

    private Map<String, String> userWeblogPermissions;
    
    private final String reelerUiPath = "/weblogger/reeler-ui";

    
    public ReelerUIBean() {
        setUserFromSession();
        log.fine("reelerUiBean user:" + this.user.getUserName());
    }

    
    @PostConstruct
    private void init(){
        setUserWeblogs();
        try {
            setWeblogPermissions();
        } catch (WebloggerException ex) {
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
    
    public Map<String, String> getUserWeblogPermissions(){
        return userWeblogPermissions;
    }

    public List<Weblog> getUserWeblogs() {
        return userWeblogs;
    }
    
    public void setUserWeblogs(User user) {
        
        List<Weblog> ublogs = null;
        try {
            ublogs = weblogger.getWeblogManager().getUserWeblogs(this.user, true);
        } catch (WebloggerException ex) {
            Logger.getLogger(ReelerUIBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (ublogs == null) {
            ublogs = new ArrayList<>();
        }
        this.userWeblogs = ublogs;
    }

    public void setUserWeblogs() {
        //log.fine("getUserWeblog user:" + this.user.getUserName());
        setUserWeblogs(this.user);

    }

    public void setWeblogPermissions(List<Weblog> blogs) {
        
        Map<String, String> weblogPermissionMap = new HashMap<>();
        StringBuilder permString = new StringBuilder();
        for(Weblog blog : blogs){
            List<WeblogPermission> perms = new ArrayList<>();
            try {
                perms = weblogger.getWeblogPermissionManager().getWeblogPermissions(blog);
                log.info("perms:" + perms.size());
                
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
    
    public void setWeblogPermissions() throws WebloggerException{
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

    public String getReelerUiPath() {
        return reelerUiPath;
    }
    
    

}
