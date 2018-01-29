package com.periodicals.dao.jdbc;

import com.periodicals.dao.connection.ConnectionManager;
import com.periodicals.dao.connection.ConnectionWrapper;
import com.periodicals.dao.interfaces.PaymentsDao;
import com.periodicals.entities.Payment;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.Role;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;
import com.periodicals.utils.propertyManagers.AttributesPropertyManager;
import com.periodicals.utils.resourceHolders.JdbcQueriesHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PaymentsJdbcDao extends AbstractJdbcDao<Payment, UUID> implements PaymentsDao {

    @Override
    public void createEntity(Payment entity) throws DaoException {
        super.insert(Query.INSERT.text, getInsertObjectParams(entity));
    }

    @Override
    public void updateEntity(Payment entity) throws DaoException {
        super.update(Query.UPDATE.text, getObjectUpdateParams(entity));
    }

    @Override
    public void deleteEntity(Payment entity) throws DaoException {
        super.delete(Query.DELETE.text, entity.getId().toString());
    }

    @Override
    public Payment getEntityByPrimaryKey(UUID key) throws DaoException {
        return super.selectObject(Query.SELECT_BY_ID.text, key.toString());
    }

    @Override
    public List<Payment> getEntityCollection() throws DaoException {
        return super.selectObjects(Query.SELECT.text);
    }

    @Override
    public List<Payment> getEntitiesListBounded(int skip, int limit) throws DaoException {
        return super.selectObjects(Query.SELECT_LIMITED_LIST.text, skip, limit);
    }

    @Override
    public int getEntitiesCount() throws DaoException {
        return super.getEntriesCount(Query.SELECT_ENTRIES_COUNT.text);
    }

    @Override
    public List<Payment> getPaymentsByUserListBounded(int skip, int limit, User user) throws DaoException {
        return super.selectObjects(
                Query.SELECT_USER_PAYMENTS_LIMITED_LIST.text,
                user.getId().toString(),
                skip,
                limit
        );
    }

    @Override
    public int getPaymentsByUserCount(User user) throws DaoException {
        return super.getEntriesCount(
                Query.SELECT_USER_PAYMENTS_COUNT.text,
                user.getId().toString()
        );
    }

    @Override
    public void deletePaymentPeriodicals(Payment payment) throws DaoException {
        super.delete(Query.DELETE_PAYMENT_PERIODICALS.text, payment.getId());
    }

    /*TODO think of how to make generic*/
    @Override
    public void addPaymentPeriodicals(Payment payment) throws DaoException {
        if (Objects.isNull(payment) ||
                Objects.isNull(payment.getPeriodicals()) ||
                (payment.getPeriodicals().size() < 1)) {
            throw new DaoException("Attempt to addNewIssue nullable payment periodicals or payment without id");
        }

        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(Query.INSERT_PAYMENT_PERIODICALS.text)) {

            String paymentId = payment.getId().toString();
            for (Periodical subscription : payment.getPeriodicals()) {
                stmt.setString(1, paymentId);
                stmt.setString(2, subscription.getId().toString());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected Object[] getInsertObjectParams(Payment payment) {
        return new Object[]{
                payment.getId().toString(),
                payment.getPaymentSum(),
                payment.getUser().getId().toString()
        };
    }

    @Override
    protected Object[] getObjectUpdateParams(Payment payment) {
        return new Object[]{
                payment.getPaymentSum(),
                payment.getUser().getId().toString(),
                payment.getId().toString()
        };
    }

    @Override
    protected List<Payment> parseResultSet(ResultSet rs) throws DaoException {
        List<Payment> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Payment payment = new Payment();
                payment.setId(UUID.fromString(rs.getString(Param.ID.name)));
                payment.setPaymentTime(rs.getTimestamp(Param.TIME.name));
                payment.setPaymentSum(rs.getBigDecimal(Param.SUM.name));

                User user = new User();
                user.setId(UUID.fromString(rs.getString(Param.USER_ID.name)));
                user.setLogin(rs.getString(Param.USER_LOGIN.name));
                user.setPassword(rs.getString(Param.USER_PASSWORD.name));
                user.setEmail(rs.getString(Param.USER_EMAIL.name));

                user.setRole(new Role(
                        UUID.fromString(rs.getString(Param.USER_ROLE_ID.name)),
                        rs.getString(Param.USER_ROLE_NAME.name)
                ));

                payment.setUser(user);

                result.add(payment);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return result;
    }

    private enum Query {
        INSERT(JdbcQueriesHolder.PAYMENT_INSERT),
        UPDATE(JdbcQueriesHolder.PAYMENT_UPDATE),
        DELETE(JdbcQueriesHolder.PAYMENT_DELETE),
        SELECT(JdbcQueriesHolder.PAYMENT_SELECT_ALL),
        SELECT_BY_ID(SELECT.text + " WHERE id = ? LIMIT 1;"),
        SELECT_LIMITED_LIST(SELECT.text + " LIMIT ?,?;"),
        SELECT_ENTRIES_COUNT(JdbcQueriesHolder.PAYMENT_ENTRIES_COUNT),

        SELECT_USER_PAYMENTS_LIMITED_LIST(SELECT.text + " WHERE user_id = ? LIMIT ?,?;"),
        SELECT_USER_PAYMENTS_COUNT(SELECT_ENTRIES_COUNT.text + " WHERE user_id = ?;"),

        DELETE_PAYMENT_PERIODICALS(JdbcQueriesHolder.PAYMENT_DELETE_PAYMENT_PERIODICALS),
        INSERT_PAYMENT_PERIODICALS(JdbcQueriesHolder.PAYMENT_INSERT_PAYMENT_PERIODICALS);

        private String text;

        Query(String text) {
            this.text = text;
        }
    }

    /**
     * Names of payment object parameters that are coming from result set in select queries
     */
    private enum Param {
        ID(AttributesPropertyManager.getProperty("payment.id")),
        TIME(AttributesPropertyManager.getProperty("payment.time")),
        SUM(AttributesPropertyManager.getProperty("payment.sum")),

        USER_ID(AttributesPropertyManager.getProperty("payment.user_id")),
        USER_LOGIN(AttributesPropertyManager.getProperty("payment.user_login")),
        USER_PASSWORD(AttributesPropertyManager.getProperty("payment.user_password")),
        USER_EMAIL(AttributesPropertyManager.getProperty("payment.user_email")),
        USER_ROLE_ID(AttributesPropertyManager.getProperty("payment.user_role_id")),
        USER_ROLE_NAME(AttributesPropertyManager.getProperty("payment.user_role_name"));

        private String name;

        Param(String name) {
            this.name = name;
        }
    }
}
