<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 19:16
 */

require_once("../dbConnect.php");
require_once("../dao/AnswersLogDAO.php");
require_once("../entities/AnswerLog.php");

use dao\AnswersLogDAO;
use entities\AnswerLog;

$dbConnect = new DBConnect();
$db = $dbConnect->connect();

$answerLogDAO = new AnswersLogDAO($db);

$response = array("error" => TRUE);

if(isset($_POST["question_id"]) && isset($_POST["competitor_id"]) && isset($_POST["is_correct"])) {
    $answerLog = new AnswerLog(null, $_POST["competitor_id"], $_POST["question_id"], $_POST["is_correct"]);

    $result = $answerLogDAO->create($answerLog);

    if($result != null) {
        $response["error"] = FALSE;
        $response["id"] = $result;
    } else {
        $response["error_msg"] = "Answer log was not created";
    }
} else {
    $response["error_msg"] = "Missing required parameters";
}

echo json_encode($response);
