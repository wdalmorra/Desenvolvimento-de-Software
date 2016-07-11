<?php

include '../models/Carga.php';
include '../models/Filtro.php';

$tipo = $_POST['tipo'];

if($tipo == "pie") {
	$isReceita = $_POST['isReceita'];
	$user = $_POST['user'];    // Os dados sao globais ou de um usuario especifico?
	$owner = $_POST['owner'];  // Qual usuario estah pedindo? (pra ver se ele deve ter acesso)

	$filtro = new Filtro();

	$filtro->cidade = $_POST['cidade'];
	$filtro->estado = $_POST['estado'];
	$filtro->pais = $_POST['pais'];
	$filtro->idadeMin = $_POST['idadeMin'];
	$filtro->idadeMax = $_POST['idadeMax'];

	$mes = $_POST['mes'];
	$ano = $_POST['ano'];

	if ($mes == "Todos") {
		$filtro->mesMin = "Janeiro";
		$filtro->mesMax = "Dezembro";
	} else {
		$filtro->mesMin = $mes;
		$filtro->mesMax = $mes;
	}

	if ($ano == "Todos") {
		$filtro->anoMin = "1900";
		$filtro->anoMax = "2100";
	} else {
		$filtro->anoMin = $ano;
		$filtro->anoMax = $ano;
	}

	$carga = new Carga();

	if ($isReceita == "1") {
		echo json_encode($carga->carregaMediaPie($filtro, false, $user, $owner));
	} else {
		echo json_encode($carga->carregaMediaPie($filtro, true, $user, $owner));
	}
} else if($tipo == "bar") {
	$user = $_POST['user'];    // Os dados sao globais ou de um usuario especifico?
	$owner = $_POST['owner'];  // Qual usuario estah pedindo? (pra ver se ele deve ter acesso)

	$filtro = new Filtro();

	$filtro->mesMin = $_POST['mesIni'];
	$filtro->anoMin = $_POST['anoIni'];

	$filtro->mesMax = $_POST['mesFim'];
	$filtro->anoMax = $_POST['anoFim'];

	$filtro->categoria = $_POST['categoria'];

	$filtro->idadeMin = $_POST['idadeIni'];
	$filtro->idadeMax = $_POST['idadeFim'];

	$filtro->cidade = $_POST['cidade'];
	$filtro->estado = $_POST['estado'];
	$filtro->pais = $_POST['pais'];

	$carga = new Carga();

	echo json_encode($carga->carregaBar($filtro, $user, $owner));
}


?>
