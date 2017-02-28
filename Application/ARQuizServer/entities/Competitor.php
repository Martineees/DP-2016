<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:24
 */

namespace entities;


class Competitor
{
    private $id;
    private $competitionId;
    private $userId;

    function __construct($id, $competitionId, $userId)
    {
        $this->id = $id;
        $this->competitionId = $competitionId;
        $this->userId = $userId;
    }

    public function getId()
    {
        return $this->id;
    }

    public function getCompetitionId()
    {
        return $this->competitionId;
    }

    public function getUserId()
    {
        return $this->userId;
    }
}