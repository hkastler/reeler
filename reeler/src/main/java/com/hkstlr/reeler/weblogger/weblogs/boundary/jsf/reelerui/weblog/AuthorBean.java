/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.weblog;

import com.hkstlr.reeler.weblogger.weblogs.boundary.Weblogger;
import com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.ReelerUIBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author henry.kastler
 */
public abstract class AuthorBean<T> {
    
    private Class<T> entityClass;
    
    @EJB
    Weblogger weblogger;
    
    @ManagedProperty(value = "#{reelerUiBean}")
    ReelerUIBean reelerUiBean;
    
    @ManagedProperty(value = "#{param.id}")
    protected String id;
    
    @ManagedProperty(value = "#{param.action}")
    protected String action;
    
    protected String actionLabel;

    public AuthorBean() {
        
    }
    
    public AuthorBean(Class<T> entityClass){
        this.entityClass = entityClass;
    }
    
   

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionLabel() {
        return actionLabel;
    }

    public void setActionLabel(String actionLabel) {
        this.actionLabel = actionLabel;
    }

    public Weblogger getWeblogger() {
        return weblogger;
    }

    public void setWeblogger(Weblogger weblogger) {
        this.weblogger = weblogger;
    }

    public ReelerUIBean getReelerUiBean() {
        return reelerUiBean;
    }

    public void setReelerUiBean(ReelerUIBean reelerUiBean) {
        this.reelerUiBean = reelerUiBean;
    }
    
    public void setActionLabel(){
        if("create".equals(action)){
            actionLabel = "Create";
        }else if("edit".equals(action)){
            actionLabel = "Edit";
        }
    }
       
    
    
}
