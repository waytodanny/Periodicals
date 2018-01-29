$(function () {
    'use strict';

    $(".ajax-form").submit(function (e) {
        var caller = $(e.target);
        var messageBox = caller.find('#message_box');
        $.ajax({
            type: caller.attr('method'),
            url: caller.attr('action'),
            data: caller.serialize(),
            success: function () {
                var successMessage = caller.attr('success-message');
                if (messageBox.length && successMessage) {
                    getMessageWrapper(successMessage).appendTo(messageBox);
                }
            },
            fail: function () {
                var failMessage = caller.attr('fail-message');
                if (messageBox.length && failMessage) {
                    getMessageWrapper(failMessage).appendTo(messageBox);
                }
            }
        });
        e.preventDefault();
    });

    function getMessageWrapper(message) {
        var wrapper = $('<div>', {
            class: 'alert alert-warning alert-dismissable'
        }).append($('<a>', {
            'class': 'close',
            'data-dismiss': 'alert',
            'aria-label': 'close',
            'href': '#',
            'html': '&times'
        })).append($('<span>', {
            'html': message
        }));

        return wrapper;
    }
});