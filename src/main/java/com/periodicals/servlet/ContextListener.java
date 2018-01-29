package com.periodicals.servlet;

import com.periodicals.dao.connection.ConnectionPool;
import com.periodicals.entities.Genre;
import com.periodicals.services.entities.PublisherService;
import com.periodicals.services.lookups.GenreService;
import com.periodicals.services.lookups.RoleService;
import com.periodicals.utils.resourceHolders.AttributesHolder;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Initializes some components before app starts:
 *
 * @see this.contextInitialized
 * <p>
 * Does something before it closes:
 * @see this.contextDestroyed
 */
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ConnectionPool.initByRootDataSource();

        GenreService genreService = GenreService.getInstance();
        servletContextEvent.getServletContext().setAttribute("genres", genreService.getEntityCollection());

        PublisherService publisherService = PublisherService.getInstance();
        servletContextEvent.getServletContext().setAttribute("publishers", publisherService.getEntityCollection());

        RoleService roleService = RoleService.getInstance();
        servletContextEvent.getServletContext().setAttribute("roles", roleService.getEntityCollection());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool.releaseDataSource();
    }
}
