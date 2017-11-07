/*
 * Copyright 2017 Henry Kastler <hkastler+github@gmail.com>.
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
package com.hkstlr.reeler.weblogger.users.entities;

import com.hkstlr.reeler.app.control.JsonBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.json.JsonObject;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name = "jdbcrealm_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JdbcrealmGroup.findAll", query = "SELECT j FROM JdbcrealmGroup j"),
    @NamedQuery(name = "JdbcrealmGroup.findByJdbcrealmGroupId", query = "SELECT j FROM JdbcrealmGroup j WHERE j.jdbcrealmGroupId = :jdbcrealmGroupId"),
    @NamedQuery(name = "JdbcrealmGroup.findByGroupname", query = "SELECT j FROM JdbcrealmGroup j WHERE j.groupname = :groupname")})
public class JdbcrealmGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "jdbcrealm_groupid")
    private Integer jdbcrealmGroupId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "groupname", nullable = false, length = 255, unique = true)
    private String groupname;
    
    @ManyToMany(mappedBy = "jdbcrealmGroups")
    private List<User> users = new ArrayList<User>();
    
    
    public JdbcrealmGroup() {
        //constructor
    }

    public JdbcrealmGroup(Integer jdbcrealmGroupId) {
        this.jdbcrealmGroupId = jdbcrealmGroupId;
    }

    public JdbcrealmGroup(Integer jdbcrealmGroupId, String groupname) {
        this.jdbcrealmGroupId = jdbcrealmGroupId;
        this.groupname = groupname;
    }

    public Integer getJdbcrealmGroupId() {
        return jdbcrealmGroupId;
    }

    public void setJdbcrealmGroupId(Integer jdbcrealmGroupId) {
        this.jdbcrealmGroupId = jdbcrealmGroupId;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

        
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jdbcrealmGroupId != null ? jdbcrealmGroupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof JdbcrealmGroup)) {
            return false;
        }
        JdbcrealmGroup other = (JdbcrealmGroup) object;
        if ((this.jdbcrealmGroupId == null && other.jdbcrealmGroupId != null) || (this.jdbcrealmGroupId != null && !this.jdbcrealmGroupId.equals(other.jdbcrealmGroupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //skip the manytomany with the owner or jsonbuilder will stackoverflow
        return new JsonBuilder().toJsonString(this,new String[]{"users"});
    }
    
     
    public JsonObject toJsonObject() {
        //skip the manytomany with the owner or jsonbuilder will stackoverflow
        return new JsonBuilder().toJsonObject(this,new String[]{"users"});
    }
    
}
