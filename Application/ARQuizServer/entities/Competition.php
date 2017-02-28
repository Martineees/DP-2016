<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:24
 */

namespace entities;


class Competition
{
    private $id;
    private $name;
    private $ownerId;
    private $created;

    function __construct($id, $name, $ownerId, $created)
    {
        $this->id = $id;
        $this->name = $name;
        $this->ownerId = $ownerId;
        $this->created = $created;
    }

    public function getId()
    {
        return $this->id;
    }

    public function getName()
    {
        return $this->name;
    }

    public function getOwnerId()
    {
        return $this->ownerId;
    }

    public function getCreated()
    {
        return $this->created;
    }
}