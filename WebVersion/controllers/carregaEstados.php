<?php
include '../models/Carga.php';

$pais = $_POST['pais'];

$record = new Carga();

echo json_encode($record->carregaEstados($pais));
?>