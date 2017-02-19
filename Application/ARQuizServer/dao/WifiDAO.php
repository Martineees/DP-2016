<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:12
 */

namespace dao;


use entities\Wifi;

class WifiDAO
{
    private $db;

    function __construct(\mysqli $db) {
        $this->db = $db;
    }

    public function create(Wifi $wifi) {
        $id = $this->exists($wifi);

        if($id != null) return $id;

        $stmt = $this->db->prepare("INSERT INTO wifi VALUES(DEFAULT,?,?)");
        $stmt->bind_param("ss", $wifi->getSsid(), $wifi->getMac());
        $stmt->execute();

        return $stmt->insert_id;
    }

    private function exists(Wifi $wifi) {
        $stmt = $this->db->prepare("SELECT id FROM wifi WHERE mac=?");
        $stmt->bind_param("s", $wifi->getMac());
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

    public function findWifiByMac($mac) {
        $stmt = $this->db->prepare("SELECT id, ssid, mac FROM wifi WHERE mac=?");
        $stmt->bind_param("s", $mac);
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($id, $ssid, $macAddr);
        $stmt -> fetch();

        if($stmt->num_rows == 1) {
            return new Wifi($id, $ssid, $macAddr);
        }

        return false;
    }
}