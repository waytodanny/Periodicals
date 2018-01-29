package com.periodicals.dao.jdbc;

import com.periodicals.dao.interfaces.PublishersDao;
import com.periodicals.entities.Publisher;
import com.periodicals.utils.exceptions.DaoException;
import com.periodicals.utils.propertyManagers.AttributesPropertyManager;
import com.periodicals.utils.resourceHolders.JdbcQueriesHolder;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PublishersJdbcDao extends AbstractJdbcDao<Publisher, UUID> implements PublishersDao {

    @Override
    public Publisher getEntityByPrimaryKey(UUID key) throws DaoException {
        if (Objects.nonNull(key)) {
            return super.selectObject(Query.SELECT_BY_ID.text, key.toString());
        }
        throw new DaoException("Attempt to get publisher by nullable key");
    }

    @Override
    public void createEntity(Publisher publisher) throws DaoException {
        if (Objects.isNull(publisher)) {
            throw new DaoException("Attempt to insert nullable publisher");
        }
        super.insert(Query.INSERT.text, getInsertObjectParams(publisher));
    }

    @Override
    public void updateEntity(Publisher publisher) throws DaoException {
        if (Objects.isNull(publisher)) {
            throw new DaoException("Attempt to update nullable publisher");
        }
        super.update(Query.UPDATE.text, getObjectUpdateParams(publisher));
    }

    @Override
    public void deleteEntity(Publisher publisher) throws DaoException {
        if (Objects.isNull(publisher)) {
            throw new DaoException("Attempt to delete nullable publisher");
        }
        super.delete(Query.DELETE.text, publisher.getId().toString());
    }

    @Override
    public List<Publisher> getEntityCollection() throws DaoException {
        return super.selectObjects(Query.SELECT.text);
    }

    @Override
    public List<Publisher> getEntitiesListBounded(int skip, int limit) throws DaoException {
        if (skip > -1 && limit > -1) {
            return super.selectObjects(Query.SELECT_LIMITED_LIST.text, skip, limit);
        }
        throw new DaoException("Skip and limit params must be greater than -1");
    }

    @Override
    public int getEntitiesCount() throws DaoException {
        return super.getEntriesCount(Query.SELECT_ENTRIES_COUNT.text);
    }

    @Override
    protected Object[] getInsertObjectParams(Publisher publisher) {
        return new Object[]{
                publisher.getId().toString(),
                publisher.getName()
        };
    }

    @Override
    protected Object[] getObjectUpdateParams(Publisher publisher) {
        return new Object[]{
                publisher.getName(),
                publisher.getId().toString()
        };
    }

    @Override
    protected List<Publisher> parseResultSet(ResultSet rs) throws DaoException {
        List<Publisher> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Publisher publisher = new Publisher();
                publisher.setId(UUID.fromString(rs.getString(Param.ID.name)));
                publisher.setName(rs.getString(Param.NAME.name));

                result.add(publisher);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return result;
    }

    private enum Query {
        INSERT(JdbcQueriesHolder.PUBLISHER_INSERT),
        UPDATE(JdbcQueriesHolder.PUBLISHER_UPDATE),
        DELETE(JdbcQueriesHolder.PUBLISHER_DELETE),
        SELECT(JdbcQueriesHolder.PUBLISHER_SELECT_ALL),
        SELECT_BY_ID(SELECT.text + " WHERE id = ? LIMIT 1;"),
        SELECT_LIMITED_LIST(SELECT.text + " LIMIT ?,?;"),
        SELECT_ENTRIES_COUNT(JdbcQueriesHolder.PUBLISHER_ENTRIES_COUNT);

        private String text;

        Query(String text) {
            this.text = text;
        }
    }


    /**
     * Names of publisher object parameters that are coming from result set in select queries
     */
    private enum Param {
        ID(AttributesPropertyManager.getProperty("publisher.id")),
        NAME(AttributesPropertyManager.getProperty("publisher.name"));

        private String name;

        Param(String name) {
            this.name = name;
        }
    }
}
