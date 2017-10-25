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
** all the criteriabuilder queries are new
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.app.control.AppConstants;
import com.hkstlr.reeler.app.control.StringPool;
import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.control.WeblogEntryCommentSearchCriteria;
import com.hkstlr.reeler.weblogger.weblogs.control.TagStat;
import com.hkstlr.reeler.weblogger.weblogs.control.WeblogEntrySearchCriteria;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogCategory;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogCategory_;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry.PubStatus;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryAttribute;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryTag;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry_;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog_;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.ejb.Stateless;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author henry.kastler
 */
@Stateless
public class WeblogEntryManager extends AbstractManager<WeblogEntry> {

    private static final Logger LOG = Logger.getLogger(WeblogEntryManager.class.getName());

    @EJB
    private WeblogEntryCommentManager wecm;

    @EJB
    private WeblogEntryTagManager wetm;

    @EJB
    private WeblogEntryTagAggregateManager wetam;

    @EJB
    private WeblogManager weblogManager;

    @PersistenceContext
    protected EntityManager em;

    private static String WEBSITE_FIELD_NAME;
    private static String PUBTIME_FIELD_NAME;

    public WeblogEntryManager() {
        super(WeblogEntry.class);
    }

    @PostConstruct
    protected void init() {
        WEBSITE_FIELD_NAME = WeblogEntry_.website.getName();
        PUBTIME_FIELD_NAME = WeblogEntry_.pubTime.getName();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WeblogEntry getWeblogEntryByHandleAndAnchor(@Size(min = 1) String handle,
            @Size(min = 1) String anchor) {

        WeblogEntry weblogEntry;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder().createQuery();

        Root<WeblogEntry> rt = cq.from(WeblogEntry.class);
        EntityType<WeblogEntry> weblogEntry_ = rt.getModel();

        cq.select(rt);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(rt.get(weblogEntry_
                .getSingularAttribute(WEBSITE_FIELD_NAME, Weblog.class))
                .get(Weblog_.handle.getName()), handle));

        //a mix of metamodels!
        predicates.add(cb.equal(rt.get(
                weblogEntry_.getSingularAttribute(WeblogEntry_.anchor.getName(), String.class)
        ), anchor));

        cq.where(predicates.toArray(new Predicate[]{}));

        Query query = getEntityManager().createQuery(cq);

        try {
            weblogEntry = (WeblogEntry) query.getSingleResult();
            weblogEntry.getTags().size();
        } catch (Exception e) {
            weblogEntry = new WeblogEntry();
            weblogEntry.setTitle("not found");
            weblogEntry.setText("not found");
            LOG.log(Level.INFO, "weblog: {0} not found", new Object[]{anchor, e});
        }

        return weblogEntry;
    }

    @SuppressWarnings("unchecked")
    public List<WeblogEntry> getWeblogEntriesForWeblog(@NotNull Weblog weblog) {
        Query query = em.createNamedQuery("WeblogEntry.getByWebsite");
        query.setParameter(1, weblog);
        List<WeblogEntry> entries = (List<WeblogEntry>) query.getResultList();
        if (entries == null) {
            entries = new ArrayList<>();
        }
        return entries;
    }

    public List<WeblogEntry> findByPinnedToMain() {
        Query query = em.createNamedQuery("WeblogEntry.findByPinnedToMain");
        query.setParameter(WeblogEntry_.pinnedToMain.getName(), true);
        List<WeblogEntry> entries = query.getResultList();
        if (entries == null) {
            entries = new ArrayList<>();
        }
        return entries;
    }

    public WeblogEntry getWeblogEntry(@NotNull String id) {
        WeblogEntry entry = em.find(WeblogEntry.class, id);
        if (entry == null) {
            entry = new WeblogEntry();
        }
        return entry;
    }

    public WeblogEntry findForEdit(@NotNull String id) {
        WeblogEntry entry = findById(id);
        if (entry == null) {
            entry = new WeblogEntry();
        }
        entry.getTags().size();
        entry.getComments().size();
        entry.getEntryAttributes().size();
        return entry;
    }

