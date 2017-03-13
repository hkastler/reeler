/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.pings.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.hkstlr.reeler.weblogger.entities.Weblog;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "autoping")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AutoPing.findAll", query = "SELECT a FROM AutoPing a")
, @NamedQuery(name = "AutoPing.findById", query = "SELECT a FROM AutoPing a WHERE a.id = :id")
, @NamedQuery(name = "AutoPing.getAll", query = "SELECT a FROM AutoPing a")
, @NamedQuery(name = "AutoPing.getByPingTarget", query = "SELECT a FROM AutoPing a WHERE a.pingTarget = ?1")
, @NamedQuery(name = "AutoPing.getByWebsite", query = "SELECT a FROM AutoPing a WHERE a.website = ?1")
, @NamedQuery(name = "AutoPing.removeByPingTarget", query = "DELETE FROM AutoPing a WHERE a.pingTarget = ?1")
, @NamedQuery(name = "AutoPing.removeByPingTarget&amp;Website", query = "DELETE FROM AutoPing a WHERE a.pingTarget = ?1 AND a.website = ?2")
, @NamedQuery(name = "AutoPing.removeAll", query = "DELETE FROM AutoPing a")})
public class AutoPing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 48)
    @Column(name = "id", nullable = false, length = 48)
    private String id;
    
    @JoinColumn(name = "pingtargetid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private PingTarget pingTarget;
    
    @JoinColumn(name = "websiteid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Weblog website;

    public AutoPing() {
    }

    public AutoPing(String id) {
        this.id = id;
    }

    public AutoPing(Object object, PingTarget pingTarget2, Weblog newWeblog) {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PingTarget getPingTarget() {
        return pingTarget;
    }

    public void setPingtargetid(PingTarget pingtarget) {
        this.pingTarget = pingtarget;
    }

    public Weblog getWebsite() {
        return website;
    }

    public void setWebsite(Weblog website) {
        this.website = website;
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
        if (!(object instanceof AutoPing)) {
            return false;
        }
        AutoPing other = (AutoPing) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.reltask.kastleblog.weblogger.entities.AutoPing[ id=" + id + " ]";
    }
    
}
