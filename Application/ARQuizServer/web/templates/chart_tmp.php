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

$result = null;
if(isset($_POST["id"])) {
    $result = $competitionsDAO->getCompetitionChart($_POST["id"]);
} else {
    echo "Competition id is not defined";
}

?>
<table class="chart-table">
    <tr>
        <th>Pos.</th>
        <th>User</th>
        <th>Pts.</th>
    </tr>
<?php
if($result != null):
    $index = 1;
    foreach($result as $chart):
        ?>

        <tr>
            <td class="position"><?php echo $index; ?>.</td>
            <td class="user"><?php echo $chart["name"]; ?></td>
            <td class="points"><?php echo $chart["score"]; ?></td>
        </tr>

    <?php
    $index++;
    endforeach;
endif;
?>
</table>



