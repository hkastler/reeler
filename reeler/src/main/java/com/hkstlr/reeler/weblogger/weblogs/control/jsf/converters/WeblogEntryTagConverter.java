/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.control.jsf.converters;

import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogEntryTagManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryTag;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author henry.kastler
 */
@ManagedBean
@RequestScoped
public class WeblogEntryTagConverter implements Converter {

    @EJB
    private WeblogEntryTagManager wtm;

    private Logger log = Logger.getLogger(WeblogCategoryConverter.class.getName());

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        log.info("weblogEntryTagConverter getAsObject value:" + value);       
        
        Collection<WeblogEntryTag> tags = new ArrayList<>();
        String[] names = value.split(" ");
        //.find apparently only works with numeric fields
        for (String name : names) {            
            WeblogEntryTag tag = new WeblogEntryTag(name);
            tags.add(tag);
        }

        return tags;

    }

    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        log.info("WeblogEntryTagConverter getAsString value:" + value);

        List<WeblogEntryTag> tagValues = (List) value;

        StringBuilder tags = new StringBuilder();
        for (WeblogEntryTag tag : tagValues) {
            tags.append(tag.getName());
            tags.append(" ");
        }
        log.info("returning:" + tags.toString());
        return tags.toString();
    }

}
