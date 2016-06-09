// <?php
include 'parser.php';

$logfile = "/var/www/html/t/commands.txt";

if(isset($_FILES['file'])) {
	$file = $_FILES['file'];
	$user = $_POST['user'];

	$error = "";

    $file_ext = explode('.', $file['name']);
    $file_ext = end($file_ext);
    $file_ext = strtolower($file_ext);

    if(strcmp($file_ext, "xml") != 0) {
		$error = $file_ext;
	}

	if(strcmp($error, "") == 0) {
		exec("echo yo >> $logfile");

		$result = parse($file['tmp_name'], $user);
		exec("echo $result >> $logfile");
		// $r = $result->getName();

	} else {
		exec("echo $error >> $logfile");
	}
}

// exec("echo bitch >> $logfile");

?>