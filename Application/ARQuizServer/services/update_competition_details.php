<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 28.2.2017
 * Time: 21:47
 */
use dao\AnswersDAO;
use dao\CompetitionsDAO;
use dao\QuestionsDAO;
use entities\Answer;
use entities\Competition;
use entities\Question;

require_once("../dbConnect.php");
require_once("../dao/CompetitionsDAO.php");
require_once("../entities/Competition.php");

$dbConnect = new DBConnect();
$db = $dbConnect->connect();

$competitionsDAO = new CompetitionsDAO($db);

$response = array("error" => TRUE);

if(isset($_POST["competition_id"]) && isset($_POST["name"]) && isset($_POST["description"])) {

    $competition = new Competition($_POST["competition_id"], $_POST["name"], null, null, $_POST["description"]);
    $result = $competitionsDAO->updateCompetitionDetails($competition);

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