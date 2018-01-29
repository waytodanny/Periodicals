package com.periodicals.command.common;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.utils.resourceHolders.PagesHolder.CATALOG_PAGE;

public class DefaultCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        return new CommandResult(REDIRECT, CATALOG_PAGE);
    }
}
