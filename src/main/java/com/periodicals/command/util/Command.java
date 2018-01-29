package com.periodicals.command.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Daniel Volnitsky
 * Interface for all application commands
 */
public interface Command {
    CommandResult execute(HttpServletRequest request, HttpServletResponse response);
}
