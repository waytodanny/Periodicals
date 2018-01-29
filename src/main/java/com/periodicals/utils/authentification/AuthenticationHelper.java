package com.periodicals.utils.authentification;

import com.periodicals.entities.User;

import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.periodicals.services.lookups.RoleService.ADMIN_ROLE_ID;
import static com.periodicals.utils.resourceHolders.AttributesHolder.ATTR_USER;

/**
 * Class that contains methods that help to get some authentication info from session
 */
public class AuthenticationHelper {
    public static User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(ATTR_USER);
    }

    public static boolean isUserLoggedIn(HttpSession session) {
        User user = getUserFromSession(session);
        return Objects.nonNull(user);
    }

    public static boolean isSessionUserAdmin(HttpSession session) {
        User user = getUserFromSession(session);
        return isAdmin(user);
    }

    private static boolean isAdmin(User user) {
        return Objects.nonNull(user) &&
                Objects.nonNull(user.getRole()) &&
                user.getRole().getId().equals(ADMIN_ROLE_ID);
    }
}
