<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 28.2.2017
 * Time: 10:20
 */
use dao\LocationDAO;
use dao\QuestionsDAO;

require_once("../../dbConnect.php");
require_once("../../dao/QuestionsDAO.php");
require_once("../../entities/Question.php");
require_once("../../dao/LocationDAO.php");
require_once("../../entities/Location.php");

session_start();

//redirect to dashboard if logged in
if(!isset($_SESSION["isLoggedIn"]) || !boolval($_SESSION["isLoggedIn"]))
    header("Location: index.php");

$dbConnect = new DBConnect();
$db = $dbConnect->connect();
$questionsDAO = new QuestionsDAO($db);
$locationDAO = new LocationDAO($db);

$questions = null;
if(isset($_POST["id"])) {
    $questions = $questionsDAO->getQuestions($_POST["id"]);
} else {
    echo "question_id is not defined";
}

if($questions != null):
    foreach($questions as $question):
        $location = $locationDAO->getLocationByID($question->getLocationId());
        ?>

        <a href="edit_question.php?id=<?php echo $question->getId(); ?>"  class="item main-item full-width">
            <div class="item-box">
                <div class="item-content">
                    <h2><?php echo $question->getName(); ?></h2>
                    <p>Location: <?php echo $location->getBlock().$location->getFloor() ?></p>
                </div>
            </div>
        </a>

    <?php endforeach;
endif;



