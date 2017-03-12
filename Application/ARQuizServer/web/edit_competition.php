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

$competition = null;
$question = null;

if(isset($_GET["id"])) {
    $userId = $_SESSION["userId"];

    $competition = $competitionsDAO->getCompetitionById($_GET["id"]);

    if($competition == null || $userId != $competition->getId())
        header("Location: dashboard.php");
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
        <h1>Competition detail: <?php echo $competition->getName(); ?></h1>
        <div class="col col-60">
            <h2>Questions</h2>
            <div id="result-box"><div class="loader"></div></div>
            <div id="addButton" class="button item ic ic-add"></div>
        </div>
        <div class="col col-40">
            <div>
                <h2>Details</h2>
                <div class="form-box">
                    <form id="competitionDetails" autocomplete="off">
                        <label for="name">Name</label>
                        <input type="text" name="name" id="name" value="<?php echo $competition->getName(); ?>" required />
                        <label for="description">Description</label>
                        <textarea name="description" id="description" rows="4"><?php echo $competition->getDescription(); ?></textarea>

                        <input type="hidden" name="competition_id" value="<?php echo $competition->getId(); ?>" />
                        <div class="button-box">
                            <input type="submit" value="Save" class="main-btn ic-af ic-save"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div id="footer"></div>
</body>

</html>
