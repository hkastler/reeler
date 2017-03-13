package com.hkstlr.reeler.app.control;


public enum AuthMethod {
    ROLLERDB("db"),
    LDAP("ldap"),
    OPENID("openid"),
    DB_OPENID("db-openid"),
    CMA("cma");

    private final String propertyName;

    AuthMethod(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public static AuthMethod getAuthMethod(String propertyName) {
        for (AuthMethod test : AuthMethod.values()) {
            if (test.getPropertyName().equals(propertyName)) {
                return test;
            }
        }
        throw new IllegalArgumentException("Unknown authentication.method property value: "
                + propertyName + " defined in Roller properties file.");
    }

}
