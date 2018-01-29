package com.periodicals.command.admin;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.entities.User;
import com.periodicals.services.entities.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.utils.resourceHolders.PagesHolder.ADMIN_EDIT_USER_PAGE;

/**
 * @author Daniel Volnitsky
 *
 * Admin command that is responsible for providing user service with
 * information about updated user
 *
 * @see com.periodicals.entities.User
 * @see UserService
 */
public class EditUserCommand implements Command {
    private static final UserService userService = UserService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        if (CommandUtils.isPostMethod(request)) {
            String referer = CommandUtils.getRefererWithoutServletPath(request);

            if (CommandUtils.paramClarifiedInQuery(request, "id")) {
                String userId = request.getParameter("id");
                String roleId = request.getParameter("role_id");

                Object[] requiredFields = {
                        roleId
                };

                if (CommandUtils.requiredFieldsNotEmpty(requiredFields)) {
                    try {
                        userService.updateEntity(
                                UUID.fromString(userId),
                                UUID.fromString(roleId)
                        );
                        request.setAttribute("resultMessage", "Successfully updated user");
                    } catch (Exception e) {
                        request.setAttribute("resultMessage", "Failed to update user");
                    }
                    return new CommandResult(REDIRECT, referer);
                }
            }
        } else{
            this.initializePageAttributes(request);
        }
        return new CommandResult(FORWARD, ADMIN_EDIT_USER_PAGE);
    }

    /**
     * provides request with needed for view layer attributes
     */
    private void initializePageAttributes(HttpServletRequest request) {
        if (CommandUtils.paramClarifiedInQuery(request, "id")) {
            UUID userId = UUID.fromString(request.getParameter("id"));
            User user = userService.getEntityByPrimaryKey(userId);
            request.setAttribute("user", user);
        }
    }
}