package com.hkstlr.reeler.weblogger.weblogs.boundary.manager.admin;


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
 * *
 *  original package org.apache.roller.weblogger.business;
 */

import com.hkstlr.reeler.weblogger.weblogs.entities.RuntimeConfigProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.weblogger.weblogs.control.config.WebloggerRuntimeConfig;
import com.hkstlr.reeler.weblogger.weblogs.control.config.runtime.ConfigDef;
import com.hkstlr.reeler.weblogger.weblogs.control.config.runtime.DisplayGroup;
import com.hkstlr.reeler.weblogger.weblogs.control.config.runtime.PropertyDef;
import com.hkstlr.reeler.weblogger.weblogs.control.config.runtime.RuntimeConfigDefs;
import java.util.ResourceBundle;

;

/**
 * Manages global properties for Reeler.
 */
@Stateless
public class RuntimeConfigManager extends AbstractManager<RuntimeConfigProperty> {

    public RuntimeConfigManager() {
        super(RuntimeConfigProperty.class);
        
    }

    /**
     * The logger instance for this class.
     */
    private Logger log = Logger.getLogger(RuntimeConfigManager.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private Map<String, String> props = new HashMap<>();

    private RuntimeConfigDefs runtimeConfigDefs;

    private List<ConfigDef> configDefs;

    private List<DisplayGroup> displayGroups;

    private List<PropertyDef> propertyDefs;

    /**
     * @inheritDoc
     */
    @PostConstruct
    public void init() {

        runtimeConfigDefs
                = WebloggerRuntimeConfig.getRuntimeConfigDefs();

        try {
            // retrieve properties from database
            this.props = this.getProperties();

            // if any default props missing from the properties DB table,
            // initialize them and save them to that table.
            initializeMissingProps(this.props);
            this.saveProperties(this.props);

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to initialize runtime configuration properties."
                    + "Please check that the database has been upgraded!", e);
            throw new RuntimeException(e);
        }

    }

    /**
     * Retrieve a single property by name.
     */
    public RuntimeConfigProperty getProperty(String name) {
        Query query = em.createNamedQuery("RuntimeConfigProperty.findByName");
        query.setParameter("name", name);
        RuntimeConfigProperty prop = (RuntimeConfigProperty) query.getSingleResult();
        return prop;
    }

    /**
     * Retrieve all properties.
     *
     * Properties are returned in a Map to make them easy to lookup. The Map
     * uses the property name as the key and the RuntimeConfigProperty object as
     * the value.
     */
    public Map<String, String> getProperties() {

        HashMap<String, String> configProps = new HashMap<String, String>();
        List<RuntimeConfigProperty> list = em.createNamedQuery("RuntimeConfigProperty.findAll",
                RuntimeConfigProperty.class).getResultList();
        /*
         * for convenience sake we are going to put the list of props
         * into a map for users to access it.  The value element of the
         * hash still needs to be the RuntimeConfigProperty object so that
         * we can save the elements again after they have been updated
         */
        for (RuntimeConfigProperty prop : list) {
            log.info(prop.getName() + ":" + prop.getValue());
            try {
                props.put(prop.getName(), prop.getValue());
            } catch (Exception e) {
                log.log(Level.WARNING,"props.put issue:",e);
            }
        }
        return props;
    }

    /**
     * Save a single property.
     */
    public void saveProperty(RuntimeConfigProperty property) {
        this.em.persist(property);
    }

    /**
     * Save all properties.
     *
     * @param properties
     */
    /*
    revised from the original method
    java 8 lamba
    properties are key value pairs
     */
    public void saveProperties(Map properties) {
        // just go through the list and saveProperties each property

        properties.forEach((k, v) -> {

            String value = null;

            if (v instanceof String) {
                value = (String) v;
            } else if (v instanceof Boolean) {
                Boolean bool = (Boolean) v;
                value = Boolean.toString(bool);
            }
            this.em.merge(new RuntimeConfigProperty((String) k, value));
        });

    }

    /**
     * This method compares the property definitions in the RuntimeConfigDefs
     * file with the properties in the given Map and initializes any properties
     * that were not found in the Map.
     *
     * If the Map of props is empty/null then we will initialize all properties.
     *
     */
    /*
    revised from the original
    props initialized as key value pairs
     */
    private Map initializeMissingProps(Map<String, String> props) {

        if (props == null) {
            props = new HashMap<>();
        }

        // start by getting our runtimeConfigDefs
        RuntimeConfigDefs runtimeConfigDefs
                = WebloggerRuntimeConfig.getRuntimeConfigDefs();

        // can't do initialization without our config defs
        if (runtimeConfigDefs == null) {
            log.log(Level.SEVERE, "runtimeConfigDefs null");
            return props;
        }

        // iterate through all the definitions and add properties
        // that are not already in our props map
        for (ConfigDef configDef : runtimeConfigDefs.getConfigDefs()) {
            for (DisplayGroup dGroup : configDef.getDisplayGroups()) {
                for (PropertyDef propDef : dGroup.getPropertyDefs()) {

                    // do we already have this prop?  if not then add it
                    if (!props.containsKey(propDef.getName())) {
                        // RuntimeConfigProperty newprop =
                        //      new RuntimeConfigProperty(
                        //              propDef.getName(), propDef.getDefaultValue());

                        props.put(propDef.getName(), propDef.getDefaultValue());

                        log.info("Property " + propDef.getName()
                                + " not yet in roller_properties database table, will initialize with "
                                + "default value of [" + propDef.getDefaultValue() + "`]");
                    }
                }
            }
        }

        return props;
    }

    public Map<String, String> getProps() {
        return props;
    }

    public void setProps(Map<String, String> props) {
        this.props = props;
    }

    public RuntimeConfigDefs getRuntimeConfigDefs() {
        return runtimeConfigDefs;
    }

    public void setRuntimeConfigDefs(RuntimeConfigDefs runtimeConfigDefs) {
        this.runtimeConfigDefs = runtimeConfigDefs;
    }

    public List<ConfigDef> getConfigDefs() {
        return configDefs;
    }

    public void setConfigDefs(List<ConfigDef> configDefs) {
        this.configDefs = configDefs;
    }

    public List<DisplayGroup> getDisplayGroups() {
        return displayGroups;
    }

    public void setDisplayGroups(List<DisplayGroup> displayGroups) {
        this.displayGroups = displayGroups;
    }

    public List<PropertyDef> getPropertyDefs() {
        return propertyDefs;
    }

    public void setPropertyDefs(List<PropertyDef> propertyDefs) {
        this.propertyDefs = propertyDefs;
    }

}
