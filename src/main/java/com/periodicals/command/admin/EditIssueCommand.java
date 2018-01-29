package com.periodicals.command.admin;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.services.entities.PeriodicalIssueService;
import com.periodicals.services.entities.PeriodicalService;
import com.periodicals.utils.resourceHolders.PagesHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.command.util.RedirectType.REDIRECT;

/**
 * @author Daniel Volnitsky
 *
 * Admin command that is responsible for providing periodicals issues service with
 * information about updated periodical's issue
 *
 * @see com.periodicals.entities.PeriodicalIssue
 * @see PeriodicalIssueService
 */
public class EditIssueCommand implements Command {
    private static PeriodicalIssueService periodicalIssueService = PeriodicalIssueService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        if (CommandUtils.isPostMethod(request)) {
            String referer = CommandUtils.getRefererWithoutServletPath(request);

            if (CommandUtils.paramClarifiedInQuery(request, "id")) {
                String id = request.getParameter("id");
                String name = request.getParameter("name");
                String issueNo = request.getParameter("issue_no");

                Object[] requiredFields = {
                        id,
                        name,
                        issueNo
                };

                if (CommandUtils.requiredFieldsNotEmpty(requiredFields)) {
                    try {
                        periodicalIssueService.updateEntity(
                                UUID.fromString(id),
                                name,
                                Integer.parseUnsignedInt(issueNo)
                        );
                        request.setAttribute("resultMessage", "Successfully updated issue");
                    } catch (Exception e) {
                        request.setAttribute("resultMessage", "Failed to update issue: " + e.getMessage());
                    }
                    return new CommandResult(REDIRECT, referer);
                } else {
                    this.initializePageAttributes(request);
                }
            }
        } else {
            this.initializePageAttributes(request);
        }
        return new CommandResult(FORWARD,  PagesHolder.ADMIN_PERIODICAL_ISSUE_EDIT_PAGE);
    }

    /**
     * provides request with needed for view layer attributes
     */
    private void initializePageAttributes(HttpServletRequest request) {
        if (CommandUtils.paramClarifiedInQuery(request, "id")) {
            UUID issueId = UUID.fromString(request.getParameter("id"));
            PeriodicalIssue issue = periodicalIssueService.getEntityByPrimaryKey(issueId);
            request.setAttribute("issue", issue);
        }
    }
}
