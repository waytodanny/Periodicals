package com.periodicals.services;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.UsersJdbcDao;
import com.periodicals.entities.User;
import com.periodicals.utils.exceptions.DaoException;
import com.periodicals.utils.encryption.MD5Cryptographer;
import org.apache.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * @author Daniel Volnitsky
 * <p>
 * Service responsible for obtaining and processing information about user login attempt
 */
public class LoginService {

    private static final Logger LOGGER = Logger.getLogger(LoginService.class.getSimpleName());

    private static final LoginService loginService = new LoginService();

    private static UsersJdbcDao usersDao = (UsersJdbcDao) JdbcDaoFactory.getInstance().getUsersDao();

    public static LoginService getInstance() {
        return loginService;
    }

    /**
     * @return user object from DB if it has been verified or null if not
     */
    public User getUserIfVerified(String login, String password) {
        User user = null;
        try {
            user = usersDao.getUserByLogin(login);
            if (Objects.nonNull(user)) {
                LOGGER.debug("Succeeded to find user by login: " + login);
                if (this.verifyUserPassword(user, password)) {
                    LOGGER.debug("User " + login + " has been verified");
                    return user;
                } else {
                    user = null;
                    throw new IllegalArgumentException("User with login " + login + " has not been verified");
                }
            } else {
                throw new NullPointerException("User with login " + login + " doesn't exist");
            }
        } catch (DaoException | NullPointerException | IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
        }
        return user;
    }

    /**
     * Checks whether given user has same password
     *
     * @param user     some user
     * @param password password that needed to be compared with users's one
     */
    private boolean verifyUserPassword(User user, String password) {
        boolean result = false;

        if (Objects.nonNull(password) && !password.isEmpty()) {
            String userPassword = user.getPassword();
            if (Objects.nonNull(userPassword) && !userPassword.isEmpty()) {
                try {
                    String encrypted = new MD5Cryptographer().encrypt(password);
                    result = Objects.nonNull(encrypted) && encrypted.equals(userPassword);
                } catch (NoSuchAlgorithmException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return result;
    }
}
