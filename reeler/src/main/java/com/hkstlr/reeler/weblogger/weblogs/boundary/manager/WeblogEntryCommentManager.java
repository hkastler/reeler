/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment.ApprovalStatus;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author henry.kastler
 */
@Stateless
public class WeblogEntryCommentManager extends AbstractManager {

    @PersistenceContext
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public WeblogEntryCommentManager() {
        //super(WeblogEntryComment.class);
    }
   
    public List<WeblogEntryComment> getCommentsForWeblog(Weblog weblog, ApprovalStatus status){
        /*String sqlString = "SELECT rc.*, re.*" +
        "	FROM public.roller_comment rc" +
        "    INNER JOIN weblogentry re" +
        "    ON re.id = rc.entryid" +
        "    INNER JOIN weblog rb" +
        "    ON rb.id = re.websiteid" +
        "    where rb.id =?1";
        Query query = getEntityManager().createNativeQuery(sqlString);
        */
        
        //System.out.println("weblog:" + weblog.getName());
        String qlString = "SELECT wec FROM WeblogEntryComment wec "
                + "LEFT JOIN FETCH wec.weblogEntry we "
                + "WHERE we.website = ?1 "
                + "AND wec.status = ?2";
        //qlString.concat("");
        //qlString.concat("JOIN Weblog rb");
        //qlString.concat("WHERE rb = :weblog ");                
        Query query = getEntityManager().createQuery(qlString);
        query.setParameter(1, weblog);
        query.setParameter(2, status);
        List<WeblogEntryComment> results = query.getResultList();
        return results;
    }

    public void saveAndLoadComments(WeblogEntryComment comment) {

        persist(comment);
        //WeblogEntry entry = comment.getWeblogEntry();
        //web.setComments(entry.getComments());
    }

}
