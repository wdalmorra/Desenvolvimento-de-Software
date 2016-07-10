<?php

include '../models/Carga.php';

$tipo = $_POST['isReceita'];
$user = $_POST['user'];
$carga = new Carga();

if ($tipo == "1") {
	echo json_encode($carga->carregaMediaPie(NULL, false, $user));
} else {
	echo json_encode($carga->carregaMediaPie(NULL, true, $user));
}

?>
