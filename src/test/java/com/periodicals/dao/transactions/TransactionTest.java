package com.periodicals.dao.transactions;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.GenresJdbcDao;
import com.periodicals.entities.Genre;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.TransactionException;
import org.junit.jupiter.api.*;
import util.H2Manager;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionTest {
    private static final H2Manager testDbManager = H2Manager.getInstance();

    private static final GenresJdbcDao genresDao =
            (GenresJdbcDao) JdbcDaoFactory.getInstance().getGenresDao();

    private static final String GENRES_TABLE_NAME = "genres";
    private static final String INSERT_FILE_PATH =
            "src\\test\\resources\\genres_Insert_Defaults.sql";

    private static final int INITIAL_GENRES_COUNT = 2;

    private static final Genre NEW_GENRE_1 = new Genre(
            UUID.fromString("d74ec6d5-feb1-11e7-982f-25e096a99612"),
            "magazine"
    );

    private static final Genre NEW_GENRE_2 = new Genre(
            UUID.fromString("dd8fe15f-feb1-11e7-a1da-4f1c9ba93af5"),
            "manga"
    );

    @BeforeAll
    static void beforeAll() {
        H2Manager.initH2ConnectionPool();
    }

    @AfterAll
    static void afterAll() {
        H2Manager.releaseDataSource();
    }

    @BeforeEach
    void setUp() {
        testDbManager.executeSQLScriptsFromFile(INSERT_FILE_PATH);
    }

    @AfterEach
    void tearDown() throws SQLException {
        testDbManager.truncateTable(GENRES_TABLE_NAME);
    }

    /**
     * @see Transaction#doTransaction(Transaction)
     */
    @Test
    void doTransaction_add_2_different_genres_success() throws TransactionException, DaoException {
        Transaction.doTransaction(() -> {
            genresDao.createEntity(NEW_GENRE_1);
            genresDao.createEntity(NEW_GENRE_2);
        });

        int expectedCount = 4;
        int resultCount = genresDao.getEntitiesCount();
        assertEquals(expectedCount, resultCount);

        List<Genre> allGenres = genresDao.getEntityCollection();
        assertTrue(allGenres.contains(NEW_GENRE_1));
        assertTrue(allGenres.contains(NEW_GENRE_2));
    }

    @Test
    void doTransaction_add_same_genre_twice_rollback() throws DaoException {
        Assertions.assertThrows(TransactionException.class, () -> {
            Transaction.doTransaction(() -> {
                genresDao.createEntity(NEW_GENRE_1);
                genresDao.createEntity(NEW_GENRE_1);
            });
        });

        int resultCount = genresDao.getEntitiesCount();
        assertEquals(INITIAL_GENRES_COUNT, resultCount);
    }
}