<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:00
 */

require_once ("dbConnect.php");
require_once ("dao/LocationDAO.php");
require_once ("dao/MeasurementDAO.php");
require_once ("dao/WifiDAO.php");
require_once ("entities/Location.php");
require_once ("entities/Measurement.php");
require_once ("entities/Wifi.php");

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


if (($handle = fopen("ExportDB-1480355183020.csv", "r")) !== FALSE) {
    while (($data = fgetcsv($handle, 1000, ",")) !== FALSE) {
        $num = count($data);

        echo "location: ".$data[0]." ".$data[1]."\n";

        $location = new Location(null, $data[0], $data[1]);
        $locationID = $locationDAO->create($location);

        echo "wifi: ".$data[3]." ".$data[2]."\n";
        $wifi = new Wifi(null, $data[3], $data[2]);
        $wifiID = $wifiDAO->create($wifi);

        echo "measure: ".$data[4]." ".$locationID." ".$wifiID."\n";
        $measurement = new Measurement(null, $data[4], $locationID, $wifiID);
        $measurementID = $measurementDAO->create($measurement);

        echo "\n";
    }
}

$db->close();