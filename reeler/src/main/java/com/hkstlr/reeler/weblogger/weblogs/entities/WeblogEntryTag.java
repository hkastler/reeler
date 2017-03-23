/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "roller_weblogentrytag")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WeblogEntryTag.findAll", query = "SELECT r FROM WeblogEntryTag r")
    , @NamedQuery(name = "WeblogEntryTag.findById", query = "SELECT r FROM WeblogEntryTag r WHERE r.id = :id")
    , @NamedQuery(name = "WeblogEntryTag.findByWebsite", query = "SELECT r FROM WeblogEntryTag r WHERE r.weblog = :website")
    , @NamedQuery(name = "WeblogEntryTag.findByCreator", query = "SELECT r FROM WeblogEntryTag r WHERE r.creator = :creator")
    , @NamedQuery(name = "WeblogEntryTag.findByName", query = "SELECT r FROM WeblogEntryTag r WHERE r.name = :name")
    , @NamedQuery(name = "WeblogEntryTag.findByTime", query = "SELECT r FROM WeblogEntryTag r WHERE r.time = :time")
    , @NamedQuery(name = "WeblogEntryTag.getByWeblog", query = "SELECT w FROM WeblogEntryTag w WHERE w.weblog = ?1")})
public class WeblogEntryTag implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 48)
    @Column(name = "id", nullable = false, length = 48)
    private String id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 48)
    @ManyToOne
    @JoinColumn(name = "websiteid", referencedColumnName = "id")
    private Weblog weblog;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "creator", nullable = false, length = 255)
    private String creator;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    
    @JoinColumn(name = "entryid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private WeblogEntry weblogEntry;

    public WeblogEntryTag() {
    }

    public WeblogEntryTag(String id) {
        this.id = id;
    }

    public WeblogEntryTag(String id, Weblog website, String creator, String name, Date time) {
        this.id = id;
        this.weblog = website;
        this.creator = creator;
        this.name = name;
        this.time = time;
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

	public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public WeblogEntry getWeblogEntry() {
		return weblogEntry;
	}

	public void setWeblogEntry(WeblogEntry weblogEntry) {
		this.weblogEntry = weblogEntry;
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
        if (!(object instanceof WeblogEntryTag)) {
            return false;
        }
        WeblogEntryTag other = (WeblogEntryTag) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.WeblogEntryTag[ id=" + id + " ]";
    }
    
}
