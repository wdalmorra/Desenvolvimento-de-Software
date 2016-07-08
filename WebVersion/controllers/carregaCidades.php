<?php
include '../models/Carga.php';

$pais = $_POST['pais'];
$estado = $_POST['estado'];

$record = new Carga();

echo json_encode($record->carregaCidades($pais, $estado));
?>