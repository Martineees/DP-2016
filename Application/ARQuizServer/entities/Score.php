<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:24
 */

namespace entities;


class Score
{
    private $id;
    private $competitorId;
    private $value;

    function __construct($id, $competitorId, $value)
    {
        $this->id = $id;
        $this->competitorId = $competitorId;
        $this->value = $value;
    }

    public function getId()
    {
        return $this->id;
    }

    /**
     * @return mixed
     */
    public function getCompetitorId()
    {
        return $this->competitorId;
    }

    /**
     * @return mixed
     */
    public function getValue()
    {
        return $this->value;
    }
}