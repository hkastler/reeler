/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.pings.entities;

import com.hkstlr.reeler.app.entities.AbstractEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import java.sql.Timestamp;
import java.util.Calendar;

;

/**
 *
 * @author henry.kastler
 *
 */
@Entity
@Table(name = "pingqueueentry")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PingQueueEntry.findAll", query = "SELECT p FROM PingQueueEntry p")
    , @NamedQuery(name = "PingQueueEntry.findById", query = "SELECT p FROM PingQueueEntry p WHERE p.id = :id")
    , @NamedQuery(name = "PingQueueEntry.findByEntryTime", query = "SELECT p FROM PingQueueEntry p WHERE p.entryTime = :entryTime")
    , @NamedQuery(name = "PingQueueEntry.findByPingTarget", query = "SELECT p FROM PingQueueEntry p WHERE p.pingTarget = :pingTarget")
    , @NamedQuery(name = "PingQueueEntry.findByWebsite", query = "SELECT p FROM PingQueueEntry p WHERE p.website = :websiteId")
    , @NamedQuery(name = "PingQueueEntry.findByPingTargetAndWebsite", query = "SELECT p FROM PingQueueEntry p WHERE p.pingTarget = :pingTarget AND p.website = :website")
    , @NamedQuery(name = "PingQueueEntry.findByAttempts", query = "SELECT p FROM PingQueueEntry p WHERE p.attempts = :attempts")
    , @NamedQuery(name = "PingQueueEntry.removeByPingTarget", query = "DELETE FROM PingQueueEntry p WHERE p.pingTarget = ?1")
    , @NamedQuery(name = "PingQueueEntry.getByPingTargetAndWebsite", query = "SELECT p FROM PingQueueEntry p WHERE p.pingTarget = :pingTarget AND p.website = :website")
    , @NamedQuery(name = "PingQueueEntry.getAllOrderByEntryTime", query = "SELECT p FROM PingQueueEntry p ORDER BY p.entryTime")
    , @NamedQuery(name = "PingQueueEntry.getByPingTarget&Website", query = "SELECT p FROM PingQueueEntry p WHERE p.pingTarget = ?1 AND p.website = ?2")
    , @NamedQuery(name = "PingQueueEntry.getByWebsite", query = "SELECT p FROM PingQueueEntry p WHERE p.website = ?1")})
public class PingQueueEntry extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull
    @Column(name = "entrytime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar entryTime;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 48)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pingtargetid", referencedColumnName = "id", nullable = false)
    private PingTarget pingTarget;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 48)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "websiteid", referencedColumnName = "id", nullable = false)
    private Weblog website;

    @Basic(optional = false)
    @NotNull
    @Column(name = "attempts", nullable = false)
    private int attempts;

    public PingQueueEntry() {
    }

    public PingQueueEntry(Calendar entryTime, PingTarget pingTarget,
            Weblog website, int attempts) {

        this.entryTime = entryTime;
        this.pingTarget = pingTarget;
        this.website = website;
        this.attempts = attempts;
    }

    public Calendar getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Calendar entryTime) {
        this.entryTime = entryTime;
    }

    public PingTarget getPingTarget() {
        return pingTarget;
    }

    public void setPingTarget(PingTarget pingTarget) {
        this.pingTarget = pingTarget;
    }

    public Weblog getWebsite() {
        return website;
    }

    public void setWebsite(Weblog website) {
        this.website = website;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PingQueueEntry)) {
            return false;
        }
        PingQueueEntry other = (PingQueueEntry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.PingQueueEntry[ id=" + id + " ]";
    }

}
