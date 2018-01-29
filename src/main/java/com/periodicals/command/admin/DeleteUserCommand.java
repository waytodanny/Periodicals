package com.periodicals.command.admin;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.services.entities.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author Daniel Volnitsky
 * <p>
 * Admin command that is responsible for providing user service with
 * information about deleted user
 * @see com.periodicals.entities.User
 * @see UserService
 */
public class DeleteUserCommand implements Command {
    private static final UserService userService = UserService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        if (CommandUtils.isPostMethod(request)) {
            if (CommandUtils.paramClarifiedInQuery(request, "id")) {
                String userIdParam = request.getParameter("id");
                try {
                    userService.deleteEntity(
                            UUID.fromString(userIdParam)
                    );
                    request.setAttribute("resultMessage", "Successfully deleted user");
                } catch (Exception e) {
                    request.setAttribute("resultMessage", "Failed to delete user: " + e.getMessage());
                }
            }
        }
        return null;
    }
}

