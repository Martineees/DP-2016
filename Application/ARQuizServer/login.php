<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 19:16
 */

require_once ("dbConnect.php");
require_once("entities/User.php");
require_once ("dao/UsersDAO.php");

use entities\User;
use dao\UsersDAO;

$dbConnect = new DBConnect();
$db = $dbConnect->connect();

$usersDAO = new UsersDAO($db);

if(isset($_GET['name_login']) && isset($_GET['password_login'])){

    $user = $usersDAO->getUserByName($_GET['name_login']);

    if($user != null) {
        if(password_verify($_GET['password_login'], $user->getPasswordHash())) {
            echo 1;
        } else echo -1;
    } else echo -1;


} else {
    echo -2;
}