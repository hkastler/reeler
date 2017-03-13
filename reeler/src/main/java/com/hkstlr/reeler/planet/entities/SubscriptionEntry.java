/*
 * Author henry.kastler hkstlr.com 2017 
 *
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
 */
package com.hkstlr.reeler.planet.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

import com.hkstlr.reeler.app.entities.AbstractEntity;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "rag_entry")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubscriptionEntry.findAll", query = "SELECT s FROM SubscriptionEntry s")
    , @NamedQuery(name = "SubscriptionEntry.findById", query = "SELECT s FROM SubscriptionEntry s WHERE s.id = :id")
    , @NamedQuery(name = "SubscriptionEntry.findBySubscriptionId", query = "SELECT s FROM SubscriptionEntry s WHERE s.subscription = :subscription")
    , @NamedQuery(name = "SubscriptionEntry.findByHandle", query = "SELECT s FROM SubscriptionEntry s WHERE s.handle = :handle")
    , @NamedQuery(name = "SubscriptionEntry.findByTitle", query = "SELECT s FROM SubscriptionEntry s WHERE s.title = :title")
    , @NamedQuery(name = "SubscriptionEntry.findByGuid", query = "SELECT s FROM SubscriptionEntry s WHERE s.guid = :guid")
    , @NamedQuery(name = "SubscriptionEntry.findByPermalink", query = "SELECT s FROM SubscriptionEntry s WHERE s.permalink = :permalink")
    , @NamedQuery(name = "SubscriptionEntry.findByAuthor", query = "SELECT s FROM SubscriptionEntry s WHERE s.author = :author")
    , @NamedQuery(name = "SubscriptionEntry.findByContent", query = "SELECT s FROM SubscriptionEntry s WHERE s.content = :content")
    , @NamedQuery(name = "SubscriptionEntry.findByCategories", query = "SELECT s FROM SubscriptionEntry s WHERE s.categoriesString = :categories")
    , @NamedQuery(name = "SubscriptionEntry.findByPublished", query = "SELECT s FROM SubscriptionEntry s WHERE s.published = :published")
    , @NamedQuery(name = "SubscriptionEntry.findByUpdated", query = "SELECT s FROM SubscriptionEntry s WHERE s.updated = :updated")})
public class SubscriptionEntry extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "subscription_id", insertable = true, updatable = true, nullable = false)
    private Subscription subscription;

    @Size(max = 255)
    @Column(name = "handle", length = 255)
    private String handle;

    @Size(max = 255)
    @Column(name = "title", length = 255)
    private String title;

    @Size(max = 255)
    @Column(name = "guid", length = 255, unique = true)
    private String guid;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "permalink", nullable = false, length = 2147483647)
    private String permalink;

    @Size(max = 255)
    @Column(name = "author", length = 255)
    private String author;

    @Size(max = 2147483647)
    @Column(name = "content", length = 2147483647)
    private String content;

    @Size(max = 2147483647)
    @Column(name = "categories", length = 2147483647)
    private String categoriesString;

    @Basic(optional = false)
    @NotNull
    @Column(name = "published", nullable = false)

    @Temporal(TemporalType.TIMESTAMP)
    private Date published;

    @Column(name = "updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    public SubscriptionEntry() {
    }

    public String getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategoriesString() {
        return categoriesString;
    }

    public void setCategoriesString(String categoriesString) {
        this.categoriesString = categoriesString;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
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
        if (!(object instanceof SubscriptionEntry)) {
            return false;
        }
        SubscriptionEntry other = (SubscriptionEntry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.planet.entities.SubscriptionEntry[ id=" + id + " ]";
    }

}
