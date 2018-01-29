package com.periodicals.commands.util;

import java.util.Objects;

/**
 * @author Daniel Volnitsky
 * <p>
 * Class that carries information about Command executing result
 * so Dispatcher could know where and how redirect further
 *
 * @see com.periodicals.servlet.Dispatcher
 * @see Command
 */
public class CommandResult {
    public final RedirectType redirectType;
    public final String pageHref;

    public CommandResult(RedirectType redirectType, String pageHref) {
        this.redirectType = redirectType;
        this.pageHref = pageHref;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandResult that = (CommandResult) o;
        return redirectType == that.redirectType &&
                Objects.equals(pageHref, that.pageHref);
    }

    @Override
    public int hashCode() {
        return Objects.hash(redirectType, pageHref);
    }
}