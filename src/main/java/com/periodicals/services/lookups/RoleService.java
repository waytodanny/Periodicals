package com.periodicals.services.lookups;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.RolesJdbcDao;
import com.periodicals.entities.Role;
import com.periodicals.exceptions.DaoException;
import com.periodicals.services.interfaces.LookupService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.AttributesHolder.ROLE_ADMIN_ID;
import static com.periodicals.utils.resourceHolders.AttributesHolder.ROLE_USER_ID;

/**
 * @author Daniel Volnitsky
 * Lookup service responsible for obtaining some role information or objects
 * @see Role
 */
public class RoleService implements LookupService<Role, UUID> {
    /**
     * Constants of common roles that could be helpful for classes
     * to check some role equality effectively,
     * without appealing to database
     */
    public static final UUID USER_ROLE_ID = UUID.fromString(ROLE_USER_ID);
    public static final UUID ADMIN_ROLE_ID = UUID.fromString(ROLE_ADMIN_ID);

    private static final Logger LOGGER = Logger.getLogger(RoleService.class.getSimpleName());

    private static RoleService roleService = new RoleService();
    private static RolesJdbcDao rolesDao =
            (RolesJdbcDao) JdbcDaoFactory.getInstance().getRolesDao();

    public static RoleService getInstance() {
        return roleService;
    }

    @Override
    public Role getEntityByPrimaryKey(UUID id) {
        Role result = null;
        try {
            result = rolesDao.getEntityByPrimaryKey(id);
            LOGGER.debug("Obtained role with id " + id);
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain role with id " + id + " due to: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Role> getEntityCollection() {
        List<Role> entities = new ArrayList<>();
        try {
            entities = rolesDao.getEntityCollection();
            LOGGER.debug("Obtained all roles");
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain all roles: " + e.getMessage());
        }
        return entities;
    }
}
