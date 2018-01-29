package com.periodicals.dao.jdbc;

import com.periodicals.dao.connection.ConnectionManager;
import com.periodicals.dao.connection.ConnectionWrapper;
import com.periodicals.dao.interfaces.PeriodicalsDao;
import com.periodicals.entities.*;
import com.periodicals.utils.exceptions.DaoException;
import com.periodicals.utils.propertyManagers.AttributesPropertyManager;
import com.periodicals.utils.resourceHolders.JdbcQueriesHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PeriodicalsJdbcDao extends AbstractJdbcDao<Periodical, UUID> implements PeriodicalsDao {

    @Override
    public Periodical getEntityByPrimaryKey(UUID key) throws DaoException {
        if (Objects.nonNull(key)) {
            return super.selectObject(Query.SELECT_BY_ID.text, key.toString());
        }
        throw new DaoException("Attempt to get periodical by nullable key");
    }

    @Override
    public void createEntity(Periodical periodical) throws DaoException {
        if (Objects.isNull(periodical)) {
            throw new DaoException("Attempt to insert nullable periodical");
        }
        super.insert(Query.INSERT.text, getInsertObjectParams(periodical));
    }

    @Override
    public void updateEntity(Periodical periodical) throws DaoException {
        if (Objects.isNull(periodical)) {
            throw new DaoException("Attempt to update nullable periodical");
        }
        super.update(Query.UPDATE.text, getObjectUpdateParams(periodical));
    }

    @Override
    public void deleteEntity(Periodical periodical) throws DaoException {
        if (Objects.isNull(periodical)) {
            throw new DaoException("Attempt to delete nullable periodical");
        }
        super.delete(Query.DELETE.text, periodical.getId().toString());
    }

    @Override
    public List<Periodical> getEntityCollection() throws DaoException {
        return super.selectObjects(Query.SELECT.text);
    }

    @Override
    public int getEntitiesCount() throws DaoException {
        return super.getEntriesCount(Query.SELECT_ENTRIES_COUNT.text);
    }

    @Override
    public List<Periodical> getEntitiesListBounded(int skip, int limit) throws DaoException {
        return super.selectObjects(Query.SELECT_LIMITED_LIST.text, skip, limit);
    }

    @Override
    public List<Periodical> getPeriodicalsByGenreListBounded(int skip, int limit, Genre genre) throws DaoException {
        if (Objects.nonNull(genre) && skip > -1 && limit > -1) {
            return super.selectObjects(
                    Query.SELECT_GENRE_PERIODICALS_LIMITED_LIST.text,
                    genre.getId().toString(),
                    skip,
                    limit
            );
        }
        throw new DaoException("Attempt to get bounded list by wrong parameters");
    }

    @Override
    public int getPeriodicalsByGenreCount(Genre genre) throws DaoException {
        if (Objects.nonNull(genre)) {
            return super.getEntriesCount(
                    Query.SELECT_GENRE_PERIODICALS_ENTRIES_COUNT.text,
                    genre.getId().toString()
            );
        }
        throw new DaoException("Attempt to get count by nullable genre");
    }

    @Override
    public List<Periodical> getPeriodicalsByPaymentList(Payment payment) throws DaoException {
        return super.selectObjects(
                Query.SELECT_PAYMENT_PERIODICALS.text,
                payment.getId().toString()
        );
    }

    @Override
    public List<Periodical> getPeriodicalsByUserList(User user) throws DaoException {
        return super.selectObjects(
                Query.SELECT_USER_PERIODICALS.text,
                user.getId().toString()
        );
    }

    @Override
    public List<Periodical> getPeriodicalsByUserListBounded(int skip, int limit, User user) throws DaoException {
        return super.selectObjects(
                Query.SELECT_USER_PERIODICALS_LIMITED_LIST.text,
                user.getId().toString(),
                skip,
                limit
        );
    }

    @Override
    public int getPeriodicalsByUserCount(User user) throws DaoException {
        return super.getEntriesCount(
                Query.SELECT_USER_PERIODICALS_ENTRIES_COUNT.text,
                user.getId().toString()
        );
    }

    @Override
    public boolean getIsUserSubscribedOnPeriodical(User user, Periodical per) throws DaoException {
        int count = super.getEntriesCount(
                Query.SELECT_IS_USER_SUBSCRIBED.text,
                per.getId().toString(),
                user.getId().toString()
        );
        return count > 0;
    }

    /*TODO think of how to make generic*/
    @Override
    public void addUserSubscriptions(User user, List<Periodical> subs) throws DaoException {
        if (Objects.isNull(user) || Objects.isNull(user.getId())) {
            throw new DaoException("Attempt to subscriptions to nullable user or with empty id.");
        }

        if (Objects.isNull(subs) || subs.size() < 1) {
            throw new DaoException("Attempt to subs without periodicals");
        }

        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(Query.INSERT_USER_SUBSCRIPTION.text)) {

            for (Periodical sub : subs) {
                stmt.setString(1, user.getId().toString());
                stmt.setString(2, sub.getId().toString());
                stmt.addBatch();
            }

            stmt.executeBatch();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected Object[] getInsertObjectParams(Periodical periodical) {
        return new Object[]{
                periodical.getId().toString(),
                periodical.getName(),
                periodical.getDescription(),
                periodical.getSubscriptionCost(),
                periodical.getIssuesPerYear(),
                periodical.getIsLimited(),
                periodical.getGenre().getId().toString(),
                periodical.getPublisher().getId().toString()
        };
    }

    @Override
    protected Object[] getObjectUpdateParams(Periodical periodical) {
        return new Object[]{
                periodical.getName(),
                periodical.getDescription(),
                periodical.getSubscriptionCost(),
                periodical.getIssuesPerYear(),
                periodical.getIsLimited(),
                periodical.getGenre().getId().toString(),
                periodical.getPublisher().getId().toString(),
                periodical.getId().toString()
        };
    }

    @Override
    protected List<Periodical> parseResultSet(ResultSet rs) throws DaoException {
        List<Periodical> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Periodical per = new Periodical();
                per.setId(UUID.fromString(rs.getString(Param.ID.name)));
                per.setName(rs.getString(Param.NAME.name));
                per.setDescription(rs.getString(Param.DESCRIPTION.name));
                per.setSubscriptionCost(rs.getBigDecimal(Param.SUBSCRIPTION_COST.name));
                per.setIssuesPerYear(rs.getShort(Param.ISSUES_PER_YEAR.name));
                per.setLimited(rs.getBoolean(Param.IS_LIMITED.name));

                per.setGenre(new Genre(
                        UUID.fromString(rs.getString(Param.GENRE_ID.name)),
                        rs.getString(Param.GENRE_NAME.name)
                ));

                per.setPublisher(new Publisher(
                        UUID.fromString(rs.getString(Param.PUBLISHER_ID.name)),
                        rs.getString(Param.PUBLISHER_NAME.name)
                ));

                result.add(per);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return result;
    }

    private enum Query {
        INSERT(JdbcQueriesHolder.PERIODICAL_INSERT),
        UPDATE(JdbcQueriesHolder.PERIODICAL_UPDATE),
        DELETE(JdbcQueriesHolder.PERIODICAL_DELETE),
        SELECT(JdbcQueriesHolder.PERIODICAL_SELECT_ALL),
        SELECT_BY_ID(SELECT.text + " WHERE per.id = ? LIMIT 1;"),
        SELECT_LIMITED_LIST(SELECT.text + " LIMIT ?,?;"),
        SELECT_ENTRIES_COUNT(JdbcQueriesHolder.PERIODICAL_ENTRIES_COUNT),

        SELECT_IS_USER_SUBSCRIBED(JdbcQueriesHolder.PERIODICAL_IS_USER_SUBSCRIBED),
        SELECT_USER_PERIODICALS_ENTRIES_COUNT(JdbcQueriesHolder.PERIODICAL_USER_SUBSCRIPTIONS_COUNT),
        INSERT_USER_SUBSCRIPTION(JdbcQueriesHolder.PERIODICAL_ADD_USER_SUBSCRIPTION),
        SELECT_USER_PERIODICALS(SELECT.text +
                " WHERE per.id IN" +
                " (SELECT periodical_id" +
                " FROM subscriptions" +
                " WHERE user_id = ?)"),
        SELECT_USER_PERIODICALS_LIMITED_LIST(SELECT_USER_PERIODICALS.text + " LIMIT ?,?;"),

        SELECT_GENRE_PERIODICALS_LIMITED_LIST(SELECT.text + " WHERE genre_id = ? LIMIT ?,?;"),
        SELECT_GENRE_PERIODICALS_ENTRIES_COUNT(SELECT_ENTRIES_COUNT.text + " WHERE genre_id = ?;"),

        SELECT_PAYMENT_PERIODICALS(SELECT.text +
                " INNER JOIN payments_periodicals AS pp" +
                " ON per.id = pp.periodical_id" +
                " WHERE pp.payment_id = ?;");

        private String text;

        Query(String text) {
            this.text = text;
        }
    }

    /**
     * Names of periodical object parameters that are coming from result set in select queries
     */
    private enum Param {
        ID(AttributesPropertyManager.getProperty("periodical.id")),
        NAME(AttributesPropertyManager.getProperty("periodical.name")),
        DESCRIPTION(AttributesPropertyManager.getProperty("periodical.description")),
        SUBSCRIPTION_COST(AttributesPropertyManager.getProperty("periodical.cost")),
        ISSUES_PER_YEAR(AttributesPropertyManager.getProperty("periodical.issues_per_year")),
        IS_LIMITED(AttributesPropertyManager.getProperty("periodical.is_limited")),
        GENRE_ID(AttributesPropertyManager.getProperty("periodical.genre.id")),
        GENRE_NAME(AttributesPropertyManager.getProperty("periodical.genre.name")),
        PUBLISHER_ID(AttributesPropertyManager.getProperty("periodical.publisher.id")),
        PUBLISHER_NAME(AttributesPropertyManager.getProperty("periodical.publisher.name"));

        private String name;

        Param(String name) {
            this.name = name;
        }
    }
}
