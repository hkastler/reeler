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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "entryattribute", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"entryid", "name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WeblogEntryAttribute.findById", query = "SELECT e FROM WeblogEntryAttribute e WHERE e.id = :id")
    , @NamedQuery(name = "WeblogEntryAttribute.findByName", query = "SELECT e FROM WeblogEntryAttribute e WHERE e.name = :name")
    , @NamedQuery(name = "WeblogEntryAttribute.findByValue", query = "SELECT e FROM WeblogEntryAttribute e WHERE e.value = :value")})
public class WeblogEntryAttribute implements Serializable {

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
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1)
    @Column(name = "value", nullable = false)
    private String value;
    
    @JoinColumn(name = "entryid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private WeblogEntry entry;

    public WeblogEntryAttribute() {
        //default constructor
    }

    public WeblogEntryAttribute(String id) {
        this.id = id;
    }

    public WeblogEntryAttribute(String id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public WeblogEntry getEntry() {
		return entry;
	}

	public void setEntry(WeblogEntry entry) {
		this.entry = entry;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof WeblogEntryAttribute)) {
            return false;
        }
        WeblogEntryAttribute other = (WeblogEntryAttribute) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.WeblogEntryAttribute[ id=" + id + " ]";
    }
    
}
