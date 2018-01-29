package com.periodicals.commands.auth;

import com.periodicals.utils.authentification.AuthenticationHelper;
import com.periodicals.commands.util.PagedCommand;
import com.periodicals.commands.util.PaginationInfoHolder;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.User;
import com.periodicals.services.SubscriptionService;
import com.periodicals.services.interfaces.PageableCollectionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.PagesHolder.LOGIN_PAGE;
import static com.periodicals.utils.resourceHolders.PagesHolder.USER_SUBSCRIPTIONS_PAGE;

/**
 * @author Daniel Volnitsky
 * <p>
 * Command for authenticated users that is responsible for providing request attributes
 * for view to display paginable user subscriptions (periodicals) collection
 * @see PagedCommand
 */
public class DisplayUserSubscriptionsCommand extends PagedCommand<Periodical> {
    private SubscriptionService subscriptionService = SubscriptionService.getInstance();

    public PaginationInfoHolder<Periodical> getPaginationInfoHolderInstance(HttpServletRequest request) {
        User user = AuthenticationHelper.getUserFromSession(request.getSession());
        if (Objects.nonNull(user)) {
            return getPeriodicalsByUserPaginationInfoHolder(request, user.getId());
        }
        return null;
    }

    @Override
    protected PageableCollectionService<Periodical> getPageableCollectionService() {
        return subscriptionService;
    }

    @Override
    protected String getPageHrefTemplate() {
        return "subscriptions";
    }

    @Override
    protected String getRedirectPageTemplate() {
        return USER_SUBSCRIPTIONS_PAGE;
    }

    @Override
    protected String getErrorRedirectPageTemplate() {
        return LOGIN_PAGE;
    }

    /**
     * @return PaginationInfoHolder object filled by sublist of user filtered periodicals
     */
    private PaginationInfoHolder<Periodical> getPeriodicalsByUserPaginationInfoHolder(HttpServletRequest request, UUID userId) {
        PaginationInfoHolder<Periodical> holder = new PaginationInfoHolder<>();

        int currentPage = PaginationInfoHolder.getPageFromRequest(request);
        holder.setCurrentPage(currentPage);

        int recordsCount = subscriptionService.getPeriodicalsByUserCount(userId);
        holder.setRecordsCount(recordsCount);
        holder.setRecordsPerPage(this.getRecordsPerPage());

        List<Periodical> displayedObjects = subscriptionService.getPeriodicalsByUserListBounded
                (holder.getSkippedRecordsCount(), holder.getRecordsPerPage(), userId);
        holder.setDisplayedObjects(displayedObjects);

        holder.setPageHrefTemplate(this.getPageHrefTemplate() + "?");

        return holder;
    }
}
