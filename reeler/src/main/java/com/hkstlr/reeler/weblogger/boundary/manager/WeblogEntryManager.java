/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.boundary.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.weblogger.entities.TagStat;
import com.hkstlr.reeler.weblogger.entities.Weblog;
import com.hkstlr.reeler.weblogger.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.entities.WeblogEntryComment;
import javax.ejb.Stateless;

/**
 *
 * @author henry.kastler
 */
@Stateless
public class WeblogEntryManager extends AbstractManager<WeblogEntry> {

    @Inject
    private Logger log;

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WeblogEntryManager() {
        super(WeblogEntry.class);
    }

    public void removeWeblogEntry(WeblogEntry entry) {
        // TODO Auto-generated method stub
        remove(entry);
    }

    public List<TagStat> getTags(Weblog weblog, Object object, Object object2, int i, int j) {
        // TODO Auto-generated method stub
        return null;
    }

    public WeblogEntry getWeblogEntryByHandleAndAnchor(String handle, String anchor) {

        Weblog weblog = null;
        WeblogEntry weblogEntry = null;

        Query website = em.createNamedQuery("Weblog.findByHandle");
        website.setParameter("handle", handle);

        try {
            weblog = (Weblog) website.getSingleResult();
        } catch (Exception e) {

        }

        log.info("weblog: " + weblog.getHandle());

        Query query = em.createNamedQuery("WeblogEntry.findByWebsiteAndAnchor");
        query.setParameter("website", weblog);
        query.setParameter("anchor", anchor);

        try {
            weblogEntry = (WeblogEntry) query.getSingleResult();
        } catch (Exception e) {
            weblogEntry = new WeblogEntry();
            weblogEntry.setText("not found");
            log.log(Level.WARNING, "weblog: {0} not found, reason: {1}", new Object[]{anchor, e.getMessage()});
        }
        
        log.log(Level.INFO,"weblogEntry found:" + weblogEntry.getAnchor());

        return weblogEntry;
    }

    public List<WeblogEntry> getWeblogEntriesForWeblog(Weblog weblog) {
        Query query = em.createNamedQuery("WeblogEntry.getByWebsite");
        query.setParameter(1, weblog);
        List<WeblogEntry> entries = query.getResultList();
        if (entries == null) {
            entries = new ArrayList<>();
        }
        return entries;
    }

    public List<WeblogEntry> findByPinnedToMain() {
        Query query = em.createNamedQuery("WeblogEntry.findByPinnedToMain");
        query.setParameter("pinnedToMain", true);
        List<WeblogEntry> entries = query.getResultList();
        if (entries == null) {
            entries = new ArrayList<>();
        }
        return entries;
    }

    public List<WeblogEntryComment> getComments(WeblogEntry entry) {
        Query query = em.createNamedQuery("WeblogEntryComment.findByWeblogEntry", WeblogEntryComment.class);
        query.setParameter("weblogEntry", entry);
        List<WeblogEntryComment> comments = query.getResultList();
        if (comments == null) {
            comments = new ArrayList<>();
        }
        return comments;
    }

    public WeblogEntry getWeblogEntry(String id) {
        WeblogEntry entry = em.find(WeblogEntry.class, id);
        if (entry == null) {
            entry = new WeblogEntry();
        }
        return entry;
    }
    public List<WeblogEntry> getWeblogEntriesByCategoryName(String weblogCategoryName){
        
        List<WeblogEntry> categoryEntries =  new ArrayList<>();
        Query q = getEntityManager().createNamedQuery("WeblogEntry.getByWeblogEntriesByWeblogCategoryName");
        log.log(Level.FINE,"weblogCategoryName:" + weblogCategoryName);
        String trimmed = weblogCategoryName.trim();
        q.setParameter("weblogCategoryName", trimmed);
        categoryEntries = q.getResultList();
        return categoryEntries;
    }
    
    public WeblogEntry getLatestEntryForWeblog(Weblog weblog){
               
        Query q = em.createNamedQuery("WeblogEntry.getLatestEntryForWeblog")
                        .setParameter("weblog", weblog)
                        .setMaxResults(1);
        WeblogEntry we = (WeblogEntry) q.getResultList();
        return we;
    }

}
