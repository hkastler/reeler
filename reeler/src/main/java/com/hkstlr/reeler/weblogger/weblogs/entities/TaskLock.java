/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.weblogs.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "roller_tasklock", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TaskLock.findAll", query = "SELECT r FROM TaskLock r")
    , @NamedQuery(name = "TaskLock.findById", query = "SELECT r FROM TaskLock r WHERE r.id = :id")
    , @NamedQuery(name = "TaskLock.findByName", query = "SELECT r FROM TaskLock r WHERE r.name = :name")
    , @NamedQuery(name = "TaskLock.findByIslocked", query = "SELECT r FROM TaskLock r WHERE r.isLocked = :isLocked")
    , @NamedQuery(name = "TaskLock.findByTimeacquired", query = "SELECT r FROM TaskLock r WHERE r.timeAcquired = :timeAcquired")
    , @NamedQuery(name = "TaskLock.findByTimeleased", query = "SELECT r FROM TaskLock r WHERE r.timeLeased = :timeLeased")
    , @NamedQuery(name = "TaskLock.findByLastRun", query = "SELECT r FROM TaskLock r WHERE r.lastRun = :lastRun")
    , @NamedQuery(name = "TaskLock.findByClient", query = "SELECT r FROM TaskLock r WHERE r.clientId = :clientId")
    , @NamedQuery(name = "TaskLock.getByName", query = "SELECT t FROM TaskLock t WHERE t.name = ?1")
    , @NamedQuery(name = "TaskLock.updateClient&Timeacquired&Timeleased&LastRunByName&Timeacquired", query = " UPDATE TaskLock t SET t.clientId=?1, t.timeAcquired= ?2, t.timeLeased= ?3, t.lastRun= ?4 WHERE t.name=?5 AND t.timeAcquired=?6 AND ?7 < CURRENT_TIMESTAMP")
    , @NamedQuery(name = "TaskLock.updateTimeLeasedByName&Client", query = "UPDATE TaskLock t SET t.timeLeased=?1 WHERE t.name=?2 AND t.clientId=?3")})
public class TaskLock implements Serializable {

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

    @Column(name = "islocked")
    private Boolean isLocked;

    @Column(name = "timeacquired")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeAcquired;

    @Column(name = "timeleased")
    private Integer timeLeased;

    @Column(name = "lastRun")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastRun;

    @Size(max = 255)
    @Column(name = "client", length = 255)
    private String clientId;

    public TaskLock() {
        //default constructor
    }

    public TaskLock(String id) {
        this.id = id;
    }

    public TaskLock(String id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof TaskLock)) {
            return false;
        }
        TaskLock other = (TaskLock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.TaskLock[ id=" + id + " ]";
    }

}
