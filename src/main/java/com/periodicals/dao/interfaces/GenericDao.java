package com.periodicals.dao.interfaces;

import com.periodicals.utils.exceptions.DaoException;

import java.util.List;

/**
 * @author Daniel Volnitsky
 * Dao pattern interface for common dao implementors
 * T - type of object implementor dao working with
 * K - primary key type
 */
public interface GenericDao<T, K> {
    /**
     * Insert operation
     */
    void createEntity(T entity) throws DaoException;

    /**
     * Update operation
     */
    void updateEntity(T entity) throws DaoException;

    /**
     * Delete operation
     */
    void deleteEntity(T entity) throws DaoException;

    /**
     * Select operation
     */
    T getEntityByPrimaryKey(K key) throws DaoException;

    /**
     * @return all objects of specified generic type
     */
    List<T> getEntityCollection() throws DaoException;

    /**
     * @return limited objects list of specified generic type
     */
    List<T> getEntitiesListBounded(int skip, int limit) throws DaoException;

    /**
     * @return count of entities of specified generic type
     */
    int getEntitiesCount() throws DaoException;
}
