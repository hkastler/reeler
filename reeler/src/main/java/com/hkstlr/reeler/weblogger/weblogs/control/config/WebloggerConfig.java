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
 */
package com.hkstlr.reeler.weblogger.weblogs.control.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Logger;

import javax.ejb.Singleton;
import javax.inject.Named;

import com.hkstlr.reeler.app.control.AuthMethod;
import com.hkstlr.reeler.app.control.PropertyExpander;
import com.hkstlr.reeler.app.control.StringPool;
import java.io.IOException;
import java.util.logging.Level;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Startup;

/**
 * This is the single entry point for accessing configuration properties in
 * Roller.
 */
@Named
@Singleton
@Startup
@ConcurrencyManagement
public class WebloggerConfig {

    private static final String DEFAULT_CONFIG = "/com/hkstlr/reeler/app/control/config/reeler.properties";
    private static final String CUSTOM_CONFIG = "/reeler-custom.properties";
    private static final String JUNIT_CONFIG = "/roller-junit.properties";
    private static final String CUSTOM_JVM_PARAM = "roller.custom.config";
    private static File CUSTOM_CONFIG_FILE = null;

    private static final Properties CONFIG = new Properties();

    private static final Logger LOG = Logger.getLogger(WebloggerConfig.class.getName());

    private WebloggerConfig() {

        LOG.log(Level.FINE, "init WebloggerConfig");
        if (CONFIG.isEmpty()) {
            try {
                // we'll need this to get at our properties files in the classpath
                Class configClass = Class.forName(WebloggerConfig.class.getName());

                // first, lets load our default properties
                InputStream is = configClass.getResourceAsStream(DEFAULT_CONFIG);
                LOG.fine("defaultConfigPath:" + DEFAULT_CONFIG);

                //config is static, so we'll need temp vars
                //TODO: less procedural
                Properties tempConfig = new Properties();

                Properties configLoad = new Properties();
                configLoad.load(is);

                tempConfig.putAll(configLoad);

                LOG.fine("customConfigPath:" + configClass.getResource(CUSTOM_CONFIG));
                // now, see if we can find our custom config
                is = configClass.getResourceAsStream(CUSTOM_CONFIG);

                if (is != null) {
                    Properties customConfigLoad = new Properties();
                    customConfigLoad.load(is);
                    tempConfig.putAll(customConfigLoad);
                    LOG.info("Successfully loaded custom properties file from classpath");
                    LOG.fine("customPropFilePath : " + configClass.getResource(CUSTOM_CONFIG));
                    LOG.finest("customConfigLoad:" + customConfigLoad.toString());
                } else {
                    LOG.severe("No custom properties file found in classpath");
                }

                // finally, check for an external config file
                String env_file = System.getProperty(CUSTOM_JVM_PARAM);
                if (env_file != null && env_file.length() > 0) {
                    CUSTOM_CONFIG_FILE = new File(env_file);

                    // make sure the file exists, then try and load it
                    if (CUSTOM_CONFIG_FILE != null && CUSTOM_CONFIG_FILE.exists()) {
                        try {
                            is = new FileInputStream(CUSTOM_CONFIG_FILE);
                            is.close();
                        } catch (IOException e) {
                            LOG.log(Level.FINE,"CUSTOM_CONFIG_FILE:",e);

                        } finally {
                            if (is != null) {
                                is.close();
                            }
                        }
                        Properties customConfigLoad2 = new Properties();
                        customConfigLoad2.load(is);
                        tempConfig.putAll(customConfigLoad2);

                    } else {
                        LOG.fine("Failed to load custom properties from "
                                + CUSTOM_CONFIG_FILE.getAbsolutePath());
                    }

                }
                // Now expand system properties for properties in the config.expandedProperties list,
                // replacing them by their expanded values.
                String expandedPropertiesDef = (String) tempConfig.get("config.expandedProperties");
                if (expandedPropertiesDef != null) {
                    String[] expandedProperties = expandedPropertiesDef.split(StringPool.COMMA);
                    for (int i = 0; i < expandedProperties.length; i++) {
                        String propName = expandedProperties[i].trim();
                        String initialValue = (String) tempConfig.get(propName);
                        if (initialValue != null) {
                            String expandedValue = PropertyExpander.expandSystemProperties(initialValue);
                            tempConfig.put(propName, expandedValue);
                        }
                    }
                }

                //transfer to static storage            
                this.CONFIG.putAll(tempConfig);
                LOG.finest("config:" + CONFIG.toString());

                if (LOG.isLoggable(Level.FINEST)) {
                    LOG.finest("WebloggerConfig looks like this ...");

                    String key;
                    Enumeration keys = CONFIG.keys();
                    while (keys.hasMoreElements()) {
                        key = (String) keys.nextElement();
                        LOG.finest(key + StringPool.EQUAL + CONFIG.getProperty(key));
                    }
                }

            } catch (Exception e) {
                LOG.log(Level.SEVERE, "WebloggerConfig failed:", e);

            }
        }
    }

