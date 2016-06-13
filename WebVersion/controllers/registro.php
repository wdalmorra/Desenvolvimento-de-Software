<?php
include '../models/Registro.php';

$email = $_POST['email'];
$nome = $_POST['nome'];
$nascimento = $_POST['nascimento'];
$pais = $_POST['pais'];
$estado = $_POST['estado'];
$cidade = $_POST['cidade'];
$senha = $_POST['senha'];

$record = new Registro($email,$nome,$nascimento,$pais,$estado,$cidade,$senha);

echo $record->registraUsuario();
?>