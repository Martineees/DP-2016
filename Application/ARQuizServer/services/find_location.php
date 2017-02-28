<?php

/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 18.2.2017
 * Time: 12:44
 */

require_once("../dbConnect.php");
require_once("../dao/LocationDAO.php");
require_once("../dao/MeasurementDAO.php");
require_once("../dao/WifiDAO.php");
require_once("../entities/Location.php");
require_once("../entities/Measurement.php");
require_once("../entities/Wifi.php");

use dao\LocationDAO;
use dao\MeasurementDAO;
use dao\WifiDAO;
use entities\Location;
use entities\Measurement;
use entities\Wifi;

$dbConnect = new DBConnect();
$db = $dbConnect->connect();

$locationDAO = new LocationDAO($db);
$wifiDAO = new WifiDAO($db);
$measurementDAO = new MeasurementDAO($db);

$response = array("error" => TRUE);

if(isset($_POST['scanResults'])){
    $location = getActualLocation($_POST['scanResults']);

   // echo var_dump($location);

    if($location != null) {
        $response["error"] = FALSE;
        $response["block"] = $location->getBlock();
        $response["floor"] = $location->getFloor();
    } else {
        $response["error_msg"] = "location not found";
    }
} else {
    $response["error_msg"] = "Missing scanResults param";
}

echo json_encode($response);

function getActualLocation($scanResultsJSON)
{
    global $wifiDAO, $measurementDAO, $locationDAO;

    $scanResults = json_decode($scanResultsJSON);

    if (json_last_error() === JSON_ERROR_NONE) {

        $globalDiff = PHP_INT_MAX;
        $globalLocation = null;

        foreach($scanResults as $scan) {
            $wifi = $wifiDAO->findWifiByMac($scan->BSSID);

            if($wifi != null) {
                $measurements = $measurementDAO->findMeasurementsForWifi($wifi);

                $diff = PHP_INT_MAX;
                $selected = null;

                foreach($measurements as $measurement) {
                    $actualDiff = abs($measurement->getLevel() - intval($scan->level));

                    if($actualDiff < $diff) {
                        $diff = $actualDiff;
                        $selected = $measurement;
                    }
                }

                if($selected != null && $diff < $globalDiff) {
                    $globalDiff = $diff;
                    $globalLocation = $locationDAO->getLocationByID($selected->getBlockID());
                }
            }
        }

        return $globalLocation;
    }

    return null;
}