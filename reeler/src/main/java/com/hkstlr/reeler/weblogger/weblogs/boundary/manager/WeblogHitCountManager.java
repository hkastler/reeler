/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.hkstlr.reeler.weblogger.weblogs.boundary.manager;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogHitCount;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author henry.kastler
 */
@Stateless
public class WeblogHitCountManager extends AbstractManager<WeblogHitCount> {

    @Inject
    private Logger log;
    
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    private static final String GETBYWEBLOG = "WeblogHitCount.getByWeblog";

    public WeblogHitCountManager() {
        super(WeblogHitCount.class);
    }
    
    /**
     * @inheritDoc
     */
    public WeblogHitCount getHitCount(String id) throws WebloggerException {
        
        // do lookup
        return find(id);
    }
    
    /**
     * @inheritDoc
     */
    public WeblogHitCount getHitCountByWeblog(Weblog weblog)
    throws WebloggerException {
        TypedQuery<WeblogHitCount> q = em.createNamedQuery(GETBYWEBLOG, WeblogHitCount.class);
        q.setParameter(1, weblog);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            log.log(Level.WARNING,null,e);
            return null;
        }
    }
    
    /**
     * @inheritDoc
     */
    public List<WeblogHitCount> getHotWeblogs(int sinceDays, int offset, int length)
    throws WebloggerException {
        
        // figure out start date
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1 * sinceDays);
        Date startDate = cal.getTime();

        TypedQuery<WeblogHitCount> query = em.createNamedQuery(
                "WeblogHitCount.getByWeblogEnabledTrueAndActiveTrue&DailyHitsGreaterThenZero&WeblogLastModifiedGreaterOrderByDailyHitsDesc",
                WeblogHitCount.class);
        query.setParameter(1, startDate);
        
        if (offset != 0) {
            query.setFirstResult(offset);
        }
        if (length != -1) {
            query.setMaxResults(length);
        }        
        return query.getResultList();
    }
    
    
    /**
     * @inheritDoc
     */
    public void saveHitCount(WeblogHitCount hitCount) throws WebloggerException {
        save(hitCount);
    }
    
    
    /**
     * @inheritDoc
     */
    public void removeHitCount(WeblogHitCount hitCount) throws WebloggerException {
        remove(hitCount);
    }
    
    
    /**
     * @inheritDoc
     */
    public void incrementHitCount(Weblog weblog, int amount)
    throws WebloggerException {
        
        if(amount == 0) {
            throw new WebloggerException("Tag increment amount cannot be zero.");
        }
        
        if(weblog == null) {
            throw new WebloggerException("Website cannot be NULL.");
        }

        TypedQuery<WeblogHitCount> q = em.createNamedQuery(GETBYWEBLOG, WeblogHitCount.class);
        q.setParameter(1, weblog);
        WeblogHitCount hitCount;
        try {
            hitCount = q.getSingleResult();
        } catch (NoResultException e) {
            log.log(Level.WARNING,null,e);
            hitCount = null;
        }
        
        // create it if it doesn't exist
        if(hitCount == null && amount > 0) {
            hitCount = new WeblogHitCount();
            hitCount.setWeblog(weblog);
            hitCount.setDailyHits(amount);
            save(hitCount);
        } else if(hitCount != null) {
            hitCount.setDailyHits(hitCount.getDailyHits() + amount);
            save(hitCount);
        }
    }
    
    /**
     * @inheritDoc
     */
    public void resetAllHitCounts() throws WebloggerException {       
        Query q = em.createNamedQuery("WeblogHitCount.updateDailyHitCountZero");
        q.executeUpdate();
    }
    
    /**
     * @inheritDoc
     */
    public void resetHitCount(Weblog weblog) throws WebloggerException {
        TypedQuery<WeblogHitCount> q = em.createNamedQuery(GETBYWEBLOG, WeblogHitCount.class);
        q.setParameter(1, weblog);
        WeblogHitCount hitCount;
        try {
            hitCount = q.getSingleResult();
            hitCount.setDailyHits(0);
            save(hitCount);
        } catch (NoResultException e) {
            // ignore: no hit count for weblog
            log.log(Level.WARNING,null,e);
        }       

    }
    
    
    
}
