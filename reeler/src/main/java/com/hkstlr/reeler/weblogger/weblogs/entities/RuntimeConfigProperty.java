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
@Table(name = "roller_properties")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RuntimeConfigProperty.findAll", query = "SELECT r FROM RuntimeConfigProperty r")
    , @NamedQuery(name = "RuntimeConfigProperty.findByName", query = "SELECT r FROM RuntimeConfigProperty r WHERE r.name = :name")
    , @NamedQuery(name = "RuntimeConfigProperty.findByValue", query = "SELECT r FROM RuntimeConfigProperty r WHERE r.value = :value")})
public class RuntimeConfigProperty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    
    @Column(name = "value")
    private String value;

    public RuntimeConfigProperty() {
        //default constructor
    }

    public RuntimeConfigProperty(String name) {
        this.name = name;
    }

    public RuntimeConfigProperty(String name, String defaultValue) {
        this.name = name;
        this.value = defaultValue;
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
        
        if (!(object instanceof RuntimeConfigProperty)) {
            return false;
        }
        RuntimeConfigProperty other = (RuntimeConfigProperty) object;
        if (!this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.RuntimeConfigProperty[ name=" + name + " ]";
    }

}
