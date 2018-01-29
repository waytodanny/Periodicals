package com.periodicals.dao.transactions;

import com.periodicals.dao.connection.ConnectionManager;
import com.periodicals.dao.connection.ConnectionWrapper;
import com.periodicals.utils.exceptions.TransactionException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author Daniel Volnitsky
 * Class responsible for transactions managing: begin, end, rollback processing
 */
class TransactionManager {
    static final Logger LOGGER = Logger.getLogger(Transaction.class.getSimpleName());
    private static ThreadLocal<ConnectionWrapper> threadLocal = ConnectionManager.getThreadLocal();

    private TransactionManager() {

    }

    static void beginTransaction() throws SQLException, TransactionException {
        if (Objects.nonNull(threadLocal.get())) {
            LOGGER.error("Attempt to begin transaction with opened connection");
            throw new TransactionException("Attempt to begin transaction with opened connection");
        }
        ConnectionWrapper wrapper = ConnectionManager.getTransactionConnectionWrapper();
        wrapper.getConnection().setAutoCommit(false);
        threadLocal.set(wrapper);
    }

    static void endTransaction() throws TransactionException {
        if (Objects.isNull(threadLocal.get())) {
            LOGGER.error("Attempt to close transaction on nullable connection");
            throw new TransactionException("Attempt to close transaction on nullable connection");
        }
        ConnectionWrapper wrapper = threadLocal.get();
        Connection connection = wrapper.getConnection();
        try {
            connection.commit();
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Failed to commit or close connection");
            throw new TransactionException("Failed to commit or close connection");
        } finally {
            threadLocal.set(null);
        }
    }

    static void doRollback() throws TransactionException {
        if (Objects.isNull(threadLocal.get())) {
            LOGGER.error("Attempt to doRollback not existing connection");
            throw new TransactionException("Attempt to doRollback not existing connection");
        }
        ConnectionWrapper wrapper = threadLocal.get();
        Connection conn = wrapper.getConnection();
        try {
            conn.rollback();
            conn.setAutoCommit(true);
            conn.close();
        } catch (SQLException e) {
            LOGGER.error("Failed to rollback transaction: " + e.getMessage());
            throw new TransactionException("Failed to rollback transaction: " + e.getMessage());
        } finally {
            threadLocal.set(null);
        }
    }
}
