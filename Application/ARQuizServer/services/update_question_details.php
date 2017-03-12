<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 28.2.2017
 * Time: 21:47
 */
use dao\AnswersDAO;
use dao\QuestionsDAO;
use entities\Answer;
use entities\Question;

require_once("../dbConnect.php");
require_once("../dao/QuestionsDAO.php");
require_once("../entities/Question.php");

$dbConnect = new DBConnect();
$db = $dbConnect->connect();

$questionsDAO = new QuestionsDAO($db);

$response = array("error" => TRUE);

if(isset($_POST["question_id"]) && isset($_POST["name"]) && isset($_POST["location_id"]) && isset($_POST["score"])) {

    $question = new Question($_POST["question_id"], $_POST["name"], null, null, $_POST["location_id"], null, $_POST["score"]);
    $result = $questionsDAO->updateQuestionDetails($question);

    if($result) {
        $response["error"] = FALSE;
        $response["success_msg"] = "Update was successful";
    } else {
        $response["error_msg"] = "Update was not successful";
    }

} else {
    $response["error_msg"] = "Missing required params";
}

echo json_encode($response);