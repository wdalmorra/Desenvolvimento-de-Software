<?php

include '../models/Carga.php';

$tipo = $_POST['isReceita'];
$carga = new Carga();

if ($tipo == "1") {
	echo json_encode($carga->carregaMediaPie(NULL, false));
} else {
	echo json_encode($carga->carregaMediaPie(NULL, true));
}

?>
