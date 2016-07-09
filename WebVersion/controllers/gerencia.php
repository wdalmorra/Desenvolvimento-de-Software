<?php

include '../models/Gerencia.php';

$tipo = $_POST['tipo'];

$logfile = "/var/www/html/WebVersion/log.txt";

exec("echo $tipo >> $logfile");

$ger = new Gerencia();

if($tipo == "admin") {
	$email = $_POST['email'];

	echo $ger->verificaAdmin($email);

} else if($tipo == "tabela") {
	$usuarios = $ger->getUsuarios();

	echo json_encode($usuarios);

} else if($tipo == "add_admin") {
	$email = $_POST['email'];

	echo $ger->addAdmin($email);

} else if($tipo == "del_admin") {
	$email = $_POST['email'];

	echo $ger->delAdmin($email);

} else if($tipo == "add_user") {
	$email = $_POST['email'];

	echo $ger->addUser($email);

} else if($tipo == "del_user") {
	$email = $_POST['email'];

	echo $ger->delUser($email);

}

?>