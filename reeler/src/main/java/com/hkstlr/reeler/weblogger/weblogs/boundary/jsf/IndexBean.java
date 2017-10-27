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

import com.hkstlr.reeler.app.control.StringPool;
import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.boundary.Weblogger;
import com.hkstlr.reeler.weblogger.weblogs.boundary.jsf.pages.WeblogBean;
import com.hkstlr.reeler.weblogger.weblogs.control.config.WebloggerRuntimeConfig;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author henry.kastler
 */
@ManagedBean
@RequestScoped
public class IndexBean {

    private static final Logger LOG = Logger.getLogger(IndexBean.class.getName());

    @EJB
    Weblogger weblogger;

    @Inject
    WebloggerRuntimeConfig wrc;

    private List<Weblog> weblogs;

    private List<WeblogEntry> pinnedToMain;

    private WeblogBean weblogBean;

    public IndexBean() {
        //constructor
    }

    @PostConstruct
    protected void init() {

        try {
            needsSetup();
            setWeblogs();
        } catch (WebloggerException ex) {
            LOG.log(Level.SEVERE, null, ex);
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

    public void setWeblogs() throws WebloggerException {

        this.pinnedToMain = weblogger.getWeblogEntryManager().findByPinnedToMain();
        this.weblogs = weblogger.getWeblogManager().getWeblogs(Boolean.TRUE, Boolean.TRUE, null, null, 0, 0);
        if (hasFrontPageBlog()) {
            String frontPageHandle = wrc.getProperty("site.frontpage.weblog.handle");
            Optional<Weblog> opFrontPageBlog = this.weblogs.stream()
                    .filter(b -> frontPageHandle.equalsIgnoreCase(b.getHandle()))
                    .findFirst();
            if (opFrontPageBlog.isPresent()) {
                weblogBean = new WeblogBean(opFrontPageBlog.get());
            }
        }
    }
    
    

    @Named
    public boolean needsSetup() {

        boolean needsSetup = false;
        long userCount = weblogger.getUserManager().getUserCount();

        if (userCount == 0) {
            needsSetup = true;
        }
        LOG.finest(Boolean.toString(needsSetup));
        return needsSetup;
    }

    @Named
    public boolean hasFrontPageBlog() {
        boolean hasFrontPageBlog = false;
        String fpb = wrc.getProperty("site.frontpage.weblog.handle", StringPool.BLANK);

        if (!StringPool.BLANK.equals(fpb)) {
            hasFrontPageBlog = true;
        }

        return hasFrontPageBlog;
    }
    
    @Named
    public boolean isAggregated(){
        //Object.toString way seems to handle a String/Boolean issue
        Object webAg = wrc.getProperty("site.frontpage.weblog.aggregated");
        return Boolean.parseBoolean(webAg.toString());
    }

    public WeblogBean getWeblogBean() {
        return weblogBean;
    }


    public void indexViewAction() {
        if ( weblogBean != null) {
            
            int[] range = new int[2];
            range[0] = 0;
            range[1] = this.weblogBean.getWeblog().getEntryDisplayCount();
            this.weblogBean.setViewEntries(
                    weblogger
                            .getWeblogEntryManager()
                            .getPaginatedEntries(this.weblogBean.getWeblog(), range));
        }
        
        
    }

}
