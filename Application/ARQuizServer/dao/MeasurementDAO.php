<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:12
 */

namespace dao;


use entities\Measurement;
use entities\Wifi;

class MeasurementDAO
{
    private $db;

    function __construct(\mysqli $db) {
        $this->db = $db;
    }

    public function create(Measurement $measurement) {

        $stmt = $this->db->prepare("INSERT INTO measurement VALUES(DEFAULT ,?,?,?)");
        $stmt->bind_param("iii", $measurement->getLevel(), $measurement->getWifiID(), $measurement->getBlockID());
        $stmt->execute();

        return $stmt->insert_id;
    }

    public function findMeasurementsForWifi(Wifi $wifi) {
        $stmt = $this->db->prepare("SELECT id, level, wifi_id, block_id FROM measurement WHERE wifi_id=?");
        $stmt->bind_param("s", $wifi->getId());
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($id, $level, $wifiId, $blockId);

        $results = null;

        if($stmt->num_rows > 0) {

            $results = new \ArrayObject();

            while($stmt -> fetch()) {
                $measurement = new Measurement($id, $level, $blockId, $wifiId);

                $results->append($measurement);
            }

            return $results;
        }
    }
}