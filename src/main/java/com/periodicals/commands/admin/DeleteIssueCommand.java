package com.periodicals.commands.admin;

import com.periodicals.commands.util.Command;
import com.periodicals.commands.util.CommandResult;
import com.periodicals.commands.util.CommandUtils;
import com.periodicals.services.entities.PeriodicalIssueService;
import com.periodicals.utils.uuid.UUIDHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.periodicals.commands.util.RedirectType.FORWARD;
import static com.periodicals.commands.util.RedirectType.REDIRECT;
import static com.periodicals.utils.resourceHolders.PagesHolder.ERROR_PAGE;

/**
 * @author Daniel Volnitsky
 * <p>
 * Admin commands that is responsible for providing periodicalIssue service with
 * information about deleted periodical issue
 * @see com.periodicals.entities.PeriodicalIssue
 * @see PeriodicalIssueService
 */
public class DeleteIssueCommand implements Command {
    private static final PeriodicalIssueService periodicalIssueService = PeriodicalIssueService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String referer = CommandUtils.getRefererWithoutServletPath(request);
        if (CommandUtils.paramClarifiedInQuery(request, "id")) {
            String issueIdParam = request.getParameter("id");
            if (UUIDHelper.isUUID(issueIdParam)) {
                try {
                    periodicalIssueService.deleteEntity(
                            UUID.fromString(issueIdParam)
                    );
                    request.setAttribute("resultMessage", "Successfully deleted issue");
                } catch (Exception e) {
                    request.setAttribute("resultMessage", "Failed to delete issue: " + e.getMessage());
                }
            }
            return new CommandResult(REDIRECT, referer);
        }
        return new CommandResult(FORWARD, ERROR_PAGE);
    }
}
