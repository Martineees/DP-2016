<?php

session_start();

$userId = null;
if(isset($_SESSION["userId"]))
    $userId = $_SESSION["userId"];
?>

<div class="form-dialog overlay">
    <form id="add_competition" autocomplete="off">
        <label for="name">Name</label>
        <input type="text" name="name" id="name" />
        <label for="description">Description</label>
        <textarea name="description" id="description"  rows="4"></textarea>
        <input type="hidden" name="id" id="id" value="<?php echo $userId; ?>" />
        <div class="button-box">
            <input type="submit" value="Add" class="main-btn ic-af ic-add"/>
            <input type="button" class="close second-btn" value="Close" />
        </div>
    </form>
</div>


