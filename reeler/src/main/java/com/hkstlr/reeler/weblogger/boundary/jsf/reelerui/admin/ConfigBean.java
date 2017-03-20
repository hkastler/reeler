/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.boundary.jsf.reelerui.admin;

import com.hkstlr.reeler.weblogger.boundary.jsf.reelerui.ReelerUIBean;
import com.hkstlr.reeler.weblogger.boundary.manager.admin.RuntimeConfigManager;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author henry.kastler
 */
/**
 *
 * @author henry.kastler
 */
@ManagedBean
@RequestScoped
public class ConfigBean {
    
    @Inject
    private ReelerUIBean reelerUiBean;
    
    @Inject
    private RuntimeConfigManager runtimeConfigManager;
    
    private Map<String,String> pages = new LinkedHashMap<>();
    
    private String path;
    
      
    private ResourceBundle res;

   
    
    public ConfigBean() {
    }
    
    @PostConstruct
    private void init(){
        
        pages.put("config", "Settings");
        pages.put("members", "Members");
        pages.put("pings", "Pings");
        pages.put("maintenance", "Maintenance");
        this.path = reelerUiBean.getReelerUiPath() + "/weblog/settings";
        setRes("/ApplicationResources");
    }
    
    public Map<String, String> getPages() {
        return pages;
    }

    public void setPages(Map<String, String> pages) {
        this.pages = pages;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public RuntimeConfigManager getRuntimeConfigManager() {
        return runtimeConfigManager;
    }

    public void setRuntimeConfigManager(RuntimeConfigManager runtimeConfigManager) {
        this.runtimeConfigManager = runtimeConfigManager;
    }
    
     public ResourceBundle getRes() {
        return res;
    }

    public void setRes(String bundleLocation) {
        this.res = ResourceBundle.getBundle(bundleLocation);
    }
    
    
    
    
    
    
}
