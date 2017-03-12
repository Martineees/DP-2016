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

$dbConnect = new DBConnect();
$db = $dbConnect->connect();

$questionsDAO = new QuestionsDAO($db);
$answersDAO = new AnswersDAO($db);

$response = array("error" => TRUE);

if(isset($_POST["question_id"]) && isset($_POST["type"]) && isset($_POST["answer"])) {

    $question = $questionsDAO->getQuestionById($_POST["question_id"]);
    $currentAnswers = $answersDAO->getAnswers($_POST["question_id"]);
    $newAnswers = $_POST["answer"];

    //remove all incorrect answers
    if($question->getType() == Question::$withOptions) {
        $result = $answersDAO->removeAllIncorrectAnswers($question->getId());

        if($result)
            $response["clear_incorrect"] = TRUE;
        else
            $response["clear_incorrect"] = FALSE;
    }

    if($question->getType() != intval($_POST["type"])) {
        if ($questionsDAO->updateQuestionType($question->getId(), intval($_POST["type"])))
            $response["update_type"] = TRUE;
        else
            $response["update_type"] = FALSE;
    }

    $first = true;
    $firstUpdated = false;
    $allCreated = true;

    foreach ($newAnswers as $answer) {
        if($first) {
            $id = $currentAnswers[0]->getId();

            if($currentAnswers[0]->getId() != $answer)
                $result = $answersDAO->updateAnswer($id, $answer);
            else {
                $result = true;
            }

            if($result) $firstUpdated = true;

            $first = false;
        } else {
            if(intval($_POST["type"]) == Question::$withOptions) {
                $newAnswer = new Answer(null, $answer, $question->getId(), Answer::$incorrect);
                $id = $answersDAO->create($newAnswer);

                if($id == null) $allCreated = false;
            } else
                $response["anwers"] = "No incorrect answers";
        }
    }

    if($allCreated && $firstUpdated) {
        $response["error"] = FALSE;
        $response["msg"] = "Answers was successful updated";
    } else {
        $response["error_msg"] = "First updated = ".$firstUpdated." allCreated = ".$allCreated;
    }

} else {
    $response["error_msg"] = "Missing required params";
}

echo json_encode($response);

function checkExitst($answers, Answer $compareAnswer) {
    foreach ($answers as $answer) {
        if($answer->getId() == $compareAnswer->getId()) {
            return $compareAnswer->getId();
        }
    }

    return null;
}
