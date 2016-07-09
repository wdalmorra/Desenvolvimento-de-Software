<?php
include '../models/Carga.php';

$mes = $_POST['mes'];
$ano = $_POST['ano'];
$email = $_POST['email'];

$record = new Carga();

echo json_encode($record->carregaReceita($mes , $ano, $email));
?>
