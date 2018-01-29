package com.periodicals.commands.common;

import com.periodicals.commands.util.Command;
import com.periodicals.commands.util.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.periodicals.commands.util.RedirectType.REDIRECT;
import static com.periodicals.utils.resourceHolders.PagesHolder.CATALOG_PAGE;

public class DefaultCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        return new CommandResult(REDIRECT, CATALOG_PAGE);
    }
}
