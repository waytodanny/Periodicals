package com.periodicals.services.entities;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.UsersJdbcDao;
import com.periodicals.entities.Role;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.services.interfaces.LookupService;
import com.periodicals.services.interfaces.PageableCollectionService;
import com.periodicals.services.lookups.RoleService;
import com.periodicals.utils.encryption.Cryptographer;
import com.periodicals.utils.encryption.MD5Cryptographer;
import com.periodicals.utils.uuid.UUIDHelper;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Daniel Volnitsky
 * <p>
 * Service responsible for processing CRUD and specified seeking operations with Users
 * @see User
 */
public class UserService implements PageableCollectionService<User>, LookupService<User, UUID> {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getSimpleName());

    private static final UserService userService = new UserService();
    private static final RoleService roleService = RoleService.getInstance();

    private static final UsersJdbcDao usersDao =
            (UsersJdbcDao) JdbcDaoFactory.getInstance().getUsersDao();

    public static UserService getInstance() {
        return userService;
    }

    @Override
    public User getEntityByPrimaryKey(UUID id) {
        User result = null;
        try {
            result = usersDao.getEntityByPrimaryKey(id);
            LOGGER.debug("Obtained user with id " + id);
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain user with id " + id + " due to: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<User> getEntityCollection() {
        List<User> entities = new ArrayList<>();
        try {
            entities = usersDao.getEntityCollection();
            LOGGER.debug("Obtained all users");
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain all users: " + e.getMessage());
        }
        return entities;
    }

    /**
     * Constructs and inserts Periodical object by incoming params
     */
    public void createEntity(String login, String password, String email, UUID roleId) throws ServiceException {
        try {
            if (this.paramsAreValid(login, password, email)) {
                User added = new User();
                UUID id = UUIDHelper.generateSequentialUuid();

                added.setId(id);
                added.setLogin(login);
                added.setEmail(email);

                Cryptographer cryptographer = new MD5Cryptographer();
                added.setPassword(cryptographer.encrypt(password));

                Role addedRole = roleService.getEntityByPrimaryKey(roleId);
                if (Objects.nonNull(addedRole)) {
                    added.setRole(addedRole);
                } else {
                    throw new NullPointerException("Role with id " + roleId + " doesn't exist");
                }
                usersDao.createEntity(added);
                LOGGER.debug("User with id " + id + " has been successfully created");
            } else {
                throw new IllegalArgumentException("Invalid params were being passed");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * updates Periodical object by incoming params
     */
    public void updateEntity(UUID id, UUID roleId) throws ServiceException {
        try {
            User updated = this.getEntityByPrimaryKey(id);
            if (Objects.nonNull(updated)) {
                Role updatedRole = roleService.getEntityByPrimaryKey(roleId);
                if (Objects.nonNull(updatedRole)) {
                    updated.setRole(updatedRole);
                } else {
                    throw new NullPointerException("Role with id " + roleId + " doesn't exist");
                }
                usersDao.updateEntity(updated);
                LOGGER.debug("User with id " + id + " has been successfully updated");
            } else {
                throw new NullPointerException("User with id " + id + " doesn't exist");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * deletes Periodical object by incoming params
     */
    public void deleteEntity(UUID id) throws ServiceException {
        try {
            User deleted = this.getEntityByPrimaryKey(id);
            if (Objects.isNull(deleted)) {
                throw new NullPointerException("User with id " + id + " doesn't exist");
            }
            usersDao.deleteEntity(deleted);
            LOGGER.debug("User with id " + id + " has been successfully deleted");
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getEntitiesListBounded(int skip, int limit) {
        List<User> entities = new ArrayList<>();
        try {
            entities = usersDao.getEntitiesListBounded(skip, limit);
            LOGGER.debug("Obtained users bounded list");
        } catch (DaoException e) {
            LOGGER.error("Failed to get users bounded list: " + e.getMessage());
        }
        return entities;
    }

    @Override
    public int getEntitiesCount() {
        int result = 0;
        try {
            result = usersDao.getEntitiesCount();
            LOGGER.debug("Obtained all users count");
        } catch (DaoException e) {
            LOGGER.error("Failed to get all users count: " + e.getMessage());
        }
        return result;
    }

    private boolean paramsAreValid(String login, String password, String email) {
        boolean result = false;
        try {
            result = login.length() <= User.MAX_LOGIN_LENGTH &&
                    Objects.nonNull(password) && !password.isEmpty() &&
                    email.length() <= User.EMAIL_MAX_LENGTH;
        } catch (Exception e) {
            LOGGER.error("Attempt to create publisher with wrong params: " + e.getMessage());
            result = false;
        }
        return result;
    }
}
