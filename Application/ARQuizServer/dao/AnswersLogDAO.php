<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:11
 */

namespace dao;


use entities\AnswerLog;
use entities\Competition;
use entities\Competitor;
use entities\User;
use dao\CompetitionsDAO;
use dao\UsersDAO;

class AnswersLogDAO
{
    private $db;

    function __construct(\mysqli $db) {
        $this->db = $db;
    }

    public function create(AnswerLog $answerLog) {
        $id = $this->exists($answerLog);

        if($id != null) return -1;

        $stmt = $this->db->prepare("INSERT INTO answers_log VALUES(DEFAULT,?,?,?)");
        $stmt->bind_param("iii", $answerLog->getCompetitorId(), $answerLog->getQuestionId(), $answerLog->isCorrect());
        $stmt->execute();

        return $stmt->insert_id;
    }

    private function exists(AnswerLog $answerLog) {
        $stmt = $this->db->prepare("SELECT id FROM answers_log WHERE competitor_id=? AND question_id=?");
        $stmt->bind_param("ii", $answerLog->getCompetitorId(), $answerLog->getQuestionId());
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

    public function getCompetitorAnswersJSONArray($id) {
        $stmt = $this->db->prepare("SELECT question_id  FROM answers_log WHERE competitor_id=?");
        $stmt->bind_param("i", $id);
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($questionId);

        $results = new \ArrayObject();

        if($stmt->num_rows > 0) {

            while($stmt -> fetch()) {
                $competitor["questionId"] = $questionId;

                $results->append($competitor);
            }
        }

        return $results;
    }
}