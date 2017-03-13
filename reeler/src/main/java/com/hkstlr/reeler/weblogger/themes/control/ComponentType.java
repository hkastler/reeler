package com.hkstlr.reeler.weblogger.themes.control;

public enum ComponentType {
    WEBLOG("Weblog"),
    PERMALINK("Permalink"),
    SEARCH("Search"),
    TAGSINDEX("Tag Index"),
    STYLESHEET("Stylesheet"),
    CUSTOM("Custom");

    private final String readableName;

    ComponentType(String readableName) {
        this.readableName = readableName;
    }

    public String getReadableName() {
        return readableName;
    }
}
