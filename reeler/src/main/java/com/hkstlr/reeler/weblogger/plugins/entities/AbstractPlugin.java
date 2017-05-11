package com.hkstlr.reeler.weblogger.plugins.entities;

public abstract class AbstractPlugin<T> implements PluginInterface<T> {

    private Class<T> pluginClass;

    public AbstractPlugin(Class<T> pluginClass) {
        this.pluginClass = pluginClass;
    }

    public AbstractPlugin() {
        //constructor
    }

}
