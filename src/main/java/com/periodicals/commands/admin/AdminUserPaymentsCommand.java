package com.periodicals.commands.admin;

import com.periodicals.commands.util.CommandUtils;
import com.periodicals.commands.util.PagedCommand;
import com.periodicals.commands.util.PaginationInfoHolder;
import com.periodicals.entities.Payment;
import com.periodicals.entities.User;
import com.periodicals.services.entities.PaymentService;
import com.periodicals.services.entities.UserService;
import com.periodicals.services.interfaces.PageableCollectionService;
import com.periodicals.utils.uuid.UUIDHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.PagesHolder.ADMIN_USER_PAYMENTS_PAGE;
import static com.periodicals.utils.resourceHolders.PagesHolder.ERROR_PAGE;

/**
 * @author Daniel Volnitsky
 *
 * Admin commands that is responsible for displaying user payments limited lists
 *
 * @see com.periodicals.entities.PeriodicalIssue
 */
public class AdminUserPaymentsCommand extends PagedCommand<Payment> {
    public static final int RECORDS_PER_PAGE = 3;

    private static final PaymentService paymentService = PaymentService.getInstance();
    private static final UserService userService = UserService.getInstance();

    @Override
    public PaginationInfoHolder<Payment> getPaginationInfoHolderInstance(HttpServletRequest request) {
        if (CommandUtils.paramClarifiedInQuery(request, "id")) {
            String userIdParam = request.getParameter("id");
            if (UUIDHelper.isUUID(userIdParam)) {
                UUID userId = UUID.fromString(request.getParameter("id"));
                this.initializePageAttributes(request, userId);
                return getUserPaymentsPaginationInfoHolder(request, userId);
            }
        }
        return null;
    }

    @Override
    protected PageableCollectionService<Payment> getPageableCollectionService() {
        return paymentService;
    }

    @Override
    protected int getRecordsPerPage() {
        return RECORDS_PER_PAGE;
    }

    @Override
    protected String getPageHrefTemplate() {
        return "user_info";
    }

    @Override
    protected String getRedirectPageTemplate() {
        return ADMIN_USER_PAYMENTS_PAGE;
    }

    @Override
    protected String getErrorRedirectPageTemplate() {
        return ERROR_PAGE;
    }

    private PaginationInfoHolder<Payment> getUserPaymentsPaginationInfoHolder(HttpServletRequest request, UUID userId) {
        PaginationInfoHolder<Payment> holder = new PaginationInfoHolder<>();

        int currentPage = PaginationInfoHolder.getPageFromRequest(request);
        holder.setCurrentPage(currentPage);

        int recordsCount = paymentService.getPaymentsByUserCount(userId);
        holder.setRecordsCount(recordsCount);
        holder.setRecordsPerPage(this.getRecordsPerPage());

        List<Payment> displayedObjects = paymentService.getPaymentsByUserListBounded
                (holder.getSkippedRecordsCount(), holder.getRecordsPerPage(), userId);
        holder.setDisplayedObjects(displayedObjects);

        holder.setPageHrefTemplate(this.getPageHrefTemplate() + "?id=" + userId + "&");

        return holder;
    }

    private void initializePageAttributes(HttpServletRequest request, UUID userId) {
        User user = userService.getEntityByPrimaryKey(userId);
        request.setAttribute("user", user);
    }
}
