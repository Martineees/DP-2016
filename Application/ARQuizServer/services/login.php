<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 19:16
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

    $user = $usersDAO->getUserByName($_POST['name_login']);

    if($user != null) {
        if(password_verify($_POST['password_login'], $user->getPasswordHash())) {

            $response["error"] = FALSE;
            $response["user"] = $user->getName();
            $response["is_admin"] = $user->getIsAdmin();
            $response["user_id"] = $user->getId();

            //create session for webpage
            if(isset($_POST['from_web']) && boolval($_POST['from_web'])) {

                session_start();

                $_SESSION["isLoggedIn"] = TRUE;
                $_SESSION["userId"] = $user->getId();
                $_SESSION["userName"] = $user->getName();
            }

            echo json_encode($response);
        } else {
            $response["error_msg"] = "Wrong email or password";
            echo json_encode($response);
        }
    } else {
        $response["error_msg"] = "Wrong email or password";
        echo json_encode($response);
    }


} else {
    $response["error_msg"] = "Missing email or password";
    echo json_encode($response);;
}