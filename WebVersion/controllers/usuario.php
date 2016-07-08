<?php
include '../models/Usuario.php';

$tipo = $_POST['tipo'];

$user = new Usuario();

if($tipo == "popula") {
	$email = $_POST['username'];

	$userInfo = $user->getInfos($email);

	$sucesso = $userInfo[0];

	if($sucesso) {
		$cidadeId = $userInfo[2];

		$local = $user->getLocal($cidadeId);

		$nomePais = $user->getPais($local[2]);

		echo json_encode(array("state" => "ok", "nasc" => $userInfo[1], "cidade" => $local[0], "estado" => $local[1], "pais" => $nomePais));
	} else {
		echo json_encode(array("state" => "erro"));
	}
} else if($tipo == "altera_dados") {
	$email = $_POST['email'];
	$nome = $_POST['nome'];
	$nascimento = $_POST['nascimento'];
	$pais = $_POST['pais'];
	$estado = $_POST['estado'];
	$cidade = $_POST['cidade'];

	$resultado = $user->alteraDados($email, $nome, $nascimento, $pais, $estado, $cidade);

	if($resultado[0]) {
		echo json_encode(array("state" => "ok", "nome" => $resultado[1]));
	} else {
		echo json_encode(array("state" => "erro"));
	}
} else if($tipo = "altera_senha") {
	$email = $_POST['email'];
	$senhaAtual = $_POST['senhaAtual'];
	$senhaNova = $_POST['senhaNova'];

	echo $user->alteraSenha($email, sha1($senhaAtual), sha1($senhaNova));
}


?>