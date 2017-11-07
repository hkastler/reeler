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

import com.hkstlr.reeler.app.entities.AbstractEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    , @NamedQuery(name = "WeblogBookmark.findByFeedUrl", query = "SELECT b FROM WeblogBookmark b WHERE b.feedUrl = :feedUrl")
    , @NamedQuery(name = "BookmarkData.getByFolder", query = "SELECT b FROM WeblogBookmark b WHERE b.folder = ?1 order by b.priority")})
public class WeblogBookmark extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private int priority = 0;
    @Size(max = 255)
    @Column(name = "image", length = 255)
    private String image;
    @Size(max = 255)
    @Column(name = "feedurl", length = 255)
    private String feedUrl;

    @JoinColumn(name = "folderid", referencedColumnName = "id", nullable = false, insertable=true, updatable=true)
    @ManyToOne(optional = false)
    private WeblogBookmarkFolder folder;

    public WeblogBookmark() {
        //default constructor
    }

    public WeblogBookmark(String name, String url, int priority) {
        this.name = name;
        this.url = url;
        this.priority = priority;
    }

     public WeblogBookmark(
            WeblogBookmarkFolder parent,
            String name,
            String desc,
            String url,
            String feedUrl,
            String image) {
        this.folder = parent;
        this.name = name;
        this.description = desc;
        this.url = url;
        this.feedUrl = feedUrl;
        this.image = image;
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

    public String getFeedUrl() {
        return feedUrl;
    }

    public void setFeedUrl(String feedurl) {
        this.feedUrl = feedurl;
    }

    public WeblogBookmarkFolder getFolder() {
        return folder;
    }

    public void setFolder(WeblogBookmarkFolder folder) {
        this.folder = folder;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object object) {
       
        if (!(object instanceof WeblogBookmark)) {
            return false;
        }
        WeblogBookmark other = (WeblogBookmark) object;
        return this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.WeblogBookmark[ id=" + id + " ]";
    }

}
