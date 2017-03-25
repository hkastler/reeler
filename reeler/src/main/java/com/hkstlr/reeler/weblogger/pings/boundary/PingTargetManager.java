/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.pings.boundary;



import com.hkstlr.reeler.app.boundary.manager.AbstractManager;
import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.pings.entities.PingTarget;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


/**
 *
 * @author henry.kastler
 */
@Stateless
public class PingTargetManager extends AbstractManager<PingTarget> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PingTargetManager() {
        super(PingTarget.class);
    }

    public void removePingTarget(PingTarget pingTarget) 
            throws WebloggerException {
        // remove contents and then target
        this.removePingTargetContents(pingTarget);
        getEntityManager().remove(pingTarget);
    }

    /**
     * Convenience method which removes any queued pings or auto pings that
     * reference the given ping target.
     */
    private void removePingTargetContents(PingTarget ping) 
            throws WebloggerException {
        // Remove the website's ping queue entries
        Query q = em.createNamedQuery("PingQueueEntry.removeByPingTarget");
        q.setParameter(1, ping);
        q.executeUpdate();
        
        // Remove the website's auto ping configurations
        q = em.createNamedQuery("AutoPing.removeByPingTarget");
        q.setParameter(1, ping);
        q.executeUpdate();
    }

    public void savePingTarget(PingTarget pingTarget)
            throws WebloggerException {
    	getEntityManager().persist(pingTarget);
    }

    public PingTarget getPingTarget(String id)
            throws WebloggerException {
        return (PingTarget)getEntityManager().find(PingTarget.class, id);
    }

    public boolean targetNameExists(String pingTargetName)
            throws WebloggerException {

        // Within that set of targets, fail if there is a target
        // with the same name and that target doesn't
        // have the same id.
        for (PingTarget pt : getCommonPingTargets()) {
            if (pt.getName().equals(pingTargetName)) {
                return true;
            }
        }
        // No conflict found
        return false;
    }

    
    public boolean isUrlWellFormed(String url)
            throws WebloggerException {

        if (url == null || url.trim().length() == 0) {
            return false;
        }
        try {
            URL parsedUrl = new URL(url);
            // OK.  If we get here, it parses ok.  Now just check 
            // that the protocol is http and there is a host portion.
            boolean isHttp = parsedUrl.getProtocol().equals("http");
            boolean hasHost = (parsedUrl.getHost() != null) && 
                (parsedUrl.getHost().trim().length() > 0);
            return isHttp && hasHost;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    
    public boolean isHostnameKnown(String url)
            throws WebloggerException {
        if (url == null || url.trim().length() == 0) {
            return false;
        }
        try {
            URL parsedUrl = new URL(url);
            String host = parsedUrl.getHost();
            if (host == null || host.trim().length() == 0) {
                return false;
            }
            InetAddress addr = InetAddress.getByName(host);
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (UnknownHostException e) {
            return false;
        }
    }

    public List<PingTarget> getCommonPingTargets()
            throws WebloggerException {
        TypedQuery<PingTarget> q = getNamedQuery(
                "PingTarget.getPingTargetsOrderByName", PingTarget.class);
        return q.getResultList();
    }
    
}
