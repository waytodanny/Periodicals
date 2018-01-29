package com.periodicals.utils.customTags;

import com.periodicals.entities.Role;
import com.periodicals.entities.User;
import com.periodicals.services.entities.UserService;
import com.periodicals.services.lookups.RoleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Objects;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.AttributesHolder.ATTR_USER;


/**
 * Custom JSP tag for checking if user has admin role
 */
public class isUserAdminTag extends TagSupport {
    private static final UserService userService = UserService.getInstance();

    @Override
    public int doStartTag() {
        HttpSession session = getRequest().getSession(false);
        if (Objects.nonNull(session)) {
            User user = (User) session.getAttribute(ATTR_USER);
            if (Objects.nonNull(user) && Objects.nonNull(user.getRole())) {
                if (userHasAdminRole(user.getRole())) {
                    return EVAL_BODY_INCLUDE;
                }
            }
        }
        return SKIP_BODY;
    }

    private boolean userHasAdminRole(Role userRole) {
        boolean result = false;
        try {
            UUID userRoleId = userRole.getId();
            UUID adminRoleId = RoleService.ADMIN_ROLE_ID;
            result = Objects.equals(userRoleId, adminRoleId);
        } catch (Exception e) {
            /*TODO log*/
        }
        return result;
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) pageContext.getRequest();
    }
}
