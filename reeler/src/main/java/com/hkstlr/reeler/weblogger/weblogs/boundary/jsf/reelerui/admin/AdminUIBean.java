/*
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.admin;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

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
    
    private static final String TEMPLATE = "/WEB-INF/weblogger/templates/reeler-ui/pageContentTemplate.xhtml";
    
    @ManagedProperty(value="#{param.page}")
    private String page;

    public AdminUIBean() {
        //default constructor
    }
    
    @PostConstruct
    public void init(){
        if(page == null){
            page = "index";
        }
        pages.put("index", new String[]{"reeler-ui_admin_index","reeler-ui_admin_serveradmin"});        
        pages.put("users", new String[]{"reeler-ui_admin_users"});        
        pages.put("comments", new String[]{"reeler-ui_admin_comments"});        
        pages.put("pings", new String[]{"reeler-ui_admin_pings"});        
        pages.put("cacheinfo", new String[]{"reeler-ui_admin_cacheinfo"});        
    }

    public Map<String, String[]> getPages() {
        return pages;
    }

    public void setPages(Map<String, String[]> pages) {
        this.pages = pages;
    }

    public String getPath() {
        return PATH;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTemplate() {
        return TEMPLATE;
    }
    
    public void viewAction(String page){
        this.page = page;
    }
    
}
