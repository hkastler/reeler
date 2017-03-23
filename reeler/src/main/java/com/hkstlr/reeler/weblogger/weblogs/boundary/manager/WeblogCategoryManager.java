/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;


import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogCategory;
import java.util.ArrayList;
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
public class WeblogCategoryManager extends AbstractManager<WeblogCategory> {

    @Inject
    private Logger log;
    
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WeblogCategoryManager() {
        super(WeblogCategory.class);
    }
    
    public List<WeblogCategory> getWeblogCategoriesForWeblog(Weblog weblog){
        List<WeblogCategory> cats = new ArrayList<>();
        Query q = em.createNamedQuery("WeblogCategory.getByWeblog");
        q.setParameter(1, weblog);
        cats = q.getResultList();
        log.info("cats:" + cats.size());
        return cats;
    }
    
}
