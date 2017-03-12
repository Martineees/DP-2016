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

        $stmt = $this->db->prepare("INSERT INTO questions VALUES(DEFAULT,?,?,?,?,?,?)");
        $stmt->bind_param("sisiii", $question->getName(), $question->getCompetitionId(), $question->getTargetId(), $question->getLocationId(), $question->getType(), $question->getScore());
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

    public function getQuestionById($id)
    {
        $stmt = $this->db->prepare("SELECT name, competition_id, target_id, location_id, type, score FROM questions WHERE id=?");
        $stmt->bind_param("i", $id);
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($name, $competitionId, $targetId, $locationId, $type, $score);
        $stmt->fetch();

        $num_rows = $stmt->num_rows;

        if ($num_rows == 1) {
            return new Question($id, $name, $competitionId, $targetId, $locationId, $type, $score);
        }

        return null;
    }

    public function getQuestions($competitionId) {
        $stmt = $this->db->prepare("SELECT id,name,target_id,location_id,type,score FROM questions WHERE competition_id=?");
        $stmt->bind_param("i", $competitionId);
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($id, $name, $targetId, $locationId, $type, $score);

        $results = null;

        if($stmt->num_rows > 0) {

            $results = new \ArrayObject();

            while($stmt -> fetch()) {
                $question = new Question($id, $name, $competitionId, $targetId, $locationId, $type, $score);

                $results->append($question);
            }
        }

        return $results;
    }

    public function getQuestionsJSONArray($competitionId) {

        $locationDAO = new LocationDAO($this->db);
        $answersDAO = new AnswersDAO($this->db);

        $stmt = $this->db->prepare("SELECT id,name,target_id,location_id,type,score FROM questions WHERE competition_id=?");
        $stmt->bind_param("i", $competitionId);
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($id, $name, $targetId, $locationId, $type, $score);

        $results = null;

        if($stmt->num_rows > 0) {

            $results = new \ArrayObject();

            while($stmt -> fetch()) {

                $location = $locationDAO->getLocationByIDJSONObj($locationId);
                $answers = $answersDAO->getAnswersJSONArray($id);

                $question = array("id" => $id);
                $question["name"] = $name;
                $question["targetId"] = $targetId;
                $question["location"] = json_encode($location);
                $question["type"] = $type;
                $question["score"] = $score;
                $question["answers"] = json_encode($answers->getArrayCopy());

                $results->append($question);
            }
        }

        return $results;
    }

    public function updateQuestionDetails(Question $question) {

        $stmt = $this->db->prepare("UPDATE questions SET name=?, location_id=?, score=? WHERE id=?");
        $stmt->bind_param("siii", $question->getName(), $question->getLocationId(), $question->getScore(), $question->getId());
        $stmt->execute();

        if($stmt->affected_rows == 1) return true;

        return false;
    }

    public function updateQuestionType($id, $type) {

        $stmt = $this->db->prepare("UPDATE questions SET type=? WHERE id=?");
        $stmt->bind_param("ii", $type, $id);
        $stmt->execute();

        if($stmt->affected_rows == 1) return true;

        return false;
    }

    public function updateQuestionTargetId($id, $targetId) {

        $stmt = $this->db->prepare("UPDATE questions SET target_id=? WHERE id=?");
        $stmt->bind_param("si", $targetId, $id);
        $stmt->execute();

        if($stmt->affected_rows == 1) return true;

        return false;
    }
}