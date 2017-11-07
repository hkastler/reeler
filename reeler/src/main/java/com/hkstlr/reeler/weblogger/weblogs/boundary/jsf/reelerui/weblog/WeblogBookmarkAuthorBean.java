/*
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
package com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.reelerui.weblog;

import com.hkstlr.reeler.weblogger.weblogs.control.jsf.FacesMessageManager;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogBookmark;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogBookmarkFolder;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author henry.kastler
 */
@ManagedBean
@RequestScoped
public class WeblogBookmarkAuthorBean extends AuthorBean {
    
    @Inject
    private Logger log;
    
    private WeblogBookmark weblogBookmark;
    
    @ManagedProperty(value = "#{param.folderid}")
    private String folderid;

    public WeblogBookmarkAuthorBean() {
        //default constructor
    }

    public WeblogBookmarkAuthorBean(Class entityClass) {
        super(entityClass);
    }
    
    @PostConstruct
    protected void init(){
        
        setActionLabel();
        if(this.id != null && !this.id.isEmpty()){
            this.weblogBookmark = weblogger.getWeblogBookmarkManager().findById(id);
        }else{
            log.info("initing new WeblogCatgory for " + reelerUiBean.getCurrentWeblog().getName());
            
            this.weblogBookmark = new WeblogBookmark();
            WeblogBookmarkFolder wbf = weblogger.getWeblogBookmarkManager()
                    .getFolderById(folderid);
            this.weblogBookmark.setFolder(wbf);
        }
        
    }

    public WeblogBookmark getWeblogBookmark() {
        return weblogBookmark;
    }

    public void setWeblogBookmark(WeblogBookmark weblogBookmark) {
        this.weblogBookmark = weblogBookmark;
    }

    public String getFolderid() {
        return folderid;
    }

    public void setFolderid(String folderid) {
        this.folderid = folderid;
    }
    
    
    public void save() {
        weblogger.getWeblogBookmarkManager().edit(weblogBookmark);
        FacesMessageManager.addSuccessMessage("weblogBookmarkForm", "Bookmark saved");
    }
    
}
