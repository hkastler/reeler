/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryTag;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author henry.kastler
 */
@Stateless
public class WeblogEntryTagManager extends AbstractManager<WeblogEntryTag> {

    @PersistenceContext(unitName = "rollerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WeblogEntryTagManager() {
        super(WeblogEntryTag.class);
    }

    public WeblogEntryTag findByName(String name) {
        Query q = getNamedQuery("WeblogEntryTag.findByName");
        q.setParameter("name", name);
        WeblogEntryTag tag;
        try {
            tag = (WeblogEntryTag) q.getSingleResult();
        } catch (NoResultException nre) {
            tag = new WeblogEntryTag();
        }
        return tag;
    }

    void removeWeblogEntryTag(WeblogEntryTag tag) {
        remove(tag);
    }

    public WeblogEntryTag findByNameAndWeblogEntry(String name, WeblogEntry weblogEntry) {
        Query q = getNamedQuery("WeblogEntryTag.findByNameAndWeblogEntry");
        q.setParameter("name", name);
        q.setParameter("weblogEntry", weblogEntry);
        WeblogEntryTag tag;
        try {
            tag = (WeblogEntryTag) q.getSingleResult();
        } catch (NoResultException nre) {
            
            tag = null;
        }
        return tag;
    }

}
