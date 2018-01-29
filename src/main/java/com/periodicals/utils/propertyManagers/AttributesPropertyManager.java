package com.periodicals.utils.propertyManagers;

import java.util.ResourceBundle;

public class AttributesPropertyManager {
    private static final String PROP_FILE_NAME = "attributes";
    private static ResourceBundle resourceBundle;

    private AttributesPropertyManager(){

    }

    public static String getProperty(String key) {
        if (resourceBundle == null)
            resourceBundle = ResourceBundle.getBundle(PROP_FILE_NAME);
        return resourceBundle.getString(key);
    }
}
