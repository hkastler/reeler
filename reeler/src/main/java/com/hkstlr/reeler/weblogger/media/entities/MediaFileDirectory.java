/*
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
 * 
 */
package com.hkstlr.reeler.weblogger.media.entities;

import com.hkstlr.reeler.app.entities.AbstractEntity;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "roller_mediafiledir")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MediaFileDirectory.findAll", query = "SELECT r FROM MediaFileDirectory r")
    , @NamedQuery(name = "MediaFileDirectory.findById", query = "SELECT r FROM MediaFileDirectory r WHERE r.id = :id")
    , @NamedQuery(name = "MediaFileDirectory.findByName", query = "SELECT r FROM MediaFileDirectory r WHERE r.name = :name")
    , @NamedQuery(name = "MediaFileDirectory.findByDescription", query = "SELECT r FROM MediaFileDirectory r WHERE r.description = :description")
    , @NamedQuery(name = "MediaFileDirectory.getByWeblog", query = "SELECT d FROM MediaFileDirectory d WHERE d.weblog = ?1")
    , @NamedQuery(name = "MediaFileDirectory.getByWeblogAndName", query = "SELECT d FROM MediaFileDirectory d WHERE d.weblog = ?1 AND d.name = ?2")})
public class MediaFileDirectory extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @JoinColumn(name = "websiteid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Weblog weblog;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "directory")
    private List<MediaFile> mediaFiles;

    public MediaFileDirectory() {
        super();
    }

    public MediaFileDirectory(String id, String name) {
        this.name = name;
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

    public Weblog getWeblog() {
        return weblog;
    }

    public void setWeblog(Weblog weblog) {
        this.weblog = weblog;
    }

    public List<MediaFile> getMediaFiles() {
        return mediaFiles;
    }

    public void setMediaFiles(List<MediaFile> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof MediaFileDirectory)) {
            return false;
        }
        MediaFileDirectory other = (MediaFileDirectory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.MediaFileDirectory[ id=" + id + " ]";
    }

}
