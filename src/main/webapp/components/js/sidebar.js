$(function () {
    'use strict';

    /*
     *  @CONSTANTS
     */
    const CLASS_OPEN = 'is-open',
        CLASS_VISIBLE = 'is-visible',
        LARGE_SCREEN = 992;

    /*
     *  @VARIABLES
     */
    var main_nav = $('#main-nav'),
        sidebar = $('#sidebar'),
        cart = $('#cart'),
        cart_button = $('#cart-button'),
        hamburger_menu = $('#hamburger-menu'),
        overlay = $('#overlay'),
        header = $('#header');

    /*
     *  @EVENTS
     */
    hamburger_menu.on('click', function () {
        cart.removeClass(CLASS_OPEN);
        togglePanelVisibility(sidebar);
    });

    cart_button.on('click', function () {
        sidebar.removeClass(CLASS_OPEN);
        togglePanelVisibility(cart);
    });

    overlay.on('click', function () {
        overlay.removeClass(CLASS_VISIBLE);
        sidebar.removeClass(CLASS_OPEN);
        cart.removeClass(CLASS_OPEN);
    });

    $(window).on('resize', function () {
        setNavigationPosition(main_nav);
    });

    /*
     *  @FUNCTIONS
     */
    function togglePanelVisibility(panel) {
        if (panel.hasClass(CLASS_OPEN)) {
            panel.removeClass(CLASS_OPEN);
            overlay.removeClass(CLASS_VISIBLE);
        } else {
            panel.addClass(CLASS_OPEN);
            overlay.addClass(CLASS_VISIBLE);
        }
    }

    function setNavigationPosition(navigation) {
        if ($(window).width() >= LARGE_SCREEN) {
            navigation.detach();
            navigation.appendTo(header);
        } else {
            navigation.detach();
            navigation.prependTo(sidebar);
        }
    }

    /*
     *  @INIT
     */
    setNavigationPosition(main_nav);
});