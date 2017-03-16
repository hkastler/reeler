package com.hkstlr.reeler.weblogger.boundary;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

//import com.hkstlr.reeler.weblogger.boundary.manager.FileContentManager;
//import com.hkstlr.reeler.weblogger.boundary.manager.MediaFileManager;
//import com.hkstlr.reeler.weblogger.boundary.manager.OAuthManager;
//import com.hkstlr.reeler.weblogger.boundary.manager.PropertiesManager;
import com.hkstlr.reeler.weblogger.users.boundary.manager.UserManager;
//import com.hkstlr.reeler.weblogger.boundary.manager.WeblogBookmarkManager;
import com.hkstlr.reeler.weblogger.boundary.manager.WeblogEntryCommentManager;
import com.hkstlr.reeler.weblogger.boundary.manager.WeblogEntryManager;
import com.hkstlr.reeler.weblogger.boundary.manager.WeblogManager;
import com.hkstlr.reeler.weblogger.boundary.manager.WeblogPermissionManager;
import com.hkstlr.reeler.weblogger.control.URLStrategy;
//import com.hkstlr.reeler.weblogger.pings.boundary.manager.AutoPingManager;
//import com.hkstlr.reeler.weblogger.pings.boundary.manager.PingQueueEntryManager;
//import com.hkstlr.reeler.weblogger.pings.boundary.manager.PingTargetManager;
import com.hkstlr.reeler.weblogger.plugins.boundary.PluginManager;
//import com.hkstlr.reeler.weblogger.themes.boundary.ThemeManager;
import javax.ejb.Stateless;

@Named
@Stateless
public class Weblogger {

    @Inject
    private UserManager userManager;

    /*  @Inject
    private WeblogBookmarkManager bookmarkManager*/;

    @Inject
    private WeblogManager weblogManager;

    @Inject
    private WeblogEntryManager weblogEntryManager;

    @Inject
    WeblogEntryCommentManager weblogEntryCommentManager;
    
    @Inject
    WeblogPermissionManager weblogPermissionManager;

    //@Inject
    //private AutoPingManager autoPingManager;

    //@Inject
    //private OAuthManager oAuthManager;

    //@Inject
    //private PingQueueEntryManager pingQueueEntryManager;

    //@Inject
    //private PingTargetManager pingTargetManager;

    //@Inject
    //private PropertiesManager propertiesManager;

    //@Inject
    //private MediaFileManager mediaFileManager;

    //@EJB
    //private FileContentManager fileContentManager;
    //@Inject
    //private ThemeManager themeManager;

    @Inject
    private PluginManager pluginManager;

    private URLStrategy urlStrategy;

    private String version;

    private String revision;

    private String buildTime;

    private String buildUser;

    public Weblogger() {

    }

    /**
     * Initialize any resources necessary for this instance of Weblogger.
     */
    @PostConstruct
    void init() {
        this.version = "1.0";
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
     * Get BookmarkManager associated with this Weblogger instance.
     */
    /* public WeblogBookmarkManager getBookmarkManager() {
    return bookmarkManager;
    }*/
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
    PluginManager getPluginManager() {
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
