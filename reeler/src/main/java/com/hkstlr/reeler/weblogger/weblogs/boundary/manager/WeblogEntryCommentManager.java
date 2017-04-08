/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.

original code from org.apache.roller.weblogger.business.jpa.JPAWeblogEntryManagerImpl;
 */


package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.control.CommentSearchCriteria;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment.ApprovalStatus;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
   
    public List<WeblogEntryComment> getCommentsForWeblog(Weblog weblog, List<ApprovalStatus> statuses){
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
        String qlString = "SELECT wec FROM WeblogEntryComment wec"
                + " LEFT JOIN FETCH wec.weblogEntry we"
                + " WHERE we.website = ?1 "
                + " AND wec.status IN (?2)"
                + " ORDER BY wec.postTime DESC";
        //qlString.concat("");
        //qlString.concat("JOIN Weblog rb");
        //qlString.concat("WHERE rb = :weblog ");                
        Query query = getEntityManager().createQuery(qlString);
        query.setParameter(1, weblog);
        query.setParameter(2, statuses);
        List<WeblogEntryComment> results = query.getResultList();
        return results;
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
    
    public List<WeblogEntryComment> getCommentsByWeblogEntryAndStatus(WeblogEntry entry, WeblogEntryComment.ApprovalStatus status) {
        Query query = em.createNamedQuery("WeblogEntryComment.findByWeblogEntryAndStatus", WeblogEntryComment.class);
        query.setParameter("weblogEntry", entry);
        query.setParameter("status", status);
        List<WeblogEntryComment> comments = query.getResultList();
        if (comments == null) {
            comments = new ArrayList<>();
        }
        return comments;
    }
    
    /**
     * @inheritDoc
     */
    public List<WeblogEntryComment> getComments(CommentSearchCriteria csc) throws WebloggerException {
        
        List<Object> params = new ArrayList<Object>();
        int size = 0;
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT c FROM WeblogEntryComment c ");
        
        StringBuilder whereClause = new StringBuilder();
        if (csc.getEntry() != null) {
            params.add(size++, csc.getEntry());
            whereClause.append("c.weblogEntry = ?").append(size);
        } else if (csc.getWeblog() != null) {
            params.add(size++, csc.getWeblog());
            whereClause.append("c.weblogEntry.website = ?").append(size);
        }
        
        if (csc.getSearchText() != null) {
            params.add(size++, "%" + csc.getSearchText().toUpperCase() + "%");
            appendConjuctionToWhereclause(whereClause, "upper(c.content) LIKE ?").append(size);
        }
        
        if (csc.getStartDate() != null) {
            Timestamp start = new Timestamp(csc.getStartDate().getTime());
            params.add(size++, start);
            appendConjuctionToWhereclause(whereClause, "c.postTime >= ?").append(size);
        }
        
        if (csc.getEndDate() != null) {
            Timestamp end = new Timestamp(csc.getEndDate().getTime());
            params.add(size++, end);
            appendConjuctionToWhereclause(whereClause, "c.postTime <= ?").append(size);
        }
        
        if (csc.getStatus() != null) {
            params.add(size++, csc.getStatus());
            appendConjuctionToWhereclause(whereClause, "c.status = ?").append(size);
        }
        
        if(whereClause.length() != 0) {
            queryString.append(" WHERE ").append(whereClause);
        }
        if (csc.isReverseChrono()) {
            queryString.append(" ORDER BY c.postTime DESC");
        } else {
            queryString.append(" ORDER BY c.postTime ASC");
        }
        
        TypedQuery<WeblogEntryComment> query = em.createQuery(queryString.toString(), WeblogEntryComment.class);
        if (csc.getOffset() != 0) {
            query.setFirstResult(csc.getOffset());
        }
        if (csc.getMaxResults() != -1) {
            query.setMaxResults(csc.getMaxResults());
        }
        for (int i=0; i<params.size(); i++) {
            query.setParameter(i+1, params.get(i));
        }
        return query.getResultList();
        
    }
    
    /**
     * Appends given expression to given whereClause. If whereClause already
     * has other conditions, an " AND " is also appended before appending
     * the expression
     * @param whereClause The given where Clauuse
     * @param expression The given expression
     * @return the whereClause.
     */
    private static StringBuilder appendConjuctionToWhereclause(StringBuilder whereClause,
            String expression) {
        if (whereClause.length() != 0 && expression.length() != 0) {
            whereClause.append(" AND ");
        }
        return whereClause.append(expression);
    }

    public void saveAndLoadComments(WeblogEntryComment comment) {
        System.out.println("comment:" + comment.getStatus().toString());
        persist(comment);
        System.out.println("comment:" + comment.getStatus().toString());
        //WeblogEntry entry = comment.getWeblogEntry();
        //web.setComments(entry.getComments());
    }
       

}
