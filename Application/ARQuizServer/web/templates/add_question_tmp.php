<?php

use dao\LocationDAO;

require_once("../../dbConnect.php");
require_once("../../dao/LocationDAO.php");
require_once("../../entities/Location.php");

session_start();

$competitionId = null;
$locations = null;

if(isset($_POST["id"])) {

    $dbConnect = new DBConnect();
    $db = $dbConnect->connect();
    $locationsDAO = new LocationDAO($db);

    $competitionId = $_POST["id"];

    $locations = $locationsDAO->getAllLocations();
}
?>

<div class="form-dialog overlay">
    <form id="add_question" autocomplete="off">
        <label for="name">Question</label>
        <input type="text" name="name" id="name" required />
        <label>Image target</label>
        <div class="input-file-box">
            <input type="file" name="image" id="image" required />
            <label for="image">Choose a file</label>
        </div>
        <label for="width">Image width in real world</label>
        <input type="text" name="width" id="width" required />
        <label for="type">Question type</label>
        <select id="type" name="type" required>
            <option value="0" selected>With options</option>
            <option value="1">Writable answer</option>
        </select>
        <label for="location_id">Question location</label>
        <select id="location_id" name="location_id" required>
            <?php
            if($locations != null) {

                $first = false;

                foreach ($locations as $location) {
                    $selected = "";
                    if(!$first) {
                        $selected = "selected";
                        $first = true;
                    }

                    echo "<option value=\"".$location->getId()."\" ".$selected.">".$location->getBlock().$location->getFloor()."</option>";
                }
            }
            ?>
        </select>

        <label for="score">Score</label>
        <input type="number" name="score" id="score" required />

        <label>Correct answer</label>
        <p class="note">Answers will be suffled</p>
        <input type="text" name="answer[]" required>
        <div id="otherAnswers"></div>
        <input type="button" id="addAnswer" class="second-btn" value="+ add answer" />

        <input type="hidden" name="competition_id" id="competition_id" value="<?php echo $competitionId; ?>" />
        <div class="button-box">
            <input type="submit" value="Add" class="main-btn ic-af ic-add"/>
            <input type="button" class="close second-btn" value="Close" />
        </div>
    </form>
</div>


