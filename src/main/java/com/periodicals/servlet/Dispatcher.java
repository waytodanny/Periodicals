package com.periodicals.servlet;

import com.periodicals.commands.util.Command;
import com.periodicals.commands.util.CommandFactory;
import com.periodicals.commands.util.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.periodicals.utils.resourceHolders.AttributesHolder.COMMAND;

/**
 * @author Daniel Vlnitsky
 * <p>
 * Application main servlet responsible for:
 * 1. Obtaining commands from incoming request
 * 2. Executing commands
 * 3. Redirecting request further by parameters obtained from CommandResult object
 * @see CommandResult
 */
public class Dispatcher extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(Dispatcher.class.getSimpleName());

    @Override
    public void init() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        this.dispatch(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        this.dispatch(request, response);
    }

    /**
     * Main dispatching method for all types of methods
     */
    private void dispatch(HttpServletRequest request, HttpServletResponse response) {
        CommandFactory factory = CommandFactory.getInstance();

        String commandName = getCommandNameFromRequest(request);
        Command command = factory.getCommand(commandName);

        CommandResult commandResult = command.execute(request, response);
        this.redirectFurther(request, response, commandResult);
    }

    /**
     * @return commands obtained from request attribute or default commands if there is none
     */
    private String getCommandNameFromRequest(HttpServletRequest request) {
        String commandName = (String) request.getAttribute(COMMAND);
        return Objects.nonNull(commandName) ? commandName.toLowerCase() : "/";
    }

    /**
     * Method that takes redirect type and next page link from CommandResult and does redirecting
     *
     * @see CommandResult
     */
    private void redirectFurther(HttpServletRequest request, HttpServletResponse response, CommandResult commandResult) {
        try {
            switch (commandResult.redirectType) {
                case FORWARD:
                    request.getRequestDispatcher(commandResult.pageHref).forward(request, response);
                    break;
                case REDIRECT:
                    String redirectPath = request.getServletPath() + commandResult.pageHref;
                    response.sendRedirect(redirectPath);
                    break;
            }
        } catch (ServletException | IOException e) {
            LOGGER.error("Failed to redirect to the next page: " + e.getMessage());
        }
    }
}
