package com.periodicals.security;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static com.periodicals.security.SecurityConfiguration.AccessType.*;
import static com.periodicals.utils.resourceHolders.AttributesHolder.*;

/**
 * @author Daniel Volnitsky
 * <p>
 * Class responsible for keeping list of possible commands and their access type
 */
public class SecurityConfiguration {
    private static final SecurityConfiguration INSTANCE = new SecurityConfiguration();

    /*Linked to save order*/
    private Map<String, AccessType> permissions = new LinkedHashMap<>();

    private SecurityConfiguration() {
        permissions.put(CHANGE_LANGUAGE, ALL);
        permissions.put(LOGIN, ALL);
        permissions.put(LOGOUT, AUTH);
        permissions.put(REGISTER, ALL);
        permissions.put(CATALOG, ALL);
        permissions.put(PERIODICAL_ISSUES, AUTH);
        permissions.put(SUBSCRIPTIONS, AUTH);
        permissions.put(SUBSCRIBE, AUTH);
        permissions.put(REMOVE_FROM_CART, AUTH);
        permissions.put(ADD_TO_CART, AUTH);

        permissions.put(ADMIN_USERS, ADMIN);
        permissions.put(ADMIN_USER_INFO, AUTH);
        permissions.put(ADMIN_EDIT_USER, AUTH);

        permissions.put(ADMIN_CATALOG, ADMIN);
        permissions.put(ADMIN_EDIT_PERIODICAL, ADMIN);
        permissions.put(ADMIN_DELETE_PERIODICAL, ADMIN);
        permissions.put(ADMIN_ADD_PERIODICAL, ADMIN);

        permissions.put(ADMIN_DISPLAY_PERIODICAL_ISSUES, ADMIN);
        permissions.put(ADMIN_EDIT_ISSUE, ADMIN);
        permissions.put(ADMIN_ADD_ISSUE, ADMIN);
        permissions.put(ADMIN_DELETE_ISSUE, ADMIN);

        permissions.put(DEFAULT, ALL);
    }

    public static SecurityConfiguration getInstance() {
        return INSTANCE;
    }

    public AccessType getCommandSecurityType(String command) {
        return permissions.get(command);
    }

    public Set<String> getEndPoints() {
        return permissions.keySet();
    }

    public enum AccessType {
        ALL, AUTH, ADMIN
    }
}
