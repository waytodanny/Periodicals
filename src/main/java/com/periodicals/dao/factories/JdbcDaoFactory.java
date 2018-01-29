package com.periodicals.dao.factories;

import com.periodicals.dao.interfaces.*;
import com.periodicals.dao.interfaces.lookup.GenresDao;
import com.periodicals.dao.interfaces.lookup.RolesDao;
import com.periodicals.dao.jdbc.*;

/**
 * JDBC  factory class that extends AbstractDaoFactory
 *
 * @author Daniel Volnitsky
 * @see AbstractDaoFactory
 */
public class JdbcDaoFactory extends AbstractDaoFactory {
    private static final JdbcDaoFactory instance = new JdbcDaoFactory();

    private static final RolesDao rolesDao = new RolesJdbcDao();
    private static final UsersDao usersDao = new UsersJdbcDao();
    private static final PaymentsDao paymentsDao = new PaymentsJdbcDao();
    private static final GenresDao genresDao = new GenresJdbcDao();
    private static final PublishersDao publishersDao = new PublishersJdbcDao();
    private static final PeriodicalsDao periodicalsDao = new PeriodicalsJdbcDao();
    private static final PeriodicalIssuesDao periodicalIssuesDao = new PeriodicalIssuesJdbcDao();

    private JdbcDaoFactory() {

    }

    public static JdbcDaoFactory getInstance() {
        return instance;
    }

    @Override
    public RolesDao getRolesDao() {
        return rolesDao;
    }

    @Override
    public UsersDao getUsersDao() {
        return usersDao;
    }

    @Override
    public GenresDao getGenresDao() {
        return genresDao;
    }

    @Override
    public PeriodicalsDao getPeriodicalsDao() {
        return periodicalsDao;
    }

    @Override
    public PeriodicalIssuesDao getPeriodicalIssuesDao() {
        return periodicalIssuesDao;
    }

    @Override
    public PublishersDao getPublishersDao() {
        return publishersDao;
    }

    @Override
    public PaymentsDao getPaymentsDao() {
        return paymentsDao;
    }
}

