<?php
include '../models/Registro.php';

$email = $_POST['email'];
$nome = $_POST['nome'];
$senha = $_POST['senha'];

$record = new Registro($email,$nome,$senha);

echo $record->registraUsuario();
?>