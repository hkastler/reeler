package com.hkstlr.reeler.weblogger.plugins.entities;

import com.hkstlr.reeler.app.control.WebloggerException;
import com.hkstlr.reeler.weblogger.entities.Weblog;

public abstract class AbstractPlugin<T> implements PluginInterface<T> {
    
    
	
    private Class<T> pluginClass;

    public AbstractPlugin(Class<T> pluginClass) {
        this.pluginClass = pluginClass;
    }
    
     public AbstractPlugin() {
        
    }
    


}
