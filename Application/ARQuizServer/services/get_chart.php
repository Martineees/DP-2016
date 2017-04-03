<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 19:16
 */

require_once("../dbConnect.php");
require_once("../dao/CompetitionsDAO.php");
require_once("../entities/Competition.php");

use dao\AnswersLogDAO;
use dao\CompetitionsDAO;
use entities\AnswerLog;

$dbConnect = new DBConnect();
$db = $dbConnect->connect();

$competitionDAO = new CompetitionsDAO($db);

$response = array("error" => TRUE);

if(isset($_POST["competition_id"])) {


    $result = $competitionDAO->getCompetitionChart($_POST["competition_id"]);

    if($result != null) {
        $response["error"] = FALSE;
        $response["chart"] = json_encode($result->getArrayCopy());
    } else {
        $response["error_msg"] = "Competition was not created";
    }
} else {
    $response["error_msg"] = "Missing required parameters";
}

echo json_encode($response);
