package com.periodicals.services.entities;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PeriodicalIssuesJdbcDao;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;
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
 * Service responsible for processing CRUD and specified seeking operations with PeriodicalIssue
 * @see PeriodicalIssue
 */
public class PeriodicalIssueService implements PageableCollectionService<PeriodicalIssue>, LookupService<PeriodicalIssue, UUID> {
    private static final Logger LOGGER = Logger.getLogger(PeriodicalIssueService.class.getSimpleName());

    private static final PeriodicalIssueService periodicalIssueService = new PeriodicalIssueService();
    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();
    private static PeriodicalIssuesJdbcDao periodicalIssuesDao =
            (PeriodicalIssuesJdbcDao) JdbcDaoFactory.getInstance().getPeriodicalIssuesDao();

    public static PeriodicalIssueService getInstance() {
        return periodicalIssueService;
    }

    @Override
    public PeriodicalIssue getEntityByPrimaryKey(UUID id) {
        PeriodicalIssue result = null;
        try {
            result = periodicalIssuesDao.getEntityByPrimaryKey(id);
            LOGGER.debug("Obtained periodical issue with id " + id);
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain periodical issue with id " + id + " due to: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<PeriodicalIssue> getEntityCollection() {
        List<PeriodicalIssue> entities = new ArrayList<>();
        try {
            entities = periodicalIssuesDao.getEntityCollection();
            LOGGER.debug("Obtained all periodical issues");
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain all periodical issues: " + e.getMessage());
        }
        return entities;
    }

    /**
     * Constructs and inserts PeriodicalIssue object by incoming params
     */
    public void createEntity(String name, int issueNo, UUID periodicalId) throws ServiceException {
        try {
            if (this.paramsAreValid(name, issueNo)) {
                PeriodicalIssue added = new PeriodicalIssue();
                UUID id = UUIDHelper.generateSequentialUuid();

                added.setId(id);
                added.setName(name);
                added.setIssueNo(issueNo);

                Periodical addedPeriodical = periodicalService.getEntityByPrimaryKey(periodicalId);
                if (Objects.nonNull(addedPeriodical)) {
                    added.setPeriodical(addedPeriodical);
                } else {
                    throw new NullPointerException("Periodical with id " + periodicalId + " doesn't exist");
                }

                periodicalIssuesDao.createEntity(added);
                LOGGER.debug("Periodical issue with id " + id + " has been successfully created");
            } else {
                throw new IllegalArgumentException("Invalid params passed");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * updates PeriodicalIssue object by incoming params
     */
    public void updateEntity(UUID id, String name, int issueNo) throws ServiceException {
        try {
            PeriodicalIssue updated = this.getEntityByPrimaryKey(id);
            if (Objects.nonNull(updated)) {
                if (this.paramsAreValid(name, issueNo)) {
                    updated.setName(name);
                    updated.setIssueNo(issueNo);
                    periodicalIssuesDao.updateEntity(updated);
                    LOGGER.debug("Periodical issue with id " + id + " has been successfully updated");
                } else {
                    throw new IllegalArgumentException("Invalid update params passed");
                }
            } else {
                throw new NullPointerException("Issue with id " + id + " doesn't exist");
            }
        } catch (Exception e) {
            LOGGER.error("Failed to update issue: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * deletes PeriodicalIssue object by incoming params
     */
    public void deleteEntity(UUID id) throws ServiceException {
        try {
            PeriodicalIssue deleted = this.getEntityByPrimaryKey(id);
            if (Objects.isNull(deleted)) {
                throw new NullPointerException("Periodical issue with id " + id + " doesn't exist");
            }
            periodicalIssuesDao.deleteEntity(deleted);
            LOGGER.debug("Periodical issue with id " + id + " has been successfully deleted");
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<PeriodicalIssue> getEntitiesListBounded(int skip, int limit) {
        List<PeriodicalIssue> entities = new ArrayList<>();
        try {
            entities = periodicalIssuesDao.getEntitiesListBounded(skip, limit);
            LOGGER.debug("Obtained periodical issues bounded list");
        } catch (DaoException e) {
            LOGGER.error("Failed to get periodical issues bounded list: " + e.getMessage());
        }
        return entities;
    }

    @Override
    public int getEntitiesCount() {
        int result = 0;
        try {
            result = periodicalIssuesDao.getEntitiesCount();
            LOGGER.debug("Obtained all periodical issues count");
        } catch (DaoException e) {
            LOGGER.error("Failed to get all periodical issues count: " + e.getMessage());
        }
        return result;
    }

    /**
     * @param periodicalId id of periodical which issues are to be obtained
     * @return PeriodicalIssue limited list of specified periodical
     */
    public List<PeriodicalIssue> getIssuesByPeriodicalListBounded(int skip, int limit, UUID periodicalId) {
        List<PeriodicalIssue> entities = new ArrayList<>();
        try {
            Periodical periodical = periodicalService.getEntityByPrimaryKey(periodicalId);
            if (Objects.nonNull(periodical)) {
                entities = periodicalIssuesDao.getIssuesByPeriodicalListBounded(skip, limit, periodical);
                LOGGER.debug("Obtained periodical issues bounded list with periodicalId " + periodicalId);
            } else {
                throw new NullPointerException("Periodical with id " + periodicalId + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        }
        return entities;
    }

    /**
     * @param periodicalId id of periodical which issues count is to be obtained
     * @return count of issues of specified periodical
     */
    public int getIssuesByPeriodicalCount(UUID periodicalId) {
        int result = 0;
        try {
            Periodical periodical = periodicalService.getEntityByPrimaryKey(periodicalId);
            if (Objects.nonNull(periodical)) {
                result = periodicalIssuesDao.getIssuesByPeriodicalCount(periodical);
                LOGGER.debug("Obtained periodical issues list count with periodicalId " + periodicalId);
            } else {
                throw new NullPointerException("Periodical with id " + periodicalId + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    private boolean paramsAreValid(String name, int issueNo) {
        boolean result = false;
        try {
            result = name.length() <= PeriodicalIssue.ISSUE_NAME_MAX_LENGTH &&
                    issueNo > 0;
        } catch (Exception e) {
            LOGGER.error("Attempt to create issue with wrong params: " + e.getMessage());
            result = false;
        }
        return result;
    }
}
