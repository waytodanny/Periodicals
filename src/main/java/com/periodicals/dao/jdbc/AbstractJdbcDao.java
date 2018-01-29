package com.periodicals.dao.jdbc;

import com.periodicals.dao.connection.ConnectionManager;
import com.periodicals.dao.connection.ConnectionWrapper;
import com.periodicals.utils.exceptions.DaoException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static com.periodicals.utils.resourceHolders.JdbcQueriesHolder.COUNT_PARAM;

public abstract class AbstractJdbcDao<T, K> {
    private static final Logger LOGGER = Logger.getLogger(AbstractJdbcDao.class);

    protected abstract Object[] getInsertObjectParams(T object) throws DaoException;

    protected abstract Object[] getObjectUpdateParams(T object) throws DaoException;

    protected abstract List<T> parseResultSet(ResultSet rs) throws DaoException;

    /**
     * Builds insert query by incoming query and params for it and executes it
     */
    protected void insert(String insertQuery, Object[] params) throws DaoException {
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            stmt.executeUpdate();
            LOGGER.debug("Successful insertion");
        } catch (Exception e) {
            LOGGER.error("Failed to insert data to DB: " + e);
            throw new DaoException(e);
        }
    }

    /**
     * Select list of object building select query  by incoming query and params
     */
    List<T> selectObjects(String query, Object... params) throws DaoException {
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            ResultSet rs = stmt.executeQuery();
            return parseResultSet(rs);
        } catch (Exception e) {
            LOGGER.error("Failed to get objects from DB: " + e.getMessage());
            throw new DaoException(e);
        }
    }

    /**
     * Selects one object from list of objects given by selectsObjects()
     */
    T selectObject(String query, Object... params) throws DaoException {
        List<T> objects = selectObjects(query, params);
        if (objects.size() > 0) {
            return objects.get(0);
        }
        throw new DaoException("No object founded by given params");
    }


    protected void update(String query, Object... params) throws DaoException {
        LOGGER.debug("Beginning update query");
        executeUpdate(query, params);
    }

    protected void delete(String query, Object... params) throws DaoException {
        LOGGER.debug("Beginning deleteUserById query");
        executeUpdate(query, params);
    }

    /**
     * Generic method for deleteUserById/update queries
     */
    private void executeUpdate(String query, Object... params) throws DaoException {
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            stmt.executeUpdate();
            LOGGER.debug("DB successfully updated");
        } catch (Exception e) {
            LOGGER.error("Failed to process DB updating: " + e.getMessage());
            throw new DaoException(e);
        }
    }

    /**
     * Generic method for queries that intend to obtain count value from DB
     */
    int getEntriesCount(String query, Object... params) throws DaoException {
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                LOGGER.debug("successfully got count from DB");
                return rs.getInt(COUNT_PARAM);
            }
            throw new DaoException("Failed to obtain count from DB");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
    }
}