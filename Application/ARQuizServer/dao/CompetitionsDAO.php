<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:11
 */

namespace dao;


use entities\Answer;

class UsersDAO
{
    private $db;

    function __construct(\mysqli $db) {
        $this->db = $db;
    }

    public function create(Answer $answer) {
        $id = $this->exists($answer);

        if($id != null) return -1;

        $stmt = $this->db->prepare("INSERT INTO answers VALUES(DEFAULT,?,?,?)");
        $stmt->bind_param("sib", $answer->getName(), $answer->getQuestionId(), $answer->getIsCorrect());
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
}