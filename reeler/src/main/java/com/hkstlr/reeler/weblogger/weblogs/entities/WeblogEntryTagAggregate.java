/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.entities;

import com.hkstlr.reeler.app.entities.AbstractEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "roller_weblogentrytagagg", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"websiteid", "name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WeblogEntryTagAggregate.findAll", query = "SELECT r FROM WeblogEntryTagAggregate r")
    , @NamedQuery(name = "WeblogEntryTagAggregate.findById", query = "SELECT r FROM WeblogEntryTagAggregate r WHERE r.id = :id")
    , @NamedQuery(name = "WeblogEntryTagAggregate.findByWebsite", query = "SELECT r FROM WeblogEntryTagAggregate r WHERE r.weblog = :website")
    , @NamedQuery(name = "WeblogEntryTagAggregate.findByName", query = "SELECT r FROM WeblogEntryTagAggregate r WHERE r.name = :name")
    , @NamedQuery(name = "WeblogEntryTagAggregate.findByTotal", query = "SELECT r FROM WeblogEntryTagAggregate r WHERE r.total = :total")
    , @NamedQuery(name = "WeblogEntryTagAggregate.findByLastUsed", query = "SELECT r FROM WeblogEntryTagAggregate r WHERE r.lastUsed = :lastused")
    , @NamedQuery(name = "WeblogEntryTagAggregate.getByName&WebsiteOrderByLastUsedDesc", query = "SELECT w FROM WeblogEntryTagAggregate w WHERE w.name = ?1 AND w.weblog = ?2 ORDER BY w.lastUsed DESC")
    , @NamedQuery(name = "WeblogEntryTagAggregate.getPopularTagsByWebsite", query = "SELECT w.name, SUM(w.total) FROM WeblogEntryTagAggregate w WHERE w.weblog = ?1 GROUP BY w.name, w.total ORDER BY w.total DESC")
    , @NamedQuery(name = "WeblogEntryTagAggregate.getPopularTagsByWebsite&amp;StartDate", query = "SELECT w.name, SUM(w.total) FROM WeblogEntryTagAggregate w WHERE w.weblog = ?1 AND w.lastUsed >= ?2 GROUP BY w.name, w.total ORDER BY w.total DESC")
    , @NamedQuery(name = "WeblogEntryTagAggregate.removeByTotalLessEqual", query = "DELETE FROM WeblogEntryTagAggregate w WHERE w.total <= ?1")
    , @NamedQuery(name = "WeblogEntryTagAggregate.removeByWeblog", query = "DELETE FROM WeblogEntryTagAggregate w WHERE w.weblog = ?1")
    , @NamedQuery(name = "WeblogEntryTagAggregate.getByName&WebsiteNullOrderByLastUsedDesc", query = "SELECT w FROM WeblogEntryTagAggregate w WHERE w.name = ?1 AND w.weblog IS NULL ORDER BY w.lastUsed DESC")
    , @NamedQuery(name = "WeblogEntryTagAggregate.getPopularTagsByWebsiteNull", query = "SELECT w.name, SUM(w.total) FROM WeblogEntryTagAggregate w WHERE w.weblog IS NULL GROUP BY w.name, w.total ORDER BY w.total DESC")
    , @NamedQuery(name = "WeblogEntryTagAggregate.getPopularTagsByWebsiteNull&amp;StartDate", query = "SELECT w.name, SUM(w.total) FROM WeblogEntryTagAggregate w WHERE w.weblog IS NULL AND w.lastUsed >= ?1 GROUP BY w.name, w.total ORDER BY w.total DESC")})
public class WeblogEntryTagAggregate extends AbstractEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "websiteid", referencedColumnName = "id", insertable = true, updatable = true, nullable = true)
    private Weblog weblog;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Basic(optional = false)
    @NotNull
    @Column(name = "total", nullable = false)
    private int total;

    @Basic(optional = false)
    @NotNull
    @Column(name = "lastused", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUsed;

    public WeblogEntryTagAggregate() {
    }

    public WeblogEntryTagAggregate(Object object, Weblog website, String name, int amount) {
        this.weblog = website;
        this.name = name;
        this.total = amount;
    }

    public Weblog getWeblog() {
        return weblog;
    }

    public void setWeblog(Weblog weblog) {
        this.weblog = weblog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastused) {
        this.lastUsed = lastused;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof WeblogEntryTagAggregate)) {
            return false;
        }
        WeblogEntryTagAggregate other = (WeblogEntryTagAggregate) object;
        if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.WeblogEntryTagAggregate[ id=" + id + " ]";
    }

}
