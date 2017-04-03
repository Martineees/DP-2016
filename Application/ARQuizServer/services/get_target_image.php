<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 19:16
 */

require_once("Vuforia/GetTarget.php");

$uploadDir = "../uploads/targets/";

$image = null;

if(isset($_POST["target_id"])) {
    $vuforiaService = new GetTarget();
    $vuforia = json_decode($vuforiaService->GetTargetInfo($_POST["target_id"]), true);

    $response["vuforia"] = json_encode($vuforia);

    if (isset($vuforia["result_code"]) && $vuforia["result_code"] == "Success") {
        $targetRecord = $vuforia["target_record"];

        $image = $uploadDir.$targetRecord["name"];

        $fp = fopen($image, 'rb');

        // send the right headers
        header("Content-Type: image/png");
        header("Content-Length: " . filesize($image));

        // dump the picture and stop the script
        fpassthru($fp);
    }
}