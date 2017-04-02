/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.control.jsf.converters;

import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author henry.kastler
 */

public class WeblogConverter implements Converter{
    
    @Inject
    private WeblogManager wm;

    
    private Logger log = Logger.getLogger(WeblogConverter.class.getName());

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
       
        try {
            String id = value;
            //.find apparently only works with numeric fields
            log.info("id:" + id);
            Weblog web = wm.findById(id);//session.load(CatalogValue .class, id);
            log.info("returned Weblog:");
            return web;
        } catch (Exception ex) {
            log.info("yes it's a fail");
            String message = ex.getMessage();
            log.log(Level.INFO,"reason:",ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, message));
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
         return ((Weblog) value).getId();
    }
    
}