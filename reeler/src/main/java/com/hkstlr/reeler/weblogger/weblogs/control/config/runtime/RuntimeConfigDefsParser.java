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
 /*
 * RuntimeConfigDefsParser.java
 *
 * Created on June 4, 2005, 1:57 PM
 */
package com.hkstlr.reeler.weblogger.weblogs.control.config.runtime;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The parser for the rollerRuntimeConfigDefs.xml file. This class uses jdom to
 * unmarshall the xml into a series of java objects.
 *
 * @author Allen Gilliland
 * @author Henry Kastler modified to use the shipped libraries of the dom, not
 * the Apache libs
 */
public class RuntimeConfigDefsParser {

    private static final Logger log = Logger.getLogger(RuntimeConfigDefsParser.class.getName());

    /**
     * Creates a new instance of RuntimeConfigDefsParser
     */
    public RuntimeConfigDefsParser() {
    }

    /**
     * Unmarshall the given input stream into our defined set of Java objects.
     *
     * @throws ParserConfigurationException
     * @throws SAXException 
     *
     */
    public RuntimeConfigDefs unmarshall(InputStream instream)
            throws IOException, ParserConfigurationException, SAXException {

        if (instream == null) {
            throw new IOException("InputStream is null!");
        }

        RuntimeConfigDefs configs = new RuntimeConfigDefs();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        /*
         * bizarrely, setValidating refers to DTD validation only, and we are using schema validation
         */
        factory.setValidating(false);
        factory.setNamespaceAware(true);

        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(instream);

        Element root = doc.getDocumentElement();
        NodeList configdefs = root.getElementsByTagName("config-def");
        for (int e = 0; e < configdefs.getLength(); e++) {
            Element config = (Element) configdefs.item(e);
            configs.addConfigDef(this.elementToConfigDef(config));
        }

        return configs;
    }

    private ConfigDef elementToConfigDef(Element element) {

        ConfigDef configdef = new ConfigDef();

        configdef.setName(element.getAttribute("name"));

        NodeList displaygroups = element.getElementsByTagName("display-group");
        for (int e = 0; e < displaygroups.getLength(); e++) {
            Element displayGroup = (Element) displaygroups.item(e);
            configdef.addDisplayGroup(this.elementToDisplayGroup(displayGroup));

        }

        return configdef;
    }

    private DisplayGroup elementToDisplayGroup(Element element) {
        DisplayGroup displaygroup = new DisplayGroup();

        displaygroup.setName(element.getAttribute("name"));
        displaygroup.setKey(element.getAttribute("key"));

        NodeList displaygroups = element.getElementsByTagName("property-def");
        for (int e = 0; e < displaygroups.getLength(); e++) {
            Element item = (Element) displaygroups.item(e);
            displaygroup.addPropertyDef(this.elementToPropertyDef(item));
        }

        return displaygroup;
    }

    private PropertyDef elementToPropertyDef(Element element) {
        PropertyDef prop = new PropertyDef();

        prop.setName(element.getAttribute("name"));
        prop.setKey(element.getAttribute("key"));

        prop.setType(element.getElementsByTagName("type").item(0).getTextContent());

        log.log(Level.FINER, "name:{0}", element.getAttribute("name"));
        log.log(Level.FINER, "defaultValue:{0}", element.getElementsByTagName("default-value").item(0).getTextContent());
        
        prop.setDefaultValue(element.getElementsByTagName("default-value").item(0).getTextContent());

        try {
            // optional elements
            if (element.getElementsByTagName("rows").getLength() > 0) {
                prop.setRows(element.getElementsByTagName("rows").item(0).getTextContent());
            }

            if (element.getElementsByTagName("cols").getLength() > 0) {
                prop.setCols(element.getElementsByTagName("cols").item(0).getTextContent());
            }
        } catch (DOMException e) {
            log.log(Level.SEVERE, "parse error:", e);
        }
        return prop;
    }

}
