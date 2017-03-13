/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.boundary.manager;

import com.hkstlr.reeler.weblogger.boundary.jsf.WeblogEntryBean;
import com.hkstlr.reeler.weblogger.entities.WeblogEntry;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hkstlr.reeler.weblogger.entities.WeblogEntryComment;
import javax.ejb.Stateless;

/**
 *
 * @author henry.kastler
 */
@Stateless
public class WeblogEntryCommentManager {

    @PersistenceContext
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public WeblogEntryCommentManager() {
        //super(WeblogEntryComment.class);
    }
    
    public void create(WeblogEntryComment entity) {
        persist(entity);
    }
    
    public void save(WeblogEntryComment entity) {    	
        persist(entity);
    }
    
    public void persist(WeblogEntryComment entity) {
    	getEntityManager().persist(entity);
    }
    
    public void saveAndLoadComments(WeblogEntryComment comment){
        
        persist(comment);
        //WeblogEntry entry = comment.getWeblogEntry();
        //web.setComments(entry.getComments());
    }
    
}
