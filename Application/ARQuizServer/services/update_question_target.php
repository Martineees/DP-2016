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
require_once("Vuforia/PostNewTarget.php");
require_once("Vuforia/DeleteTarget.php");

$allow = array("jpg", "png");
$uploadDir = "../uploads/targets/";

$dbConnect = new DBConnect();
$db = $dbConnect->connect();

$questionsDAO = new QuestionsDAO($db);

$response = array("error" => TRUE);

if(isset($_POST["question_id"]) && isset($_FILES["image"]["tmp_name"]) && isset($_POST["target_name"]) && isset($_POST["width"])) {

    $question = $questionsDAO->getQuestionById($_POST["question_id"]);

    if($question != null) {

        //delete target
        $vuforiaDeleteService = new DeleteTarget();
        $vuforiaDeleteResult = json_decode($vuforiaDeleteService->DeleteTargetService($question->getTargetId()), true);

        $response["vuforia_delete_result"] = json_encode($vuforiaDeleteResult);

        if (isset($vuforiaDeleteResult["result_code"]) && $vuforiaDeleteResult["result_code"] == "Success") {

            if(unlink($uploadDir.$_POST["target_name"])) {

                //creating new target
                $info = explode('.', strtolower( $_FILES['image']['name']) ); // whats the extension of the file

                if ( in_array( end($info), $allow) ) // is this file allowed
                {
                    $vuforiaPostService = new PostNewTarget();
                    $vuforiaPostResult = json_decode($vuforiaPostService->VuforiaPostNewTarget($_FILES["image"]["name"], getImageAsBase64($_FILES["image"]["tmp_name"]), floatval($_POST["width"])), true);

                    //vuforia transaction success
                    if (isset($vuforiaPostResult["result_code"]) && $vuforiaPostResult["result_code"] == "TargetCreated") {
                        $response["vuforia_result_code"] = $vuforiaPostResult["result_code"];
                        $response["vufofria_target_id"] = $vuforiaPostResult["target_id"];

                        //upload image to our server
                        if (move_uploaded_file($_FILES["image"]["tmp_name"], $uploadDir . basename($_FILES["image"]["name"]))) {
                            $response["upload_image"] = "Image was successful uploaded: " . $_FILES["image"]["name"] . " " . $_FILES["image"]["type"];

                            if($questionsDAO->updateQuestionTargetId($question->getId(), $vuforiaPostResult["target_id"])) {
                                $response["error"] = FALSE;
                            } else
                                $response["error_msg"] = "Question target id was not updated";
                        } else
                            $response["upload_image"] = "Image was not successful uploaded";
                    } else
                        $response["error_msg"] = "Target was not post successful";
                } else
                    $response["error_msg"] = "Image format is not supported";

            } else
                $response["error_msg"] = "Target was not unlinked on server";

        } else
            $response["error_msg"] = "Target was not deleted successful";

    } else
        $response["error_msg"] = "Question was not find";

} else {
    $response["error_msg"] = "Missing required params: ".isset($_POST["question_id"])." ".isset($_FILES["image"]["tmp_name"])." ".isset($_POST["target_name"])." ".isset($_POST["width"]);
}

echo json_encode($response);

function getImageAsBase64($filePath){
    $file = file_get_contents($filePath);
    if( $file ){
        $file = base64_encode( $file );
    }
    return $file;
}