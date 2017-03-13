/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.themes.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.weblogger.themes.entities.CustomTemplateRendition;

/**
 *
 * @author henry.kastler
 */
@Stateless
public class CustomTemplateRenditionFacade extends AbstractManager<CustomTemplateRendition> {

    @PersistenceContext(unitName = "rollerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomTemplateRenditionFacade() {
        super(CustomTemplateRendition.class);
    }
    
}
