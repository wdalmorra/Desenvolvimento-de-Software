<?php

include '../models/Carga.php';
include '../models/Filtro.php';

$tipo = $_POST['isReceita'];
$user = $_POST['user'];

$filtro = new Filtro();

$filtro->cidade = $_POST['cidade'];
$filtro->estado = $_POST['estado'];
$filtro->pais = $_POST['pais'];

$carga = new Carga();

if ($tipo == "1") {
	echo json_encode($carga->carregaMediaPie($filtro, false, $user));
} else {
	echo json_encode($carga->carregaMediaPie($filtro, true, $user));
}

?>
