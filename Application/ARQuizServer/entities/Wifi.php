<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:24
 */

namespace entities;


class Wifi
{
    private $id;
    private $ssid;
    private $mac;

    function __construct($id, $ssid, $mac)
    {
        $this->id = $id;
        $this->ssid = $ssid;
        $this->mac = $mac;
    }

    public function getId()
    {
        return $this->id;
    }

    public function getMac()
    {
        return $this->mac;
    }

    public function getSsid()
    {
        return $this->ssid;
    }
}