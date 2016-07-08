<?php

include '../models/Gerencia.php';

$tipo = $_POST['tipo'];

$ger = new Gerencia();

if($tipo === "admin") {
	$email = $_POST['email'];

	echo $ger->verificaAdmin($email);
} else if($tipo == "tabela") {
	$usuarios = $ger->getUsuarios();

	echo json_encode(array("state" => "ok"));
}

?>