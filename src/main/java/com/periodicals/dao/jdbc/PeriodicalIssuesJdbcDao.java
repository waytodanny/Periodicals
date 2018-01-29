package com.periodicals.dao.jdbc;

import com.periodicals.dao.interfaces.PeriodicalIssuesDao;
import com.periodicals.entities.Genre;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.entities.Publisher;
import com.periodicals.utils.exceptions.DaoException;
import com.periodicals.utils.propertyManagers.AttributesPropertyManager;
import com.periodicals.utils.resourceHolders.JdbcQueriesHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PeriodicalIssuesJdbcDao extends AbstractJdbcDao<PeriodicalIssue, UUID> implements PeriodicalIssuesDao {

    @Override
    public void createEntity(PeriodicalIssue entity) throws DaoException {
        super.insert(Query.INSERT.text, getInsertObjectParams(entity));
    }

    @Override
    public void updateEntity(PeriodicalIssue entity) throws DaoException {
        super.update(Query.UPDATE.text, getObjectUpdateParams(entity));
    }

    @Override
    public void deleteEntity(PeriodicalIssue entity) throws DaoException {
        super.delete(Query.DELETE.text, entity.getId().toString());
    }

    @Override
    public PeriodicalIssue getEntityByPrimaryKey(UUID key) throws DaoException {
        return super.selectObject(Query.SELECT_BY_ID.text, key.toString());
    }

    @Override
    public List<PeriodicalIssue> getEntityCollection() throws DaoException {
        return super.selectObjects(Query.SELECT.text);
    }

    @Override
    public List<PeriodicalIssue> getEntitiesListBounded(int skip, int limit) throws DaoException {
        return super.selectObjects(Query.SELECT_LIMITED_LIST.text, skip, limit);
    }

    @Override
    public int getEntitiesCount() throws DaoException {
        return super.getEntriesCount(Query.SELECT_ENTRIES_COUNT.text);
    }

    @Override
    public List<PeriodicalIssue> getIssuesByPeriodicalListBounded(int skip, int limit, Periodical periodical) throws DaoException {
        return super.selectObjects(
                Query.SELECT_ISSUES_BY_PERIODICAL_LIMITED_LIST.text,
                periodical.getId().toString(),
                skip,
                limit
        );
    }

    @Override
    public int getIssuesByPeriodicalCount(Periodical periodical) throws DaoException {
        return super.getEntriesCount(
                Query.SELECT_ISSUES_BY_PERIODICAL_ENTRIES_COUNT.text,
                periodical.getId().toString()
        );
    }

    @Override
    protected Object[] getInsertObjectParams(PeriodicalIssue issue) {
        return new Object[]{
                issue.getId().toString(),
                issue.getIssueNo(),
                issue.getName(),
                issue.getPeriodical().getId().toString()
        };
    }

    @Override
    protected Object[] getObjectUpdateParams(PeriodicalIssue issue) {
        return new Object[]{
                issue.getIssueNo(),
                issue.getName(),
                issue.getPeriodical().getId().toString(),
                issue.getId().toString()
        };
    }

    @Override
    protected List<PeriodicalIssue> parseResultSet(ResultSet rs) throws DaoException {
        List<PeriodicalIssue> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PeriodicalIssue issue = new PeriodicalIssue();
                issue.setId(UUID.fromString(rs.getString(Param.ID.name)));
                issue.setIssueNo(rs.getInt(Param.ISSUE_NO.name));
                issue.setName(rs.getString(Param.NAME.name));
                issue.setPublishDate(rs.getDate(Param.PUBLISHING_DATE.name));

                Periodical periodical = new Periodical();
                periodical.setId(UUID.fromString(rs.getString(Param.PERIODICAL_ID.name)));
                periodical.setName(rs.getString(Param.PERIODICAL_NAME.name));
                periodical.setDescription(rs.getString(Param.PERIODICAL_DESCRIPTION.name));
                periodical.setSubscriptionCost(rs.getBigDecimal(Param.PERIODICAL_SUBSCRIPTION_COST.name));
                periodical.setIssuesPerYear(rs.getShort(Param.PERIODICAL_ISSUES_PER_YEAR.name));
                periodical.setLimited(rs.getBoolean(Param.PERIODICAL_IS_LIMITED.name));

                periodical.setGenre(new Genre(
                        UUID.fromString(rs.getString(Param.PERIODICAL_GENRE_ID.name)),
                        rs.getString(Param.PERIODICAL_GENRE_NAME.name)
                ));

                periodical.setPublisher(new Publisher(
                        UUID.fromString(rs.getString(Param.PERIODICAL_PUBLISHER_ID.name)),
                        rs.getString(Param.PERIODICAL_PUBLISHER_NAME.name)
                ));

                issue.setPeriodical(periodical);
                result.add(issue);
            }
        } catch (SQLException | IllegalArgumentException e) {
            throw new DaoException(e);
        }
        return result;
    }

    private enum Query {
        INSERT(JdbcQueriesHolder.PERIODICAL_ISSUE_INSERT),
        UPDATE(JdbcQueriesHolder.PERIODICAL_ISSUE_UPDATE),
        DELETE(JdbcQueriesHolder.PERIODICAL_ISSUE_DELETE),
        SELECT(JdbcQueriesHolder.PERIODICAL_ISSUE_SELECT_ALL),
        SELECT_BY_ID(SELECT.text + " WHERE issues.id = ? LIMIT 1;"),
        SELECT_LIMITED_LIST(SELECT.text + " LIMIT ?,?;"),
        SELECT_ENTRIES_COUNT(JdbcQueriesHolder.PERIODICAL_ISSUE_ENTRIES_COUNT),

        SELECT_ISSUES_BY_PERIODICAL_LIMITED_LIST(SELECT.text + " WHERE periodical_id = ? LIMIT ?,?;"),
        SELECT_ISSUES_BY_PERIODICAL_ENTRIES_COUNT(SELECT_ENTRIES_COUNT.text + " WHERE periodical_id = ?;");

        private String text;

        Query(String text) {
            this.text = text;
        }
    }


    /**
     * Names of periodical issue object parameters that are coming from result set in select queries
     */
    private enum Param {
        ID(AttributesPropertyManager.getProperty("periodical_issue.id")),
        NAME(AttributesPropertyManager.getProperty("periodical_issue.name")),
        ISSUE_NO(AttributesPropertyManager.getProperty("periodical_issue.no")),
        PUBLISHING_DATE(AttributesPropertyManager.getProperty("periodical_issue.publishing_date")),

        PERIODICAL_ID(AttributesPropertyManager.getProperty("periodical_issue.periodical_id")),
        PERIODICAL_NAME(AttributesPropertyManager.getProperty("periodical_issue.periodical.name")),
        PERIODICAL_DESCRIPTION(AttributesPropertyManager.getProperty("periodical_issue.periodical.description")),
        PERIODICAL_SUBSCRIPTION_COST(AttributesPropertyManager.getProperty("periodical_issue.periodical.cost")),
        PERIODICAL_ISSUES_PER_YEAR(AttributesPropertyManager.getProperty("periodical_issue.periodical.issues_per_year")),
        PERIODICAL_IS_LIMITED(AttributesPropertyManager.getProperty("periodical_issue.periodical.is_limited")),
        PERIODICAL_GENRE_ID(AttributesPropertyManager.getProperty("periodical_issue.periodical.genre.id")),
        PERIODICAL_GENRE_NAME(AttributesPropertyManager.getProperty("periodical_issue.periodical.genre.name")),
        PERIODICAL_PUBLISHER_ID(AttributesPropertyManager.getProperty("periodical_issue.periodical.publisher.id")),
        PERIODICAL_PUBLISHER_NAME(AttributesPropertyManager.getProperty("periodical_issue.periodical.publisher.name"));

        private String name;

        Param(String name) {
            this.name = name;
        }
    }
}
