package com.periodicals.services.interfaces;

import java.util.List;

/**
 * @author Daniel Volnitsky
 * <p>
 * Interface for services that can be used only for
 * obtaining some information or objects
 * <p>
 * T - entity generic type
 * K - primary key generic type
 */
public interface LookupService<T, K> {
    /**
     * @return one entity of specified entity generic key
     */
    T getEntityByPrimaryKey(K key);

    /**
     * @return all entities of specified generic type
     */
    List<T> getEntityCollection();
}
