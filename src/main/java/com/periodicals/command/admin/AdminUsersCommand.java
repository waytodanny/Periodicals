package com.periodicals.command.admin;

import com.periodicals.command.util.PagedCommand;
import com.periodicals.entities.User;
import com.periodicals.services.entities.UserService;
import com.periodicals.services.interfaces.PageableCollectionService;

import static com.periodicals.utils.resourceHolders.PagesHolder.ADMIN_USERS_PAGE;
import static com.periodicals.utils.resourceHolders.PagesHolder.ERROR_PAGE;

/**
 * @author Daniel Volnitsky
 * <p>
 * Admin command that is responsible for displaying users
 * @see com.periodicals.entities.PeriodicalIssue
 */
public class AdminUsersCommand extends PagedCommand<User> {
    public static final int RECORDS_PER_PAGE = 10;

    @Override
    protected PageableCollectionService<User> getPageableCollectionService() {
        return UserService.getInstance();
    }

    @Override
    protected String getPageHrefTemplate() {
        return "see_users";
    }

    @Override
    protected String getRedirectPageTemplate() {
        return ADMIN_USERS_PAGE;
    }

    @Override
    protected int getRecordsPerPage() {
        return RECORDS_PER_PAGE;
    }

    @Override
    protected String getErrorRedirectPageTemplate() {
        return ERROR_PAGE;
    }
}
