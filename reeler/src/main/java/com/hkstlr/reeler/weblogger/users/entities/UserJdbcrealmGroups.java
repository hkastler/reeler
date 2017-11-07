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
 * 
 */
package com.hkstlr.reeler.weblogger.users.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "user_jdbcrealm_groups")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserJdbcrealmGroups.findAll", query = "SELECT u FROM UserJdbcrealmGroups u")
    ,
    @NamedQuery(name = "UserJdbcrealmGroups.findByUserJdbcrealmGroupId", query = "SELECT u FROM UserJdbcrealmGroups u WHERE u.userJdbcrealmGroupId = :userJdbcrealmGroupId")})
public class UserJdbcrealmGroups implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_jdbcrealm_groupid")
    private int userJdbcrealmGroupId;

    
    @JoinColumn(name = "groupname",unique = true)
    private JdbcrealmGroup group_;
    
    
    @JoinColumn(name = "username",unique = true)
    private User user_;

    @Override
    public String toString() {
        return "reeler.weblogger.users.entity.UserJdbcrealmGroups[ userJdbcrealmGroupId=" + userJdbcrealmGroupId + " ]";
    }

}
