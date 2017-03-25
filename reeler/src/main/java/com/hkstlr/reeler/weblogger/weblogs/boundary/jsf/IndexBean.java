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
import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author henry.kastler
 */
@ManagedBean
@RequestScoped
public class IndexBean {
    
    @Inject
    Weblogger weblogger;
    
    //@Inject
    //WeblogManager weblogManager;
    
    @Inject
    private Logger log;
    
    private List<Weblog> weblogs;

    public IndexBean() {
    }
    
    @PostConstruct
    private void init(){
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
    
    public void setWeblogs() throws WebloggerException{
       log.fine("setting weblogs");
       List<Weblog> blogs = weblogger.getWeblogManager().getWeblogs(Boolean.TRUE, Boolean.TRUE, null, null, 0, 0);
        //Weblog blog = weblogManager.getWeblogs(Boolean.TRUE, Boolean.TRUE, null, null, 0, 0);
        //blog.setHandle("test-handle");
        //blog.setName("Test Name");
        //blogs.add(blog);
        blogs.forEach((blog) -> {
            blog.getWeblogEntries();
            blog.getWeblogEntries().size();
        });
       
       this.weblogs = blogs;
    }
    
    
    
    
    
}
