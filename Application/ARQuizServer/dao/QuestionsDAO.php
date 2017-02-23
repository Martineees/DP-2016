<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:11
 */

namespace dao;


use entities\Question;

class QuestionsDAO
{
    private $db;

    function __construct(\mysqli $db) {
        $this->db = $db;
    }

    public function create(Question $question) {
        $id = $this->exists($question);

        if($id != null) return -1;

        $stmt = $this->db->prepare("INSERT INTO questions VALUES(DEFAULT,?,?,?,?)");
        $stmt->bind_param("siii", $question->getName(), $question->getCompetitionId(), $question->getTargetId(), $question->getLocationId());
        $stmt->execute();

        return $stmt->insert_id;
    }

    private function exists(Question $question) {
        $stmt = $this->db->prepare("SELECT id FROM questions WHERE name=?");
        $stmt->bind_param("s", $question->getName());
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
}