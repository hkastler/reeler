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
 * derived from roller code, sometime 2017
 */
package com.hkstlr.reeler.weblogger.weblogs.entities;

import com.hkstlr.reeler.app.control.JsonBuilder;
import com.hkstlr.reeler.app.entities.AbstractEntity;
import java.io.Serializable;
import java.util.List;
import javax.json.JsonObject;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "bookmark_folder")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WeblogBookmarkFolder.findById", query = "SELECT b FROM WeblogBookmarkFolder b WHERE b.id = :id")
    , @NamedQuery(name = "WeblogBookmarkFolder.findByName", query = "SELECT b FROM WeblogBookmarkFolder b WHERE b.name = :name")
    , @NamedQuery(name = "WeblogBookmarkFolder.getByWebsite", query = "SELECT f FROM WeblogBookmarkFolder f WHERE f.weblog = ?1")
    , @NamedQuery(name = "WeblogBookmarkFolder.getByWebsite&amp;Name", query = "SELECT f FROM WeblogBookmarkFolder f WHERE f.weblog = ?1 AND f.name = ?2")})
public class WeblogBookmarkFolder extends AbstractEntity implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @JoinColumn(name = "websiteid", referencedColumnName = "id", nullable = false, insertable = true, updatable = true)
    @ManyToOne(optional = false)
    private Weblog weblog;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "folder", fetch = FetchType.EAGER)
    private List<WeblogBookmark> bookmarks;

    public WeblogBookmarkFolder() {
        //default constructor
    }

    public WeblogBookmarkFolder(String name) {
        this.name = name;
    }

    public WeblogBookmarkFolder(String name, Weblog newWeblog) {
        this.name = name;
        this.weblog = newWeblog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Weblog getWeblog() {
        return weblog;
    }

    public void setWeblog(Weblog weblog) {
        this.weblog = weblog;
    }

    @XmlTransient
    public List<WeblogBookmark> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<WeblogBookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += id.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof WeblogBookmarkFolder)) {
            return false;
        }
        WeblogBookmarkFolder other = (WeblogBookmarkFolder) object;
        return this.id.equals(other.id);
    }

    @Override
    public String toJsonString() {
        return this.toJsonObject().toString();
    }

    @Override
    public JsonObject toJsonObject() {
        return new JsonBuilder().toJsonObject(this, new String[]{"weblog"});
    }
}
