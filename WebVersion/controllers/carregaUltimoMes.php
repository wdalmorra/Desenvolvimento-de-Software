<?php
include '../models/Carga.php';

$record = new Carga();

$user = $_POST['user'];

// echo json_encode(array("state" => "ok"));
echo json_encode($record->carregaUltimoMes($user));
?>