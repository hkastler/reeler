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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author henry.kastler
 */
@ManagedBean(name="weblogAuthorBean")
@ViewScoped
public class WeblogAuthorBean implements Serializable {
    
    @ManagedProperty(value = "#{reelerUiBean.currentWeblog}")
    private Weblog weblog;
    
    @Inject
    private ReelerUIBean reelerUiBean;
    
    //@ManagedProperty(value = "#{}")
    //private String selectedWeblogId;
    
     
    @Inject
    private Weblogger weblogger;
    
    
    private static Logger log = Logger.getLogger(WeblogAuthorBean.class.getName());

    public WeblogAuthorBean() {
    }

    @PostConstruct
    private void init() {
        log.info("current Weblog: " + weblog.getName());
        
        
        //set the default categories
        //this.weblog.setWeblogCategories(wcm.findAll());
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

    /* public String getSelectedWeblogId() {
    return selectedWeblogId;
    }
    
    public void setSelectedWeblogId(String selectedWeblogId) {
    this.selectedWeblogId = selectedWeblogId;
    }*/
    
    
       

    @Transactional(Transactional.TxType.MANDATORY)
    public void createWeblog() throws WebloggerException {
        
        //set in addWeblogContents?
        //WeblogPermission wp = new WeblogPermission(weblog, reelerUiBean.getUser(), "admin");
        //wp.setPending(Boolean.FALSE);
        //weblogger.getWeblogPermissionManager().save(wp);
        weblogger.getWeblogManager().addWeblog(weblog,reelerUiBean.getUser());
    }
    
    @Transactional(Transactional.TxType.MANDATORY)
    public void updateWeblog() throws WebloggerException {
        
        //set in addWeblogContents?
        //WeblogPermission wp = new WeblogPermission(weblog, reelerUiBean.getUser(), "admin");
        //wp.setPending(Boolean.FALSE);
        //weblogger.getWeblogPermissionManager().save(wp);
        log.info("updating weblog");
        log.info("weblog:" + weblog.getTagline());
        weblogger.getWeblogManager().saveWeblog(weblog);
        
        FacesMessageManager.addSuccessMessage("editWeblog", "Weblog Updated");
        
    }
    
    public void configViewAction(){
        log.info("configViewAction here");
        log.info("currentWeblog:" + weblog.getName());       
    }
    
     //@FacesConverter(forClass = Weblog.class)
    public static class WeblogConverter implements Converter {

       @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
       
        try {
            String id = value;
            //.find apparently only works with numeric fields
            log.info("id:" + id);
            Weblog web = new Weblog();//weblogger.getWeblogManager().findById(id);//session.load(CatalogValue .class, id);
            log.info("returned Weblog:");
            return web;
        } catch (Exception ex) {
            log.info("yes it's a fail");
            String message = ex.getMessage();
            log.log(Level.INFO,"reason:",ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, message));
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
         return ((Weblog) value).getId();
    }

    }
}
