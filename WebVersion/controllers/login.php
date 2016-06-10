<?php

include '../models/Login.php';

$email = $_POST['username'];
$senha = $_POST['password'];

$login = new Login($email, $senha);

// Veririca existencia do usuario
if($login->autentica()){
	echo json_encode(array("state" => "ok"));
} else {
	echo json_encode(array("state" => "erro"));
}

?>