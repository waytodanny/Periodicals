package com.periodicals.command.auth;

import com.periodicals.authentification.AuthenticationHelper;
import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.services.CartService;
import com.periodicals.utils.entities.Cart;
import com.periodicals.utils.uuid.UUIDHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.UUID;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.utils.resourceHolders.PagesHolder.ERROR_PAGE;
import static com.periodicals.utils.resourceHolders.PagesHolder.LOGIN_PAGE;

/**
 * @author Daniel Volnitsky
 *
 * Command for authenticated users that is responsible for handling
 * user deleting from cart action
 * @see Cart
 */
public class CartRemoveItemCommand implements Command {
    private static final CartService cartService = CartService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String referer = CommandUtils.getRefererWithoutServletPath(request);
        HttpSession session = request.getSession();

        if (AuthenticationHelper.isUserLoggedIn(session)) {
            if (CommandUtils.paramClarifiedInQuery(request, "item_id")) {
                String itemId = request.getParameter("item_id");
                if (UUIDHelper.isUUID(itemId)) {
                    Cart cart = this.getCartFromSession(session);
                    if (Objects.nonNull(cart)) {
                        cartService.removeItemFromCart(cart, UUID.fromString(itemId));
                        return new CommandResult(REDIRECT, referer);
                    }
                }
            }
            return new CommandResult(FORWARD, ERROR_PAGE);
        }
        return new CommandResult(FORWARD, LOGIN_PAGE);
    }

    private Cart getCartFromSession(HttpSession session) {
        return (Cart) session.getAttribute("cart");
    }
}
