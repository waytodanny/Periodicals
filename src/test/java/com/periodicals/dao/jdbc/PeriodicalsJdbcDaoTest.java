package com.periodicals.dao.jdbc;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.entities.Genre;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.Publisher;
import com.periodicals.exceptions.DaoException;
import org.junit.jupiter.api.*;
import util.H2Manager;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PeriodicalsJdbcDaoTest {
    private static final H2Manager testDbManager = H2Manager.getInstance();

    private static final GenresJdbcDao genresDao =
            (GenresJdbcDao) JdbcDaoFactory.getInstance().getGenresDao();
    private static final PeriodicalsJdbcDao periodicalsDao =
            (PeriodicalsJdbcDao) JdbcDaoFactory.getInstance().getPeriodicalsDao();

    private static final String INSERT_FILE_PATH = "src\\test\\resources\\periodicals_Insert_Defaults.sql";

    private static final String GENRES_TABLE_NAME = "genres";
    private static final String PUBLISHERS_TABLE_NAME = "publishers";
    private static final String PERIODICALS_TABLE_NAME = "periodicals";

    private static final int GENRE_PERIODICALS_COUNT = 2;

    private static final Genre EXISTING_GENRE = new Genre(
            UUID.fromString("c1fae08d-feb1-11e7-8e6b-313d4bf1847c"),
            "comics"
    );

    private static final Genre NOT_EXISTING_GENRE = new Genre(
            UUID.fromString("fa41df-f7a5-11e7-adbd-41982924721f"),
            "unknown"
    );

    private static final Publisher EXISTING_PUBLISHER = new Publisher(
            UUID.fromString("fc13ecc9-feb1-11e7-8a29-d5a2d48a37f0"),
            "Marvel"
    );

    private static final Periodical EXISTING_PERIODICAL = new Periodical();

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

        EXISTING_PERIODICAL.setId(UUID.fromString("426f8ce0-feb2-11e7-bc2e-bd4c345931c8"));
        EXISTING_PERIODICAL.setName("Ultimate spider man1");
        EXISTING_PERIODICAL.setDescription("best spider-man story you have ever read");
        EXISTING_PERIODICAL.setSubscriptionCost(new BigDecimal("12.50"));
        EXISTING_PERIODICAL.setIssuesPerYear(24);
        EXISTING_PERIODICAL.setLimited(true);
        EXISTING_PERIODICAL.setGenre(EXISTING_GENRE);
        EXISTING_PERIODICAL.setPublisher(EXISTING_PUBLISHER);
    }

    @AfterEach
    void tearDown() throws SQLException {
        testDbManager.truncateTable(PERIODICALS_TABLE_NAME);
        testDbManager.truncateTable(GENRES_TABLE_NAME);
        testDbManager.truncateTable(PUBLISHERS_TABLE_NAME);
    }

    /**
     * @see PeriodicalsJdbcDao#getPeriodicalsByGenreListBounded(int, int, Genre)
     */
    @Test
    void getPeriodicalsByGenreListBounded_skip0_limit1_success() throws DaoException {
        int skip = 0;
        int limit = 1;

        List<Periodical> periodicals = periodicalsDao.
                getPeriodicalsByGenreListBounded(skip, limit, EXISTING_GENRE);

        assertNotNull(periodicals);
        assertEquals(limit, periodicals.size());
        assertTrue(periodicals.contains(EXISTING_PERIODICAL));
    }

    @Test
    void getPeriodicalsByGenreListBounded_skip_take_less_than_zero_throws() throws DaoException {
        int skip = -1;
        int limit = -1;

        Assertions.assertThrows(DaoException.class, () -> {
            List<Periodical> periodicals = periodicalsDao.
                    getPeriodicalsByGenreListBounded(skip, limit, EXISTING_GENRE);
        });
    }

    @Test
    void getPeriodicalsByGenreListBounded_nullable__genre_throws() throws DaoException {
        int skip = -1;
        int limit = -1;

        Assertions.assertThrows(DaoException.class, () -> {
            List<Periodical> periodicals = periodicalsDao.
                    getPeriodicalsByGenreListBounded(skip, limit, null);
        });
    }

    /**
     * @see PeriodicalsJdbcDao#getPeriodicalsByGenreCount(Genre)
     */
    @Test
    void getPeriodicalsByGenreCount_existing_genre_success() throws DaoException {
       int result = periodicalsDao.getPeriodicalsByGenreCount(EXISTING_GENRE);
       assertEquals(GENRE_PERIODICALS_COUNT, result);
    }

    @Test
    void getPeriodicalsByGenreCount_not_existing_genre_return_0() throws DaoException {
        int expected = 0;
        int result = periodicalsDao.getPeriodicalsByGenreCount(NOT_EXISTING_GENRE);
        assertEquals(expected, result);
    }

    @Test
    void getPeriodicalsByGenreCount_nullable_genre_throws() throws DaoException {
        Assertions.assertThrows(DaoException.class, () -> {
            int result = periodicalsDao.getPeriodicalsByGenreCount(null);
        });
    }

//    @Test
//    void getPeriodicalsByPaymentList() {
//
//    }
//
//    @Test
//    void getPeriodicalsByUserList() {
//
//    }
//
//    @Test
//    void getPeriodicalsByUserListBounded() {
//
//    }
//
//    @Test
//    void getPeriodicalsByUserCount() {
//
//    }
//
//    @Test
//    void getIsUserSubscribedOnPeriodical() {
//
//    }
//
//    @Test
//    void addUserSubscriptions() {
//
//    }
}