package com.periodicals.dao.jdbc;

import com.periodicals.dao.interfaces.UsersDao;
import com.periodicals.entities.Role;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;
import com.periodicals.utils.propertyManagers.AttributesPropertyManager;
import com.periodicals.utils.resourceHolders.JdbcQueriesHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UsersJdbcDao extends AbstractJdbcDao<User, UUID> implements UsersDao {

    @Override
    public User getEntityByPrimaryKey(UUID key) throws DaoException {
        if (Objects.isNull(key)) {
            throw new DaoException("Attempt to get user by nullable key");
        }
        return super.selectObject(Query.SELECT_BY_ID.text, key.toString());
    }

    @Override
    public void createEntity(User user) throws DaoException {
        if (Objects.isNull(user)) {
            throw new DaoException("Attempt to insert nullable user");
        }
        super.insert(Query.INSERT.text, getInsertObjectParams(user));
    }

    @Override
    public void updateEntity(User user) throws DaoException {
        if (Objects.isNull(user)) {
            throw new DaoException("Attempt to update nullable user");
        }
        super.update(Query.UPDATE.text, getObjectUpdateParams(user));
    }

    @Override
    public void deleteEntity(User user) throws DaoException {
        if (Objects.isNull(user)) {
            throw new DaoException("Attempt to delete nullable user");
        }
        super.delete(Query.DELETE.text, user.toString());
    }

    @Override
    public List<User> getEntityCollection() throws DaoException {
        return super.selectObjects(Query.SELECT.text);
    }

    @Override
    public List<User> getEntitiesListBounded(int skip, int limit) throws DaoException {
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
    public User getUserByLogin(String login) throws DaoException {
        if (Objects.nonNull(login)) {
            return super.selectObject(Query.SELECT_BY_LOGIN.text, login);
        }
        throw new DaoException("Attempt to get user by nullable login");
    }

    @Override
    protected Object[] getInsertObjectParams(User user) {
        return new Object[]{
                user.getId().toString(),
                user.getLogin(),
                user.getPassword(),
                user.getEmail(),
                user.getRole().getId().toString()
        };
    }

    @Override
    protected Object[] getObjectUpdateParams(User user) {
        return new Object[]{
                user.getLogin(),
                user.getPassword(),
                user.getEmail(),
                user.getRole().getId().toString(),
                user.getId().toString()
        };
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws DaoException {
        List<User> result = new ArrayList<>();
        try {
            while (rs.next()) {
                User user = new User();

                user.setId(UUID.fromString(rs.getString(Param.ID.name)));
                user.setLogin(rs.getString(Param.NAME.name));
                user.setPassword(rs.getString(Param.PASSWORD.name));
                user.setEmail(rs.getString(Param.EMAIL.name));

                user.setRole(new Role(
                        UUID.fromString(rs.getString(Param.ROLE_ID.name)),
                        rs.getString(Param.ROLE_NAME.name)
                ));

                result.add(user);
            }
        } catch (SQLException | IllegalArgumentException e) {
            throw new DaoException(e.getMessage());
        }
        return result;
    }

    private enum Query {
        INSERT(JdbcQueriesHolder.USER_INSERT),
        UPDATE(JdbcQueriesHolder.USER_UPDATE),
        DELETE(JdbcQueriesHolder.USER_DELETE),
        SELECT(JdbcQueriesHolder.USER_SELECT_ALL),
        SELECT_BY_ID(SELECT.text + " WHERE users.id = ? LIMIT 1;"),
        SELECT_BY_LOGIN(SELECT.text + " WHERE users.login = ? LIMIT 1;"),
        SELECT_LIMITED_LIST(SELECT.text + " LIMIT ?,?;"),
        SELECT_ENTRIES_COUNT(JdbcQueriesHolder.USER_ENTRIES_COUNT);

        private String text;

        Query(String text) {
            this.text = text;
        }
    }


    /**
     * Names of user object parameters that are coming from result set in select queries
     */
    private enum Param {
        ID(AttributesPropertyManager.getProperty("user.id")),
        NAME(AttributesPropertyManager.getProperty("user.login")),
        PASSWORD(AttributesPropertyManager.getProperty("user.password")),
        EMAIL(AttributesPropertyManager.getProperty("user.email")),
        ROLE_ID(AttributesPropertyManager.getProperty("user.role_id")),
        ROLE_NAME(AttributesPropertyManager.getProperty("user.role_name"));

        private String name;

        Param(String name) {
            this.name = name;
        }
    }
}
