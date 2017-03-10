<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 19:16
 */

require_once("../dbConnect.php");
require_once("../entities/Competition.php");
require_once("../dao/CompetitionsDAO.php");

use dao\CompetitionsDAO;

$dbConnect = new DBConnect();
$db = $dbConnect->connect();

$competitionsDAO = new CompetitionsDAO($db);

$response = array("error" => TRUE);

$competitions = $competitionsDAO->getAllCompetitionsJSONArray();

if($competitions != null) {

    $response["error"] = FALSE;
    $response["competitions"] = json_encode($competitions->getArrayCopy());

} else {
    $response["error_msg"] = "Competitions weren't found";
}

echo json_encode($response);