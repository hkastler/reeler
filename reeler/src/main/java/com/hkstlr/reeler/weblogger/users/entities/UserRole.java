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
 * derived from roller db table, by netbeans, sometime 2017
 */
package com.hkstlr.reeler.weblogger.users.entities;

import com.hkstlr.reeler.app.control.JsonBuilder;
import com.hkstlr.reeler.app.entities.AbstractEntity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
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
@Table(name = "userrole")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserRole.findAll", query = "SELECT u FROM UserRole u")
    , @NamedQuery(name = "UserRole.findById", query = "SELECT u FROM UserRole u WHERE u.id = :id")
    , @NamedQuery(name = "UserRole.findByRolename", query = "SELECT u FROM UserRole u WHERE u.roleName = :roleName")
    , @NamedQuery(name = "UserRole.findByUsername", query = "SELECT u FROM UserRole u WHERE u.userName = :userName")
    , @NamedQuery(name = "UserRole.getByUserName", query = "SELECT r FROM UserRole r WHERE r.userName = ?1")
    , @NamedQuery(name = "UserRole.getByUserNameAndRole", query = "SELECT r FROM UserRole r WHERE r.userName = ?1 AND r.roleName = ?2")})
public class UserRole extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "rolename", nullable = false, length = 255, unique = true)
    private String roleName;

    @Basic
    @Size(max = 255)
    @Column(name = "username",length = 255)
    private String userName;

    public UserRole() {
        super();
    }

    public UserRole(String rolename, String username) {
        super();
        this.roleName = rolename;
        this.userName = username;
    }
    
    public UserRole(String rolename) {
        super();
        this.roleName = rolename;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @ManyToMany(mappedBy="roles")
    @MapKey(name="userName")
    private Map<String, User> owners = new HashMap<String, User>();

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof UserRole)) {
            return false;
        }
        UserRole other = (UserRole) object;
        if ((!this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
         return Json.createObjectBuilder()
        .add("id", id)
        .add("roleName", roleName)
        .build()
        .toString();
    }
    
    @Override
    public String toJsonString(){
        return this.toJsonObject().toString();
    }
    
    @Override
    public JsonObject toJsonObject(){
        return new JsonBuilder().toJsonObject(this, new String[]{"owners"});
    }
    

}
