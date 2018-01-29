package com.periodicals.dao.jdbc;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.entities.Role;
import com.periodicals.entities.User;
import com.periodicals.utils.exceptions.DaoException;
import org.junit.jupiter.api.*;
import util.H2ConnectionManager;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UsersJdbcDaoTest {

    private static final H2ConnectionManager testDbManager = H2ConnectionManager.getInstance();
    private static final UsersJdbcDao usersDao =
            (UsersJdbcDao) JdbcDaoFactory.getInstance().getUsersDao();

    private static final String INSERT_FILE_PATH = "src\\test\\resources\\users_Insert_Defaults.sql";

    private static final String ROLES_TABLE_NAME = "roles";
    private static final String USERS_TABLE_NAME = "users";

    private static final String NOT_EXISTING_USER_LOGIN = "unknown login";

    private static final Role ADMIN_ROLE = new Role(
            UUID.fromString("89179577-feb1-11e7-a9e6-e35467cb8f58"),
            "admin"
    );

    private static final User EXISTED_USER = new User();

    @BeforeAll
    static void beforeAll(){
        H2ConnectionManager.initH2ConnectionPool();

        EXISTED_USER.setId( UUID.fromString("1f940bd3-f7a5-11e7-93e6-a30f6152aa28"));
        EXISTED_USER.setLogin("user");
        EXISTED_USER.setPassword("1A1DC91C907325C69271DDF0C944BC72");
        EXISTED_USER.setEmail("email@gmail.com");
        EXISTED_USER.setRole(ADMIN_ROLE);
    }

    @AfterAll
    static void afterAll() {
        H2ConnectionManager.releaseDataSource();
    }

    @BeforeEach
    void setUp() {
        testDbManager.executeSQLScriptsFromFile(INSERT_FILE_PATH);
    }

    @AfterEach
    void tearDown() throws SQLException {
        testDbManager.truncateTable(USERS_TABLE_NAME);
        testDbManager.truncateTable(ROLES_TABLE_NAME);
    }

    /**
     * @see UsersJdbcDao#getUserByLogin(String)
     */
    @Test
    void getUserByLogin_existing_login_success() throws DaoException {
        User user = usersDao.getUserByLogin(EXISTED_USER.getLogin());
        assertNotNull(user);
        assertEquals(user.getLogin(), EXISTED_USER.getLogin());
    }

    @Test
    void getUserByLogin_not_existing_login_throws() {
        Assertions.assertThrows(DaoException.class, () -> {
            User user = usersDao.getUserByLogin(NOT_EXISTING_USER_LOGIN);
        });
    }
}