<?php

/**
 * Created by PhpStorm.
 * User: Martin
 * Date: 13.2.2017
 * Time: 23:50
 */

class DBConnect {

    var $db;

    // constructor
    function __construct() {
        // connecting to database
        $this->connect();
    }

    /**
     * Function to connect with database
     */
    function connect() {
        // import database connection variables
        require_once __DIR__ . '/config.php';

        $this->db = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);

        // Check connection
        if ($this->db->connect_error) {
            die("Connection failed: " . $this->db->connect_error);
        }
        $this->db->set_charset("utf8");


        return $this->db;
    }

    /**
     * Function to close db connection
     */
    function close() {
        // closing db connection
        mysqli_close($this->db);
    }

}

?>