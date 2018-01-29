package com.periodicals.entities;

import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Daniel Volnitsky
 * <p>
 * Class that represents periodical issue
 * @see Periodical
 */
public class PeriodicalIssue {
    public static final int ISSUE_NAME_MAX_LENGTH = 100;

    private UUID id;
    private int issueNo;
    private String name;
    private Date publishDate;
    private Periodical periodical;

    public PeriodicalIssue() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(int issueNo) {
        this.issueNo = issueNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Periodical getPeriodical() {
        return periodical;
    }

    public void setPeriodical(Periodical periodical) {
        this.periodical = periodical;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
       this.publishDate = publishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeriodicalIssue that = (PeriodicalIssue) o;
        return issueNo == that.issueNo &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(publishDate, that.publishDate) &&
                Objects.equals(periodical, that.periodical);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, issueNo, name, publishDate, periodical);
    }
}
