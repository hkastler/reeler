package com.hkstlr.reeler.weblogger.plugins.boundary;

import com.hkstlr.reeler.app.control.WebloggerException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.hkstlr.reeler.weblogger.weblogs.control.config.WebloggerConfig;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntryComment;
import com.hkstlr.reeler.weblogger.plugins.comment.control.WeblogEntryCommentPlugin;
import com.hkstlr.reeler.weblogger.plugins.entry.control.WeblogEntryPlugin;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;

@Singleton
@DependsOn("WebloggerConfig")
@ConcurrencyManagement
public class PluginManager {

    private Logger log = Logger.getLogger(PluginManager.class.getName());

    // Plugin classes keyed by plugin name
    static Map<String, Class> mPagePlugins = new ConcurrentHashMap<String, Class>();

    // Comment plugins
    private final List<WeblogEntryCommentPlugin> commentPlugins = new ArrayList<>();

    /**
     * Creates a new instance of PluginManagerImpl
     */
    public PluginManager() {
        //constructor        
    }
    
    @PostConstruct
    public void init(){
        // load weblog entry plugins
        loadPagePluginClasses();
        // load weblog entry comment plugins
        loadCommentPlugins();
        
        log.info("PluginManager instantiated");
    }

    public boolean hasPagePlugins() {
        log.fine("mPluginClasses.size(): " + mPagePlugins.size());
        return !mPagePlugins.isEmpty();
    }

    /**
     * Create and init plugins for processing entries in a specified website.
     */
    public Map<String, WeblogEntryPlugin> getWeblogEntryPlugins(Weblog website) {
        Map<String, WeblogEntryPlugin> ret = new LinkedHashMap<String, WeblogEntryPlugin>();
        for (Class pluginClass : PluginManager.mPagePlugins.values()) {
            try {
                WeblogEntryPlugin plugin = (WeblogEntryPlugin) pluginClass.newInstance();
                plugin.init(website);
                ret.put(plugin.getName(), plugin);
            } catch (WebloggerException | IllegalAccessException | InstantiationException e) {
                log.log(Level.WARNING, "Unable to init() PagePlugin: ", e);
            }
        }
        return ret;
    }

    public String applyWeblogEntryPlugins(Map pagePlugins, WeblogEntry entry, String str) {

        String ret = str;
        WeblogEntry copy = new WeblogEntry(entry);
        List<String> entryPlugins = copy.getPluginsList();

        if (entryPlugins != null) {
            for (String key : entryPlugins) {
                WeblogEntryPlugin pagePlugin = (WeblogEntryPlugin) pagePlugins.get(key);
                if (pagePlugin != null) {
                    ret = pagePlugin.render(entry, ret);
                } else {
                    log.warning("ERROR: plugin not found: " + key);
                }
            }
        }

        return Jsoup.clean(ret, Whitelist.basic());
    }

    /**
     * @inheritDoc
     */
    public List<WeblogEntryCommentPlugin> getCommentPlugins() {
        return commentPlugins;
    }

    /**
     * @inheritDoc
     */
    public WeblogEntryComment applyCommentPlugins(WeblogEntryComment comment, String text) {

        if (comment == null || text == null) {
            throw new IllegalArgumentException("comment cannot be null");
        }

        String content = text;

        if (!commentPlugins.isEmpty()) {
            for (WeblogEntryCommentPlugin plugin : commentPlugins) {
                if (comment.getPlugins() != null
                        && comment.getPlugins().contains(plugin.getId())) {
                    log.fine("Invoking comment plugin " + plugin.getId());
                    content = plugin.render(comment, content);
                    comment.setContent(content);
                }
            }
        }

        return comment;
    }

    /**
     * Initialize PagePlugins declared in roller.properties. By using the full
     * class name we also allow for the implementation of "external" Plugins
     * (maybe even packaged seperately). These classes are then later
     * instantiated by PageHelper.
     */
    private void loadPagePluginClasses() {
        

        String pluginStr = WebloggerConfig.getProperty("plugins.page");
        log.info("pluginStr:" + pluginStr);
        if (pluginStr != null) {
            String[] plugins = pluginStr.split(",");
            for (String plugin : plugins) {

                try {
                    String trimmed = plugin.trim();
                    log.info("trimmed:" + trimmed);
                    Class pluginClass = Class.forName(trimmed);
                    log.info("pluginClass:" + pluginClass.getName());
                    if (isPagePlugin(pluginClass)) {
                        WeblogEntryPlugin weblogEntryPlugin = (WeblogEntryPlugin) pluginClass.newInstance();
                        
                        mPagePlugins.put(weblogEntryPlugin.getName(), pluginClass);
                    } else {
                        log.warning(pluginClass + " is not a PagePlugin");
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException  e) {
                    log.log(Level.SEVERE, "Exception for " + plugin, e);
                }
            }
        }
    }

    /**
     * Initialize all comment plugins defined in weblogger config.
     */
    private void loadCommentPlugins() {

        log.fine("Initializing comment plugins");

        String pluginStr = WebloggerConfig.getProperty("comment.formatter.classnames");
        if (pluginStr != null) {
            String[] plugins = pluginStr.split(",");
            for (int i = 0; i < plugins.length; i++) {
                log.fine("trying " + plugins[i]);

                try {
                    Class pluginClass = Class.forName(plugins[i]);
                    WeblogEntryCommentPlugin plugin
                            = (WeblogEntryCommentPlugin) pluginClass.newInstance();

                    // make sure and maintain ordering
                    commentPlugins.add(i, plugin);

                    log.fine("Configured comment plugin: " + plugins[i]);

                } catch (ClassCastException e) {
                    log.log(Level.WARNING,"ClassCastException for " + plugins[i],e);
                } catch (ClassNotFoundException e) {
                    log.log(Level.WARNING, "ClassNotFoundException for " + plugins[i],e);
                } catch (InstantiationException e) {
                    log.log(Level.WARNING, "InstantiationException for " + plugins[i],e);
                } catch (IllegalAccessException e) {
                    log.log(Level.WARNING,"IllegalAccessException for " + plugins[i],e);
                }
            }
        }

    }

    private boolean isPagePlugin(Class pluginClass) {
        Class superclass = pluginClass.getSuperclass();
        

        log.log(Level.INFO, "clazz:{0}", superclass.getName());
        if (superclass.equals(WeblogEntryPlugin.class)) {
            return true;

        }
        return false;
    }


}
