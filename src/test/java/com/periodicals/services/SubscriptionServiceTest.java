package com.periodicals.services;

import com.periodicals.entities.*;
import com.periodicals.services.entities.PeriodicalService;
import com.periodicals.services.entities.UserService;
import org.junit.jupiter.api.*;
import util.H2ConnectionManager;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.AttributesHolder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SubscriptionServiceTest {
    private static final H2ConnectionManager testDbManager = H2ConnectionManager.getInstance();
    private static final PeriodicalService mockPeriodicalService = mock(PeriodicalService.class);
    private static final String INSERT_DEFAULTS_FILE_PATH =
            "src\\test\\resources\\mySQL_Insert_Defaults1.sql";

    private static final Role ADMIN_ROLE = new Role(
            UUID.fromString("89179577-feb1-11e7-a9e6-e35467cb8f58"),
            "admin"
    );
    private static final User EXISTING_USER = new User();

    private static final Genre GENRE_COMICS = new Genre(
            UUID.fromString("c1fae08d-feb1-11e7-8e6b-313d4bf1847c"),
            "comics"
    );
    private static final Publisher PUBLISHER_MARVEL = new Publisher(
            UUID.fromString("fc13ecc9-feb1-11e7-8a29-d5a2d48a37f0"),
            "Marvel"
    );
    private static final Periodical PERIODICAL_GAMBIT = new Periodical();
    private static final Periodical PERIODICAL_WOLVERINE = new Periodical();

    private static UserService mockUserService = mock(UserService.class);

    private static SubscriptionService subscriptionService;

    @BeforeAll
    static void beforeAll() {
        H2ConnectionManager.initH2ConnectionPool();

        EXISTING_USER.setId(UUID.fromString("1f940bd3-f7a5-11e7-93e6-a30f6152aa28"));
        EXISTING_USER.setLogin("user");
        EXISTING_USER.setPassword("pass");
        EXISTING_USER.setEmail("email@gmail.com");
        EXISTING_USER.setRole(ADMIN_ROLE);

        PERIODICAL_GAMBIT.setId(UUID.fromString("426f8ce0-feb2-11e7-bc2e-bd4c345931c8"));
        PERIODICAL_GAMBIT.setName("gambit");
        PERIODICAL_GAMBIT.setDescription("");
        PERIODICAL_GAMBIT.setSubscriptionCost(new BigDecimal("12.50"));
        PERIODICAL_GAMBIT.setIssuesPerYear(24);
        PERIODICAL_GAMBIT.setLimited(true);
        PERIODICAL_GAMBIT.setGenre(GENRE_COMICS);
        PERIODICAL_GAMBIT.setPublisher(PUBLISHER_MARVEL);

        PERIODICAL_WOLVERINE.setId(UUID.fromString("46b26700-feb2-11e7-b445-f3b95d28ec2b"));
        PERIODICAL_WOLVERINE.setName("wolverine");
        PERIODICAL_WOLVERINE.setDescription("");
        PERIODICAL_WOLVERINE.setSubscriptionCost(new BigDecimal("10.50"));
        PERIODICAL_WOLVERINE.setIssuesPerYear(24);
        PERIODICAL_WOLVERINE.setLimited(true);
        PERIODICAL_WOLVERINE.setGenre(GENRE_COMICS);
        PERIODICAL_WOLVERINE.setPublisher(PUBLISHER_MARVEL);
    }

    @AfterAll
    static void afterAll() {
        H2ConnectionManager.releaseDataSource();
        subscriptionService = null;
    }

    @BeforeEach
    void setUp() {
        subscriptionService = SubscriptionService.getInstance();
        testDbManager.executeSQLScriptsFromFile(INSERT_DEFAULTS_FILE_PATH);
    }

    @AfterEach
    void tearDown() throws SQLException {
        testDbManager.truncateTable(SUBSCRIPTIONS_TABLE_NAME);
        testDbManager.truncateTable(ISSUES_TABLE_NAME);
        testDbManager.truncateTable(PERIODICALS_TABLE_NAME);
        testDbManager.truncateTable(USERS_TABLE_NAME);
        testDbManager.truncateTable(ROLES_TABLE_NAME);
        testDbManager.truncateTable(GENRES_TABLE_NAME);
        testDbManager.truncateTable(PUBLISHERS_TABLE_NAME);
    }

    /**
     * @see SubscriptionService#getPeriodicalsByUserList(UUID)
     */
    @Test
    void getPeriodicalsByUserList_get_by_existing_user_return_2() {
        when(mockUserService.getEntityByPrimaryKey(EXISTING_USER.getId())).thenReturn(EXISTING_USER);

        subscriptionService = new SubscriptionService(mockUserService, null);

        List<Periodical> subscriptions =
                subscriptionService.getPeriodicalsByUserList(EXISTING_USER.getId());

        final int expectedSize = 2;
        assertEquals(expectedSize, subscriptions.size());
        assertTrue(subscriptions.contains(PERIODICAL_GAMBIT));
        assertTrue(subscriptions.contains(PERIODICAL_WOLVERINE));
    }

    /**
     * @see SubscriptionService#getPeriodicalsByUserCount(UUID)
     */
    @Test
    void getPeriodicalsByUserCount_get_by_existing_user_return_2() {
        when(mockUserService.getEntityByPrimaryKey(EXISTING_USER.getId())).thenReturn(EXISTING_USER);

        subscriptionService = new SubscriptionService(mockUserService, null);

        List<Periodical> subscriptions =
                subscriptionService.getPeriodicalsByUserList(EXISTING_USER.getId());

        final int expectedSize = 2;
        assertEquals(expectedSize, subscriptions.size());
    }

    /**
     * @see SubscriptionService#getPeriodicalsByUserListBounded(int, int, UUID)
     */
    @Test
    void getPeriodicalsByUserListBounded_skip1_take1_return_1() {
        when(mockUserService.getEntityByPrimaryKey(EXISTING_USER.getId())).thenReturn(EXISTING_USER);

        subscriptionService = new SubscriptionService(mockUserService, null);

        final int skip = 1;
        final int take = 1;
        List<Periodical> subscriptions =
                subscriptionService.getPeriodicalsByUserListBounded(skip, take, EXISTING_USER.getId());

        final int expectedSize = 1;
        assertEquals(expectedSize, subscriptions.size());
        assertTrue(subscriptions.contains(PERIODICAL_WOLVERINE));
    }

    /**
     * @see SubscriptionService#getIsUserSubscribedForPeriodical(UUID, UUID)
     */
    @Test
    void getIsUserSubscribedForPeriodical_existing_user_for_wolverine_return_true() {
        when(mockUserService.getEntityByPrimaryKey(EXISTING_USER.getId())).thenReturn(EXISTING_USER);
        when(mockPeriodicalService.getEntityByPrimaryKey(PERIODICAL_WOLVERINE.getId())).thenReturn(PERIODICAL_WOLVERINE);

        subscriptionService = new SubscriptionService(mockUserService, mockPeriodicalService);

        final boolean expected = true;
        boolean result = subscriptionService.getIsUserSubscribedForPeriodical(
                EXISTING_USER.getId(),
                PERIODICAL_WOLVERINE.getId()
        );
        assertEquals(expected, result);
    }
}