<?php

include '../models/Carga.php';
include '../models/Filtro.php';

$tipo = $_POST['isReceita'];
$user = $_POST['user'];    // Os dados sao globais ou de um usuario especifico?
$owner = $_POST['owner'];  // Qual usuario estah pedindo? (pra ver se ele deve ter acesso)

$filtro = new Filtro();

$filtro->cidade = $_POST['cidade'];
$filtro->estado = $_POST['estado'];
$filtro->pais = $_POST['pais'];

$carga = new Carga();

if ($tipo == "1") {
	echo json_encode($carga->carregaMediaPie($filtro, false, $user, $owner));
} else {
	echo json_encode($carga->carregaMediaPie($filtro, true, $user, $owner));
}

?>
