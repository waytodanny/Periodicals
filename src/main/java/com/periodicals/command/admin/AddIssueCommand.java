package com.periodicals.command.admin;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.entities.Periodical;
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
 * <p>
 * Admin command that is responsible for creating new PeriodicalIssue object
 * @see com.periodicals.entities.PeriodicalIssue
 */
public class AddIssueCommand implements Command {
    private static final PeriodicalIssueService periodicalIssueService = PeriodicalIssueService.getInstance();
    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        this.initializePageAttributes(request);
        String referer = CommandUtils.getRefererWithoutServletPath(request);

        String name = request.getParameter("name");
        String issueNo = request.getParameter("issue_no");
        String periodicalId = request.getParameter("periodical");

        Object[] requiredFields = {
                name,
                issueNo,
                periodicalId
        };

        if (CommandUtils.requiredFieldsNotEmpty(requiredFields)) {
            try {
                periodicalIssueService.createEntity(
                        name,
                        Integer.parseInt(issueNo),
                        UUID.fromString(periodicalId)
                );
                request.setAttribute("resultMessage", "Successfully added issue");
            } catch (Exception e) {
//                request.setAttribute("resultMessage", "Failed to add issue");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            return new CommandResult(REDIRECT, referer);
        }

        return new CommandResult(FORWARD, PagesHolder.ADMIN_ADD_ISSUE_PAGE);
    }

    /**
     * provides request with needed for view layer attributes
     */
    private void initializePageAttributes(HttpServletRequest request) {
        if (CommandUtils.paramClarifiedInQuery(request, "periodical")) {
            String periodicalId = request.getParameter("periodical");
            Periodical periodical = periodicalService.getEntityByPrimaryKey(
                    UUID.fromString(periodicalId)
            );
            request.setAttribute("periodical_id", periodical.getId());
            request.setAttribute("periodical_name", periodical.getName());
        }
    }

//    public static void handleRuntimeException(Exception ex, HttpServletResponse
//            response,String message) {
//        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        response.getWriter().write(message);
//        response.flushBuffer();
//    }
}
