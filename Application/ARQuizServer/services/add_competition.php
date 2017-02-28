<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 19:16
 */

require_once("../dbConnect.php");
require_once("../dao/CompetitionsDAO.php");
require_once("../entities/Competition.php");

use entities\Competition;
use dao\CompetitionsDAO;

$dbConnect = new DBConnect();
$db = $dbConnect->connect();

$competitionsDAO = new CompetitionsDAO($db);

$response = array("error" => TRUE);

if(isset($_POST["name"]) && isset($_POST["id"])) {
    $competition = new Competition(null, $_POST["name"], $_POST["id"], null);

    $result = $competitionsDAO->create($competition);

    if($result != null) {
        $response["error"] = FALSE;
        $response["id"] = $result;
    } else {
        $response["error_msg"] = "Competition was not created";
    }
} else {
    $response["error_msg"] = "Missing required parameters";
}

echo $response;