    public List<WeblogEntry> getWeblogEntriesByCategoryName(@Size(min = 1) String weblogCategoryName) {

        Query q = getEntityManager().createNamedQuery("WeblogEntry.getByWeblogEntriesByWeblogCategoryName");
        q.setParameter(WeblogCategory_.name.getName(), weblogCategoryName.trim());
        return q.getResultList();

    }

    public List<WeblogEntry> getWeblogEntriesByCategoryNameAndWeblog(
            @Size(min = 1) String weblogCategoryName, @NotNull Weblog weblog) {

        Query q = getEntityManager().createNamedQuery("WeblogEntry.getByWeblogEntriesByCategoryNameAndWeblog");
        q.setParameter(WeblogCategory_.name.getName(), weblogCategoryName.trim());
        q.setParameter(WeblogCategory_.weblog.getName(), weblog);
        return q.getResultList();
    }

    private Query queryWeblogEntriesByDateAndWeblog(Weblog weblog,
            Calendar pubTimeStart,
            Calendar pubTimeEnd,
            String selecting) {

        String selectStatement = "SELECT".concat(selecting)        
                .concat(" FROM WeblogEntry we")
                .concat(" WHERE we.website = :blog")
                .concat(" AND we.publishEntry = true")
                .concat(" AND we.pubTime < :now")
                .concat(" AND we.pubTime BETWEEN ")
                .concat(":pubTimeStart")
                .concat(" AND ")
                .concat(":pubTimeEnd"); 
        if(" we ".equals(selecting)){
            selectStatement+=" ORDER BY pubTime DESC";
        }
        Query q = getEntityManager().createQuery(
                selectStatement
                );
        
        
        q.setParameter("blog", weblog);

        q.setParameter("now", new Date(), TemporalType.TIMESTAMP);
        q.setParameter("pubTimeStart", pubTimeStart, TemporalType.TIMESTAMP);
        q.setParameter("pubTimeEnd", pubTimeEnd, TemporalType.TIMESTAMP);

        return q;
    }

