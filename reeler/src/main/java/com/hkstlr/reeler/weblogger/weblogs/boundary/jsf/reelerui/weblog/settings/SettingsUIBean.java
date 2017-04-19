/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.weblog.settings;

import com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.ReelerUIBean;
import java.util.LinkedHashMap;
import java.util.Map;
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
public class SettingsUIBean {
    
    @Inject
    private ReelerUIBean reelerUiBean;
        
    private Map<String,String> pages = new LinkedHashMap<>();
    
    private static String PATH;
      
    public SettingsUIBean() {
    }
    
    @PostConstruct
    private void init(){
        
        pages.put("config", "Settings");
        pages.put("members", "Members");
        pages.put("pings", "Pings");
        pages.put("maintenance", "Maintenance");
        this.PATH = reelerUiBean.getPATH() + "/settings";
       
    }
    
    public Map<String, String> getPages() {
        return pages;
    }

    public void setPages(Map<String, String> pages) {
        this.pages = pages;
    }

    public String getPATH() {
        return PATH;
    }

    public void setPATH(String path) {
        this.PATH = path;
    }
       
}
