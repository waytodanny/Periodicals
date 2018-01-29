package com.periodicals.command.common;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Objects;

import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.utils.resourceHolders.AttributesHolder.LANGUAGE;

public class ChangeLanguageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String referer = CommandUtils.getRefererWithoutServletPath(request);
        String chosenLanguage = request.getParameter(LANGUAGE);
        if (Objects.nonNull(chosenLanguage)) {
            Locale locale = new Locale(chosenLanguage);
            HttpSession session = request.getSession(true);
            session.setAttribute(LANGUAGE, locale);
        }
        return new CommandResult(REDIRECT, referer);
    }
}
