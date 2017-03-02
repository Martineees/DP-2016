<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:11
 */

namespace dao;


use entities\Location;

class LocationDAO
{
    private $db;

    function __construct(\mysqli $db) {
        $this->db = $db;
    }

    public function create(Location $location) {
        $id = $this->exists($location);

        if($id != null) return $id;

        $stmt = $this->db->prepare("INSERT INTO location VALUES(DEFAULT,?,?)");
        $stmt->bind_param("si", $location->getBlock(), $location->getFloor());
        $stmt->execute();

        return $stmt->insert_id;
    }

    private function exists(Location $location) {
        $stmt = $this->db->prepare("SELECT id FROM location WHERE block=? AND floor=?");
        $stmt->bind_param("si", $location->getBlock(), $location->getFloor());
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($id);
        $stmt -> fetch();

        $num_rows = $stmt->num_rows;

        if($num_rows == 1) {
            return $id;
        }

        return null;
    }

    public function getLocationByID($locationID) {
        $stmt = $this->db->prepare("SELECT id, block, floor FROM location WHERE id=?");
        $stmt->bind_param("i", $locationID);
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($id, $block, $floor);
        $stmt -> fetch();

        $num_rows = $stmt->num_rows;

        if($num_rows == 1) {
            return new Location($id, $block, $floor);
        }

        return null;
    }

    public function getAllLocations() {
        $stmt = $this->db->prepare("SELECT id, block, floor FROM location ORDER BY block, floor");
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($id, $block, $floor);
        $stmt -> fetch();

        $results = null;

        if($stmt->num_rows > 0) {

            $results = new \ArrayObject();

            while($stmt -> fetch()) {
                $location = new Location($id, $block, $floor);

                $results->append($location);
            }
        }

        return $results;
    }
}