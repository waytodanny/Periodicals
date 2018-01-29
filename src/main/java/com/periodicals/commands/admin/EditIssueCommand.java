package com.periodicals.commands.admin;

import com.periodicals.commands.util.Command;
import com.periodicals.commands.util.CommandResult;
import com.periodicals.commands.util.CommandUtils;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.services.entities.PeriodicalIssueService;
import com.periodicals.services.entities.PeriodicalService;
import com.periodicals.utils.resourceHolders.PagesHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static com.periodicals.commands.util.RedirectType.FORWARD;
import static com.periodicals.commands.util.RedirectType.REDIRECT;

/**
 * @author Daniel Volnitsky
 * <p>
 * Admin commands that is responsible for providing periodicals issues service with
 * information about updated periodical's issue
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
                        return new CommandResult(REDIRECT, referer);
                    } catch (Exception e) {
                        try {
                            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                        } catch (IOException e1) {
                            /*TODO log*/
                        }
                        return null;
                    }

                } else {
                    this.initializePageAttributes(request);
                }
            }
        } else {
            this.initializePageAttributes(request);
        }
        return new CommandResult(FORWARD, PagesHolder.ADMIN_PERIODICAL_ISSUE_EDIT_PAGE);
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