    private Map<String, Calendar> getCalendarsFromDateString(String dateString, Weblog weblog) {

        Map<String, Calendar> retMap = new HashMap<>();
        
        Integer year = Integer.parseInt(dateString.substring(0, 4));
        Integer month = Integer.parseInt(dateString.substring(4, 6));
        Integer startDate = 1;

        if (dateString.length() == 8) {
            startDate = Integer.parseInt(dateString.substring(6, 8));
        }

        Calendar pubTimeStart = Calendar.getInstance(weblog.getTimeZoneInstance());
        pubTimeStart.set(year, month - 1, startDate, 0, 0, 0);

        Calendar pubTimeEnd = Calendar.getInstance(weblog.getTimeZoneInstance());

        //if the dateString is 6
        //then we want the whole month
        if (dateString.length() == 6) {
            pubTimeEnd.set(year, month - 1, pubTimeStart.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
        }

        //if the dateString is 8
        //then we want just a day
        if (dateString.length() == 8) {
            pubTimeEnd.set(year, month - 1, startDate, 23, 59, 59);
        }
        retMap.put("pubTimeStart", pubTimeStart);
        retMap.put("pubTimeEnd", pubTimeEnd);
        return retMap;
    }

    public long getWeblogEntriesByDateAndWeblogCount(@Size(min = 6, max = 8) String dateString,
            @NotNull Weblog weblog) {

        Map<String, Calendar> pubTimes = getCalendarsFromDateString(dateString, weblog);
        
        Query q = queryWeblogEntriesByDateAndWeblog(weblog,
                pubTimes.get("pubTimeStart"),
                pubTimes.get("pubTimeEnd"),
                " COUNT(we) ");
        
        Long maxResultsQ = (Long)q.getSingleResult();
        long maxResults = maxResultsQ;
        
        if (maxResults > 100) {
            maxResults = 100;
        }
        return maxResults;
    }

    public List<WeblogEntry> getWeblogEntriesByDateAndWeblog(
            @Size(min = 6, max = 8) String dateString, @NotNull Weblog weblog,
            int[] range) {

        Map<String, Calendar> pubTimes = getCalendarsFromDateString(dateString, weblog);
        
        Query q = queryWeblogEntriesByDateAndWeblog(weblog,
                pubTimes.get("pubTimeStart"),
                pubTimes.get("pubTimeEnd"),
                " we ");
        
        if (range != null) {
            q.setMaxResults(range[1] - range[0] + 1);
            q.setFirstResult(range[0]);
        }

        return q.getResultList();

    }

    public List<Calendar> getWeblogEntryDatesForCalendar(
            @Size(min = 6, max = 8) String dateString, Weblog weblog) {

        Integer year = Integer.parseInt(dateString.substring(0, 4));
        Integer month = Integer.parseInt(dateString.substring(4, 6));

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        if (weblog != null) {
            startDate = Calendar.getInstance(weblog.getTimeZoneInstance());
            endDate = Calendar.getInstance(weblog.getTimeZoneInstance());
        }
        startDate.set(year, month - 1, 1, 0, 0, 0);
        endDate.set(year, month - 1, startDate.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);

        Query query = em.createQuery("SELECT DISTINCT we.pubTime FROM WeblogEntry we"
                + " WHERE we.website = :website"
                + " AND we.publishEntry = true"
                + " AND we.pubTime < :now"
                + " AND we.pubTime BETWEEN "
                + ":startDate"
                + " AND "
                + ":endDate");
        query.setParameter(WeblogEntry_.website.getName(), weblog);
        query.setParameter("now", new Timestamp(new Date().getTime()), TemporalType.TIMESTAMP);
        query.setParameter("startDate", startDate, TemporalType.TIMESTAMP);
        query.setParameter("endDate", endDate, TemporalType.TIMESTAMP);

        return query.getResultList();
    }

    public WeblogEntry getLatestEntryForWeblog(Weblog weblog) {

        TypedQuery q = em.createNamedQuery("WeblogEntry.getLatestEntryForWeblog", WeblogEntry.class)
                .setParameter(WeblogEntry_.website.getName(), weblog)
                .setMaxResults(1);
        return (WeblogEntry) q.getSingleResult();

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

        if (entry.getAnchor() == null || "".equals(entry.getAnchor().trim())) {
            //TODO: anchor logic
        }

        // if the entry was published to future, set status as SCHEDULED
        // we only consider an entry future published if it is scheduled
        // more than 1 minute into the future
        if (entry.getStatus().equals(WeblogEntry.PubStatus.PUBLISHED.toString())
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
        //TODO: this.entryAnchorToIdMap.remove(entry.getWebsite().getHandle()+":"+entry.getAnchor());
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
        cq.where(cb.equal(weblogEntry.get(WEBSITE_FIELD_NAME), weblog));
        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public int getWeblogEntryCountForWeblog(String weblogHandle) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder().createQuery();

        Root<WeblogEntry> weblogEntry = cq.from(WeblogEntry.class);
        EntityType<WeblogEntry> WeblogEntry_ = weblogEntry.getModel();

        cq.select(getEntityManager().getCriteriaBuilder().count(weblogEntry));

        cq.where(cb.equal(weblogEntry.get(WeblogEntry_
                .getSingularAttribute(WEBSITE_FIELD_NAME, Weblog.class))
                .get(Weblog_.handle.getName()), weblogHandle));

        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public List<WeblogEntry> getPaginatedEntries(Weblog weblog, int[] range) {

        CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<WeblogEntry> t = cq.from(WeblogEntry.class);

        cq.select(t);

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(t.get(WEBSITE_FIELD_NAME), weblog));
        predicates.add(cb.equal(t.get(WeblogEntry_.publishEntry.getName()), true));
        predicates.add(cb.lessThanOrEqualTo(t.<Calendar>get(PUBTIME_FIELD_NAME), Calendar.getInstance()));

        cq.where(predicates.toArray(new Predicate[]{}));
        cq.orderBy(cb.desc(t.get(PUBTIME_FIELD_NAME)));

        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);

        return q.getResultList();

    }

    public List<WeblogEntry> getAllEntriesPaginated(Weblog weblog, int[] range) {

        CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<WeblogEntry> t = cq.from(WeblogEntry.class);

        cq.select(t);

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(t.get(WEBSITE_FIELD_NAME), weblog));

        cq.where(predicates.toArray(new Predicate[]{}));
        cq.orderBy(cb.desc(t.get(PUBTIME_FIELD_NAME)));

        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);

