/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryTag;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author henry.kastler
 */
@Stateless
public class WeblogEntryTagManager extends AbstractManager<WeblogEntryTag> {

    private static final Logger log = Logger.getLogger(WeblogEntryTagManager.class.getName());
    
    @PersistenceContext
    private EntityManager em;

    public WeblogEntryTagManager() {
        super(WeblogEntryTag.class);
    }    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WeblogEntryTag findByName(String name) {
        TypedQuery<WeblogEntryTag> q = getNamedQuery("WeblogEntryTag.findByName");
        q.setParameter("name", name);
        WeblogEntryTag tag;
        try {
            tag = q.getSingleResult();
        } catch (NoResultException e) {
            log.log(Level.WARNING,"WeblogEntryTag.findByName",e);
            tag = new WeblogEntryTag();
        }
        return tag;
    }

    void removeWeblogEntryTag(WeblogEntryTag tag) {
        remove(tag);
    }

    public WeblogEntryTag findByNameAndWeblogEntry(String name, WeblogEntry weblogEntry) {
        TypedQuery<WeblogEntryTag> q = getNamedQuery("WeblogEntryTag.findByNameAndWeblogEntry");
        q.setParameter("name", name);
        q.setParameter("weblogEntry", weblogEntry);
        WeblogEntryTag tag;
        try {
            tag = q.getSingleResult();
        } catch (NoResultException e) {
            log.log(Level.WARNING,"WeblogEntryTag.findByNameAndWeblogEntry",e);
            tag = null;
        }
        return tag;
    }

}
