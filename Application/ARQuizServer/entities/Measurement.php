<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:24
 */

namespace entities;


class Measurement
{
    private $id;
    private $level;
    private $blockID;
    private $wifiID;

    function __construct($id, $level, $blockID, $wifiID)
    {
        $this->id = $id;
        $this->level = $level;
        $this->blockID = $blockID;
        $this->wifiID = $wifiID;
    }

    public function getBlockID()
    {
        return $this->blockID;
    }

    public function getId()
    {
        return $this->id;
    }

    public function getLevel()
    {
        return $this->level;
    }

    public function getWifiID()
    {
        return $this->wifiID;
    }
}