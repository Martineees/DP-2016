<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 19:16
 */

require_once("../dbConnect.php");
require_once("../entities/Question.php");
require_once("../dao/QuestionsDAO.php");
require_once("../entities/Answer.php");
require_once("../dao/AnswersDAO.php");
require_once("../entities/Location.php");
require_once("../dao/LocationDAO.php");

use dao\QuestionsDAO;

$dbConnect = new DBConnect();
$db = $dbConnect->connect();

$questionsDAO = new QuestionsDAO($db);

$response = array("error" => TRUE);

if(isset($_POST["competitionId"])) {
    $questions = $questionsDAO->getQuestionsJSONArray($_POST["competitionId"]);

    if ($questions != null) {

        $response["error"] = FALSE;
        $response["questions"] = json_encode($questions->getArrayCopy());

    } else {
        $response["error_msg"] = "Competitions weren't found";
    }
} else {
    $response["error_msg"] = "Missing required params";
}

echo json_encode($response);