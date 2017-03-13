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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "rag_properties")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanetRuntimeConfigProperty.findAll", query = "SELECT r FROM PlanetRuntimeConfigProperty r")
    , @NamedQuery(name = "PlanetRuntimeConfigProperty.findByName", query = "SELECT r FROM PlanetRuntimeConfigProperty r WHERE r.name = :name")
    , @NamedQuery(name = "PlanetRuntimeConfigProperty.findByValue", query = "SELECT r FROM PlanetRuntimeConfigProperty r WHERE r.value = :value")})
public class PlanetRuntimeConfigProperty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Size(max = 2147483647)
    @Column(name = "value", length = 2147483647)
    private String value;

    public PlanetRuntimeConfigProperty() {
    }

    public PlanetRuntimeConfigProperty(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanetRuntimeConfigProperty)) {
            return false;
        }
        PlanetRuntimeConfigProperty other = (PlanetRuntimeConfigProperty) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.planet.entities.RuntimeConfigProperty[ name=" + name + " ]";
    }
    
}
