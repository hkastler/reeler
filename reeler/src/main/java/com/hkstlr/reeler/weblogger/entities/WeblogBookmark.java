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
@Table(name = "bookmark")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WeblogBookmark.findAll", query = "SELECT b FROM WeblogBookmark b")
    , @NamedQuery(name = "WeblogBookmark.findById", query = "SELECT b FROM WeblogBookmark b WHERE b.id = :id")
    , @NamedQuery(name = "WeblogBookmark.findByName", query = "SELECT b FROM WeblogBookmark b WHERE b.name = :name")
    , @NamedQuery(name = "WeblogBookmark.findByDescription", query = "SELECT b FROM WeblogBookmark b WHERE b.description = :description")
    , @NamedQuery(name = "WeblogBookmark.findByUrl", query = "SELECT b FROM WeblogBookmark b WHERE b.url = :url")
    , @NamedQuery(name = "WeblogBookmark.findByPriority", query = "SELECT b FROM WeblogBookmark b WHERE b.priority = :priority")
    , @NamedQuery(name = "WeblogBookmark.findByImage", query = "SELECT b FROM WeblogBookmark b WHERE b.image = :image")
    , @NamedQuery(name = "WeblogBookmark.findByFeedurl", query = "SELECT b FROM WeblogBookmark b WHERE b.feedurl = :feedurl")
    , @NamedQuery(name = "BookmarkData.getByFolder", query = "SELECT b FROM WeblogBookmark b WHERE b.folder = ?1 order by b.priority")})
public class WeblogBookmark implements Serializable {

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
    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "url", nullable = false, length = 255)
    private String url;
    @Basic(optional = false)
    @NotNull
    @Column(name = "priority", nullable = false)
    private int priority;
    @Size(max = 255)
    @Column(name = "image", length = 255)
    private String image;
    @Size(max = 255)
    @Column(name = "feedurl", length = 255)
    private String feedurl;
    
    @JoinColumn(name = "folderid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private WeblogBookmarkFolder folder;

    public WeblogBookmark() {
    }

    public WeblogBookmark(String id) {
        this.id = id;
    }

    public WeblogBookmark(String id, String name, String url, int priority) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.priority = priority;
    }

    public WeblogBookmark(WeblogBookmarkFolder defaultFolder, String string, String string2, String trim, Object object,
			Object object2) {
		// TODO Auto-generated constructor stub
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFeedurl() {
        return feedurl;
    }

    public void setFeedurl(String feedurl) {
        this.feedurl = feedurl;
    }

    public WeblogBookmarkFolder getFolder() {
        return folder;
    }

    public void setFolderid(WeblogBookmarkFolder folder) {
        this.folder = folder;
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
        if (!(object instanceof WeblogBookmark)) {
            return false;
        }
        WeblogBookmark other = (WeblogBookmark) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.WeblogBookmark[ id=" + id + " ]";
    }
    
}
