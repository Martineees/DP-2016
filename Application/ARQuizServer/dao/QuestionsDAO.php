<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:11
 */

namespace dao;


use entities\Question;
use entities\Answer;

class QuestionsDAO
{
    private $db;

    function __construct(\mysqli $db)
    {
        $this->db = $db;
    }

    public function create(Question $question)
    {
        $id = $this->exists($question);

        if ($id != null) return -1;

        $stmt = $this->db->prepare("INSERT INTO questions VALUES(DEFAULT,?,?,?,?)");
        $stmt->bind_param("siii", $question->getName(), $question->getCompetitionId(), $question->getTargetId(), $question->getLocationId());
        $stmt->execute();

        return $stmt->insert_id;
    }

    private function exists(Question $question)
    {
        $stmt = $this->db->prepare("SELECT id FROM questions WHERE name=?");
        $stmt->bind_param("s", $question->getName());
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($id);
        $stmt->fetch();

        $num_rows = $stmt->num_rows;

        if ($num_rows == 1) {
            return $id;
        }

        return null;
    }

    public function getQuestions($competitionId) {
        $stmt = $this->db->prepare("SELECT id,name,target_id,location_id FROM questions WHERE competition_id=?");
        $stmt->bind_param("i", $competitionId);
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($id, $name, $targetId, $locationId);

        $results = null;

        if($stmt->num_rows > 0) {

            $results = new \ArrayObject();

            while($stmt -> fetch()) {
                $question = new Question($id, $name, $competitionId, $targetId, $locationId);

                $results->append($question);
            }
        }

        return $results;
    }
}