    /**
     * Retrieve a property value
     *
     * @param key Name of the property
     * @return String Value of property requested, null if not found
     */
    public static String getProperty(String key) {
        LOG.finer("Fetching property [" + key + "=" + CONFIG.getProperty(key) + "]");
        String value = CONFIG.getProperty(key);

        //for system properties
        if (value.startsWith("$")) {
            String newValue = value.substring(2, value.indexOf('}'));
            String theEndPart = value.split("}")[1];
            newValue = System.getProperty(newValue);
            newValue = newValue.concat(theEndPart);
            newValue = newValue.replace('/', File.separatorChar);
            value = newValue;
        }

        return value == null ? null : value.trim();
    }

    /**
     * Retrieve a property value
     *
     * @param key Name of the property
     * @param defaultValue Default value of property if not found
     * @return String Value of property requested or defaultValue
     */
    public static String getProperty(String key, String defaultValue) {
        LOG.finer("Fetching property [" + key + "=" + CONFIG.getProperty(key) + ",defaultValue=" + defaultValue + "]");
        String value = CONFIG.getProperty(key);
        if (value == null) {
            return defaultValue;
        }

        return value.trim();
    }

    /**
     * Retrieve a property as a boolean ... defaults to false if not present.
     */
    public static boolean getBooleanProperty(String name) {
        return getBooleanProperty(name, false);
    }

    /**
     * Retrieve a property as a boolean ... with specified default if not
     * present.
     */
    public static boolean getBooleanProperty(String name, boolean defaultValue) {
        // get the value first, then convert
        String value = CONFIG.getProperty(name);

        if (value == null) {
            return defaultValue;
        }

        return Boolean.valueOf(value);
    }

    /**
     * Retrieve a property as an int ... defaults to 0 if not present.
     *
     * @param name
     * @return
     */
    public static int getIntProperty(String name) {
        return getIntProperty(name, 0);
    }

    /**
     * Retrieve a property as a int ... with specified default if not present.
     */
    public static int getIntProperty(String name, int defaultValue) {
        // get the value first, then convert
        String value = WebloggerConfig.getProperty(name);

        if (value == null) {
            return defaultValue;
        }

        return Integer.valueOf(value);
    }

    /**
     * Retrieve all property keys
     *
     * @return Enumeration A list of all keys
     *
     */
    public static Enumeration keys() {
        return CONFIG.keys();
    }

    /**
     * Get properties starting with a specified string.
     *
     * @param startingWith
     * @return
     */
    public static Properties getPropertiesStartingWith(String startingWith) {
        Properties props = new Properties();
        for (Enumeration it = CONFIG.keys(); it.hasMoreElements();) {
            String key = (String) it.nextElement();
            props.put(key, CONFIG.get(key));
        }
        return props;
    }

    /**
     * Set the "uploads.dir" property at runtime.
     * <p />
     * Properties are meant to be read-only, but we make this exception because
     * we know that some people are still writing their uploads to the webapp
     * context and we can only get that path at runtime (and for unit testing).
     * <p />
     * This property is *not* persisted in any way.
     */
    public static void setUploadsDir(String path) {
        // only do this if the user wants to use the webapp context
        if ("${webapp.context}".equals(CONFIG.getProperty("uploads.dir"))) {
            CONFIG.setProperty("uploads.dir", path);
        }
    }

    /**
     * Set the "themes.dir" property at runtime.
     * <p />
     * Properties are meant to be read-only, but we make this exception because
     * we know that some people are still using their themes in the webapp
     * context and we can only get that path at runtime (and for unit testing).
     * <p />
     * This property is *not* persisted in any way.
     */
    public static void setThemesDir(String path) {
        // only do this if the user wants to use the webapp context
        if ("${webapp.context}".equals(CONFIG.getProperty("themes.dir"))) {
            CONFIG.setProperty("themes.dir", path);
        }
    }

    /**
     * Return the value of the authentication.method property as an AuthMethod
     * enum value. Matching is done by checking the propertyName of each
     * AuthMethod enum object.
     * <p />
     *
     * @return
     * @throws IllegalArgumentException if property value defined in the
     * properties file is missing or not the property name of any AuthMethod
     * enum object.
     */
    public static AuthMethod getAuthMethod() {
        return AuthMethod.getAuthMethod(getProperty("authentication.method"));
    }

}
