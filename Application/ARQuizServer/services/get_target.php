<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 19:16
 */

require_once("Vuforia/GetTarget.php");

$uploadDir = "../uploads/targets/";

$response = array("error" => TRUE);

if(isset($_POST["target_id"])) {
    $vuforiaService = new GetTarget();
    $vuforia = json_decode($vuforiaService->GetTargetInfo($_POST["target_id"]), true);

    $response["vuforia"] = json_encode($vuforia);

    if (isset($vuforia["result_code"]) && $vuforia["result_code"] == "Success") {
        $targetRecord = $vuforia["target_record"];

        $response["error"] = FALSE;
        $response["target_id"] = $targetRecord["target_id"];
        $response["target_name"] = $targetRecord["name"];
        $response["target_width"] = $targetRecord["width"];
        $response["target_rate"] = $targetRecord["tracking_rating"];
        $response["target_status"] = $vuforia["status"];

        $image = $uploadDir.$targetRecord["name"];

        $response["target_image"] = $image;
    }
} else {
    $response["error_msg"] = "Missing required params";
}

echo json_encode($response);

function getImageAsBase64($filePath){
    $file = file_get_contents($filePath);
    if( $file ){
        $file = base64_encode( $file );
    }
    return $file;
}