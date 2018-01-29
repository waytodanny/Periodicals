package com.periodicals.commands.common;

import com.periodicals.commands.util.CommandUtils;
import com.periodicals.commands.util.PagedCommand;
import com.periodicals.commands.util.PaginationInfoHolder;
import com.periodicals.entities.Periodical;
import com.periodicals.services.entities.PeriodicalService;
import com.periodicals.services.interfaces.PageableCollectionService;
import com.periodicals.utils.uuid.UUIDHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.AttributesHolder.GENRE;
import static com.periodicals.utils.resourceHolders.PagesHolder.CATALOG_PAGE;
import static com.periodicals.utils.resourceHolders.PagesHolder.ERROR_PAGE;

// TODO НЕ ВЫВОДИТЬ КНОПКУ ПОКУПКИ ДЛЯ ИЗДАНИЙ, НА КОТОРЫЕ ПОЛЬЗОВАТЕЛЬ УЖЕ ПОДПИСАН

/**
 * @author Daniel Volnitsky
 * <p>
 * Command for authenticated users that is responsible for providing request attributes
 * for view to display paginable periodical collection
 * @see PagedCommand
 */
public class DisplayPeriodicalsCommand extends PagedCommand<Periodical> {
    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();

    @Override
    public PaginationInfoHolder<Periodical> getPaginationInfoHolderInstance(HttpServletRequest request) {
        if (CommandUtils.paramClarifiedInQuery(request, GENRE)) {
            String genreId = request.getParameter(GENRE);
            if (UUIDHelper.isUUID(genreId)) {
                return getPeriodicalsByGenrePaginationInfoHolder(request, UUID.fromString(genreId));
            }
        }
        return super.getPaginationInfoHolderInstance(request);
    }

    @Override
    protected PageableCollectionService<Periodical> getPageableCollectionService() {
        return periodicalService;
    }

    @Override
    protected String getPageHrefTemplate() {
        return "catalog";
    }

    @Override
    protected String getRedirectPageTemplate() {
        return CATALOG_PAGE;
    }

    @Override
    protected String getErrorRedirectPageTemplate() {
        return ERROR_PAGE;
    }

    /**
     * @return PaginationInfoHolder object filled by sublist of genre filtered periodicals
     */
    private PaginationInfoHolder<Periodical> getPeriodicalsByGenrePaginationInfoHolder(HttpServletRequest request, UUID genreId) {
        PaginationInfoHolder<Periodical> holder = new PaginationInfoHolder<>();

        int currentPage = PaginationInfoHolder.getPageFromRequest(request);
        holder.setCurrentPage(currentPage);

        int recordsCount = periodicalService.getPeriodicalsByGenreCount(genreId);
        holder.setRecordsCount(recordsCount);
        holder.setRecordsPerPage(this.getRecordsPerPage());

        List<Periodical> displayedObjects = periodicalService.getPeriodicalsByGenreListBounded(
                holder.getSkippedRecordsCount(), holder.getRecordsPerPage(), genreId);
        holder.setDisplayedObjects(displayedObjects);

        holder.setPageHrefTemplate(this.getPageHrefTemplate() + "?genre=" + genreId + "&");

        return holder;
    }
}