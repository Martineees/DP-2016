<?php
/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 21:24
 */

namespace entities;


class User
{
    private $id;
    private $name;
    private $passwordHash;
    private $is_admin;

    function __construct($id, $name, $passwordHash, $is_admin)
    {
        $this->id = $id;
        $this->name = $name;
        $this->passwordHash = $passwordHash;
        $this->is_admin = $is_admin;
    }

    public function getId()
    {
        return $this->id;
    }

    public function getName()
    {
        return $this->name;
    }

    public function getPasswordHash()
    {
        return $this->passwordHash;
    }

    public function getIsAdmin()
    {
        return $this->is_admin;
    }
}