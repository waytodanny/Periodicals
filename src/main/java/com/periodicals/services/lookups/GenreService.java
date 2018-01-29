package com.periodicals.services.lookups;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.GenresJdbcDao;
import com.periodicals.entities.Genre;
import com.periodicals.exceptions.DaoException;
import com.periodicals.services.interfaces.LookupService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Daniel Volnitsky
 * Lookup service responsible for obtaining some genre information or objects
 *
 * @see Genre
 */
public class GenreService implements LookupService<Genre, UUID> {
    private static final Logger LOGGER = Logger.getLogger(GenreService.class.getSimpleName());

    private static final GenreService genreService = new GenreService();
    private static final GenresJdbcDao genresDao =
            (GenresJdbcDao) JdbcDaoFactory.getInstance().getGenresDao();

    public static GenreService getInstance() {
        return genreService;
    }

    @Override
    public Genre getEntityByPrimaryKey(UUID id) {
        Genre result = null;
        try {
            result = genresDao.getEntityByPrimaryKey(id);
            LOGGER.debug("Obtained genre with id " + id);
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain genre with id " + id + " due to: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Genre> getEntityCollection() {
        List<Genre> entities = new ArrayList<>();
        try {
            entities = genresDao.getEntityCollection();
            LOGGER.debug("Obtained all genres");
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain all genres: " + e.getMessage());
        }
        return entities;
    }
}
