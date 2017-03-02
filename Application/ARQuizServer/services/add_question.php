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
require_once("../dao/AnswersDAO.php");
require_once("../entities/Answer.php");
require_once("Vuforia/PostNewTarget.php");

$dbConnect = new DBConnect();
$db = $dbConnect->connect();

$questionsDAO = new QuestionsDAO($db);
$answerDAO = new AnswersDAO($db);

$response = array("error" => TRUE);

if(isset($_POST["name"]) && isset($_POST["type"]) && isset($_POST["width"]) && isset($_POST["answer"])
    && isset($_FILES["image"]["tmp_name"])&& isset($_POST["competition_id"]) && isset($_POST["location_id"])) {

    $vuforiaService = new PostNewTarget();
    $vuforia = json_decode($vuforiaService->VuforiaPostNewTarget($_FILES["image"]["name"], getImageAsBase64($_FILES["image"]["tmp_name"]), floatval($_POST["width"])), true);

    //vuforia transaction success
    if(isset($vuforia["result_code"]) && $vuforia["result_code"] == "TargetCreated") {
        $response["vuforia_result_code"] = $vuforia["result_code"];
        $response["vufofria_target_id"] = $vuforia["target_id"];


        //creating question obj
        $question = new Question(null, $_POST["name"], $_POST["competition_id"], $vuforia["target_id"], $_POST["location_id"], $_POST["type"]);
        $questionId = $questionsDAO->create($question);

        if($questionId != null) {

            //first is correct
            $first = true;
            $allCreated = true;

            foreach($_POST["answer"] as $answer) {
                $isCorrect = false;
                if($first) {
                    $isCorrect = true;
                    $first = false;
                }

                $answerObj = new Answer(null, $answer, $questionId, $isCorrect);
                $answerId = $answerDAO->create($answerObj);

                if($answerId == null) $allCreated = false;
            }

            if($allCreated) {
                $response["error"] = FALSE;
            } else {
                $response["error_msg"] = "Not all answers were created";
            }

        } else {
            $response["error_msg"] = "Question was not created";
        }

    } else {
        $response["vuforia_result_code"] = $vuforia["result_code"];
    }

} else {
    $response["error_msg"] = "Missing required params";
}

echo json_encode($response);

function getImageAsBase64($filePath){
    $file = file_get_contents($filePath);
    if( $file ){
        $file = base64_encode( $file );
    }
    return $file;
}