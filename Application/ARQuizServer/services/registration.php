<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 22.2.2017
 * Time: 19:43
 */

require_once("../dbConnect.php");
require_once("../entities/User.php");
require_once("../dao/UsersDAO.php");

use entities\User;
use dao\UsersDAO;

$dbConnect = new DBConnect();
$db = $dbConnect->connect();

$usersDAO = new UsersDAO($db);

$response = array("error" => TRUE);

if(isset($_POST['name_login']) && isset($_POST['password_login'])){
    $passowrdHash = password_hash($_POST['password_login'], PASSWORD_BCRYPT );

    $user = new User(null, $_POST['name_login'], $passowrdHash, false);

    $result = $usersDAO->create($user);

    if($result == -1) {
        $response["error_msg"] = "User is already registered";
    } else {
        $response["error"] = FALSE;
        $response["user_id"] = $result;
    }

} else {
    $response["error_msg"] = "Missing required params";
}

echo json_encode($response);