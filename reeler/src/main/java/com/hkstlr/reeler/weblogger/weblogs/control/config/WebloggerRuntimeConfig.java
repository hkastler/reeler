package com.hkstlr.reeler.weblogger.weblogs.control.config;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 * package org.apache.roller.weblogger.config;
 *
 */

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.hkstlr.reeler.weblogger.weblogs.control.config.runtime.RuntimeConfigDefs;
import com.hkstlr.reeler.weblogger.weblogs.control.config.runtime.RuntimeConfigDefsParser;
import com.hkstlr.reeler.weblogger.weblogs.entities.RuntimeConfigProperty;
import com.hkstlr.reeler.app.control.AppConstants;
import com.hkstlr.reeler.app.control.StringPool;
import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.admin.RuntimeConfigManager;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Named;


/**
 * This class acts as a convenience gateway for getting property values
 * via the PropertiesManager.  We do this because most calls to the
 * PropertiesManager are just to get the value of a specific property and
 * thus the caller doesn't need the full RuntimeConfigProperty object.
 * 
 * We also provide some methods for converting to different data types.
 */

@Named
@Singleton
@Startup
@ConcurrencyManagement
public class WebloggerRuntimeConfig {
    
    private static final Logger LOG = Logger.getLogger(WebloggerRuntimeConfig.class.getName());
    
    private static final String RUNTIME_CONFIG = "/com/hkstlr/reeler/weblogger/control/config/runtime/runtimeConfigDefs.xml";
    private RuntimeConfigDefs configDefs;
    
    // special case for our context urls
    private static String relativeContextURL;
    private static String absoluteContextURL;
    private Map<String, String> props = new HashMap();
    
    @EJB
    RuntimeConfigManager runtimeConfigManager;
    
    
    protected WebloggerRuntimeConfig() {
        // prevent instantiations
    }
    
    @PostConstruct
    protected void init(){
        LOG.info("init");
        if(this.props.isEmpty()) {
            this.props = runtimeConfigManager.getProperties();
            if(this.props.isEmpty()){
                LOG.info("loading from xml config file on first time run");
                LOG.info(getRuntimeConfigDefsAsString());
                configDefs = getRuntimeConfigDefs();
                configDefs.getConfigDefs().forEach((cd) -> {
                    cd.getDisplayGroups().forEach((dg) -> {
                       dg.getPropertyDefs().forEach((prop) -> {
                           LOG.info(prop.getName());
                           props.put(prop.getName(), prop.getDefaultValue());
                       });
                    });
                });
                runtimeConfigManager.saveProperties(props);

            }
        } 
    }

    public Map<String, String> getProps() {
        return props;
    }

    public void setProps(Map<String, String> props) {
        this.props = props;
    }
    
    
    /**
     * Retrieve a single property from the PropertiesManager ... returns null
     * if there is an error
     * @param name
     * @return 
     **/
    public String getProperty(String name) {
        
        String value = null;
        
        try {
            
            RuntimeConfigProperty prop = runtimeConfigManager.getProperty(name);
            if(prop != null) {
                value = prop.getValue();
            }
        } catch(Exception e) {
            LOG.log(Level.WARNING,"Trouble accessing property: "+name , e);
        }
        
        LOG.fine("fetched property ["+name+"="+value+"]");

        return value;
    }
    
    
    /**
     * Retrieve a property as a boolean ... defaults to false if there is an error
     **/
    public boolean getBooleanProperty(String name) {
        
        // get the value first, then convert
        String value = getProperty(name);
        
        if (value == null) {
            return false;
        }

        return Boolean.valueOf(value);
    }
    
    
    /**
     * Retrieve a property as an int ... defaults to -1 if there is an error
     **/
    public int getIntProperty(String name) {
        
        // get the value first, then convert
        String value = getProperty(name);
        
        if (value == null) {
            return -1;
        }
        
        int intval = -1;
        try {
            intval = Integer.parseInt(value);
        } catch(Exception e) {
            LOG.log(Level.FINE,"Trouble converting to int: "+name, e);
        }
        
        return intval;
    }
    
    
    public RuntimeConfigDefs getRuntimeConfigDefs() {
        
        if(configDefs == null) {
            
            // unmarshall the config defs file
            try {
                InputStream is = 
                        WebloggerRuntimeConfig.class.getResourceAsStream(RUNTIME_CONFIG);
                
                RuntimeConfigDefsParser parser = new RuntimeConfigDefsParser();
                configDefs = parser.unmarshall(is);
                
            } catch(Exception e) {
                // error while parsing :(
                LOG.log(Level.SEVERE,"Error parsing runtime config defs", e);
            }
            
        }
        
        return configDefs;
    }
    
    
    /**
     * Get the runtime configuration definitions XML file as a string.
     *
     * This is basically a convenience method for accessing this file.
     * The file itself contains meta-data about what configuration
     * properties we change at runtime via the UI and how to setup
     * the display for editing those properties.
     */
    public String getRuntimeConfigDefsAsString() {
        
        LOG.fine("Trying to load runtime config defs file");
        
        try {
            InputStreamReader reader =
                    new InputStreamReader(WebloggerConfig.class.getResourceAsStream(RUNTIME_CONFIG));
            StringWriter configString = new StringWriter();
            
            char[] buf = new char[AppConstants.EIGHT_KB_IN_BYTES];
            int length = 0;
            while((length = reader.read(buf)) > 0) {
                configString.write(buf, 0, length);
            }
            
            reader.close();
            
            return configString.toString();
        } catch(Exception e) {
            LOG.log(Level.SEVERE,"Error loading runtime config defs file", e);
        }
        
        return StringPool.BLANK;
    }
    
    
    /**
     * Special method which sets the non-persisted absolute url to this site.
     *
     * This property is *not* persisted in any way.
     */
    public static void setAbsoluteContextURL(String url) {
        absoluteContextURL = url;
    }
    
    
    /**
     * Get the absolute url to this site.
     *
     * This method will just return the value of the "site.absoluteurl"
     * property if it is set, otherwise it will return the non-persisted
     * value which is set by the InitFilter.
     */
    public String getAbsoluteContextURL() {
        
        // db prop takes priority if it exists
        String absURL = getProperty("site.absoluteurl");
        if(absURL != null && absURL.trim().length() > 0) {
            return absURL;
        }
        
        return absoluteContextURL;
    }
    
    
    /**
     * Special method which sets the non-persisted relative url to this site.
     *
     * This property is *not* persisted in any way.
     */
    public static void setRelativeContextURL(String url) {
        relativeContextURL = url;
    }
    
    
    public String getRelativeContextURL() {
        return relativeContextURL;
    }
    
    
    /**
     * Convenience method for Roller classes trying to determine if a given
     * weblog handle represents the front page blog.
     */
    public boolean isFrontPageWeblog(String weblogHandle) {
        
        String frontPageHandle = getProperty("site.frontpage.weblog.handle");
        if(frontPageHandle == null){
            return false;
        }
        
        return (frontPageHandle.equals(weblogHandle));
    }
    
    
    /**
     * Convenience method for Roller classes trying to determine if a given
     * weblog handle represents the front page blog configured to render
     * site-wide data.
     */
    public boolean isSiteWideWeblog(String weblogHandle) {
        
        boolean siteWide = getBooleanProperty("site.frontpage.weblog.aggregated");
        
        return (isFrontPageWeblog(weblogHandle) && siteWide);
    }
    
}
