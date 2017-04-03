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

//REGISTRATION
window.arquiz.registration = window.arquiz.registration || (function ($) {

        var _register = function($form) {

            var data = $form.serialize();

            $.ajax({
                type: "POST",
                url: "../services/registration.php",
                dataType: "json",
                data: data,
                success: _onRegisterSuccess,
                failed: _onRegisterFailed
            });
        };

        var _onRegisterSuccess = function($response) {
            if($response.error) _onRegisterFailed($response);
            else {
                window.location.href = "index.php";
            }

        };

        var _onRegisterFailed = function($response) {
            console.error($response);
        };

        var _bindBtns = function() {

            $("#registerForm").submit(function(e){
                e.preventDefault();

                _register($(this));
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

//EDIT_COMPETITION
window.arquiz.editCompetition = window.arquiz.editCompetition || (function ($) {

        var _bindCompetitionForm = function() {
            var $form = $("#competitionDetails");

            $form.submit(function(e) {
                e.preventDefault();

                _updateAJAX($(this), "update_competition_details.php");
            });
        };

        var _updateAJAXSuccess = function($response) {

            console.log($response);

            $responseObj = $.parseJSON($response);
            if($responseObj["error"] == "true") _updateAnswersFailed($reponse);

            window.location.reload();
        };

        var _updateAJAXFailed = function($response) {
            console.error($response);
        };

        var _updateAJAX = function($form, page) {
            var data = $form.serialize();
            $("form").addClass("disabled");
            arquiz.helper.addLoader($form);

            $.ajax({
                type: "POST",
                url: "../services/" + page,
                dataTypes: 'json',
                data: data,
                success: _updateAJAXSuccess,
                failed: _updateAJAXFailed
            });
        };

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

        var _bindChartForm = function() {
            var $form = $("#chartDetails");

            $form.submit(function(e) {
                e.preventDefault();

                _getChart();
            });
        };

        var _getChart = function() {
            var $container = $("#chart");
            $container.empty();
            arquiz.helper.addLoader($container);

            var competitionId = arquiz.helper.getQueryParam("id");
            var data = "id=" + competitionId;

            $.ajax({
                type: "POST",
                url: "templates/chart_tmp.php",
                data: data,
                success: _getChartSuccess,
                failed: _getChartFailed
            });
        };

        var _getChartSuccess = function($response) {
            var $container = $("#chart");
            $container.empty();

            if($response != null && $response != "" && $container != null) {
                $container.append($response);
            } else {
                $container.append("No results");
            }
        };

        var _getChartFailed = function($response) {
            console.error($response);
        };

        var initialize = function() {
            _bindBtns();
            _getQuestions();
            _getChart();
            _bindCompetitionForm();
            _bindChartForm();
        };

        return {
            initialize: initialize
        }
    })(jQuery);

//EDIT_QUESTION
window.arquiz.editQuestion = window.arquiz.editQuestion || (function ($) {

        var _bindQuestionForm = function() {
            var $form = $("#questionDetails");

            $form.submit(function(e) {
                e.preventDefault();

                _updateAJAX($(this), "update_question_details.php");
            });
        };

        var _removeAnswer = function($elem) {
            var $input = $elem.next();
            $input.remove();
            $elem.remove();
        };

        var _updateAJAXSuccess = function($response) {

            console.log($response);

            $responseObj = $.parseJSON($response);
            if($responseObj["error"] == "true") _updateAnswersFailed($reponse);

            window.location.reload();
        };

        var _updateAJAXFailed = function($response) {
            console.error($response);
        };

        var _updateAJAX = function($form, page) {
            var data = $form.serialize();
            $("form").addClass("disabled");
            arquiz.helper.addLoader($form);

            $.ajax({
                type: "POST",
                url: "../services/" + page,
                dataTypes: 'json',
                data: data,
                success: _updateAJAXSuccess,
                failed: _updateAJAXFailed
            });
        };

        var _updateAJAXFile = function($form, page) {
            var data = new FormData($form[0]);

            $("form").addClass("disabled");
            arquiz.helper.addLoader($form);

            $.ajax({
                type: "POST",
                url: "../services/" + page,
                dataTypes: 'json',
                data: data,
                success: _updateAJAXSuccess,
                failed: _updateAJAXFailed,
                cache: false,
                contentType: false,
                processData: false
            });
        };

        var _bindAnswerForm = function() {
            var $form = $("#questionAnswers");

            $form.submit(function(e) {
                e.preventDefault();

                var type = $('#type').val();
                if(type == 1) {
                    $form.find("#otherAnswers").empty();
                }

                _updateAJAX($(this), "update_question_answers.php");
            });

            $form.find("#addAnswer").on('click', function(e) {
                e.preventDefault();

                var $container = $form.find("#otherAnswers");

                if($container.find(".label-incorrect").length == 0) {
                    $container.append('<label class="label-incorrect">Incorrect answer</label>');
                }

                var $removeBtn = $('<input type="button" class="second-btn remove-btn" value="remove" />');
                $removeBtn.on('click', function(e) { _removeAnswer($(this))});

                $container.append($removeBtn);
                $container.append('<input type="text" name="answer[]" />');
            });

            $form.find('#type').on('change', function(e) {
                var $addBtn = $form.find("#addAnswer");

                if($(this).val() == 0) {
                    $form.find("#otherAnswers").show();
                    $addBtn.show();
                } else {
                    $addBtn.hide();
                    $form.find("#otherAnswers").hide();
                }
            });

            $form.find(".remove-btn").each(function(i, elem) {
                $(elem).on("click", function(e) {
                    _removeAnswer($(this));
                });
            });
        };

        var _onGetTargetSuccess = function($response) {
            console.log($response);

            var jsonObj = $.parseJSON($response);
            if(jsonObj["error"] == "true") {
                _onGetTargetFailed($response);
                return;
            }

            var status = jsonObj["target_status"] == "processing" ? " - processing" : "";
            $("#targetTitle").append("<span class='note'>" + status + "</span>");

            var background = jsonObj["target_image"];
            var $targetImage = $("#targetImage");
            var targetRate = jsonObj["target_rate"];
            var $targetRateStars = $(".target-rate .ic");

            $("#target_name").val(jsonObj["target_name"]);

            $targetImage.find(".loader").remove();
            $targetImage.css("background-image","url("+background+")");

            for(i=0; i < targetRate; i++) {
                $($targetRateStars.get(i)).removeClass("ic-star");
                $($targetRateStars.get(i)).addClass("ic-star-filled");
            }

            $(".target-rate, #changeTargetForm").removeClass("hide");
        };

        var _onGetTargetFailed = function($response) {
            console.error($response);
        };

        var _getTarget = function() {
            var $targetImage = $("#targetImage");
            var targetId = $targetImage.data("target");
            var data = "target_id="+targetId;

            $.ajax({
                type: "POST",
                url: "../services/get_target.php",
                dataTypes: 'json',
                data: data,
                success: _onGetTargetSuccess,
                failed: _onGetTargetFailed
            });
        };

        var _bindTargetForm = function() {
            var $form = $("#changeTargetForm");

            $form.submit(function(e) {
                e.preventDefault();

                $form.addClass("processing");

                _updateAJAXFile($(this), "update_question_target.php");
            });

            $form.find("input[type=file]").each(function(i, elem) {
                arquiz.helper.initFileSelector(elem);
            });
        };

        var _bindBtns = function() {
            _bindQuestionForm();
            _bindAnswerForm();
            _bindTargetForm();
        };

        var initialize = function() {
            _bindBtns();
            _getTarget();
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
            case 'register.php':
                window.arquiz.registration.initialize();
                break;
            case 'dashboard.php':
                window.arquiz.dashboard.initialize();
                break;
            case 'edit_competition.php':
                window.arquiz.editCompetition.initialize();
                break;
            case 'edit_question.php':
                window.arquiz.editQuestion.initialize();
                break;
        }
    };

    var path = window.location.pathname;
    var page = path.substring(path.lastIndexOf("/") + 1, path.length);

    initPage(page);

});
