package com.periodicals.services;

import com.periodicals.entities.Periodical;
import com.periodicals.services.entities.PeriodicalService;
import com.periodicals.utils.entities.Cart;
import org.apache.log4j.Logger;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Daniel Volnitsky
 * <p>
 * Service responsible for processing some actions that has to do with cart
 * @see Cart
 */
public class CartService {

    private static final Logger LOGGER = Logger.getLogger(CartService.class.getSimpleName());

    private static final CartService cartService = new CartService();
    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();

    public static CartService getInstance() {
        return cartService;
    }

    /**
     * @param periodicalId id of periodical that needed to be added to cart
     */
    public void addItemToCart(Cart cart, UUID periodicalId) {
        try {
            Periodical added = periodicalService.getEntityByPrimaryKey(periodicalId);
            if (Objects.nonNull(added)) {
                cart.addItem(added);
                LOGGER.debug("Added periodical with id " + periodicalId + " to cart");
            } else {
                throw new NullPointerException("Periodical with id " + periodicalId + " doesn't exist");
            }
        } catch (Exception e) {
            LOGGER.error("Failed to add periodical with id " + periodicalId + " to cart due to: " + e.getMessage());
        }
    }

    /**
     * @param periodicalId id of periodical that needed to be removed from cart
     */
    public void removeItemFromCart(Cart cart, UUID periodicalId) {
        try {
            Periodical removed = periodicalService.getEntityByPrimaryKey(periodicalId);
            if (Objects.nonNull(removed)) {
                cart.removeItem(removed);
                LOGGER.debug("Removed periodical with id " + periodicalId + " from cart");
            } else {
                throw new NullPointerException("Periodical with id " + periodicalId + " doesn't exist");
            }
        } catch (Exception e) {
            LOGGER.error("Failed to remove periodical with id " + periodicalId + " from cart due to: " + e.getMessage());
        }
    }
}
