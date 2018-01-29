package com.periodicals.services.interfaces;

import java.util.List;

/**
 * @author Daniel Volnitsky
 * <p>
 * Interface for services that have to provide pagination info to views (jsp-pages etc.)
 * <p>
 * T - entity generic type
 */
public interface PageableCollectionService<T> {
    /**
     * @return count of pagination needed entities
     */
    int getEntitiesCount();

    /**
     * @return limited list of pagination needed entities
     */
    List<T> getEntitiesListBounded(int skip, int limit);
}
