<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 27.2.2017
 * Time: 21:38
 */

session_start();
session_unset();
header("Location: index.php");