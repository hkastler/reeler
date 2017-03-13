/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.pings.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "pingtarget")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PingTarget.findAll", query = "SELECT p FROM PingTarget p")
    , @NamedQuery(name = "PingTarget.findById", query = "SELECT p FROM PingTarget p WHERE p.id = :id")
    , @NamedQuery(name = "PingTarget.findByName", query = "SELECT p FROM PingTarget p WHERE p.name = :name")
    , @NamedQuery(name = "PingTarget.findByPingurl", query = "SELECT p FROM PingTarget p WHERE p.pingurl = :pingurl")
    , @NamedQuery(name = "PingTarget.findByConditioncode", query = "SELECT p FROM PingTarget p WHERE p.conditioncode = :conditioncode")
    , @NamedQuery(name = "PingTarget.findByLastsuccess", query = "SELECT p FROM PingTarget p WHERE p.lastsuccess = :lastsuccess")
    , @NamedQuery(name = "PingTarget.findByAutoenabled", query = "SELECT p FROM PingTarget p WHERE p.autoEnabled = :autoenabled")
    , @NamedQuery(name = "PingTarget.getPingTargetsOrderByName", query = "SELECT p FROM PingTarget p ORDER BY p.name")})
public class PingTarget implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 48)
    @Column(name = "id", nullable = false, length = 48)
    private String id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "pingurl", nullable = false, length = 255)
    private String pingurl;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "conditioncode", nullable = false)
    private int conditioncode;
    
    @Column(name = "lastsuccess")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastsuccess;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "autoenabled", nullable = false)
    private boolean autoEnabled;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pingTarget")
    private Collection<AutoPing> autopingCollection;

    public PingTarget() {
    }

    public PingTarget(String id) {
        this.id = id;
    }

    public PingTarget(String id, String name, String pingurl, int conditioncode, boolean autoenabled) {
        this.id = id;
        this.name = name;
        this.pingurl = pingurl;
        this.conditioncode = conditioncode;
        this.autoEnabled = autoenabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPingurl() {
        return pingurl;
    }

    public void setPingurl(String pingurl) {
        this.pingurl = pingurl;
    }

    public int getConditioncode() {
        return conditioncode;
    }

    public void setConditioncode(int conditioncode) {
        this.conditioncode = conditioncode;
    }

    public Date getLastsuccess() {
        return lastsuccess;
    }

    public void setLastsuccess(Date lastsuccess) {
        this.lastsuccess = lastsuccess;
    }

    public boolean isAutoEnabled() {
		return autoEnabled;
	}

	public void setAutoEnabled(boolean autoEnabled) {
		this.autoEnabled = autoEnabled;
	}

	@XmlTransient
    public Collection<AutoPing> getAutopingCollection() {
        return autopingCollection;
    }

    public void setAutopingCollection(Collection<AutoPing> autopingCollection) {
        this.autopingCollection = autopingCollection;
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
        if (!(object instanceof PingTarget)) {
            return false;
        }
        PingTarget other = (PingTarget) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.reltask.kastleblog.weblogger.entities.PingTarget[ id=" + id + " ]";
    }
    
}
