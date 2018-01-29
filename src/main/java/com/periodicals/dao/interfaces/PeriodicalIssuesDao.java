package com.periodicals.dao.interfaces;

import com.periodicals.entities.Periodical;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.exceptions.DaoException;

import java.util.List;
import java.util.UUID;

public interface PeriodicalIssuesDao extends GenericDao<PeriodicalIssue, UUID> {
    List<PeriodicalIssue> getIssuesByPeriodicalListBounded(int skip, int limit, Periodical periodical) throws DaoException;

    int getIssuesByPeriodicalCount(Periodical periodical) throws DaoException;
}
