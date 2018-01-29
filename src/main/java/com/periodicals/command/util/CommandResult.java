package com.periodicals.command.util;

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
}