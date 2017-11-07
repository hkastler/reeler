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

import com.hkstlr.reeler.app.entities.AbstractEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "roller_hitcounts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WeblogHitCount.findById", query = "SELECT r FROM WeblogHitCount r WHERE r.id = :id")
    , @NamedQuery(name = "WeblogHitCount.getByWeblog", query = "SELECT h FROM WeblogHitCount h WHERE h.weblog = ?1")
     
    , @NamedQuery(name = "WeblogHitCount.updateDailyHitCountZero", query = "UPDATE WeblogHitCount h SET h.dailyHits = 0")})
public class WeblogHitCount extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    

    @Basic(optional = false)
    @NotNull
    @ManyToOne
    @JoinColumn(name = "websiteid", nullable = false, insertable=true, updatable=true )
    private Weblog weblog;

    @Column(name = "dailyhits")
    private Integer dailyHits;

    public WeblogHitCount() {
        super();
    }
    
    public WeblogHitCount(Weblog weblog) {
        super();
        this.weblog = weblog;
    }
    
    public Weblog getWeblog() {
        return weblog;
    }

    public void setWeblog(Weblog weblog) {
        this.weblog = weblog;
    }

    public Integer getDailyHits() {
        return dailyHits;
    }

    public void setDailyHits(Integer dailyHits) {
        this.dailyHits = dailyHits;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof WeblogHitCount)) {
            return false;
        }
        WeblogHitCount other = (WeblogHitCount) object;
        if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.WeblogHitCount[ id=" + id + " ]";
    }

}
