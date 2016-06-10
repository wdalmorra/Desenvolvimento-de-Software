<?php

include '../models/Parser.php';

$logfile = "/var/www/html/WebVersion/log.txt";

if(isset($_FILES['file'])) {
	$file = $_FILES['file'];
	$email = $_POST['email'];

	$error = "";

    $file_ext = explode('.', $file['name']);
    $file_ext = end($file_ext);
    $file_ext = strtolower($file_ext);

    if(strcmp($file_ext, "xml") != 0) {
		$error = $file_ext;
	}

	if(strcmp($error, "") == 0) {
		exec("echo yo >> $logfile");
		
		echo false;

		// $parser = new Parser($file['tmp_name'], $email);

		// echo $parser->parse();

	} else {
		echo false;
		exec("echo $error >> $logfile");
	}
}

exec("echo bitch >> $logfile");

?>