<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:24
 */

namespace entities;


class Answer
{
    public static $correct = 1;
    public static $incorrect = 0;

    private $id;
    private $name;
    private $questionId;
    private $isCorrect;

    function __construct($id, $name, $questionId, $isCorrect)
    {
        $this->id = $id;
        $this->name = $name;
        $this->questionId = $questionId;
        $this->isCorrect = $isCorrect;
    }

    public function getId()
    {
        return $this->id;
    }

    public function getName()
    {
        return $this->name;
    }

    public function getIsCorrect()
    {
        return $this->isCorrect;
    }

    public function getQuestionId()
    {
        return $this->questionId;
    }
}