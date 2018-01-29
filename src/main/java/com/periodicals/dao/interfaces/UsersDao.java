package com.periodicals.dao.interfaces;

import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;

import java.util.List;
import java.util.UUID;

public interface UsersDao extends GenericDao<User, UUID> {
    User getUserByLogin(String login) throws DaoException;
}
