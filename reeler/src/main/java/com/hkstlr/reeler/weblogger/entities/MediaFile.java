/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.hkstlr.reeler.app.entities.Resource;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "roller_mediafile")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MediaFile.findAll", query = "SELECT r FROM MediaFile r")
    , @NamedQuery(name = "MediaFile.findById", query = "SELECT r FROM MediaFile r WHERE r.id = :id")
    , @NamedQuery(name = "MediaFile.findByName", query = "SELECT r FROM MediaFile r WHERE r.name = :name")
    , @NamedQuery(name = "MediaFile.findByDescription", query = "SELECT r FROM MediaFile r WHERE r.description = :description")
    , @NamedQuery(name = "MediaFile.findByOrigpath", query = "SELECT r FROM MediaFile r WHERE r.originalPath = :originalPath")
    , @NamedQuery(name = "MediaFile.findByContentType", query = "SELECT r FROM MediaFile r WHERE r.contentType = :contentType")
    , @NamedQuery(name = "MediaFile.findByCopyrightText", query = "SELECT r FROM MediaFile r WHERE r.copyrightText = :copyrightText")
    , @NamedQuery(name = "MediaFile.findByWeblog", query = "SELECT r FROM MediaFile r WHERE r.weblog = :weblog")
    , @NamedQuery(name = "MediaFile.findByWidth", query = "SELECT r FROM MediaFile r WHERE r.width = :width")
    , @NamedQuery(name = "MediaFile.findByHeight", query = "SELECT r FROM MediaFile r WHERE r.height = :height")
    , @NamedQuery(name = "MediaFile.findBySizeInBytes", query = "SELECT r FROM MediaFile r WHERE r.sizeInBytes = :sizeInBytes")
    , @NamedQuery(name = "MediaFile.findByDateUploaded", query = "SELECT r FROM MediaFile r WHERE r.dateUploaded = :dateUploaded")
    , @NamedQuery(name = "MediaFile.findByLastUpdated", query = "SELECT r FROM MediaFile r WHERE r.lastUpdated = :lastUpdated")
    , @NamedQuery(name = "MediaFile.findByAnchor", query = "SELECT r FROM MediaFile r WHERE r.anchor = :anchor")
    , @NamedQuery(name = "MediaFile.findByCreator", query = "SELECT r FROM MediaFile r WHERE r.creator = :creator")
    , @NamedQuery(name = "MediaFile.findByIsPublic", query = "SELECT r FROM MediaFile r WHERE r.isPublic = :isPublic")
    , @NamedQuery(name = "MediaFile.getByWeblogAndOrigpath", query = "SELECT f FROM MediaFile f WHERE f.weblog = ?1 AND f.originalPath = ?2")})

public class MediaFile extends Resource implements Serializable {

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
    
    @Size(max = 255)
    @Column(name = "origpath", length = 255)
    private String originalPath;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "content_type", nullable = false, length = 50)
    private String contentType;
    
    @Size(max = 1023)
    @Column(name = "copyright_text", length = 1023)
    private String copyrightText;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 48)
    @ManyToOne()
    @JoinColumn(name = "weblogid", nullable = false)
    private Weblog weblog;
    
    @Column(name = "width")
    private Integer width;
    
    @Column(name = "height")
    private Integer height;
    
    @Column(name = "size_in_bytes")
    private Integer sizeInBytes;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_uploaded", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUploaded;
    
    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;
    
    @Size(max = 255)
    @Column(name = "anchor", length = 255)
    private String anchor;
    
    @Size(max = 255)
    @Column(name = "creator", length = 255)
    private String creator;
    @Basic(optional = false)
    
    @NotNull
    @Column(name = "is_public", nullable = false)
    private boolean isPublic;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "mediaFile", fetch = FetchType.LAZY)
    private List<MediaFileTag> tags;
    
    @JoinColumn(name = "directoryid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private MediaFileDirectory directory;

    public MediaFile() {
    }

    public MediaFile(String id) {
        this.id = id;
    }

    public MediaFile(String id, String name, String contentType, Weblog weblog, Date dateUploaded, boolean isPublic) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
        this.weblog = weblog;
        this.dateUploaded = dateUploaded;
        this.isPublic = isPublic;
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

    public String getOriginalPath() {
		return originalPath;
	}

	public void setOriginalPath(String originalPath) {
		this.originalPath = originalPath;
	}

	public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCopyrightText() {
        return copyrightText;
    }

    public void setCopyrightText(String copyrightText) {
        this.copyrightText = copyrightText;
    }

    public Weblog getWeblog() {
		return weblog;
	}

	public void setWeblog(Weblog weblog) {
		this.weblog = weblog;
	}

	public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getSizeInBytes() {
        return sizeInBytes;
    }

    public void setSizeInBytes(Integer sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
    }

    public Date getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(Date dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getAnchor() {
        return anchor;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public List<MediaFileTag> getTags() {
		return tags;
	}

	public void setTags(List<MediaFileTag> tags) {
		this.tags = tags;
	}

	public MediaFileDirectory getDirectory() {
		return directory;
	}

	public void setDirectory(MediaFileDirectory directory) {
		this.directory = directory;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
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
        if (!(object instanceof MediaFile)) {
            return false;
        }
        MediaFile other = (MediaFile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.MediaFile[ id=" + id + " ]";
    }
    
}
