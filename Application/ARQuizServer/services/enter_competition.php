<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 22.2.2017
 * Time: 19:43
 */

use dao\AnswersLogDAO;
use dao\CompetitorsDAO;
use entities\Competitor;

require_once("../dbConnect.php");
require_once("../dao/CompetitorsDAO.php");
require_once("../dao/CompetitionsDAO.php");
require_once("../dao/UsersDAO.php");
require_once("../entities/Competitor.php");
require_once("../dao/AnswersLogDAO.php");

$dbConnect = new DBConnect();
$db = $dbConnect->connect();

$competitorsDAO = new CompetitorsDAO($db);
$answerLogDAO = new AnswersLogDAO($db);

$response = array("error" => TRUE);

if(isset($_POST['user_id']) && isset($_POST['competition_id'])){
    $competitor = new Competitor(null, $_POST['competition_id'], $_POST['user_id']);

    $result = $competitorsDAO->create($competitor);

    if($result != -1) {
        $response["error"] = FALSE;
        $response["competitor_id"] = $result;
        $response["answers_list"] = json_encode($answerLogDAO->getCompetitorAnswersJSONArray($result)->getArrayCopy());

    } else {
        $response["error_msg"] = "Already entered in competition";
    }

} else {
    $response["error_msg"] = "Missing required params";
}

echo json_encode($response);