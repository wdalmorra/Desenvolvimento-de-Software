<?php
include '../models/Carga.php';

$record = new Carga();

// echo json_encode(array("state" => "ok"));
echo json_encode($record->carregaPaises());
?>