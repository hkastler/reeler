/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    
    @JoinColumn(name = "groupname")
    private JdbcrealmGroup group_;
    
    
    @JoinColumn(name = "username")
    private User user_;

    @Override
    public String toString() {
        return "reeler.weblogger.users.entity.UserJdbcrealmGroups[ userJdbcrealmGroupId=" + userJdbcrealmGroupId + " ]";
    }

}
