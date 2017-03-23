/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.control;

import java.util.ResourceBundle;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author henry.kastler
 */
@ApplicationScoped
@Named("labels")
public class ApplicationResourceBundle {

    public ResourceBundle res = null;
    
    public ApplicationResourceBundle() {
         if(res == null){
             setRes("/ApplicationResources");
         }
    }    
    
    public ResourceBundle getRes() {
        return res;
    }

    public void setRes(String bundleLocation) {
        this.res = ResourceBundle.getBundle(bundleLocation);
    }
}
