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

import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.admin.RuntimeConfigManager;
import com.hkstlr.reeler.weblogger.weblogs.control.config.WebloggerRuntimeConfig;
import com.hkstlr.reeler.weblogger.weblogs.control.config.runtime.RuntimeConfigDefs;
import com.hkstlr.reeler.weblogger.weblogs.control.jsf.FacesMessageManager;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author henry.kastler
 */

@RolesAllowed("admin")
@ManagedBean
@RequestScoped
public class RuntimeConfigBean {
    
    private static final Logger LOG = Logger.getLogger(RuntimeConfigBean.class.getName());
    
    @Inject
    private AdminUIBean adminUIBean;
    
    @Inject
    private RuntimeConfigManager runtimeConfigManager;
    
    @Inject
    private WebloggerRuntimeConfig webloggerRuntimeConfig;
    
    @Named
    private Map<String,String> runtimeConfigs;
    
    @Named
    private RuntimeConfigDefs runtimeConfigDefs;
    
    private ResourceBundle res;

    public RuntimeConfigBean() {
        //constructor
    }
    
    @PostConstruct
    public void init(){       
        runtimeConfigDefs = webloggerRuntimeConfig.getRuntimeConfigDefs();
        runtimeConfigs = runtimeConfigManager.getProperties();
        
        setRes("/ApplicationResources");        
    }

    public RuntimeConfigManager getRuntimeConfigManager() {
        return runtimeConfigManager;
    }

    public void setRuntimeConfigManager(RuntimeConfigManager runtimeConfigManager) {
        this.runtimeConfigManager = runtimeConfigManager;
    }

    public RuntimeConfigDefs getRuntimeConfigDefs() {
        return runtimeConfigDefs;
    }

    public void setRuntimeConfigDefs(RuntimeConfigDefs runtimeConfigDefs) {
        this.runtimeConfigDefs = runtimeConfigDefs;
    }
    
    

    public Map<String, String> getRuntimeConfigs() {
        return runtimeConfigs;
    }

    public void setRuntimeConfigs(Map<String, String> runtimeConfigs) {
        this.runtimeConfigs = runtimeConfigs;
    }
    
        
    public ResourceBundle getRes() {
        return res;
    }

    public void setRes(ResourceBundle res) {
        this.res = res;
    }
    
    
    public void setRes(String bundleLocation) {
        this.res = ResourceBundle.getBundle(bundleLocation);
    }

       
    
    public void updateConfigs() {
        
        runtimeConfigManager.saveProperties(runtimeConfigs);
        webloggerRuntimeConfig.setProps(runtimeConfigs);
        
        FacesMessageManager.addSuccessMessage("globalConfig", "Config updated");
        
    }    
}
