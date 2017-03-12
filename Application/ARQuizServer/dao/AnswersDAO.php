<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:11
 */

namespace dao;


use entities\Answer;

class AnswersDAO
{
    private $db;

    function __construct(\mysqli $db) {
        $this->db = $db;
    }

    public function create(Answer $answer) {
        $id = $this->exists($answer);

        if($id != null) return -1;

        $stmt = $this->db->prepare("INSERT INTO answers VALUES(DEFAULT,?,?,?)");
        $stmt->bind_param("sii", $answer->getName(), $answer->getQuestionId(), $answer->getIsCorrect());
        $stmt->execute();

        return $stmt->insert_id;
    }

    private function exists(Answer $answer) {
        $stmt = $this->db->prepare("SELECT id FROM answers WHERE name=?");
        $stmt->bind_param("s", $answer->getName());
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($id);
        $stmt -> fetch();

        $num_rows = $stmt->num_rows;

        if($num_rows == 1) {
            return $id;
        }

        return null;
    }

    public function getAnswers($questionId)
    {
        $stmt = $this->db->prepare("SELECT id,name,is_correct FROM answers WHERE question_id=?");
        $stmt->bind_param("i", $questionId);
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($id, $name, $isCorrect);

        $results = null;

        if ($stmt->num_rows > 0) {

            $results = new \ArrayObject();

            while ($stmt->fetch()) {
                $answer = new Answer($id, $name, $questionId, $isCorrect);

                $results->append($answer);
            }
        }

        return $results;
    }
    public function getCorrectAnswer($questionId)
    {
        $stmt = $this->db->prepare("SELECT id,name,is_correct FROM answers WHERE question_id=? AND is_correct=1");
        $stmt->bind_param("i", $questionId);
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($id, $name, $isCorrect);

        $result = null;

        if ($stmt->num_rows == 1) {
            $stmt->fetch();
            $result = new Answer($id, $name, $questionId, $isCorrect);
        }

        return $result;
    }


    public function getAnswersJSONArray($questionId)
    {
        $stmt = $this->db->prepare("SELECT id,name,is_correct FROM answers WHERE question_id=?");
        $stmt->bind_param("i", $questionId);
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($id, $name, $isCorrect);

        $results = null;

        if($stmt->num_rows > 0) {

            $results = new \ArrayObject();

            while($stmt -> fetch()) {
                $answer = array("id" => $id);
                $answer["name"] = $name;
                $answer["isCorrect"] = $isCorrect;

                $results->append($answer);
            }
        }

        return $results;
    }

    public function updateAnswer($id, $name) {
        $stmt = $this->db->prepare("UPDATE answers SET name=? WHERE id=?");
        $stmt->bind_param("si", $name, $id);
        $stmt->execute();

        if($stmt->affected_rows == 1) return true;

        return false;
    }

    public function removeAllIncorrectAnswers($questionId) {
        $stmt = $this->db->prepare("DELETE FROM answers WHERE question_id=? AND is_correct=0");
        $stmt->bind_param("i", $questionId);
        $stmt->execute();

        if($stmt->affected_rows > 0) return true;

        return false;
    }
}