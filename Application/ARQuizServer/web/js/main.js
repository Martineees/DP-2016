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

    var addLoader = function($container) {
        if(!($container.find(".loader").length > 0))
            $container.append('<div class="loader"></div>');
    };

    var getQueryParam = function(name, url) {
        if (!url) {
            url = window.location.href;
        }
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    };

    var initFileSelector = function($input) {
        var $label = $input.nextElementSibling;
        var labelVal = $label.innerHTML;

        $($input).on("change", function(e) {
            var fileName = e.target.value.split( '\\' ).pop();

            if(fileName)
                $label.innerHTML = fileName;
            else
                $label.innerHTML = labelVal;
        });
    };

    return {
        showDialog: showDialog,
        closeDialog: closeDialog,
        addLoader: addLoader,
        getQueryParam: getQueryParam,
        initFileSelector: initFileSelector
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
        $form.addClass("disabled");
        arquiz.helper.addLoader($form);

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
            if($response.error) _addCompetitionFailed($response);
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
        var $container = $("#result-box");
        arquiz.helper.addLoader($container);

        $.ajax({
            type: "POST",
            url: "templates/competitions_tmp.php",
            success: _getCompetitionsSuccess,
            failed: _getCompetitionsFailed
        });
    };

    var _getCompetitionsSuccess = function($response) {
        var $container = $("#result-box");
        $container.empty();

        if($response != null && $response != "" && $container != null) {
            $container.append($response);
        } else {
            $container.append("No results");
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

//DASHBOARD
window.arquiz.editCompetition = window.arquiz.editCompetition || (function ($) {

        var _bindBtns = function() {
            $("#addButton").on("click", function() {

                $(this).addClass("disabled");
                var competitionId = arquiz.helper.getQueryParam("id");
                var data = "id=" + competitionId;

                arquiz.helper.showDialog("add_question_tmp.php", data,
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

            var $form = $("#add_question");

            $form.submit(function(e) {
                e.preventDefault();

                _addQuestion($(this));
            });

            $form.find(".close").on("click", function(e) {
                e.preventDefault();
                arquiz.helper.closeDialog("#add_question");
            });

            $form.find("#addAnswer").on('click', function(e) {
                e.preventDefault();

                var $container = $form.find("#otherAnswers");

                if($container.find(".label-incorrect").length == 0) {
                    $container.append('<label class="label-incorrect">Incorrect answer</label>');
                }
                $container.append('<input type="text" name="answer[]" />');
            });

            $form.find("input[type=file]").each(function(i, elem) {
               arquiz.helper.initFileSelector(elem);
            });

            $form.find('#type').on('change', function(e) {
                var $addBtn = $form.find("#addAnswer");

                if($(this).val() == 0) {
                    $addBtn.show();
                } else {
                    $addBtn.hide();
                    $form.find("#otherAnswers").empty();
                }
            });
        };

        var _addQuestion = function($form) {
            var data = new FormData($form[0]);
            $form.addClass("disabled");
            arquiz.helper.addLoader($form);

            $.ajax({
                type: "POST",
                url: "../services/add_question.php",
                dataTypes: 'json',
                data: data,
                success: _addQuestionSuccess,
                failed: _addQuestionFailed,
                cache: false,
                contentType: false,
                processData: false
            });
        };

        var _addQuestionSuccess = function($response) {
            if($response.error) _addQuestionFailed($response);
            else {
                var $form = $("#add_question");
                var $formParent = $form.parent();

                _getQuestions();

                $formParent.remove();
            }

        };

        var _addQuestionFailed = function($response) {
            console.error($response);
        };

        var _getQuestions = function() {
            var $container = $("#result-box");
            arquiz.helper.addLoader($container);

            var competitionId = arquiz.helper.getQueryParam("id");
            var data = "id=" + competitionId;

            $.ajax({
                type: "POST",
                url: "templates/questions_tmp.php",
                data: data,
                success: _getQuestionsSuccess,
                failed: _getQuestionsFailed
            });
        };

        var _getQuestionsSuccess = function($response) {
            var $container = $("#result-box");
            $container.empty();

            if($response != null && $response != "" && $container != null) {
                $container.append($response);
            } else {
                $container.append("No results");
            }
        };

        var _getQuestionsFailed = function($response) {
            console.error($response);
        };

        var initialize = function() {
            _bindBtns();
            _getQuestions();
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
            case 'edit_competition.php':
                window.arquiz.editCompetition.initialize();
                break;
        }
    };

    var path = window.location.pathname;
    var page = path.substring(path.lastIndexOf("/") + 1, path.length);

    initPage(page);

});
