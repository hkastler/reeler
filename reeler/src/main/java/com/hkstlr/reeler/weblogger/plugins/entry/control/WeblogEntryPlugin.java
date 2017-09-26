package com.hkstlr.reeler.weblogger.plugins.entry.control;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.weblogs.entities.Weblog;
import com.hkstlr.reeler.weblogger.weblogs.entities.WeblogEntry;
import com.hkstlr.reeler.weblogger.plugins.entities.AbstractPlugin;
import com.hkstlr.reeler.weblogger.plugins.entities.PluginInterface;

public class WeblogEntryPlugin extends AbstractPlugin<Object> implements PluginInterface<Object> {

    public WeblogEntryPlugin() {
        super();
    }

    public WeblogEntryPlugin(Class pluginClass) {
        super(pluginClass);
    }

    /**
     * Returns the display name of this Plugin.
     */
    public String getName() {
        return null;
    }

    /**
     * Briefly describes the function of the Plugin. May contain HTML.
     */
    public String getDescription() {
        return null;
    }

    public String render(WeblogEntry entry, String str) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String render(Class pluginClass, String str) {
        return null;//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void init(Object type) throws WebloggerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
