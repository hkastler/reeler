/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.entities;

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

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "roller_hitcounts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WeblogHitCount.findAll", query = "SELECT r FROM WeblogHitCount r")
    , @NamedQuery(name = "WeblogHitCount.findById", query = "SELECT r FROM WeblogHitCount r WHERE r.id = :id")
    , @NamedQuery(name = "WeblogHitCount.findByWebsiteid", query = "SELECT r FROM WeblogHitCount r WHERE r.weblog = :websiteid")
    , @NamedQuery(name = "WeblogHitCount.findByDailyhits", query = "SELECT r FROM WeblogHitCount r WHERE r.dailyHits = :dailyhits")
    , @NamedQuery(name = "WeblogHitCount.getByWeblog", query = "SELECT h FROM WeblogHitCount h WHERE h.weblog = ?1")
    , @NamedQuery(name = "WeblogHitCount.getByWeblogEnabledTrueAndActiveTrue&DailyHitsGreaterThenZero&WeblogLastModifiedGreaterOrderByDailyHitsDesc", query = "SELECT h FROM WeblogHitCount h WHERE h.weblog.visible = true AND h.weblog.isActive = true AND h.weblog.lastModified > ?1 AND h.dailyHits > 0 ORDER BY h.dailyHits DESC")
    , @NamedQuery(name = "WeblogHitCount.updateDailyHitCountZero", query = "UPDATE WeblogHitCount h SET h.dailyHits = 0")})
public class WeblogHitCount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 48)
    @Column(name = "id", nullable = false, length = 48)
    private String id;
    
    @Basic(optional = false)
    @NotNull
    @ManyToOne
    @JoinColumn(name = "websiteid", nullable = false)
    private Weblog weblog;
    
    @Column(name = "dailyhits")
    private Integer dailyHits;

    public WeblogHitCount() {
    }

    public WeblogHitCount(String id) {
        this.id = id;
    }

    public WeblogHitCount(String id, Weblog weblog) {
        this.id = id;
        this.weblog = weblog;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Weblog getWeblog() {
		return weblog;
	}

	public void setWeblog(Weblog weblog) {
		this.weblog = weblog;
	}

	public Integer getDailyHits() {
		return dailyHits;
	}

	public void setDailyHits(Integer dailyHits) {
		this.dailyHits = dailyHits;
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
        if (!(object instanceof WeblogHitCount)) {
            return false;
        }
        WeblogHitCount other = (WeblogHitCount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.WeblogHitCount[ id=" + id + " ]";
    }
    
}
