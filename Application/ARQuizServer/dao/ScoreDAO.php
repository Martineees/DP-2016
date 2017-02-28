<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:11
 */

namespace dao;


use entities\Score;

class ScoreDAO
{
    private $db;

    function __construct(\mysqli $db) {
        $this->db = $db;
    }

    public function create(Score $score) {
        $id = $this->exists($score);

        if($id != null) return -1;

        $stmt = $this->db->prepare("INSERT INTO score VALUES(DEFAULT,?,?)");
        $stmt->bind_param("sib", $score->getCompetitorId(), $score->getValue());
        $stmt->execute();

        return $stmt->insert_id;
    }

    private function exists(Score $score) {
        $stmt = $this->db->prepare("SELECT id FROM score WHERE competitor_id=?");
        $stmt->bind_param("s", $score->getCompetitorId());
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

    public function getScore($competitorId) {
        $stmt = $this->db->prepare("SELECT value FROM score WHERE competitor_id=?");
        $stmt->bind_param("i", $competitorId);
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($value);
        $stmt -> fetch();

        $num_rows = $stmt->num_rows;

        if($num_rows == 1) {
            return $value;
        }

        return null;
    }

    public function updateScore(Score $newScore) {
        $stmt = $this->db->prepare("UPDATE score SET value=? WHERE competitor_id=?");
        $stmt->bind_param("ii", $newScore->getValue(), $newScore->getCompetitorId());
        $stmt->execute();
    }
}