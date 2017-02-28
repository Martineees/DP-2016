<?php

session_start();

$userId = null;
if(isset($_SESSION["userId"]))
    $userId = $_SESSION["userId"];
?>

<div class="form-dialog overlay">
    <form id="add_competition">
        <label for="name">Name</label>
        <input type="text" name="name" id="name" />
        <input type="hidden" name="id" id="id" value="<?php echo $userId; ?>" />
        <div class="button-box">
            <input type="submit" value="Add" class="main-btn ic-af ic-add"/>
            <button class="close second-btn">Close</button>
        </div>
    </form>
</div>


