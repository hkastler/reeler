/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.boundary.manager;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.weblogger.entities.Weblog;
import com.hkstlr.reeler.weblogger.entities.WeblogBookmark;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author henry.kastler
 */
@Stateless
public class WeblogBookmarkManager extends AbstractManager<WeblogBookmark> {

    @Inject
    private Logger log;
    
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WeblogBookmarkManager() {
        super(WeblogBookmark.class);
    }

    public List<WeblogBookmark> getBookmarksForWeblog(Weblog weblog) {
        String qlString = "SELECT b FROM WeblogBookmark b JOIN b.folder WHERE b.folder.weblog = ?1";
        Query query = getEntityManager().createQuery(qlString);
        query.setParameter(1, weblog);
        List<WeblogBookmark> results = query.getResultList();
        log.info("found " + results.size() + " bookmarks");
        return results;
    }

}
