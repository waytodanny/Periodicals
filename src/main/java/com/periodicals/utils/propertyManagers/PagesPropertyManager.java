package com.periodicals.utils.propertyManagers;

import java.util.ResourceBundle;

public class PagesPropertyManager {
    private static final String PROP_FILE_NAME = "pages";
    private static ResourceBundle resourceBundle;

    private PagesPropertyManager(){

    }

    public static String getProperty(String key) {
        if (resourceBundle == null)
            resourceBundle = ResourceBundle.getBundle(PROP_FILE_NAME);
        return resourceBundle.getString(key);
    }
}
