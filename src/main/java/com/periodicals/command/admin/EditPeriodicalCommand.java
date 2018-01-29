package com.periodicals.command.admin;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.entities.Periodical;
import com.periodicals.services.entities.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.UUID;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.utils.resourceHolders.PagesHolder.ADMIN_PERIODICAL_EDIT_PAGE;

/**
 * @author Daniel Volnitsky
 *
 * Admin command that is responsible for providing periodicals service with
 * information about updated periodical
 *
 * @see com.periodicals.entities.Periodical
 * @see PeriodicalService
 */
public class EditPeriodicalCommand implements Command {
    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        if (CommandUtils.isPostMethod(request)) {
            String referer = CommandUtils.getRefererWithoutServletPath(request);

            if (CommandUtils.paramClarifiedInQuery(request, "id")) {
                String id = request.getParameter("id");
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                String subscriptionCost = request.getParameter("subscription_cost");
                String isLimited = request.getParameter("is_limited");
                String issuesPerYear = request.getParameter("issues_per_year");
                String genreId = request.getParameter("genre_id");
                String publisherId = request.getParameter("publisher_id");

                String[] requiredFields = {
                        name,
                        description,
                        subscriptionCost,
                        isLimited,
                        issuesPerYear,
                        genreId,
                        publisherId
                };

                if (CommandUtils.requiredFieldsNotEmpty(requiredFields)) {
                    try {
                        periodicalService.updateEntity(
                                UUID.fromString(id),
                                name,
                                description,
                                new BigDecimal(subscriptionCost),
                                Boolean.valueOf(isLimited),
                                Short.parseShort(issuesPerYear),
                                UUID.fromString(genreId),
                                UUID.fromString(publisherId)
                        );
                        request.setAttribute("resultMessage", "Successfully updated periodical");
                    } catch (Exception e) {
                        request.setAttribute("resultMessage", "Failed to update periodical: " + e.getMessage());
                    }
                    return new CommandResult(REDIRECT, referer);
                }
            }
        } else{
            this.initializePageAttributes(request);
        }
        return new CommandResult(FORWARD, ADMIN_PERIODICAL_EDIT_PAGE);
    }

    /**
     * provides request with needed for view layer attributes
     */
    private void initializePageAttributes(HttpServletRequest request) {
        if (CommandUtils.paramClarifiedInQuery(request, "id")) {
            UUID periodicalId = UUID.fromString(request.getParameter("id"));
            Periodical periodical = periodicalService.getEntityByPrimaryKey(periodicalId);
            request.setAttribute("periodical", periodical);
        }
    }
}
