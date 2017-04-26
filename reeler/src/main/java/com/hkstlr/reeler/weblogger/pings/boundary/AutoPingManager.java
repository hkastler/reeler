/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.pings.boundary;

import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.pings.control.PingConfig;
import com.hkstlr.reeler.weblogger.pings.entities.AutoPing;
import com.hkstlr.reeler.weblogger.pings.entities.PingTarget;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Logger;
import javax.ejb.EJB;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author henry.kastler
 */
@Stateless
public class AutoPingManager extends AbstractManager<AutoPing> {

    
    @Inject
    private Logger logger;
    
    
    
    @EJB
    private PingQueueEntryManager pqem;
    
    @PersistenceContext
    private EntityManager em;    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AutoPingManager() {
        super(AutoPing.class);
    }

    public void saveAutoPing(AutoPing autoPing) {
        save(autoPing);

    }

     public void removeAutoPing(AutoPing autoPing) throws WebloggerException {
        em.remove(autoPing);
    }

    public void removeAutoPing(PingTarget pingTarget, Weblog website) throws WebloggerException {
        Query q = em.createNamedQuery("AutoPing.removeByPingTarget&Website");
        q.setParameter(1, pingTarget);
        q.setParameter(2, website);
        q.executeUpdate();
    }

    public void removeAutoPings(Collection<AutoPing> autopings) throws WebloggerException {
        removeAll(autopings);
    }

    public void removeAllAutoPings() throws WebloggerException {
        TypedQuery<AutoPing> q = em.createNamedQuery("AutoPing.getAll", AutoPing.class);
        removeAutoPings(q.getResultList());
    }

   

    public List<AutoPing> getAutoPingsByWebsite(Weblog website) throws WebloggerException {
        TypedQuery<AutoPing> q = em.createNamedQuery("AutoPing.getByWebsite", AutoPing.class);
        q.setParameter(1, website);
        return q.getResultList();
    }

    public List<AutoPing> getAutoPingsByTarget(PingTarget pingTarget) throws WebloggerException {
        TypedQuery<AutoPing> q = em.createNamedQuery("AutoPing.getByPingTarget", AutoPing.class);
        q.setParameter(1, pingTarget);
        return q.getResultList();
    }

    public List<AutoPing> getApplicableAutoPings(WeblogEntry changedWeblogEntry) throws WebloggerException {
        return getAutoPingsByWebsite(changedWeblogEntry.getWebsite());
    }

}
