package com.periodicals.dao.jdbc;

import com.periodicals.dao.interfaces.lookup.GenresDao;
import com.periodicals.entities.Genre;
import com.periodicals.exceptions.DaoException;
import com.periodicals.utils.propertyManagers.AttributesPropertyManager;
import com.periodicals.utils.resourceHolders.JdbcQueriesHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class GenresJdbcDao extends AbstractJdbcDao<Genre, UUID> implements GenresDao {

    @Override
    public void createEntity(Genre genre) throws DaoException {
        if (Objects.isNull(genre)) {
            throw new DaoException("Attempt to insert nullable genre");
        }
        super.insert(Query.INSERT.text, getInsertObjectParams(genre));
    }

    @Override
    public void updateEntity(Genre genre) throws DaoException {
        if (Objects.isNull(genre)) {
            throw new DaoException("Attempt to update nullable genre");
        }
        super.update(Query.UPDATE.text, getObjectUpdateParams(genre));
    }

    @Override
    public void deleteEntity(Genre genre) throws DaoException {
        if (Objects.isNull(genre)) {
            throw new DaoException("Attempt to delete nullable genre");
        }
        super.delete(Query.DELETE.text, genre.getId().toString());
    }

    @Override
    public Genre getEntityByPrimaryKey(UUID key) throws DaoException {
        if (Objects.nonNull(key)) {
            return super.selectObject(
                    Query.SELECT_BY_ID.text,
                    key.toString()
            );
        }
        throw new DaoException("Attempt to get entity by nullable key");

    }

    @Override
    public List<Genre> getEntityCollection() throws DaoException {
        return super.selectObjects(Query.SELECT.text);
    }

    @Override
    public List<Genre> getEntitiesListBounded(int skip, int limit) throws DaoException {
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
    protected Object[] getInsertObjectParams(Genre genre) {
        return new Object[]{
                genre.getId().toString(),
                genre.getName()
        };
    }

    @Override
    protected Object[] getObjectUpdateParams(Genre genre) {
        return new Object[]{
                genre.getName(),
                genre.getId().toString()
        };
    }

    @Override
    protected List<Genre> parseResultSet(ResultSet rs) throws DaoException {
        List<Genre> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(UUID.fromString(rs.getString(Param.ID.name)));
                genre.setName(rs.getString(Param.NAME.name));

                result.add(genre);
            }
        } catch (SQLException | IllegalArgumentException e) {
            throw new DaoException(e);
        }
        return result;
    }

    private enum Query {
        INSERT(JdbcQueriesHolder.GENRE_INSERT),
        UPDATE(JdbcQueriesHolder.GENRE_UPDATE),
        DELETE(JdbcQueriesHolder.GENRE_DELETE),
        SELECT(JdbcQueriesHolder.GENRE_SELECT_ALL),
        SELECT_BY_ID(SELECT.text + " WHERE id = ? LIMIT 1;"),
        SELECT_LIMITED_LIST(SELECT.text + " LIMIT ?,?;"),
        SELECT_ENTRIES_COUNT(JdbcQueriesHolder.GENRE_ENTRIES_COUNT);

        private String text;

        Query(String text) {
            this.text = text;
        }
    }

    /**
     * Names of genre object parameters that are coming from result set in select queries
     */
    private enum Param {
        ID(AttributesPropertyManager.getProperty("genre.id")),
        NAME(AttributesPropertyManager.getProperty("genre.name"));

        private String name;

        Param(String name) {
            this.name = name;
        }
    }
}
