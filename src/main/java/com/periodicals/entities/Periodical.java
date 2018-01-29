package com.periodicals.entities;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Daniel Volnitsky
 * <p>
 * Class that represents periodical of some genre published by some publisher
 * @see Genre
 * @see Publisher
 */
public class Periodical {
    public static final int PERIODICAL_NAME_MAX_LENGTH = 100;
    public static final int PERIODICAL_DESCRIPTION_MAX_LENGTH = 1000;
    public static final int SUBSCRIPTION_COST_SCALE = 2;

    private UUID id;
    private String name;
    private String description;
    private BigDecimal subscriptionCost;
    private int issuesPerYear;

    /**
     * Variable that shows whether periodical has limited edition or not
     */
    private boolean isLimited;
    private Genre genre;
    private Publisher publisher;

    public Periodical() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getSubscriptionCost() {
        return subscriptionCost;
    }

    public void setSubscriptionCost(BigDecimal subscriptionCost) {
        this.subscriptionCost = subscriptionCost;
    }

    public int getIssuesPerYear() {
        return issuesPerYear;
    }

    public void setIssuesPerYear(int issuesPerYear) {
        this.issuesPerYear = issuesPerYear;
    }

    public boolean getIsLimited() {
        return isLimited;
    }

    public void setLimited(boolean limited) {
        isLimited = limited;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Periodical that = (Periodical) o;
        return issuesPerYear == that.issuesPerYear &&
                isLimited == that.isLimited &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(subscriptionCost, that.subscriptionCost) &&
                Objects.equals(genre, that.genre) &&
                Objects.equals(publisher, that.publisher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, subscriptionCost, issuesPerYear, isLimited, genre, publisher);
    }
}
