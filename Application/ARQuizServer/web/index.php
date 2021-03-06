<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 27.2.2017
 * Time: 15:08
 */

session_start();

//redirect to dashboard if logged in
if(isset($_SESSION["isLoggedIn"]) && boolval($_SESSION["isLoggedIn"]))
    header("Location: dashboard.php");

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

<body id="public-page">
    <div id="content">
        <div class="logo"></div>
        <form id="loginForm" autocomplete="off">
            <div class="row">
                <label for="nameLogin">Email</label>
                <input type="text" name="name_login" id="nameLogin" required/>
            </div>
            <div class="row">
                <label for="passwordLogin">Password</label>
                <input type="password" name="password_login" id="passwordLogin" required/>
            </div>

            <input type="hidden" name="from_web" value="true" required/>
            <input type="submit" value="Login" id="loginBtn" class="main-btn">
            <a href="register.php">Sign Up</a>
        </form>
    </div>
    <div id="footer"></div>
</body>

</html>
