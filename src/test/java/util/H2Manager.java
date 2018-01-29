package util;

import com.periodicals.dao.connection.ConnectionManager;
import com.periodicals.dao.connection.ConnectionPool;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class H2Manager {
    private static final String CREATE_DB_SCRIPT_PATH =
            "src\\test\\resources\\mySQL_CreateDB.sql";
//    private static final String INSERT_DEFAULTS_SCRIPT_PATH =
//            "src\\test\\resources\\mySQL_Insert_Defaults1.sql";

    private static H2Manager instance = new H2Manager();

    private H2Manager() {
        initH2ConnectionPool();
        initializeDB();
    }

    public static H2Manager getInstance() {
        return instance;
    }

    private static DataSource getH2DataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:periodicals;Mode=MYSQL;DB_CLOSE_DELAY=-1");/**/
        dataSource.setUser("sa");
        dataSource.setPassword("sa");

        return dataSource;
    }

    public static void initH2ConnectionPool() {
        try {
            DataSource h2DataSource = getH2DataSource();
            ConnectionPool.initByDataSource(h2DataSource);
        } catch (Exception e) {
            /*TODO log*/
        }
    }

    private void initializeDB() {
        executeSQLScriptsFromFile(CREATE_DB_SCRIPT_PATH);
    }

    public void executeSQLScriptsFromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException();
        }
        try {
            String script = new String(Files.readAllBytes(file.toPath()));
            script = script.replaceAll("[\\s]+", " ");
            String[] queries = script.split(";");
            Connection connection = getConnection();
            try {
                for (String query : queries) {
                    if (query.trim().isEmpty()) {
                        continue;
                    }
                    Statement statement = connection.createStatement();
                    statement.execute(query);
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

//    public void dropDB(){
//        try (Connection conn = getConnectionWrapper();
//             Statement statement = conn.createStatement()) {
//
//            String sqlDrop = "";
//            statement.executeUpdate(sqlDrop);
//        } catch (SQLException e) {
//            throw new SQLException("FAILED TO TRUNCATE TABLE");
//        }
//    }

    public void truncateTable(String tableName) throws SQLException {
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement()) {

            String sqlTruncate = "DELETE FROM " + tableName + ";";
            statement.executeUpdate(sqlTruncate);
        } catch (SQLException e) {
            throw new SQLException("FAILED TO TRUNCATE TABLE " + tableName);
        }
    }

    public Connection getConnection() {
        return ConnectionManager.getConnectionWrapper().getConnection();
    }

    public static void releaseDataSource(){
        ConnectionPool.releaseDataSource();
    }
}
