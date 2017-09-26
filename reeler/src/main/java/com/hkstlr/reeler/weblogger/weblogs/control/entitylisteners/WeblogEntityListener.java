/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.control.entitylisteners;

import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

/**
 *
 * @author henry.kastler
 */
public class WeblogEntityListener {
    
    @EJB
    WeblogManager wm;
    
    
    Logger log = Logger.getLogger(WeblogEntityListener.class.getName());

    @PrePersist
    public void weblogPrePersist(Weblog ob) {
        //System.out.println("Listening Weblog Pre Persist : " + ob.getName());
    }

    @PostPersist
    public void weblogPostPersist(Weblog ob) {
        //System.out.println("Listening Weblog Post Persist : " + ob.getName());
    }

    @PostLoad
    public void weblogPostLoad(Weblog ob) {
        //System.out.println("Listening Weblog Post Load : " + ob.getName());
        //List<WeblogCategory> weblogCategories = wcm.getWeblogCategoriesForWeblog(ob);
        //System.out.println("weblogCategories:" + ob.getWeblogCategories().size());
        //ob.setWeblogCategories(weblogCategories);
    }

    @PreUpdate
    public void weblogPreUpdate(Weblog ob) {
        //System.out.println("Listening Weblog Pre Update : " + ob.getName());
    }

    @PostUpdate
    public void weblogPostUpdate(Weblog ob) {
        //System.out.println("Listening Weblog Post Update : " + ob.getName());
    }

    @PreRemove
    public void weblogPreRemove(Weblog ob) {
        //System.out.println("Listening Weblog Pre Remove : " + ob.getName());
    }

    @PostRemove
    public void weblogPostRemove(Weblog ob) {
        //System.out.println("Listening Weblog Post Remove : " + ob.getName());
    }
}
