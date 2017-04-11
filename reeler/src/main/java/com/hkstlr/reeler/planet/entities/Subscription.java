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

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.hkstlr.reeler.app.entities.AbstractEntity;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "rag_subscription", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"feed_url"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subscription.findAll", query = "SELECT s FROM Subscription s")
    , @NamedQuery(name = "Subscription.findById", query = "SELECT s FROM Subscription s WHERE s.id = :id")
    , @NamedQuery(name = "Subscription.findByTitle", query = "SELECT s FROM Subscription s WHERE s.title = :title")
    , @NamedQuery(name = "Subscription.findByFeedURL", query = "SELECT s FROM Subscription s WHERE s.feedURL = :feedURL")
    , @NamedQuery(name = "Subscription.findBySiteURL", query = "SELECT s FROM Subscription s WHERE s.siteURL = :siteURL")
    , @NamedQuery(name = "Subscription.findByAuthor", query = "SELECT s FROM Subscription s WHERE s.author = :author")
    , @NamedQuery(name = "Subscription.findByLastUpdated", query = "SELECT s FROM Subscription s WHERE s.lastUpdated = :lastUpdated")
    , @NamedQuery(name = "Subscription.findByInboundlinks", query = "SELECT s FROM Subscription s WHERE s.inboundlinks = :inboundLinks")
    , @NamedQuery(name = "Subscription.findByInboundblogs", query = "SELECT s FROM Subscription s WHERE s.inboundblogs = :inboundBlogs")})
public class Subscription extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "title", nullable = false, length = 255)
    private String title;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "feed_url", nullable = false, length = 255)
    private String feedURL;
    
    @Size(max = 255)
    @Column(name = "site_url", length = 255)
    private String siteURL;
    
    @Size(max = 255)
    @Column(name = "author", length = 255)
    private String author;
    
    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;
    
    @Column(name = "inbound_links")
    private Integer inboundlinks;
    
    @Column(name = "inbound_blogs")
    private Integer inboundblogs;
    
    @OneToMany(mappedBy = "subscription" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SubscriptionEntry> entries;

    public Subscription() {
    }

    public Subscription(String id, String title, String feedURL) {
        this.title = title;
        this.feedURL = feedURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFeedURL() {
        return feedURL;
    }

    public void setFeedURL(String feedURL) {
        this.feedURL = feedURL;
    }

    public String getSiteURL() {
        return siteURL;
    }

    public void setSiteURL(String siteURL) {
        this.siteURL = siteURL;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    

    public Integer getInboundlinks() {
		return inboundlinks;
	}

	public void setInboundlinks(Integer inboundlinks) {
		this.inboundlinks = inboundlinks;
	}

	public Integer getInboundblogs() {
		return inboundblogs;
	}

	public void setInboundblogs(Integer inboundblogs) {
		this.inboundblogs = inboundblogs;
	}

	public List<SubscriptionEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<SubscriptionEntry> entries) {
		this.entries = entries;
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
        if (!(object instanceof Subscription)) {
            return false;
        }
        Subscription other = (Subscription) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.planet.entities.Subscription[ id=" + id + " ]";
    }
    
}
