<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:24
 */

namespace entities;


class AnswerLog
{
    private $id;
    private $competitorId;
    private $questionId;
    private $isCorrect;

    function __construct($id, $competitorId, $questionId, $isCorrect)
    {
        $this->id = $id;
        $this->competitorId = $competitorId;
        $this->questionId = $questionId;
        $this->isCorrect = $isCorrect;
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
    public function isCorrect()
    {
        return $this->isCorrect;
    }

    /**
     * @return mixed
     */
    public function getQuestionId()
    {
        return $this->questionId;
    }
}