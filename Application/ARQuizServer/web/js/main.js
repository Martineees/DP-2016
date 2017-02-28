/**
 * Created by Martin on 27.2.2017.
 */

window.arquiz = window.arquiz || {};

//HELPER
window.arquiz.helper = window.arquiz.helper || (function ($) {
    var showDialog = function($dialogId, $data, $successCallback, $failedCallback) {
            $.ajax({
                type: "POST",
                url: "templates/" + $dialogId,
                data: $data,
                success: $successCallback,
                failed: $failedCallback
            })
        };

    var closeDialog = function($id) {
        var $form = $($id);
        var $formParent = $form.parent();
        $formParent.remove();
    };

    return {
        showDialog: showDialog,
        closeDialog: closeDialog
    }

})(jQuery);

//LOGIN
window.arquiz.login = window.arquiz.login || (function ($) {

    var _login = function($form) {

        var data = $form.serialize();

        $.ajax({
            type: "POST",
            url: "../services/login.php",
            dataType: "json",
            data: data,
            success: _onLoginSuccess,
            failed: _onLoginFailed
        });
    };

    var _onLoginSuccess = function($response) {
        if($response.error) _onLoginFailed($response);
        else {
            window.location.href = "dashboard.php";
        }

    };

    var _onLoginFailed = function($response) {
        console.error($response);
    };

    var _bindBtns = function() {

        $("#loginForm").submit(function(e){
            e.preventDefault();

            _login($(this));
        });
    };

    var initialize = function() {
        _bindBtns();
    };

    return {
        initialize: initialize
    }
})(jQuery);

//DASHBOARD
window.arquiz.dashboard = window.arquiz.dashboard || (function ($) {

    var _bindBtns = function() {
        $("#addButton").on("click", function() {

            $(this).addClass("disabled");

            arquiz.helper.showDialog("add_competition_tmp.php", null,
                _onShowAddDialogSuccess, _onShowAddDialogFailed);

            return false;
        });
    };

    var _onShowAddDialogSuccess = function($response) {

        if($response != null) {
            var $dialog = $($response);

            $('body').append($dialog);
            _bindAddDialogBtns();

            $dialog.addClass("show");
            $("#addButton").removeClass("disabled");
        }
    };

    var _onShowAddDialogFailed = function($response) {
        console.error($response);
    };

    var _bindAddDialogBtns = function() {

        var $form = $("#add_competition");

        $form.submit(function(e) {
           e.preventDefault();

           _addCompetition($(this));
        });

        $form.find(".close").on("click", function(e) {
            e.preventDefault();
            arquiz.helper.closeDialog("#add_competition");
        });

    };

    var _addCompetition = function($form) {
        var data = $form.serialize();

        $.ajax({
            type: "POST",
            url: "../services/add_competition",
            dataTypes: 'json',
            data: data,
            success: _addCompetitionSuccess,
            failed: _addCompetitionFailed
        });
    };

        var _addCompetitionSuccess = function($response) {
            if($response.error) _onLoginFailed($response);
            else {
                var $form = $("#add_competition");
                var $formParent = $form.parent();

                _getCompetitions();

                $formParent.remove();
            }

        };

        var _addCompetitionFailed = function($response) {
            console.error($response);
        };

    var _getCompetitions = function() {
        $.ajax({
            type: "POST",
            url: "templates/competitions_tmp.php",
            success: _getCompetitionsSuccess,
            failed: _getCompetitionsFailed
        });
    };

    var _getCompetitionsSuccess = function($response) {
        var $container = $("#cmp-box");

        if($response != null && $response != "" && $container != null) {
            $container.empty();
            $container.append($response);
        }
    };

    var _getCompetitionsFailed = function($response) {
        console.error($response);
    };

    var initialize = function() {
        _bindBtns();
        _getCompetitions();
    };

    return {
        initialize: initialize
    }
})(jQuery);


//INIT

$(document).ready(function($) {

    var initPage = function(page) {
        switch(page) {
            case 'index.php':
                window.arquiz.login.initialize();
                break;
            case 'dashboard.php':
                window.arquiz.dashboard.initialize();
                break;
        }
    };

    var path = window.location.pathname;
    var page = path.substring(path.lastIndexOf("/") + 1, path.length);

    initPage(page);

});
