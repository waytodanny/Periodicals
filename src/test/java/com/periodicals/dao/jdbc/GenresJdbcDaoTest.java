package com.periodicals.dao.jdbc;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.entities.Genre;
import com.periodicals.utils.exceptions.DaoException;
import org.junit.jupiter.api.*;
import util.H2ConnectionManager;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.AttributesHolder.*;
import static org.junit.jupiter.api.Assertions.*;

class GenresJdbcDaoTest {

    private static final H2ConnectionManager testDbManager = H2ConnectionManager.getInstance();
    private static final GenresJdbcDao genresDao =
            (GenresJdbcDao) JdbcDaoFactory.getInstance().getGenresDao();

    private static final String INSERT_DEFAULTS_FILE_PATH =
            "src\\test\\resources\\genres_Insert_Defaults.sql";

    private static final String TABLE_NAME = "genres";

    private static final int EXPECTED_ENTRIES_COUNT = 2;

    private static final UUID TEST_GENRE_ID = UUID.fromString("86ed52aa-0158-11e8-b7ab-e500aed34992");
    private static final String TEST_GENRE_NAME = "newGenre";

    private static final Genre TEST_GENRE = new Genre(
            UUID.fromString("86ed52aa-0158-11e8-b7ab-e500aed34992"),
            "newGenre"
    );

    private static final UUID EXISTED_GENRE_COMICS_ID = UUID.fromString("c1fae08d-feb1-11e7-8e6b-313d4bf1847c");
    private static final String EXISTED_GENRE_COMICS_NAME = "comics";

    private static final Genre EXISTED_GENRE_COMICS = new Genre(
            EXISTED_GENRE_COMICS_ID,
            EXISTED_GENRE_COMICS_NAME
    );

    private static final UUID EXISTED_GENRE_NEWSPAPER_ID = UUID.fromString("cf8c14b2-feb1-11e7-ba96-313a67f5f27a");
    private static final String EXISTED_GENRE_NEWSPAPER_NAME = "newspaper";

    private static final Genre EXISTED_GENRE_NEWSPAPER = new Genre(
            EXISTED_GENRE_NEWSPAPER_ID,
            EXISTED_GENRE_NEWSPAPER_NAME
    );

    private static Genre sameGenre;

    @BeforeAll
    static void beforeAll() {
        H2ConnectionManager.initH2ConnectionPool();
        sameGenre = null;
    }


    @AfterAll
    static void afterAll() {
        H2ConnectionManager.releaseDataSource();
    }

    @BeforeEach
    void setUp() {
        testDbManager.executeSQLScriptsFromFile(INSERT_DEFAULTS_FILE_PATH);
    }

    @AfterEach
    void tearDown() throws SQLException {
        sameGenre = null;

        TEST_GENRE.setId(TEST_GENRE_ID);
        TEST_GENRE.setName(TEST_GENRE_NAME);

        EXISTED_GENRE_COMICS.setId(EXISTED_GENRE_COMICS_ID);
        EXISTED_GENRE_COMICS.setName(EXISTED_GENRE_COMICS_NAME);

        EXISTED_GENRE_NEWSPAPER.setId(EXISTED_GENRE_NEWSPAPER_ID);
        EXISTED_GENRE_NEWSPAPER.setName(EXISTED_GENRE_NEWSPAPER_NAME);

        testDbManager.truncateTable(GENRES_TABLE_NAME);
    }

    /**
     * @see GenresJdbcDao#getEntityByPrimaryKey(UUID)
     */
    @Test
    void getEntityByPrimaryKey_existing_key_success() throws DaoException {
        Genre resultGenre = genresDao.getEntityByPrimaryKey(EXISTED_GENRE_COMICS_ID);

        assertNotNull(resultGenre);
        assertEquals(EXISTED_GENRE_COMICS, resultGenre);
    }

    @Test
    void getEntityByPrimaryKey_null_key_throws() {
        Assertions.assertThrows(DaoException.class, () -> {
            Genre resultGenre = genresDao.getEntityByPrimaryKey(null);
        });
    }

    /**
     * @see GenresJdbcDao#createEntity(Genre)
     */
    @Test
    void createEntity_valid_success() throws DaoException {
        genresDao.createEntity(TEST_GENRE);

        sameGenre = genresDao.getEntityByPrimaryKey(TEST_GENRE.getId());
        assertNotNull(sameGenre);
        assertEquals(TEST_GENRE, sameGenre);
    }

    @Test
    void createEntity_null_throws() {
        Assertions.assertThrows(DaoException.class, () -> {
            genresDao.createEntity(null);
        });
    }

    /**
     * @see GenresJdbcDao#updateEntity(Genre)
     */
    @Test
    void updateEntity_existed_success() throws DaoException {
        String newName = "newName";

        Genre updatableGenre = genresDao.getEntityByPrimaryKey(EXISTED_GENRE_COMICS.getId());

        assertNotNull(updatableGenre);
        assertEquals(EXISTED_GENRE_COMICS_NAME, updatableGenre.getName());

        updatableGenre.setName(newName);
        genresDao.updateEntity(updatableGenre);

        updatableGenre = genresDao.getEntityByPrimaryKey(EXISTED_GENRE_COMICS.getId());
        assertNotNull(updatableGenre);
        assertEquals(newName, updatableGenre.getName());
    }

    @Test
    void updateEntity_null_throws() {
        Assertions.assertThrows(DaoException.class, () -> {
            genresDao.updateEntity(null);
        });
    }

    /**
     * @see GenresJdbcDao#deleteEntity(Genre)
     */
    @Test
    void deleteEntity_existed_success() throws DaoException {
        Genre deletableGenre = genresDao.getEntityByPrimaryKey(EXISTED_GENRE_COMICS.getId());
        assertNotNull(deletableGenre);

        genresDao.deleteEntity(deletableGenre);

        Assertions.assertThrows(DaoException.class, () -> {
            genresDao.getEntityByPrimaryKey(EXISTED_GENRE_COMICS.getId());
        });
    }

    @Test
    void deleteEntity_null_throws() {
        Assertions.assertThrows(DaoException.class, () -> {
            genresDao.deleteEntity(null);
        });
    }

    /**
     * @see GenresJdbcDao#getEntitiesCount()
     */
    @Test
    void getEntitiesCount_success() throws DaoException {
        int result = genresDao.getEntitiesCount();
        assertEquals(EXPECTED_ENTRIES_COUNT, result);
    }

    /**
     * @see GenresJdbcDao#getEntityCollection()
     */
    @Test
    void getEntityCollection_success() throws DaoException {
        List<Genre> allGenres = genresDao.getEntityCollection();

        assertEquals(EXPECTED_ENTRIES_COUNT, allGenres.size());
        assertTrue(allGenres.contains(EXISTED_GENRE_COMICS));
    }

    /**
     * @see GenresJdbcDao#getEntitiesListBounded(int, int)
     */
    @Test
    void getEntitiesListBounded_skip1_limit1_success() throws DaoException {
        final int skip = 1;
        final int limit = 1;

        List<Genre> genres = genresDao.getEntitiesListBounded(skip, limit);

        assertEquals(limit, genres.size());

        assertFalse(genres.contains(EXISTED_GENRE_COMICS));
        assertTrue(genres.contains(EXISTED_GENRE_NEWSPAPER));
    }

    @Test
    void getEntitiesListBounded_skip_limit_less_than_zero_failure() throws DaoException {
        final int skip = -1;
        final int limit = -1;

        Assertions.assertThrows(DaoException.class, () -> {
            List<Genre> genres = genresDao.getEntitiesListBounded(skip, limit);
        });
    }
}