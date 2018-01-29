package com.periodicals.services;

import com.periodicals.entities.Role;
import com.periodicals.entities.User;
import com.periodicals.services.entities.UserService;
import org.junit.jupiter.api.*;
import util.H2ConnectionManager;

import java.sql.SQLException;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.AttributesHolder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class LoginServiceTest {
    private static final H2ConnectionManager testDbManager = H2ConnectionManager.getInstance();

    private static final LoginService loginService = LoginService.getInstance();

    private static final String INSERT_DEFAULTS_FILE_PATH =
            "src\\test\\resources\\users_Insert_Defaults.sql";

    private static final String WRONG_LOGIN = "unknown login";
    private static final String WRONG_PASS = "unknown pass";

    private static final Role ADMIN_ROLE = new Role(
            UUID.fromString("89179577-feb1-11e7-a9e6-e35467cb8f58"),
            "admin"
    );
    private static final String EXISTING_USER_DECRYPTED_PASS = "pass";
    private static final User EXISTING_USER = new User();

    @BeforeAll
    static void beforeAll() {
        H2ConnectionManager.initH2ConnectionPool();

        EXISTING_USER.setId(UUID.fromString("1f940bd3-f7a5-11e7-93e6-a30f6152aa28"));
        EXISTING_USER.setLogin("user");
        EXISTING_USER.setPassword("1A1DC91C907325C69271DDF0C944BC72");
        EXISTING_USER.setEmail("email@gmail.com");
        EXISTING_USER.setRole(ADMIN_ROLE);
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
        testDbManager.truncateTable(USERS_TABLE_NAME);
        testDbManager.truncateTable(ROLES_TABLE_NAME);
    }


    /**
     * @see LoginService#getUserIfVerified(String, String)
     */
    @Test
    void getUserIfVerified_get_existing_user_valid_pasword_return() {
        User user = loginService.getUserIfVerified(
                EXISTING_USER.getLogin(),
                EXISTING_USER_DECRYPTED_PASS
        );
        assertNotNull(user);
        assertEquals(EXISTING_USER, user);
    }

    @Test
    void getUserIfVerified_get_existing_user_invalid_pasword_return_null() {
        User user = loginService.getUserIfVerified(
                EXISTING_USER.getLogin(),
                WRONG_PASS
        );
        assertNull(user);
    }

    @Test
    void getUserIfVerified_get_not_existing_user_return_null() {
        User user = loginService.getUserIfVerified(
                WRONG_LOGIN,
                WRONG_PASS
        );
        assertNull(user);
    }
}