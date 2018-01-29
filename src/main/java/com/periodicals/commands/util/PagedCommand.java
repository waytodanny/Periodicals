package com.periodicals.commands.util;

import com.periodicals.services.interfaces.PageableCollectionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

import static com.periodicals.commands.util.RedirectType.FORWARD;
import static com.periodicals.utils.resourceHolders.PagesHolder.ERROR_PAGE;

/**
 * @author Daniel Volnitsky
 * <p>
 * Basic class for those commands which need to specify pagination information
 * @see Command
 */
public abstract class PagedCommand<T> implements Command {
    private static final int DEFAULT_RECORDS_PER_PAGE = 4;

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        PaginationInfoHolder<T> paginationInfoHolder = this.getPaginationInfoHolderInstance(request);

        if (Objects.nonNull(paginationInfoHolder)) {
            paginationInfoHolder.setAttributesToRequest(request);
        } else {
            return new CommandResult(FORWARD, this.getErrorRedirectPageTemplate());
        }

        return new CommandResult(FORWARD, this.getRedirectPageTemplate());
    }

    protected PaginationInfoHolder<T> getPaginationInfoHolderInstance(HttpServletRequest request) {
        return getBaseEntityPaginationHolder(request);
    }

    /**
     * @return Basic Generic PaginationInfoHolder for objects
     * that child-class manipulates with
     */
    private PaginationInfoHolder<T> getBaseEntityPaginationHolder(HttpServletRequest request) {
        PageableCollectionService<T> service = this.getPageableCollectionService();
        PaginationInfoHolder<T> holder = new PaginationInfoHolder<>();

        int currentPage = PaginationInfoHolder.getPageFromRequest(request);
        holder.setCurrentPage(currentPage);

        int recordsCount = service.getEntitiesCount();
        holder.setRecordsCount(recordsCount);
        holder.setRecordsPerPage(this.getRecordsPerPage());

        List<T> displayedObjects = service.getEntitiesListBounded
                (holder.getSkippedRecordsCount(), holder.getRecordsPerPage());
        holder.setDisplayedObjects(displayedObjects);

        holder.setPageHrefTemplate(this.getPageHrefTemplate() + "?");

        return holder;
    }

    /**
     * @return service that is responsible for providing pagination information
     */
    protected abstract PageableCollectionService<T> getPageableCollectionService();

    protected int getRecordsPerPage() {
        return DEFAULT_RECORDS_PER_PAGE;
    }

    /**
     * @return path to page where pagination objects will be rendered
     */
    protected abstract String getPageHrefTemplate();

    protected abstract String getRedirectPageTemplate();

    protected abstract String getErrorRedirectPageTemplate();
}
