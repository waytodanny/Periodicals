package com.periodicals.dao.interfaces;

import com.periodicals.entities.User;
import com.periodicals.utils.exceptions.DaoException;

import java.util.UUID;

public interface UsersDao extends GenericDao<User, UUID> {
    User getUserByLogin(String login) throws DaoException;
}
