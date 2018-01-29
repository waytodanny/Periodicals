package com.periodicals.command.auth;

import com.periodicals.authentification.AuthenticationHelper;
import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.entities.User;
import com.periodicals.services.CartService;
import com.periodicals.services.SubscriptionService;
import com.periodicals.utils.entities.Cart;
import com.periodicals.utils.uuid.UUIDHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.UUID;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.utils.resourceHolders.AttributesHolder.ATTR_CART;
import static com.periodicals.utils.resourceHolders.AttributesHolder.ATTR_USER;
import static com.periodicals.utils.resourceHolders.PagesHolder.ERROR_PAGE;
import static com.periodicals.utils.resourceHolders.PagesHolder.LOGIN_PAGE;

/**
 * @author Daniel Volnitsky
 * <p>
 * Command for authenticated users that is responsible for handling
 * user adding to cart action
 * @see Cart
 */
public class CartAddItemCommand implements Command {
    private static final CartService cartService = CartService.getInstance();
    private static final SubscriptionService subscriptionService = SubscriptionService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String referer = CommandUtils.getRefererWithoutServletPath(request);
        HttpSession session = request.getSession();

        if (AuthenticationHelper.isUserLoggedIn(session)) {
            if (CommandUtils.paramClarifiedInQuery(request, "item_id")) {
                String itemId = request.getParameter("item_id");
                if (UUIDHelper.isUUID(itemId)) {
                    User user = (User) session.getAttribute(ATTR_USER);
                    UUID itemIdValue = UUID.fromString(itemId);
                    boolean isAlreadySubscribed =
                            subscriptionService.getIsUserSubscribedForPeriodical(user.getId(), itemIdValue);
                    if (!isAlreadySubscribed) {
                        Cart cart = getCartFromSession(session);
                        cartService.addItemToCart(cart, itemIdValue);
                    }
                    return new CommandResult(REDIRECT, referer);
                }
            }
            return new CommandResult(FORWARD, ERROR_PAGE);
        }
        return new CommandResult(REDIRECT, LOGIN_PAGE);
    }

    private Cart getCartFromSession(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(ATTR_CART);
        if (Objects.isNull(cart)) {
            cart = new Cart();
            session.setAttribute(ATTR_CART, cart);
        }
        return cart;
    }
}
