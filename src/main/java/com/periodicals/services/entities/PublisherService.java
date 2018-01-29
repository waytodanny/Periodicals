package com.periodicals.services.entities;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PublishersJdbcDao;
import com.periodicals.entities.Publisher;
import com.periodicals.utils.exceptions.DaoException;
import com.periodicals.utils.exceptions.ServiceException;
import com.periodicals.services.interfaces.LookupService;
import com.periodicals.services.interfaces.PageableCollectionService;
import com.periodicals.utils.uuid.UUIDHelper;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Daniel Volnitsky
 * <p>
 * Service responsible for processing CRUD and specified seeking operations with Publishers
 * @see Publisher
 */
public class PublisherService implements PageableCollectionService<Publisher>, LookupService<Publisher, UUID> {
    private static final Logger LOGGER = Logger.getLogger(PublisherService.class.getSimpleName());

    private static final PublisherService publisherService = new PublisherService();
    private static final PublishersJdbcDao publishersDao =
            (PublishersJdbcDao) JdbcDaoFactory.getInstance().getPublishersDao();

    public static PublisherService getInstance() {
        return publisherService;
    }

    @Override
    public Publisher getEntityByPrimaryKey(UUID id) {
        Publisher result = null;
        try {
            result = publishersDao.getEntityByPrimaryKey(id);
            LOGGER.debug("Obtained publisher with id " + id);
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain publisher with id " + id + " due to: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Publisher> getEntityCollection() {
        List<Publisher> entities = new ArrayList<>();
        try {
            entities = publishersDao.getEntityCollection();
            LOGGER.debug("Obtained all publishers");
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain all publishers: " + e.getMessage());
        }
        return entities;
    }

    /**
     * Constructs and inserts Periodical object by incoming params
     */
    public void createEntity(String name) throws ServiceException {
        try {
            Publisher added = new Publisher();
            if (paramsAreValid(name)) {
                UUID id = UUIDHelper.generateSequentialUuid();

                added.setId(id);
                added.setName(name);
                publishersDao.createEntity(added);
                LOGGER.debug("Publisher with id " + id + " has been successfully created");
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
    public void updateEntity(UUID id, String name) throws ServiceException {
        try {
            if (paramsAreValid(name)) {
                Publisher updated = this.getEntityByPrimaryKey(id);
                if (Objects.nonNull(updated)) {
                    updated.setName(name);
                    publishersDao.updateEntity(updated);
                    LOGGER.debug("Publisher with id " + id + " has been successfully updated");
                } else {
                    throw new NullPointerException("Publisher with id " + id + " doesn't exist");
                }
            } else {
                throw new IllegalArgumentException("Invalid params were being passed");
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
            Publisher deleted = this.getEntityByPrimaryKey(id);
            if (Objects.isNull(deleted)) {
                throw new NullPointerException("Publisher with id " + id + " doesn't exist");
            }
            publishersDao.deleteEntity(deleted);
            LOGGER.debug("Publisher with id " + id + " has been successfully deleted");
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Publisher> getEntitiesListBounded(int skip, int limit) {
        List<Publisher> entities = new ArrayList<>();
        try {
            entities = publishersDao.getEntitiesListBounded(skip, limit);
            LOGGER.debug("Obtained publishers bounded list");
        } catch (DaoException e) {
            LOGGER.error("Failed to get publishers bounded list: " + e.getMessage());
        }
        return entities;
    }

    @Override
    public int getEntitiesCount() {
        int result = 0;
        try {
            result = publishersDao.getEntitiesCount();
            LOGGER.debug("Obtained all publishers count");
        } catch (DaoException e) {
            LOGGER.error("Failed to get all publishers count: " + e.getMessage());
        }
        return result;
    }

    private boolean paramsAreValid(String name) {
        boolean result = false;
        try {
            result = name.length() <= Publisher.PUBLISHER_NAME_MAX_LENGTH;
        } catch (Exception e) {
            LOGGER.error("Attempt to create publisher with wrong params: " + e.getMessage());
            result = false;
        }
        return result;
    }
}
