<?php
///////////////////////////////////////////////////////
// PERFECT PHP                                       //
// v2.1.1 (September 16, 2018)                       //
// Process a web form to extract the user input and  //
//    then email the data to a predefined recipient. //
// MIT License or WTFPL (your choice)                //
// https://centerkey.com/php                         //
///////////////////////////////////////////////////////

// Configuration settings
$sendFrom =    "Snap Backup <feedback@@@pkg.extra.domain>";
$sendTo =      "feedback@@@pkg.extra.domain";
$subjectLine = "Snap Backup Feedback";
$thanksUrl =   "thanks.html";  //confirmation page

// Build message body from web form input
$body = $_SERVER["SERVER_NAME"] . PHP_EOL . PHP_EOL;
foreach ($_POST as $field=>$value)
   $body .= "$field: $value" . PHP_EOL;
$body .= PHP_EOL . @gethostbyaddr($_SERVER["REMOTE_ADDR"]);
$body = htmlspecialchars($body, ENT_NOQUOTES);  //make safe

// Send email and direct browser to confirmation page
mail($sendTo, $subjectLine, $body, "From: $sendFrom");
header("Location: $thanksUrl");
?>
