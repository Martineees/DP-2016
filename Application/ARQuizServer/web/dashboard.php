<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 27.2.2017
 * Time: 15:08
 */

session_start();

//redirect to dashboard if logged in
if(!isset($_SESSION["isLoggedIn"]) || !boolval($_SESSION["isLoggedIn"]))
    header("Location: index.php");



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
        <h1>Your competitions</h1>
        <div id="cmp-box">There are no competitions.</div>
        <div id="addButton" class="button item ic ic-add"></div>
    </div>
    <div id="footer"></div>
</body>

</html>
