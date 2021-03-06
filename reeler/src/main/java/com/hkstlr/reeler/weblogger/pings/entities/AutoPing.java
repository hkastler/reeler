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
 * 
 */
package com.hkstlr.reeler.weblogger.pings.entities;

import com.hkstlr.reeler.app.entities.AbstractEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;

/**
 *
 * @author henry.kastler
 */
@Entity
@Table(name = "autoping")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AutoPing.findById", query = "SELECT a FROM AutoPing a WHERE a.id = :id")
    , @NamedQuery(name = "AutoPing.getAll", query = "SELECT a FROM AutoPing a")
    , @NamedQuery(name = "AutoPing.getByPingTarget", query = "SELECT a FROM AutoPing a WHERE a.pingTarget = ?1")
    , @NamedQuery(name = "AutoPing.getByWebsite", query = "SELECT a FROM AutoPing a WHERE a.website = ?1")
    , @NamedQuery(name = "AutoPing.removeByPingTarget", query = "DELETE FROM AutoPing a WHERE a.pingTarget = ?1")
    , @NamedQuery(name = "AutoPing.removeByPingTarget&amp;Website", query = "DELETE FROM AutoPing a WHERE a.pingTarget = ?1 AND a.website = ?2")
    , @NamedQuery(name = "AutoPing.removeAll", query = "DELETE FROM AutoPing a")})
public class AutoPing extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "pingtargetid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private PingTarget pingTarget;

    @JoinColumn(name = "websiteid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Weblog website;

    public AutoPing() {
        super();
    }

    public AutoPing(PingTarget pingTarget, Weblog weblog) {
        this.pingTarget = pingTarget;
        this.website = weblog;
    }

    public PingTarget getPingTarget() {
        return pingTarget;
    }

    public void setPingTarget(PingTarget pingTarget) {
        this.pingTarget = pingTarget;
    }    

    public Weblog getWebsite() {
        return website;
    }

    public void setWebsite(Weblog website) {
        this.website = website;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof AutoPing)) {
            return false;
        }
        AutoPing other = (AutoPing) object;
        return this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "com.hkstlr.reeler.weblogger.entities.AutoPing[ id=" + id + " ]";
    }

}
