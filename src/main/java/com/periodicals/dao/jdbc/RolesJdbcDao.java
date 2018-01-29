package com.periodicals.dao.jdbc;

import com.periodicals.dao.interfaces.lookup.RolesDao;
import com.periodicals.entities.Role;
import com.periodicals.utils.exceptions.DaoException;
import com.periodicals.utils.propertyManagers.AttributesPropertyManager;
import com.periodicals.utils.resourceHolders.JdbcQueriesHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class RolesJdbcDao extends AbstractJdbcDao<Role, UUID> implements RolesDao {

    @Override
    public Role getEntityByPrimaryKey(UUID key) throws DaoException {
        if (Objects.isNull(key)) {
            throw new DaoException("Attempt to get role by nullable key");
        }
        return super.selectObject(Query.SELECT_BY_ID.text, key.toString());
    }

    @Override
    public void createEntity(Role role) throws DaoException {
        if (Objects.isNull(role)) {
            throw new DaoException("Attempt to insert nullable role");
        }
        super.insert(Query.INSERT.text, getInsertObjectParams(role));
    }

    @Override
    public void updateEntity(Role role) throws DaoException {
        if (Objects.isNull(role)) {
            throw new DaoException("Attempt to update nullable role");
        }
        super.update(Query.UPDATE.text, getObjectUpdateParams(role));
    }

    @Override
    public void deleteEntity(Role role) throws DaoException {
        if (Objects.isNull(role)) {
            throw new DaoException("Attempt to delete nullable role");
        }
        super.delete(Query.DELETE.text, role.getId().toString());
    }

    @Override
    public List<Role> getEntityCollection() throws DaoException {
        return super.selectObjects(Query.SELECT.text);
    }

    @Override
    public List<Role> getEntitiesListBounded(int skip, int limit) throws DaoException {
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
    protected Object[] getInsertObjectParams(Role role) {
        return new Object[]{
                role.getId().toString(),
                role.getName()
        };
    }

    @Override
    protected Object[] getObjectUpdateParams(Role role) {
        return new Object[]{
                role.getName(),
                role.getId().toString()
        };
    }

    @Override
    protected List<Role> parseResultSet(ResultSet rs) throws DaoException {
        List<Role> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Role role = new Role();
                role.setId(UUID.fromString(rs.getString(Param.ID.name)));
                role.setName(rs.getString(Param.NAME.name));

                result.add(role);
            }
        } catch (SQLException | IllegalArgumentException e) {
            throw new DaoException(e.getMessage());
        }
        return result;
    }

    private enum Query {
        INSERT(JdbcQueriesHolder.ROLE_INSERT),
        UPDATE(JdbcQueriesHolder.ROLE_UPDATE),
        DELETE(JdbcQueriesHolder.ROLE_DELETE),
        SELECT(JdbcQueriesHolder.ROLE_SELECT_ALL),
        SELECT_BY_ID(SELECT.text + " WHERE id = ? LIMIT 1;"),
        SELECT_LIMITED_LIST(SELECT.text + " LIMIT ?,?;"),
        SELECT_ENTRIES_COUNT(JdbcQueriesHolder.ROLE_ENTRIES_COUNT);

        private String text;

        Query(String text) {
            this.text = text;
        }
    }

    /**
     * Names of role object parameters that are coming from result set in select queries
     */
    private enum Param {
        ID(AttributesPropertyManager.getProperty("role.id")),
        NAME(AttributesPropertyManager.getProperty("role.name"));

        private String name;

        Param(String name) {
            this.name = name;
        }
    }
}
