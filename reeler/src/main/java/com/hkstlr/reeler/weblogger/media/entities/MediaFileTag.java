/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.media.entities;

import com.hkstlr.reeler.app.entities.AbstractEntity;
import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author henry.kastler
 * 
 */
@Entity
@Table(name = "roller_mediafiletag")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MediaFileTag.findAll", query = "SELECT r FROM MediaFileTag r")
    , @NamedQuery(name = "MediaFileTag.findById", query = "SELECT r FROM MediaFileTag r WHERE r.id = :id")
    , @NamedQuery(name = "MediaFileTag.findByName", query = "SELECT r FROM MediaFileTag r WHERE r.name = :name")
    , @NamedQuery(name = "MediaFileTag.getByMediaFile", query = "SELECT w FROM MediaFileTag w WHERE w.mediaFile = ?1")})
public class MediaFileTag extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "mediafile_id", referencedColumnName = "id", insertable=true, updatable=true, nullable=false)
    private MediaFile mediaFile;

    public MediaFileTag() {
    }

    

    public MediaFileTag(String id, String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MediaFile getMediafile() {
		return mediaFile;
	}

	public void setMediafile(MediaFile mediafile) {
		this.mediaFile = mediafile;
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
        if (!(object instanceof MediaFileTag)) {
            return false;
        }
        MediaFileTag other = (MediaFileTag) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.MediaFileTag[ id=" + id + " ]";
    }
    
}
