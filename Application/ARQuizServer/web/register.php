<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 27.2.2017
 * Time: 15:08
 */

?>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ARQuiz Web Client</title>

    <link type="text/css" rel="stylesheet" charset="UTF-8" href="styles/style.css">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700" rel="stylesheet">

    <script type="application/javascript" charset="UTF-8" src="js/jquery-3.1.1.min.js"></script>
    <script type="application/javascript" charset="UTF-8" src="js/main.js"></script>
</head>

<body id="public-page">
    <div id="content">
        <div class="logo"></div>
        <form id="registerForm" autocomplete="off">
            <div class="row">
                <label for="nameLogin">Email</label>
                <input type="text" name="name_login" id="nameLogin" required/>
            </div>
            <div class="row">
                <label for="passwordLogin">Password</label>
                <input type="password" name="password_login" id="passwordLogin" required/>
            </div>
            <div class="row">
                <label for="passwordLoginRepeat">Password again</label>
                <input type="password" name="password_login_repeat" id="passwordLoginRepeat" required/>
            </div>
            <input type="submit" value="Register" id="registerBtn" class="main-btn">
        </form>
    </div>
    <div id="footer"></div>
</body>

</html>
