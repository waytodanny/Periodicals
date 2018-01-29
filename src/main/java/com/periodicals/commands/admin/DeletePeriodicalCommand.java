package com.periodicals.commands.admin;

import com.periodicals.commands.util.Command;
import com.periodicals.commands.util.CommandResult;
import com.periodicals.commands.util.CommandUtils;
import com.periodicals.commands.util.RedirectType;
import com.periodicals.services.entities.PeriodicalService;
import com.periodicals.utils.resourceHolders.PagesHolder;
import com.periodicals.utils.uuid.UUIDHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author Daniel Volnitsky
 * <p>
 * Admin commands that is responsible for providing periodical service with
 * information about deleted periodical
 * @see com.periodicals.entities.Periodical
 * @see PeriodicalService
 */
public class DeletePeriodicalCommand implements Command {
    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        if (CommandUtils.paramClarifiedInQuery(request, "id")) {
            String periodicalIdParam = request.getParameter("id");
            if (UUIDHelper.isUUID(periodicalIdParam)) {
                try {
                    periodicalService.deleteEntity(
                            UUID.fromString(periodicalIdParam)
                    );
                    request.setAttribute("resultMessage", "Successfully deleted periodical");
                } catch (Exception e) {
                    request.setAttribute("resultMessage", "Failed to delete periodical: " + e.getMessage());
                }
            }
        }
        return new CommandResult(RedirectType.REDIRECT, PagesHolder.ADMIN_CATALOG_EDIT_PAGE);
    }
}
