/*
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
        //default constructor
    }
    
    @PostConstruct
    private void init(){
        
        pages.put("config", "Settings");
        pages.put("members", "Members");
        pages.put("pings", "Pings");
        pages.put("maintenance", "Maintenance");
        setPath(reelerUiBean.getPath() + "/settings");
       
    }
    
    public Map<String, String> getPages() {
        return pages;
    }

    public void setPages(Map<String, String> pages) {
        this.pages = pages;
    }

    public String getPath() {
        return PATH;
    }

    public static void setPath(String path) {
        SettingsUIBean.PATH = path;
    }
       
}
