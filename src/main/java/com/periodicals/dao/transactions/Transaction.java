package com.periodicals.dao.transactions;

import com.periodicals.utils.exceptions.DaoException;
import com.periodicals.utils.exceptions.ServiceException;
import com.periodicals.utils.exceptions.TransactionException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Functional interface that helps to cover transactional block of code
 */
@FunctionalInterface
public interface Transaction {

    static void doTransaction(Transaction tx, int transactionIsolationLevel) throws TransactionException {
        try {
            TransactionManager.beginTransaction();
            tx.pass();
            TransactionManager.endTransaction();
        } catch (TransactionException | ServiceException | DaoException | SQLException e) {
            TransactionManager.doRollback();
            throw new TransactionException(e);
        }
    }

    static void doTransaction(Transaction tx) throws TransactionException {
        doTransaction(tx, Connection.TRANSACTION_READ_COMMITTED);
    }

    /**
     * This is where all transaction-reliable code will be passed
     */
    void pass() throws DaoException, ServiceException;
}
