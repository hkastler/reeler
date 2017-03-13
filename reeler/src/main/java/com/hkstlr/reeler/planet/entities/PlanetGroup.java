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

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
@Table(name = "rag_group", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"planet_id", "handle"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanetGroup.findAll", query = "SELECT p FROM PlanetGroup p")
    , @NamedQuery(name = "PlanetGroup.findById", query = "SELECT p FROM PlanetGroup p WHERE p.id = :id")
    , @NamedQuery(name = "PlanetGroup.findByPlanetId", query = "SELECT p FROM PlanetGroup p WHERE p.planet = :planet")
    , @NamedQuery(name = "PlanetGroup.findByHandle", query = "SELECT p FROM PlanetGroup p WHERE p.handle = :handle")
    , @NamedQuery(name = "PlanetGroup.findByTitle", query = "SELECT p FROM PlanetGroup p WHERE p.title = :title")
    , @NamedQuery(name = "PlanetGroup.findByDescription", query = "SELECT p FROM PlanetGroup p WHERE p.description = :description")
    , @NamedQuery(name = "PlanetGroup.findByMaxPageEntries", query = "SELECT p FROM PlanetGroup p WHERE p.maxPageEntries = :maxPageEntries")
    , @NamedQuery(name = "PlanetGroup.findByMaxFeedEntries", query = "SELECT p FROM PlanetGroup p WHERE p.maxFeedEntries = :maxFeedEntries")
    , @NamedQuery(name = "PlanetGroup.findByCatRestriction", query = "SELECT p FROM PlanetGroup p WHERE p.categoryRestriction = :categoryRestriction")
    , @NamedQuery(name = "PlanetGroup.findByGroupPage", query = "SELECT p FROM PlanetGroup p WHERE p.groupPage = :groupPage")})
public class PlanetGroup extends AbstractEntity{

    private static final long serialVersionUID = 1L;
   
    @ManyToOne(targetEntity = Planet.class)
    @JoinColumn(name = "planet_id", referencedColumnName = "id", insertable = true, updatable = true, nullable = true)
    private List<Planet> planet;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "handle", nullable = false, length = 32)
    private String handle;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "title", nullable = false, length = 255)
    private String title;
    
    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;
    
    @Column(name = "max_page_entries")
    private Integer maxPageEntries;
    
    @Column(name = "max_feed_entries")
    private Integer maxFeedEntries;
    
    @Size(max = 2147483647)
    @Column(name = "cat_restriction", length = 2147483647)
    private String categoryRestriction;
    
    @Size(max = 255)
    @Column(name = "group_page", length = 255)
    private String groupPage;
    
   

    public PlanetGroup() {
    }

    public List<Planet> getPlanet() {
		return planet;
	}

	public void setPlanet(List<Planet> planet) {
		this.planet = planet;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxPageEntries() {
        return maxPageEntries;
    }

    public void setMaxPageEntries(Integer maxPageEntries) {
        this.maxPageEntries = maxPageEntries;
    }

    public Integer getMaxFeedEntries() {
        return maxFeedEntries;
    }

    public void setMaxFeedEntries(Integer maxFeedEntries) {
        this.maxFeedEntries = maxFeedEntries;
    }

    public String getCategoryRestriction() {
        return categoryRestriction;
    }

    public void setCategoryRestriction(String categoryRestriction) {
        this.categoryRestriction = categoryRestriction;
    }

    

    public String getGroupPage() {
        return groupPage;
    }

    public void setGroupPage(String groupPage) {
        this.groupPage = groupPage;
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
        if (!(object instanceof PlanetGroup)) {
            return false;
        }
        PlanetGroup other = (PlanetGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.planet.entities.PlanetGroup[ id=" + id + " ]";
    }
    
}
