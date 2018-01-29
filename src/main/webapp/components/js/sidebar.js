$(function () {
    'use strict';

    var $main_nav = $('#main-nav'),
        $sidebar = $('#sidebar'),
        $cart = $('#cart'),
        $cart_button = $('#cart-button'),
        $hamburger_menu = $('#hamburger-menu'),
        $overlay = $('#overlay'),
        $header = $('#header');

    var _isOpenClass = 'is-open',
        _isVisibleClass = 'is-visible',
        _largeScreen = 992;

    $hamburger_menu.on('click', function(){
        $cart.removeClass(_isOpenClass);
        toggle_panel_visibility($sidebar, $overlay);
    });

    $cart_button.on('click', function(){
        $sidebar.removeClass(_isOpenClass);
        toggle_panel_visibility($cart, $overlay);
    });

    $overlay.on('click', function(){
        $overlay.removeClass(_isVisibleClass);
        $sidebar.removeClass(_isOpenClass);
        $cart.removeClass(_isOpenClass);
    });

    move_navigation($main_nav, _largeScreen);
    $(window).on('resize', function(){
        move_navigation($main_nav, _largeScreen);
    });

    function toggle_panel_visibility ($panel) {
        if( $panel.hasClass(_isOpenClass) ) {
            $panel.removeClass(_isOpenClass);
            $overlay.removeClass(_isVisibleClass);
        } else {
            $panel.addClass(_isOpenClass);
            $overlay.addClass(_isVisibleClass);
        }
    }

    function move_navigation($navigation, $size_condition) {
        if ($(window).width() >= $size_condition) {
            $navigation.detach();
            $navigation.appendTo($header);
        } else {
            $navigation.detach();
            $navigation.prependTo($sidebar);
        }
    }
});