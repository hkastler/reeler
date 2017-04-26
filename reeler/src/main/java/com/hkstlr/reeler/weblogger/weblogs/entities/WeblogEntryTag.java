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
    , @NamedQuery(name = "WeblogEntryTag.findByNameAndWeblogEntry", query = "SELECT r FROM WeblogEntryTag r WHERE r.name = :name AND r.weblogEntry = :weblogEntry")
    , @NamedQuery(name = "WeblogEntryTag.findByCreator", query = "SELECT r FROM WeblogEntryTag r WHERE r.creator = :creator")
    , @NamedQuery(name = "WeblogEntryTag.findByName", query = "SELECT r FROM WeblogEntryTag r WHERE r.name = :name")
    , @NamedQuery(name = "WeblogEntryTag.findByCreateDate", query = "SELECT r FROM WeblogEntryTag r WHERE r.createDate = :createDate")
    , @NamedQuery(name = "WeblogEntryTag.getByWeblog", query = "SELECT w FROM WeblogEntryTag w WHERE w.weblog = ?1")})
public class WeblogEntryTag extends AbstractEntity implements Serializable {

    @Basic(optional = false)
    @NotNull(message = "weblog can not be null")
    @ManyToOne
    @JoinColumn(name = "websiteid", referencedColumnName = "id", insertable = true, updatable = true, nullable = false)
    private Weblog weblog;

    @Basic(optional = false)
    @NotNull(message = "creator can not be null")
    @Size(min = 1, max = 255)
    @Column(name = "creator", nullable = false, length = 255)
    private String creator;

    @Basic(optional = false)
    @NotNull(message = "name can not be null")
    @Size(min = 1, max = 255)
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Basic(optional = false)
    @NotNull
    @Column(name = "time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate = new Date();

    
    @ManyToOne(optional = false)
    @JoinColumn(
        name = "entryid",
        referencedColumnName = "id",
        insertable = true,
        updatable = false,
        nullable = false
    )
    private WeblogEntry weblogEntry;

    public WeblogEntryTag() {
    }

    public WeblogEntryTag(String name) {
        this.name = name;
        this.createDate = new Date();
    }

    public WeblogEntryTag(Weblog website, String creator, String name, Date time) {
        this.weblog = website;
        this.creator = creator;
        this.name = name;
        this.createDate = time;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

        if (!(object instanceof WeblogEntryTag)) {
            return false;
        }
        WeblogEntryTag other = (WeblogEntryTag) object;
        if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.WeblogEntryTag[ id=" + id + " ]".concat(name);
    }

}
