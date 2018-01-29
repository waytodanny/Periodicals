package com.periodicals.utils.propertyManagers;

import java.util.ResourceBundle;

public class JdbcQueriesPropertyManager {
    private static final String PROP_FILE_NAME = "queries";
    private static ResourceBundle resourceBundle;

    private JdbcQueriesPropertyManager(){

    }

    public static String getProperty(String key) {
        if (resourceBundle == null)
            resourceBundle = ResourceBundle.getBundle(PROP_FILE_NAME);
        return resourceBundle.getString(key);
    }
}