        return q.getResultList();

    }

    public List<WeblogEntry> getBySearchCriteria(WeblogEntrySearchCriteria wesc) {

        CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<WeblogEntry> t = cq.from(WeblogEntry.class);

        cq.select(t);

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(t.get(WEBSITE_FIELD_NAME), wesc.getWeblog()));

        if (wesc.getCategoryId() != null && !wesc.getCategoryId().isEmpty()) {
            //should be a join?
            WeblogCategory category = em.find(WeblogCategory.class, wesc.getCategoryId());
            predicates.add(cb.equal(t.get(WeblogEntry_.category.getName()), category));
        }

        if (wesc.getText() != null && !wesc.getText().isEmpty()) {
            String lcased = wesc.getText().toLowerCase();
            Predicate textP;
            textP = cb.or(
                    cb.like(cb.lower(t.get(WeblogEntry_.title.getName())),
                            StringPool.PERCENT + lcased + StringPool.PERCENT),
                    cb.like(cb.lower(t.get(WeblogEntry_.text.getName())),
                            StringPool.PERCENT + lcased + StringPool.PERCENT)
            );
            predicates.add(textP);
        }

        if (wesc.getStartDate() != null && wesc.getEndDate() != null) {
            Predicate p;
            Calendar startDate = Calendar.getInstance();
            Calendar endDate = Calendar.getInstance();
            if (wesc.getWeblog() != null) {
                startDate = Calendar.getInstance(wesc.getWeblog().getTimeZoneInstance());
                endDate = Calendar.getInstance(wesc.getWeblog().getTimeZoneInstance());
            }
            startDate.setTime(wesc.getStartDate());
            endDate.setTime(wesc.getEndDate());
            p = cb.between(t.<Calendar>get(PUBTIME_FIELD_NAME), startDate, endDate);
            predicates.add(p);
        }

        if (wesc.getStatus() != null) {
            predicates.add(cb.equal(t.get(WeblogEntry_.status.getName()),
                    wesc.getStatus().toString()));
        }

        cq.where(predicates.toArray(new Predicate[]{}));
        cq.orderBy(cb.desc(t.get(PUBTIME_FIELD_NAME)));

        Query q = getEntityManager().createQuery(cq);
        if (wesc.getMaxResults() > 0) {
            q.setMaxResults(wesc.getMaxResults());
        }
        q.setFirstResult(wesc.getOffset());

        return q.getResultList();

    }
        
    private Query getCategoryViewQuery(String categoryName, Weblog weblog, Boolean forCount){
        
        CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<WeblogEntry> t = cq.from(WeblogEntry.class);
        EntityType<WeblogEntry> ent_ = t.getModel();
        
        
        if(forCount){
            cq.select(getEntityManager().getCriteriaBuilder().count(t));
        }else{
            cq.select(t);
        }
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(t.get(WEBSITE_FIELD_NAME), weblog));
        predicates.add(cb.equal(t.get(WeblogEntry_.status.getName()), PubStatus.PUBLISHED.toString()));

        predicates.add(cb.equal(t.get(ent_
                .getSingularAttribute(WeblogEntry_.category.getName()))
                .get(WeblogCategory_.name.getName()),
                categoryName));

        cq.where(predicates.toArray(new Predicate[]{}));
        //order by breaks count
        if(!forCount){
            cq.orderBy(cb.desc(t.get(PUBTIME_FIELD_NAME)));
        }    
        Query q = getEntityManager().createQuery(cq);
        
        return q;
    }
    
    public List<WeblogEntry> categoryViewAction(String categoryName, 
            Weblog weblog, int[] range) {
        
        Query q = getCategoryViewQuery(categoryName, weblog,false);
        if(range != null){
            q.setMaxResults(range[1] - range[0] + 1);
            q.setFirstResult(range[0]);
        }

        return q.getResultList();
    }
    
    public int categoryViewActionCount(String categoryName, Weblog weblog) {
        
        Query q = getCategoryViewQuery(categoryName, weblog,true);
        
        return ((Long) q.getSingleResult()).intValue();
    }

}
