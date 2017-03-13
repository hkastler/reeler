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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;
import javax.xml.bind.annotation.XmlRootElement;

import com.hkstlr.reeler.app.entities.AbstractEntity;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "rag_planet", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"handle"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Planet.findAll", query = "SELECT p FROM Planet p")
    , @NamedQuery(name = "Planet.findById", query = "SELECT p FROM Planet p WHERE p.id = :id")
    , @NamedQuery(name = "Planet.findByHandle", query = "SELECT p FROM Planet p WHERE p.handle = :handle")
    , @NamedQuery(name = "Planet.findByTitle", query = "SELECT p FROM Planet p WHERE p.title = :title")
    , @NamedQuery(name = "Planet.findByDescription", query = "SELECT p FROM Planet p WHERE p.description = :description")})
public class Planet extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32, message = "{Size.Planet.handle}")
    @Column(name = "handle", nullable = false, length = 32, unique = true)
    private String handle;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255, message = "{Size.Planet.title}")
    @Column(name = "title", nullable = false, length = 255)
    private String title;
    
    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;
    
    @Size(min = 1, max = 48)
    @OneToMany(mappedBy = "planet")
    private List<PlanetGroup> groups;

    public Planet() {
    }

    public Planet(String id, String handle, String title) {
        this.handle = handle;
        this.title = title;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof Planet)) {
            return false;
        }
        Planet other = (Planet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.planet.entities.Planet[ id=" + id + " ]";
    }
    
}
