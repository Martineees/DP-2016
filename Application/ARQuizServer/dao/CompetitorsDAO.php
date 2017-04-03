<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:11
 */

namespace dao;


use entities\Competition;
use entities\Competitor;
use entities\User;
use dao\CompetitionsDAO;
use dao\UsersDAO;

class CompetitorsDAO
{
    private $db;
    private $compettionsDAO;
    private $usersDAO;
    private $answerLogDAO;

    function __construct(\mysqli $db) {
        $this->db = $db;
        $this->compettionsDAO = new CompetitionsDAO($db);
        $this->usersDAO = new UsersDAO($db);
        $this->answerLogDAO = new AnswersLogDAO($db);
    }

    public function create(Competitor $competitor) {
        $id = $this->exists($competitor);

        if($id != null) return -1;

        $stmt = $this->db->prepare("INSERT INTO competitors VALUES(DEFAULT,?,?)");
        $stmt->bind_param("ii", $competitor->getCompetitionId(), $competitor->getUserId());
        $stmt->execute();

        return $stmt->insert_id;
    }

    private function exists(Competitor $competitor) {
        $stmt = $this->db->prepare("SELECT id FROM competitors WHERE competition_id=? AND user_id=?");
        $stmt->bind_param("ii", $competitor->getCompetitionId(), $competitor->getUserId());
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

    public function getUserCompetitions(User $user) {
        $stmt = $this->db->prepare("SELECT competition_id FROM competitors WHERE user_id=?");
        $stmt->bind_param("i", $user->getId());
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($competitionId);

        $results = null;

        if($stmt->num_rows > 0) {

            $results = new \ArrayObject();

            while($stmt -> fetch()) {
                $competition = $this->compettionsDAO->getCompetitionById($competitionId);

                $results->append($competition);
            }

            return $results;
        }

        return $results;
    }

    public function getUserCompetitionJSONArray(User $user) {
        $stmt = $this->db->prepare("SELECT id, competition_id FROM competitors WHERE user_id=?");
        $stmt->bind_param("i", $user->getId());
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($id, $competitionId);

        $results = new \ArrayObject();

        if($stmt->num_rows > 0) {

            while($stmt -> fetch()) {
                $competitor = array("id" => $id);
                $competitor["competition_id"] = $competitionId;
                $competitor["answers_list"] = json_encode($this->answerLogDAO->getCompetitorAnswersJSONArray($id)->getArrayCopy());

                $results->append($competitor);
            }

            return $results;
        }

        return $results;
    }

    public function getCompetitionUsers(Competition $competition)
    {
        $stmt = $this->db->prepare("SELECT user_id FROM competitors WHERE competition_id=?");
        $stmt->bind_param("i", $competition->getId());
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($userId);

        $results = null;

        if ($stmt->num_rows > 0) {

            $results = new \ArrayObject();

            while ($stmt->fetch()) {
                $user = $this->usersDAO->getUserById($userId);

                $results->append($user);
            }

            return $results;
        }

        return $results;
    }
}