<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 27.2.2017
 * Time: 15:08
 */
require_once("../dbConnect.php");
require_once("../dao/CompetitionsDAO.php");
require_once("../entities/Competition.php");
require_once("../dao/QuestionsDAO.php");
require_once("../entities/Question.php");
require_once("../dao/AnswersDAO.php");
require_once("../entities/Answer.php");
require_once("../dao/LocationDAO.php");
require_once("../entities/Location.php");

use dao\AnswersDAO;
use dao\LocationDAO;
use dao\QuestionsDAO;
use entities\Competition;
use dao\CompetitionsDAO;

session_start();

//redirect to dashboard if logged in
if(!isset($_SESSION["isLoggedIn"]) || !boolval($_SESSION["isLoggedIn"]))
    header("Location: index.php");



$dbConnect = new DBConnect();
$db = $dbConnect->connect();
$competitionsDAO = new CompetitionsDAO($db);
$questionsDAO = new QuestionsDAO($db);
$answerDAO = new AnswersDAO($db);
$locationDAO = new LocationDAO($db);

$competition = null;
$question = null;
$answers = null;
$correctAnswer = null;
$locations = null;

if(isset($_GET["id"])) {
    $userId = $_SESSION["userId"];

    $question = $questionsDAO->getQuestionById($_GET["id"]);

    if($question == null)
        header("Location: dashboard.php");

    $competition = $competitionsDAO->getCompetitionById($question->getCompetitionId());

    if($userId != $competition->getId())
        header("Location: dashboard.php");

    $answers = $answerDAO->getAnswers($question->getId());
    $correctAnswer = $answerDAO->getCorrectAnswer($question->getId());

    $locations = $locationDAO->getAllLocations();
}
?>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
    <title>ARQuiz Web Client</title>

    <link type="text/css" rel="stylesheet" charset="UTF-8" href="styles/style.css">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700" rel="stylesheet">

    <script type="application/javascript" charset="UTF-8" src="js/jquery-3.1.1.min.js"></script>
    <script type="application/javascript" charset="UTF-8" src="js/main.js"></script>
</head>

<body id="private-page">
<div id="header" class="shadow">
    <div class="header-box">
        <div class="logo"></div>
        <div id="nav"></div>
        <div class="logout">
            <a href="logout.php">Logout</a>
        </div>
        <div class="name"><?php echo $_SESSION["userName"] ?></div>
    </div>
</div>
<div id="main" class="shadow">
    <h1>Question detail: <?php echo $question->getName(); ?></h1>
    <div>
        <div class="col col-60">
            <h2 id="targetTitle">Target image</h2>
            <div class="target-rate hide">
                <div class="ic ic-star"></div>
                <div class="ic ic-star"></div>
                <div class="ic ic-star"></div>
                <div class="ic ic-star"></div>
                <div class="ic ic-star"></div>
            </div>
            <div id="targetImage" class="target-preview" data-target="<?php echo $question->getTargetId(); ?>">
                <div class="loader"></div>

                <div class="form-box target-form-box">
                    <form id="changeTargetForm" class="hide">
                        <h2>Change target image</h2>
                        <label>New target image</label>
                        <p class="note">.jpg or .png</p>
                        <div class="input-file-box">
                            <input type="file" name="image" id="image" accept="image/jpeg, image/png" required />
                            <label for="image">Choose a file</label>
                        </div>
                        <label for="width">Image width in real world (mm)</label>
                        <input type="number" id="width" name="width" required>
                        <input type="hidden" name="target_name" id="target_name" />
                        <input type="hidden" name="question_id" value="<?php echo $question->getId(); ?>" />
                        <div class="button-box">
                            <input type="submit" value="Save" class="main-btn ic-af ic-save"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="col col-40">
            <div>
                <h2>Details</h2>
                <div class="form-box">
                    <form id="questionDetails" autocomplete="off">
                        <label for="name">Question</label>
                        <input type="text" name="name" id="name" value="<?php echo $question->getName(); ?>" required />
                        <label for="location_id">Question location</label>
                        <select id="location_id" name="location_id" required>
                            <?php
                            if($locations != null) {

                                foreach ($locations as $location) {
                                    $selected = "";
                                    if($location->getId() == $question->getLocationId())
                                        $selected = "selected";

                                    echo "<option value=\"".$location->getId()."\" ".$selected.">".$location->getBlock().$location->getFloor()."</option>";
                                }
                            }
                            ?>
                        </select>

                        <label for="score">Score</label>
                        <input type="number" name="score" id="score" value="<?php echo $question->getScore(); ?>" required />

                        <input type="hidden" name="question_id" value="<?php echo $question->getId(); ?>" />
                        <div class="button-box">
                            <input type="submit" value="Save" class="main-btn ic-af ic-save"/>
                        </div>
                    </form>
                </div>
            </div>
            <div>
                <h2>Answers</h2>
                <div class="form-box">
                    <form id="questionAnswers" autocomplete="off">
                        <label for="type">Question type</label>
                        <select id="type" name="type" required>

                            <?php
                            if(count($answers) > 1) {
                                echo "<option value=\"0\" selected>With options</option>";
                                echo "<option value=\"1\">Writable answer</option>";
                            } else {
                                echo "<option value=\"0\">With options</option>";
                                echo "<option value=\"1\" selected>Writable answer</option>";
                            }
                            ?>
                        </select>
                        <label>Correct answer</label>
                        <p class="note">Answers will be suffled</p>
                        <input type="text" name="answer[]" value="<?php echo $correctAnswer->getName(); ?>" required>
                        <div id="otherAnswers">
                            <?php
                            $first = true;
                                foreach ($answers as $answer) {
                                    if($answer->getIsCorrect() != 1) {
                                        if($first) {
                                            echo "<label class='label-incorrect'>Incorrect answer</label>";
                                            $first = false;
                                        }
                                        echo "<input type=\"button\" class=\"second-btn remove-btn\" value=\"remove\" />";
                                        echo "<input type=\"text\" name=\"answer[]\" value='".$answer->getName()."'>";
                                    }
                                }
                            ?>
                        </div>
                        <input type="button" id="addAnswer" class="second-btn" value="+ add answer" />

                        <input type="hidden" name="question_id" value="<?php echo $question->getId(); ?>" />

                        <div class="button-box">
                            <input type="submit" value="Save" class="main-btn ic-af ic-save"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="footer"></div>
</body>

</html>
