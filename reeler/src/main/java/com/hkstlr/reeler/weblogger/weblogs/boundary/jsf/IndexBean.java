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
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.boundary.Weblogger;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author henry.kastler
 */
@ManagedBean
@RequestScoped
public class IndexBean {
    
    @EJB
    Weblogger weblogger;
        
    private List<Weblog> weblogs;
    
    private List<WeblogEntry> pinnedToMain;

    public IndexBean() {
        //constructor
    }
    
    @PostConstruct
    protected void init(){
        try {
            setWeblogs();
        } catch (WebloggerException ex) {
            Logger.getLogger(IndexBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Weblog> getWeblogs() {
        return weblogs;
    }

    public void setWeblogs(List<Weblog> weblogs) {
        this.weblogs = weblogs;
    }

    public List<WeblogEntry> getPinnedToMain() {
        return pinnedToMain;
    }

    public void setPinnedToMain(List<WeblogEntry> pinnedToMain) {
        this.pinnedToMain = pinnedToMain;
    }
    
    public void setWeblogs() throws WebloggerException{
       
       this.pinnedToMain = weblogger.getWeblogEntryManager().findByPinnedToMain();
       this.weblogs = weblogger.getWeblogManager().getWeblogs(Boolean.TRUE, Boolean.TRUE, null, null, 0, 0);
       
    }    
    
}
