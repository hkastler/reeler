/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.admin;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author henry.kastler
 */
@RolesAllowed("admin")
@ManagedBean
@RequestScoped
public class AdminUIBean {
    
    private Map<String, String[]> pages = new LinkedHashMap<>();

    private static final String PATH = "/weblogger/reeler-ui/admin";
    
    private static final String template = "/WEB-INF/weblogger/templates/reeler-ui/pageContentTemplate.xhtml";
    
    @ManagedProperty(value="#{param.page}")
    private String page;

    public AdminUIBean() {
    }
    
    @PostConstruct
    public void init(){
        if(page == null){
            page = "index";
        }
        pages.put("index", new String[]{"label_configuration","label_serveradmin"});        
        pages.put("users", new String[]{"label_useradmin"});        
        pages.put("comments", new String[]{"label_globalcomment"});        
        pages.put("pings", new String[]{"label_pings"});        
        pages.put("cacheinfo", new String[]{"label_cacheinfo"});        
    }

    public Map<String, String[]> getPages() {
        return pages;
    }

    public void setPages(Map<String, String[]> pages) {
        this.pages = pages;
    }

    public String getPATH() {
        return PATH;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTemplate() {
        return template;
    }
    
    public void viewAction(String page){
        this.page = page;
    }
    
}
