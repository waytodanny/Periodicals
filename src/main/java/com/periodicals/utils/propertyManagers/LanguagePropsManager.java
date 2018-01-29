package com.periodicals.utils.propertyManagers;

import java.util.Locale;
import java.util.ResourceBundle;

/*TODO rearrange logic*/
public class LanguagePropsManager {
    private static final String PARAM_RESOURCES = "resources";
    private static final String LOCALE_RU = "ru";
    private static final String LOCALE_EN = "en";

    private static ResourceBundle resourceBundle;

    private LanguagePropsManager() {
    }

    public static String getProperty(String key) {
        if (resourceBundle == null)
            resourceBundle = ResourceBundle.getBundle(PARAM_RESOURCES);
        return resourceBundle.getString(key);
    }

    public static String getProperty(String key, Locale locale) {
        String lang = locale.getLanguage();
        Locale loc;
        if (lang == null || lang.equals(LOCALE_EN))
            loc = Locale.getDefault();
        else
            loc = new Locale(LOCALE_RU);
        resourceBundle = ResourceBundle.getBundle(PARAM_RESOURCES, loc);
        return resourceBundle.getString(key);
    }
}
