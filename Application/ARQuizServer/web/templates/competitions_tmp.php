<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 28.2.2017
 * Time: 10:20
 */
require_once("../../dbConnect.php");
require_once("../../dao/CompetitionsDAO.php");
require_once("../../entities/Competition.php");

use entities\Competition;
use dao\CompetitionsDAO;

session_start();

//redirect to dashboard if logged in
if(!isset($_SESSION["isLoggedIn"]) || !boolval($_SESSION["isLoggedIn"]))
    header("Location: index.php");

$dbConnect = new DBConnect();
$db = $dbConnect->connect();
$competitionsDAO = new CompetitionsDAO($db);

$competitions = null;
if(isset($_SESSION["userId"])) {
    $competitions = $competitionsDAO->getCompetitionsByOwner($_SESSION["userId"]);
} else {
    echo "userId session is not defined";
}

if($competitions != null):
    foreach($competitions as $competition):
        ?>

        <a href="edit_competition.php?id=<?php echo $competition->getId(); ?>"  class="item main-item">
            <div class="item-box">
                <div class="item-content">
                    <h2><?php echo $competition->getName(); ?></h2>
                    <p>Created: <?php echo $competition->getCreated(); ?></p>
                </div>
            </div>
        </a>

    <?php endforeach;
endif;



