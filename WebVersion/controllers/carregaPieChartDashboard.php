<?php
include '../models/Carga.php';

$record = new Carga();

$user = $_POST['user'];
$tipo = $_POST['tipo'];

// echo json_encode(array("state" => "ok"));
echo json_encode($record->carregaPieChartDashboard($user, $tipo));
?>