<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:11
 */

namespace dao;

use entities\Competition;
use entities\Question;

class CompetitionsDAO
{
    private $db;

    function __construct(\mysqli $db) {
        $this->db = $db;
    }

    public function create(Competition $competition) {
        $id = $this->exists($competition);

        if($id != null) return -1;

        $stmt = $this->db->prepare("INSERT INTO competitions VALUES(DEFAULT,?,?, DEFAULT, ?)");
        $stmt->bind_param("sis", $competition->getName(), $competition->getOwnerId(), $competition->getDescription());
        $stmt->execute();

        return $stmt->insert_id;
    }

    private function exists(Competition $competition) {
        $stmt = $this->db->prepare("SELECT id FROM competitions WHERE name=?");
        $stmt->bind_param("s", $competition->getName());
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

    public function getCompetitionById($id) {
        $stmt = $this->db->prepare("SELECT name, owner, created, description  FROM competitions WHERE id=?");
        $stmt->bind_param("i", $id);
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($name, $owner, $created, $description);
        $stmt -> fetch();

        $num_rows = $stmt->num_rows;

        if($num_rows == 1) {
            return new Competition($id, $name, $owner, $created, $description);
        }

        return null;
    }

    public function getCompetitionsByOwner($ownerId) {
        $stmt = $this->db->prepare("SELECT id,name,created,description FROM competitions WHERE owner=? ORDER BY created");
        $stmt->bind_param("i", $ownerId);
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($id, $name, $created, $description);

        $results = null;

        if($stmt->num_rows > 0) {

            $results = new \ArrayObject();

            while($stmt -> fetch()) {
                $competition = new Competition($id, $name, $ownerId, $created, $description);

                $results->append($competition);
            }
        }

        return $results;
    }

    public function getAllCompetitions() {
        $stmt = $this->db->prepare("SELECT id,name, owner, created, description FROM competitions ORDER BY created");
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($id, $name, $ownerId, $created, $description);

        $results = null;

        if($stmt->num_rows > 0) {

            $results = new \ArrayObject();

            while($stmt -> fetch()) {
                $competition = new Competition($id, $name, $ownerId, $created, $description);

                $results->append($competition);
            }
        }

        return $results;
    }

    public function getAllCompetitionsJSONArray() {
        $stmt = $this->db->prepare("SELECT id,name, owner, created, description FROM competitions ORDER BY created");
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($id, $name, $ownerId, $created, $description);

        $results = null;

        if($stmt->num_rows > 0) {

            $results = new \ArrayObject();

            while($stmt -> fetch()) {
                $competition = array("id" => $id);
                $competition["name"] = $name;
                $competition["owner"] = $ownerId;
                $competition["created"] = $created;
                $competition["description"] = $description;

                $results->append($competition);
            }
        }

        return $results;
    }

    public function getCompetitionChart($competitionId) {
        $stmt = $this->db->prepare("SELECT cmr.id, u.name, SUM(q.score) as score 
                                    FROM competitors cmr 
                                      JOIN users u on u.id=cmr.user_id 
                                      JOIN answers_log a on a.competitor_id = cmr.id 
                                      JOIN questions q on q.id = a.question_id 
                                    WHERE cmr.competition_id=?
                                    GROUP BY u.name
                                    ORDER BY score DESC");
        $stmt->bind_param("i", $competitionId);
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($id, $name, $score);

        $results = new \ArrayObject();

        if($stmt->num_rows > 0) {

            while($stmt -> fetch()) {
                $chartItem = array("competitor_id" => $id);
                $chartItem["name"] = $name;
                $chartItem["score"] = $score;

                $results->append($chartItem);
            }
        }

        return $results;
    }

    public function updateCompetitionDetails(Competition $competition) {

        $stmt = $this->db->prepare("UPDATE competitions SET name=?, description=? WHERE id=?");
        $stmt->bind_param("ssi", $competition->getName(), $competition->getDescription(), $competition->getId());
        $stmt->execute();

        if($stmt->affected_rows == 1) return true;

        return false;
    }
}