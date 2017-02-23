<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:24
 */

namespace entities;


class Question
{
    private $id;
    private $name;
    private $competitionId;
    private $targetId;
    private $locationId;

    function __construct($id, $name, $competitionId, $targetId, $locationId)
    {
        $this->id = $id;
        $this->name = $name;
        $this->competitionId = $competitionId;
        $this->targetId = $targetId;
        $this->locationId = $locationId;
    }

    public function getId()
    {
        return $this->id;
    }

    public function getName()
    {
        return $this->name;
    }

    public function getCompetitionId()
    {
        return $this->competitionId;
    }

    public function getTargetId()
    {
        return $this->targetId;
    }

    public function getLocationId()
    {
        return $this->locationId;
    }
}