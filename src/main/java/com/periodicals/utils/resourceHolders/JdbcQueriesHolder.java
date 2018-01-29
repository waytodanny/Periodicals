package com.periodicals.utils.resourceHolders;

import com.periodicals.utils.propertyManagers.JdbcQueriesPropertyManager;

public class JdbcQueriesHolder {

    /*params*/
    public static final String COUNT_PARAM = JdbcQueriesPropertyManager.getProperty("param.count");

    /*genres table*/
    public static final String GENRE_INSERT = JdbcQueriesPropertyManager.getProperty("genre.insert");
    public static final String GENRE_UPDATE = JdbcQueriesPropertyManager.getProperty("genre.update");
    public static final String GENRE_DELETE = JdbcQueriesPropertyManager.getProperty("genre.delete");
    public static final String GENRE_SELECT_ALL = JdbcQueriesPropertyManager.getProperty("genre.select.all");
    public static final String GENRE_ENTRIES_COUNT = JdbcQueriesPropertyManager.getProperty("genre.entries.count");

    /*role table*/
    public static final String ROLE_INSERT = JdbcQueriesPropertyManager.getProperty("role.insert");
    public static final String ROLE_UPDATE = JdbcQueriesPropertyManager.getProperty("role.update");
    public static final String ROLE_DELETE = JdbcQueriesPropertyManager.getProperty("role.delete");
    public static final String ROLE_SELECT_ALL = JdbcQueriesPropertyManager.getProperty("role.select.all");
    public static final String ROLE_ENTRIES_COUNT = JdbcQueriesPropertyManager.getProperty("role.entries.count");

    /*publishers table*/
    public static final String PUBLISHER_INSERT = JdbcQueriesPropertyManager.getProperty("publisher.insert");
    public static final String PUBLISHER_UPDATE = JdbcQueriesPropertyManager.getProperty("publisher.update");
    public static final String PUBLISHER_DELETE = JdbcQueriesPropertyManager.getProperty("publisher.delete");
    public static final String PUBLISHER_SELECT_ALL = JdbcQueriesPropertyManager.getProperty("publisher.select.all");
    public static final String PUBLISHER_ENTRIES_COUNT = JdbcQueriesPropertyManager.getProperty("publisher.entries.count");

    /*user table*/
    public static final String USER_INSERT = JdbcQueriesPropertyManager.getProperty("user.insert");
    public static final String USER_UPDATE = JdbcQueriesPropertyManager.getProperty("user.update");
    public static final String USER_DELETE = JdbcQueriesPropertyManager.getProperty("user.delete");
    public static final String USER_SELECT_ALL = JdbcQueriesPropertyManager.getProperty("user.select.all");
    public static final String USER_ENTRIES_COUNT = JdbcQueriesPropertyManager.getProperty("user.entries.count");

    /*periodicals table*/
    public static final String PERIODICAL_INSERT = JdbcQueriesPropertyManager.getProperty("periodical.insert");
    public static final String PERIODICAL_UPDATE = JdbcQueriesPropertyManager.getProperty("periodical.update");
    public static final String PERIODICAL_DELETE = JdbcQueriesPropertyManager.getProperty("periodical.delete");
    public static final String PERIODICAL_SELECT_ALL = JdbcQueriesPropertyManager.getProperty("periodical.select.all");
    public static final String PERIODICAL_ENTRIES_COUNT = JdbcQueriesPropertyManager.getProperty("periodical.entries.count");
    public static final String PERIODICAL_IS_USER_SUBSCRIBED = JdbcQueriesPropertyManager.getProperty("periodical.subscriptions.is.user.subscribed");
    public static final String PERIODICAL_ADD_USER_SUBSCRIPTION = JdbcQueriesPropertyManager.getProperty("periodical.subscriptions.insert");
    public static final String PERIODICAL_USER_SUBSCRIPTIONS_COUNT = JdbcQueriesPropertyManager.getProperty("periodical.subscriptions.user.count");

    /*payments table*/
    public static final String PAYMENT_INSERT = JdbcQueriesPropertyManager.getProperty("payments.insert");
    public static final String PAYMENT_UPDATE = JdbcQueriesPropertyManager.getProperty("payments.update");
    public static final String PAYMENT_DELETE = JdbcQueriesPropertyManager.getProperty("payments.delete");
    public static final String PAYMENT_SELECT_ALL = JdbcQueriesPropertyManager.getProperty("payments.select.all");
    public static final String PAYMENT_ENTRIES_COUNT = JdbcQueriesPropertyManager.getProperty("payments.entries.count");
    public static final String PAYMENT_INSERT_PAYMENT_PERIODICALS = JdbcQueriesPropertyManager.getProperty("payments.insert.payment.periodicals");
    public static final String PAYMENT_DELETE_PAYMENT_PERIODICALS= JdbcQueriesPropertyManager.getProperty("payments.delete.payment.periodicals");

    /*periodical_issues table*/
    public static final String PERIODICAL_ISSUE_INSERT = JdbcQueriesPropertyManager.getProperty("periodical_issues.insert");
    public static final String PERIODICAL_ISSUE_UPDATE = JdbcQueriesPropertyManager.getProperty("periodical_issues.update");
    public static final String PERIODICAL_ISSUE_DELETE = JdbcQueriesPropertyManager.getProperty("periodical_issues.delete");
    public static final String PERIODICAL_ISSUE_SELECT_ALL = JdbcQueriesPropertyManager.getProperty("periodical_issues.select.all");
    public static final String PERIODICAL_ISSUE_ENTRIES_COUNT = JdbcQueriesPropertyManager.getProperty("periodical_issues.entries.count");
}
