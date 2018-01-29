package com.periodicals.dao.connection;

import java.sql.Connection;
import java.util.Objects;

/**
 * Class responsible for connections distributing;
 */
public class ConnectionManager {
    /**
     * ThreadLocal object responsible for holding and distributing
     * local connections for each thread that intends to take a connection
     */
    private static ThreadLocal<ConnectionWrapper> threadLocal = new ThreadLocal<>();
    private static ConnectionPool pool = ConnectionPool.getInstance();

    public static ThreadLocal<ConnectionWrapper> getThreadLocal() {
        return threadLocal;
    }

    public static ConnectionWrapper getConnectionWrapper() {
        return getSpecifiedConnectionWrapper(false);
    }

    public static ConnectionWrapper getTransactionConnectionWrapper() {
        return getSpecifiedConnectionWrapper(true);
    }

    /**
     * Takes connection from thread local object if there is a connection for exact thread
     * or takes new connection from pool if not
     */
    private static ConnectionWrapper getSpecifiedConnectionWrapper(boolean isTransaction) {
        if (Objects.isNull(threadLocal.get())) {
            Connection conn = pool.getConnection();
            return new ConnectionWrapper(conn, isTransaction);
        } else {
            return threadLocal.get();
        }
    }
}
