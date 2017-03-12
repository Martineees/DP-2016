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
    public static $withOptions = 0;
    public static $writableAnswer = 1;

    private $id;
    private $name;
    private $competitionId;
    private $targetId;
    private $locationId;
    private $type;
    private $score;

    function __construct($id, $name, $competitionId, $targetId, $locationId, $type, $score)
    {
        $this->id = $id;
        $this->name = $name;
        $this->competitionId = $competitionId;
        $this->targetId = $targetId;
        $this->locationId = $locationId;
        $this->type = $type;
        $this->score = $score;
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
    public function getType()
    {
        return $this->type;
    }

    public function getScore()
    {
        return $this->score;
    }
}