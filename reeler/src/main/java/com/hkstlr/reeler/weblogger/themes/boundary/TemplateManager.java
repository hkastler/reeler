/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.themes.boundary;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Startup;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

/**
 *
 * @author henry.kastler
 */

@ManagedBean
public class TemplateManager {

    @Inject
    Logger log;
    
    public TemplateManager() {
    }
    
    
    

    /**
     * Get the value of pathForPage
     *
     * @return the value of pathForPage
     */
    public String getPathForPage(String page) {
        log.log(Level.INFO,"page:" + page);
        String pathForPage = "";
        return pathForPage;
    }

   

}
