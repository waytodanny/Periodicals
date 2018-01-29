package com.periodicals.commands.common;

import com.mysql.cj.api.Session;
import com.periodicals.commands.util.Command;
import com.periodicals.commands.util.CommandResult;
import com.periodicals.commands.util.RedirectType;
import com.periodicals.entities.Role;
import com.periodicals.entities.User;
import com.periodicals.services.LoginService;
import com.periodicals.utils.resourceHolders.AttributesHolder;
import com.periodicals.utils.resourceHolders.PagesHolder;
import org.junit.jupiter.api.*;
import util.H2ConnectionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.AttributesHolder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginCommandTest {

    private static final H2ConnectionManager testDbManager = H2ConnectionManager.getInstance();

    private static final String INSERT_DEFAULTS_FILE_PATH =
            "src\\test\\resources\\users_Insert_Defaults.sql";

    private static final String ADMIN_DEFAULT_PAGE = PagesHolder.ADMIN_CATALOG_EDIT_PAGE;
    private static final CommandResult EXPECTED_ADMIN_COMMAND_RESULT = new CommandResult(
            RedirectType.REDIRECT,
            ADMIN_DEFAULT_PAGE
    );

    private static final String CORRECT_LOGIN = "user";
    private static final String CORRECT_PASS = "pass";

    private static CommandResult commandResult;

    @BeforeAll
    static void beforeAll() {
        H2ConnectionManager.initH2ConnectionPool();
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
        commandResult = null;
        testDbManager.truncateTable(USERS_TABLE_NAME);
        testDbManager.truncateTable(ROLES_TABLE_NAME);
    }

    /**
     * @see LoginCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Test
    void execute_admin_credentials_return_admin_page_redirect() {
        HttpSession mockSession = mock(HttpSession.class);
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        when(mockRequest.getMethod()).thenReturn(POST);
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockRequest.getParameter(ATTR_LOGIN)).thenReturn(CORRECT_LOGIN);
        when(mockRequest.getParameter(ATTR_PASSWORD)).thenReturn(CORRECT_PASS);

        commandResult = new LoginCommand().execute(mockRequest, mockResponse);

        assertNotNull(commandResult);
        assertEquals(EXPECTED_ADMIN_COMMAND_RESULT, commandResult);
    }
}