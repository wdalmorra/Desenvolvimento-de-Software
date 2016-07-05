<?php

include '../models/Login.php';

$email = $_POST['username'];
$senha = $_POST['password'];

$login = new Login($email, $senha);

// Veririca existencia do usuario
$autenticacao = $login->autentica();
$autenticado = $autenticacao[0];
if($autenticado){
	$nome = $autenticacao[1];
	echo json_encode(array("state" => "ok", "nome" => $nome));
} else {
	echo json_encode(array("state" => "erro", "nome" => "erro"));
}

?>