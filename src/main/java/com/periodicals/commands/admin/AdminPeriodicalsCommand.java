package com.periodicals.commands.admin;

import com.periodicals.commands.util.PagedCommand;
import com.periodicals.entities.Periodical;
import com.periodicals.services.entities.PeriodicalService;
import com.periodicals.services.interfaces.PageableCollectionService;
import com.periodicals.utils.resourceHolders.PagesHolder;

import static com.periodicals.utils.resourceHolders.PagesHolder.ADMIN_PERIODICAL_EDIT_PAGE;
import static com.periodicals.utils.resourceHolders.PagesHolder.ERROR_PAGE;

/**
 * @author Daniel Volnitsky
 *
 * Admin commands that is responsible for displaying periodicals limited lists
 *
 * @see com.periodicals.entities.PeriodicalIssue
 */
public class AdminPeriodicalsCommand extends PagedCommand<Periodical> {
    private static final int RECORDS_PER_PAGE = 5;

    @Override
    protected PageableCollectionService<Periodical> getPageableCollectionService() {
        return PeriodicalService.getInstance();
    }

    @Override
    protected int getRecordsPerPage() {
        return RECORDS_PER_PAGE;
    }

    @Override
    protected String getPageHrefTemplate() {
        return "edit_catalog";
    }

    @Override
    protected String getRedirectPageTemplate() {
        return PagesHolder.ADMIN_CATALOG_EDIT_PAGE;
    }

    @Override
    protected String getErrorRedirectPageTemplate() {
        return ERROR_PAGE;
    }
}
