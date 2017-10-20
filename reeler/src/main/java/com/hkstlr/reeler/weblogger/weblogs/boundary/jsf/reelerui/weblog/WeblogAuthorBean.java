/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.weblog;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.boundary.Weblogger;
import com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.ReelerUIBean;
import com.hkstlr.reeler.weblogger.weblogs.control.jsf.FacesMessageManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author henry.kastler
 */
@ManagedBean(name = "weblogAuthorBean")
@ViewScoped
public class WeblogAuthorBean implements Serializable {

    @ManagedProperty(value = "#{reelerUiBean.currentWeblog}")
    private Weblog weblog;

    @Inject
    private ReelerUIBean reelerUiBean;

    @Inject
    private Weblogger weblogger;

    private static Logger log = Logger.getLogger(WeblogAuthorBean.class.getName());

    public WeblogAuthorBean() {
        //constructor
    }

    public Weblog getWeblog() {
        return weblog;
    }

    public void setWeblog(Weblog weblog) {
        this.weblog = weblog;
    }

    public ReelerUIBean getReelerUiBean() {
        return reelerUiBean;
    }

    public void setReelerUiBean(ReelerUIBean reelerUiBean) {
        this.reelerUiBean = reelerUiBean;
    }

    @Transactional(Transactional.TxType.MANDATORY)
    public void createWeblog() throws WebloggerException {
        String msg;
        Weblog check = weblogger.getWeblogManager()
                .findByHandle(weblog.getHandle());
        
        if (check == null) {
            weblogger.getWeblogManager().addWeblog(weblog, reelerUiBean.getUser());
            msg = java.text.MessageFormat
                    .format("Weblog {0} Created", new String[]{weblog.getName()});
            FacesMessageManager.addSuccessMessage("createWeblogMsgs", msg);
         } else {
            msg = java.text.MessageFormat
                    .format("Weblog with handle {0} already exists", new String[]{weblog.getHandle()});
            FacesMessageManager.addErrorMessage(msg);
        }

    }

    @Transactional(Transactional.TxType.MANDATORY)
    public void updateWeblog() throws WebloggerException {

        weblogger.getWeblogManager().saveWeblog(weblog);

        FacesMessageManager.addSuccessMessage("editWeblog", "Weblog Updated");

    }

    public void configViewAction() {
       //do something?
    }

}
