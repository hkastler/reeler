/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.weblog;

import com.hkstlr.reeler.weblogger.weblogs.boundary.Weblogger;
import com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.ReelerUIBean;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogPermission;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author henry.kastler
 */
@ManagedBean
@RequestScoped
public class WeblogAuthorBean {

    private Weblog weblog = new Weblog();

    @Inject
    private ReelerUIBean reelerUiBean;
    
    @Inject
    private Weblogger weblogger;

    public WeblogAuthorBean() {
    }

    @PostConstruct
    private void init() {
        this.weblog.setCreator(reelerUiBean.getUser().getUserName());
    }

    public Weblog getWeblog() {
        return weblog;
    }

    public void setWeblog(Weblog weblog) {
        this.weblog = weblog;
    }
       

    public void createWeblog() {
        weblog.setDateCreated(new Date());
        WeblogPermission wp = new WeblogPermission(weblog, reelerUiBean.getUser(), "admin");
        wp.setPending(Boolean.FALSE);
        weblogger.getWeblogPermissionManager().save(wp);
        weblogger.getWeblogManager().save(weblog);
    }
}
