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
 * 
 * adapted from org.apache.roller.weblogger.business.jpa.JPAWeblogEntryManagerImpl.java
**
 */


package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.app.control.AppConstants;
import com.hkstlr.reeler.app.control.StringPool;
import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.control.WeblogEntryCommentSearchCriteria;
import com.hkstlr.reeler.weblogger.weblogs.control.TagStat;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryAttribute;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryTag;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import javax.ejb.EJB;

import javax.ejb.Stateless;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author henry.kastler
 */
@Stateless
public class WeblogEntryManager extends AbstractManager<WeblogEntry> {

    @Inject
    private Logger log;

    @EJB
    private WeblogEntryCommentManager wecm;

    @EJB
    private WeblogEntryTagManager wetm;

    @EJB
    private WeblogEntryTagAggregateManager wetam;

    @EJB
    private WeblogManager weblogManager;

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WeblogEntryManager() {
        super(WeblogEntry.class);
    }
    
    public WeblogEntry getWeblogEntryByHandleAndAnchor(String handle, String anchor) {

        Weblog weblog = null;
        WeblogEntry weblogEntry;

        Query website = em.createNamedQuery("Weblog.findByHandle");
        website.setParameter("handle", handle);

        try {
            weblog = (Weblog) website.getSingleResult();
        } catch (Exception e) {

        }

        //log.info("weblog: " + weblog.getHandle());

        Query query = em.createNamedQuery("WeblogEntry.findByWebsiteAndAnchor");
        query.setParameter("website", weblog);
        query.setParameter("anchor", anchor);
        
        try {
            weblogEntry = (WeblogEntry) query.getSingleResult();
            weblogEntry.getTags().size();
            weblogEntry.setWebsite(weblog);
        } catch (Exception e) {
            weblogEntry = new WeblogEntry();
            weblogEntry.setTitle("not found");
            weblogEntry.setText("not found");
            log.log(Level.WARNING, "weblog: {0} not found, reason: {1}", new Object[]{anchor, e.getMessage()});
        }

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

    public WeblogEntry getWeblogEntry(String id) {
        WeblogEntry entry = em.find(WeblogEntry.class, id);
        if (entry == null) {
            entry = new WeblogEntry();
        }
        return entry;
    }

    public WeblogEntry findForEdit(String id) {
        WeblogEntry entry = findById(id);
        if (entry == null) {
            entry = new WeblogEntry();
        }
        entry.getTags().size();
        return entry;
    }

    public List<WeblogEntry> getWeblogEntriesByCategoryName(String weblogCategoryName) {

        List<WeblogEntry> categoryEntries = new ArrayList<>();
        Query q = getEntityManager().createNamedQuery("WeblogEntry.getByWeblogEntriesByWeblogCategoryName");
        
        String trimmed = weblogCategoryName.trim();
        q.setParameter("weblogCategoryName", trimmed);
        categoryEntries = q.getResultList();
        return categoryEntries;
    }

    public List<WeblogEntry> getWeblogEntriesByCategoryNameAndWeblog(String weblogCategoryName, Weblog weblog) {

        List<WeblogEntry> categoryEntries;
        Query q = getEntityManager().createNamedQuery("WeblogEntry.getByWeblogEntriesByCategoryNameAndWeblog");
        
        String trimmed = weblogCategoryName.trim();
        q.setParameter("weblogCategoryName", trimmed);
        q.setParameter("weblog", weblog);
        categoryEntries = q.getResultList();
        return categoryEntries;
    }

    public List<WeblogEntry> getWeblogEntriesByDateAndWeblog(String dateString, Weblog weblog) {

        Integer year = Integer.parseInt(dateString.substring(0, 4));
        Integer month = Integer.parseInt(dateString.substring(4, 6));
        Integer startDate = 1;

        if (dateString.length() == 8) {
            startDate = Integer.parseInt(dateString.substring(6, 8));
        }
        Integer afterDate = startDate + 1;
        
        Calendar pubTimeBefore = Calendar.getInstance(weblog.getTimeZoneInstance());
        pubTimeBefore.set(year, month-1, startDate,0,0,0);

        Calendar pubTimeAfter = Calendar.getInstance(weblog.getTimeZoneInstance());

        List<WeblogEntry> entries;
        Query q = null;
        //if the dateString is 6
        //then we want the whole month
        if (dateString.length() == 6) {
            pubTimeAfter.set(year, month-1, pubTimeBefore.getActualMaximum(Calendar.DAY_OF_MONTH),23,59,59);
        }

        //if the dateString is 8
        //then we want just a day
        if (dateString.length() == 8) {
            pubTimeAfter.set(year, month-1,startDate,23,59,59);
        }
        q = getEntityManager().createQuery("SELECT we FROM WeblogEntry we"
                    + " WHERE we.website = :blog"
                    + " AND we.publishEntry = true"
                    + " AND we.pubTime < :now"
                    + " AND we.pubTime BETWEEN "
                    + ":pubTimeBefore"
                    + " AND "
                    + ":pubTimeAfter");
             q.setParameter("blog", weblog);
             Date now = new Date();             
             q.setParameter("now",now,TemporalType.TIMESTAMP);             
             q.setParameter("pubTimeBefore", pubTimeBefore, TemporalType.TIMESTAMP);             
             q.setParameter("pubTimeAfter", pubTimeAfter, TemporalType.TIMESTAMP);

        entries = q.getResultList();
        return entries;
    }
    
     public List<Calendar> getWeblogEntryDatesForCalendar(String dateString, Weblog weblog) {

        Integer year = Integer.parseInt(dateString.substring(0, 4));
        Integer month = Integer.parseInt(dateString.substring(4, 6));

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        if (weblog != null) {
            startDate = Calendar.getInstance(weblog.getTimeZoneInstance());
            endDate = Calendar.getInstance(weblog.getTimeZoneInstance());
        }
        startDate.set(year, month - 1, 1,0,0,0);
        endDate.set(year, month - 1,startDate.getActualMaximum(Calendar.DAY_OF_MONTH),23,59,59);
       
        Query query = em.createQuery("SELECT DISTINCT we.pubTime FROM WeblogEntry we"
                + " WHERE we.website = :blog"
                + " AND we.publishEntry = true"
                + " AND we.pubTime < :now"
                + " AND we.pubTime BETWEEN "
                + ":startDate"
                + " AND "
                + ":endDate");
        query.setParameter("blog", weblog);
        query.setParameter("startDate", startDate, TemporalType.TIMESTAMP);
        query.setParameter("endDate", endDate, TemporalType.TIMESTAMP);
        query.setParameter("now", new Timestamp(new Date().getTime()), TemporalType.TIMESTAMP);
        
        List<Calendar> results = query.getResultList();

        return results;

    }

    public WeblogEntry getLatestEntryForWeblog(Weblog weblog) {

        Query q = em.createNamedQuery("WeblogEntry.getLatestEntryForWeblog")
                .setParameter("weblog", weblog)
                .setMaxResults(1);
        WeblogEntry we = (WeblogEntry) q.getSingleResult();
        return we;
    }

    /**
     * @inheritDoc
     */
    // TODO: perhaps the createAnchor() and queuePings() items should go outside this method?
    public void saveWeblogEntry(WeblogEntry entry) throws WebloggerException {

        // Entry is invalid without local. if missing use weblog default
        if (entry.getLocale() == null) {
            entry.setLocale(entry.getWebsite().getLocale());
        }

        if (entry.getAnchor() == null || entry.getAnchor().trim().equals("")) {
            //entry.setAnchor(this.createAnchor(entry));
        }

        // if the entry was published to future, set status as SCHEDULED
        // we only consider an entry future published if it is scheduled
        // more than 1 minute into the future
        if (WeblogEntry.PubStatus.PUBLISHED.equals(entry.getStatus())
                && entry.getPubTime().after(new Date(System.currentTimeMillis() + AppConstants.MIN_IN_MS))) {
            entry.setStatus(WeblogEntry.PubStatus.SCHEDULED.toString());
        }

        // Store value object (creates new or updates existing)
        entry.setUpdateTime(new Date());
        save(entry);
        

        if (entry.isPublishEntry()) {
            weblogManager.saveWeblog(entry.getWebsite());
        }
        em.flush();

    }
    
    public void removeWeblogEntry(WeblogEntry entry) throws WebloggerException {
        Weblog weblog = entry.getWebsite();

        WeblogEntryCommentSearchCriteria csc = new WeblogEntryCommentSearchCriteria();
        csc.setEntry(entry);

        // remove comments
        List<WeblogEntryComment> comments = wecm.getComments(entry);
        for (WeblogEntryComment comment : comments) {
            wecm.remove(comment);
        }

        // remove tag & tag aggregates
        if (entry.getTags() != null) {
            for (WeblogEntryTag tag : entry.getTags()) {
                wetm.removeWeblogEntryTag(tag);
            }
        }

        // remove attributes
        if (entry.getEntryAttributes() != null) {
            for (Iterator it = entry.getEntryAttributes().iterator(); it.hasNext();) {
                WeblogEntryAttribute att = (WeblogEntryAttribute) it.next();
                it.remove();
                this.em.remove(att);
            }
        }

        // remove entry
        this.em.remove(entry);

        // update weblog last modified date.  date updated by saveWebsite()
        if (entry.isPublishEntry()) {
            weblogManager.saveWeblog(weblog);
        }

        // remove entry from cache mapping
        //this.entryAnchorToIdMap.remove(entry.getWebsite().getHandle()+":"+entry.getAnchor());
    }

    public List<TagStat> getTags(Weblog weblog, Object object, Object object2, int i, int j) {
        // TODO Auto-generated method stub
        return null;
    }
    
     public int getWeblogEntryCountForWeblog(Weblog weblog) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<WeblogEntry> weblogEntry = cq.from(WeblogEntry.class);
        cq.select(getEntityManager().getCriteriaBuilder().count(weblogEntry));
        cq.where(cb.equal(weblogEntry.get("website"), weblog));
        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
     
     
     public List<WeblogEntry> getPaginatedEntries(Weblog weblog,int[] range) {
                    
        CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder().createQuery();        
        Root<WeblogEntry> t = cq.from(WeblogEntry.class);
        
        cq.select(t); 
        
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();  
        List<Predicate> predicates = new ArrayList<>();
                
        predicates.add(cb.equal(t.get("website"), weblog));
        predicates.add(cb.equal(t.get("publishEntry"), true));
        predicates.add(cb.lessThanOrEqualTo(t.<Calendar>get("pubTime"), Calendar.getInstance()));        
        
        cq.where(predicates.toArray(new Predicate[]{}));
        cq.orderBy(cb.desc(t.get("pubTime")));  
        
        Query q = getEntityManager().createQuery(cq);        
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        
        return q.getResultList();
        
    }
     
    public List<WeblogEntry> getAllEntriesPaginated(Weblog weblog,int[] range) {
                    
        CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder().createQuery();        
        Root<WeblogEntry> t = cq.from(WeblogEntry.class);
        
        cq.select(t); 
        
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();  
        List<Predicate> predicates = new ArrayList<>();
                
        predicates.add(cb.equal(t.get("website"), weblog));       
        
        cq.where(predicates.toArray(new Predicate[]{}));
        cq.orderBy(cb.desc(t.get("pubTime")));  
        
        Query q = getEntityManager().createQuery(cq);        
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        
        return q.getResultList();
        
    }  
   
}
