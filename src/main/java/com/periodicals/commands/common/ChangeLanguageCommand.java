package com.periodicals.commands.common;

import com.periodicals.commands.util.Command;
import com.periodicals.commands.util.CommandResult;
import com.periodicals.commands.util.CommandUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Objects;

import static com.periodicals.commands.util.RedirectType.REDIRECT;
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
