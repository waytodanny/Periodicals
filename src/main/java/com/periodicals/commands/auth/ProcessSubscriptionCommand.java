package com.periodicals.commands.auth;

import com.periodicals.utils.authentification.AuthenticationHelper;
import com.periodicals.commands.util.Command;
import com.periodicals.commands.util.CommandResult;
import com.periodicals.entities.User;
import com.periodicals.utils.exceptions.ServiceException;
import com.periodicals.services.SubscriptionService;
import com.periodicals.services.entities.PaymentService;
import com.periodicals.utils.entities.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.periodicals.commands.util.RedirectType.REDIRECT;
import static com.periodicals.utils.resourceHolders.PagesHolder.CATALOG_PAGE;
import static com.periodicals.utils.resourceHolders.PagesHolder.LOGIN_PAGE;
import static com.periodicals.utils.resourceHolders.PagesHolder.USER_SUBSCRIPTIONS_PAGE;

/**
 * @author Daniel Volnitsky
 * <p>
 * Command for authenticated users that is responsible for providing paymentService with
 * info about incoming payment
 * @see PaymentService
 */
public class ProcessSubscriptionCommand implements Command {
    private SubscriptionService subscriptionService = SubscriptionService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        if (!AuthenticationHelper.isUserLoggedIn(session)) {
            return new CommandResult(REDIRECT, LOGIN_PAGE);
        }

        Cart cart = this.getCartFromSession(session);
        if (Objects.nonNull(cart)) {
            try {
                User user = AuthenticationHelper.getUserFromSession(session);
                if (Objects.nonNull(user)) {
                    subscriptionService.processSubscriptions(
                            user,
                            cart.getTotalValue(),
                            cart.getItems()
                    );
                    request.setAttribute("resultMessage", "Successfully processed subscriptions");
                    return new CommandResult(REDIRECT, USER_SUBSCRIPTIONS_PAGE);
                }
            } catch (ServiceException e) {
                request.setAttribute("resultMessage", "Failed to process subscriptions");
            } finally {
                cart.cleanUp();
            }
        }
        return new CommandResult(REDIRECT, CATALOG_PAGE);
    }

    private Cart getCartFromSession(HttpSession session) {
        return (Cart) session.getAttribute("cart");
    }
}
