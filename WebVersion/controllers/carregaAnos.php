<?php
include '../models/Carga.php';

$record = new Carga();

echo json_encode($record->carregaAnos());
?>