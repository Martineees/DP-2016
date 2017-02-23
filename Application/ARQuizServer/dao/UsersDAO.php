<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:11
 */

namespace dao;


use entities\User;

class UsersDAO
{
    private $db;

    function __construct(\mysqli $db) {
        $this->db = $db;
    }

    public function create(User $user) {
        $id = $this->exists($user);

        if($id != null) return -1;

        $stmt = $this->db->prepare("INSERT INTO users VALUES(DEFAULT,?,?,?)");
        $stmt->bind_param("ssi", $user->getName(), $user->getPasswordHash(), $user->getIsAdmin());
        $stmt->execute();

        return $stmt->insert_id;
    }

    private function exists(User $user) {
        $stmt = $this->db->prepare("SELECT id FROM users WHERE name=?");
        $stmt->bind_param("s", $user->getName());
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

    public function getUserByName($name) {
        $stmt = $this->db->prepare("SELECT id,password_hash,is_admin  FROM users WHERE name=?");
        $stmt->bind_param("s", $name);
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($id,$password_hash,$is_admin);
        $stmt -> fetch();

        $num_rows = $stmt->num_rows;

        if($num_rows == 1) {
            return new User($id, $name, $password_hash, $is_admin);
        }

        return null;
    }
}