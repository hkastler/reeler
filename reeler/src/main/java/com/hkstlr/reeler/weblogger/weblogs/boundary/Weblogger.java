package com.hkstlr.reeler.weblogger.weblogs.boundary;

import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogBookmarkManager;
import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogCategoryManager;
import javax.annotation.PostConstruct;

import javax.inject.Named;

//import com.hkstlr.reeler.weblogger.boundary.manager.FileContentManager;
//import com.hkstlr.reeler.weblogger.boundary.manager.MediaFileManager;
//import com.hkstlr.reeler.weblogger.boundary.manager.OAuthManager;
//import com.hkstlr.reeler.weblogger.boundary.manager.PropertiesManager;
import com.hkstlr.reeler.weblogger.users.boundary.manager.UserManager;
//import com.hkstlr.reeler.weblogger.boundary.manager.WeblogBookmarkManager;
import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogEntryCommentManager;
import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogEntryManager;
import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogManager;
import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogPermissionManager;
import com.hkstlr.reeler.weblogger.weblogs.control.URLStrategy;
//import com.hkstlr.reeler.weblogger.pings.boundary.manager.AutoPingManager;
//import com.hkstlr.reeler.weblogger.pings.boundary.manager.PingQueueEntryManager;
//import com.hkstlr.reeler.weblogger.pings.boundary.manager.PingTargetManager;
import com.hkstlr.reeler.weblogger.plugins.boundary.PluginManager;
import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogEntryTagManager;
import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.admin.RuntimeConfigManager;
import javax.ejb.EJB;
//import com.hkstlr.reeler.weblogger.themes.boundary.ThemeManager;
import javax.ejb.Stateless;

@Named
@Stateless
public class Weblogger {
    
    @EJB
    private RuntimeConfigManager runtimeConfigManager;

    @EJB
    private UserManager userManager;

    @EJB
    private WeblogManager weblogManager;

    @EJB
    private WeblogEntryManager weblogEntryManager;

    @EJB
    WeblogEntryCommentManager weblogEntryCommentManager;
    
    @EJB
    private WeblogEntryTagManager weblogEntryTagManager;
    
    @EJB
    WeblogPermissionManager weblogPermissionManager;
    
    @EJB
    private WeblogCategoryManager weblogCategoryManager;
    
    @EJB
    private WeblogBookmarkManager weblogBookmarkManager;
    
    

    //@EJB
    //private AutoPingManager autoPingManager;

    //@EJB
    //private OAuthManager oAuthManager;

    //@EJB
    //private PingQueueEntryManager pingQueueEntryManager;

    //@EJB
    //private PingTargetManager pingTargetManager;

    //@EJB
    //private PropertiesManager propertiesManager;

    //@EJB
    //private MediaFileManager mediaFileManager;

    //@EJB
    //private FileContentManager fileContentManager;
    

    @EJB
    private PluginManager pluginManager;

    private URLStrategy urlStrategy;

    private String version;

    private String revision;

    private String buildTime;

    private String buildUser;

    public Weblogger() {
        //constructor
    }

    /**
     * Initialize any resources necessary for this instance of Weblogger.
     */
    @PostConstruct
    void init() {
        this.version = "1.0";
    }

    public RuntimeConfigManager getRuntimeConfigManager() {
        return runtimeConfigManager;
    }
    
    

    /**
     *
     * Get UserManager associated with this Weblogger instance.
     */
    public UserManager getUserManager() {
        return userManager;
    }

    
    /**
     *
     * Get OAuthManager associated with this Weblogger instance.
     */
    /*public OAuthManager getOAuthManager() {
    return oAuthManager;
    }*/

    /**
     *
     * Get WeblogManager associated with this Weblogger instance.
     */
    public WeblogManager getWeblogManager() {
        return weblogManager;
    }

    /**
     *
     * Get WeblogManager associated with this Weblogger instance.
     */
    public WeblogEntryManager getWeblogEntryManager() {
        return weblogEntryManager;
    }

    /**
     *
     * Get WeblogManager associated with this Weblogger instance.
     */
    public WeblogEntryCommentManager getWeblogEntryCommentManager() {
        return weblogEntryCommentManager;
    }
    
    public WeblogPermissionManager getWeblogPermissionManager() {
        return weblogPermissionManager;
    }
    
    public WeblogCategoryManager getWeblogCategoryManager() {
        return weblogCategoryManager;
    }
    
    public WeblogEntryTagManager getWeblogEntryTagManager() {
        return weblogEntryTagManager;
    }
    
    public WeblogBookmarkManager getWeblogBookmarkManager() {
        return weblogBookmarkManager;
    }

    /**
     * Get the AutoPingManager associated with this Weblogger instance.
     */
    /*public AutoPingManager getAutopingManager() {
    return autoPingManager;
    }*/

    /**
     * Get the PingTargetManager associated with this Weblogger instance.
     */
    /*public PingTargetManager getPingTargetManager() {
    return pingTargetManager;
    }*/

    /**
     * Get the PingQueueManager associated with this Weblogger instance.
     */
    /*PingQueueEntryManager getPingQueueManager() {
    return pingQueueEntryManager;
    }*/

    /**
     *
     * Get PropertiesManager associated with this Weblogger instance.
     */
    /*public PropertiesManager getPropertiesManager() {
    return propertiesManager;
    }*/

    /**
     * Get ThreadManager associated with this Weblogger instance.
     */
    //ThreadManager getThreadManager();
    /**
     * Get IndexManager associated with this Weblogger instance.
     */
    //IndexManager getIndexManager();
    /**
     * Get ThemeManager associated with this Weblogger instance.
     */
    /*ThemeManager getThemeManager() {
    return themeManager;
    }*/

    /**
     * Get PluginManager associated with this Weblogger instance.
     */
    public PluginManager getPluginManager() {
        return pluginManager;
    }

    /**
     * Get MediaFileManager associated with this Weblogger instance.
     */
    /*public MediaFileManager getMediaFileManager() {
    return mediaFileManager;
    }*/
    /**
     * Get FileContentManager associated with this Weblogger instance.
     */
    /*public FileContentManager getFileContentManager() {
    return null; //fileContentManager;
    }*/

    /**
     * Get the URLStrategy used to build all urls in the system.
     */
    public URLStrategy getUrlStrategy() {
        return urlStrategy;
    }

    public void setUrlStrategy(URLStrategy urlStrategy) {
        this.urlStrategy = urlStrategy;
    }

    /**
     * Weblogger version
     */
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Weblogger source code management revision
     */
    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    /**
     * Weblogger build time
     */
    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    /**
     * Get username that built Weblogger
     */
    public String getBuildUser() {
        return buildUser;
    }

    public void setBuildUser(String buildUser) {
        this.buildUser = buildUser;
    }

    //FeedFetcher getFeedFetcher();
    //PlanetManager getPlanetManager();
    //org.apache.roller.planet.business.PlanetURLStrategy getPlanetURLStrategy();
}
