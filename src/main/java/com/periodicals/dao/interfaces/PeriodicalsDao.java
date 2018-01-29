package com.periodicals.dao.interfaces;

import com.periodicals.entities.Genre;
import com.periodicals.entities.Payment;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;

import java.util.List;
import java.util.UUID;

public interface PeriodicalsDao extends GenericDao<Periodical, UUID> {
    List<Periodical> getPeriodicalsByGenreListBounded(int skip, int limit, Genre genre) throws DaoException;

    int getPeriodicalsByGenreCount(Genre genre) throws DaoException;

    List<Periodical> getPeriodicalsByPaymentList(Payment payment) throws DaoException;

    void addUserSubscriptions(User user, List<Periodical> periodicals) throws DaoException;

    List<Periodical> getPeriodicalsByUserList(User user) throws DaoException;

    int getPeriodicalsByUserCount(User user) throws DaoException;

    List<Periodical> getPeriodicalsByUserListBounded(int skip, int limit, User user) throws DaoException;

    boolean getIsUserSubscribedOnPeriodical(User user, Periodical periodical) throws DaoException;
}
