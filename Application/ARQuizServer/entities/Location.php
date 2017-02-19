<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:24
 */

namespace entities;


class Location
{
    private $id;
    private $block;
    private $floor;

    function __construct($id, $block, $floor)
    {
        $this->id = $id;
        $this->block = $block;
        $this->floor = $floor;
    }

    public function getBlock()
    {
        return $this->block;
    }

    public function getFloor()
    {
        return $this->floor;
    }

    public function getId()
    {
        return $this->id;
    }
}