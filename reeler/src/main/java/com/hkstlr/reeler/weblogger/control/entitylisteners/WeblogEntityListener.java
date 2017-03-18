/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.control.entitylisteners;

import com.hkstlr.reeler.weblogger.boundary.manager.WeblogCategoryManager;
import com.hkstlr.reeler.weblogger.entities.Weblog;
import com.hkstlr.reeler.weblogger.entities.WeblogCategory;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import org.ocpsoft.logging.Logger;

/**
 *
 * @author henry.kastler
 */
public class WeblogEntityListener {
    
    @EJB
    WeblogCategoryManager wcm;
    
    Logger log = Logger.getLogger(WeblogEntityListener.class.getName());

    @PrePersist
    public void weblogPrePersist(Weblog ob) {
        System.out.println("Listening Weblog Pre Persist : " + ob.getName());
    }

    @PostPersist
    public void weblogPostPersist(Weblog ob) {
        System.out.println("Listening Weblog Post Persist : " + ob.getName());
    }

    @PostLoad
    public void weblogPostLoad(Weblog ob) {
        //System.out.println("Listening Weblog Post Load : " + ob.getName());
        //List<WeblogCategory> weblogCategories = wcm.getWeblogCategoriesForWeblog(ob);
        System.out.println("weblogCategories:" + ob.getWeblogCategories().size());
        //ob.setWeblogCategories(weblogCategories);
    }

    @PreUpdate
    public void weblogPreUpdate(Weblog ob) {
        System.out.println("Listening Weblog Pre Update : " + ob.getName());
    }

    @PostUpdate
    public void weblogPostUpdate(Weblog ob) {
        System.out.println("Listening Weblog Post Update : " + ob.getName());
    }

    @PreRemove
    public void weblogPreRemove(Weblog ob) {
        System.out.println("Listening Weblog Pre Remove : " + ob.getName());
    }

    @PostRemove
    public void userPostRemove(Weblog ob) {
        System.out.println("Listening Weblog Post Remove : " + ob.getName());
    }
}
