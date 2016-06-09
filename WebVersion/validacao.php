<?php

// Verifica se houve POST e se o usuсrio ou a senha щ(sуo) vazio(s)
if (!empty($_POST) AND (empty($_POST['usuario']) OR empty($_POST['senha']))) {
	header("Location: index.php"); exit;
}

// Tenta se conectar ao servidor MySQL
mysql_connect('localhost', 'root', '123') or trigger_error(mysql_error());
// Tenta se conectar a um banco de dados MySQL
mysql_select_db('drefinancas') or trigger_error(mysql_error());

$usuario = mysql_real_escape_string($_POST['usuario']);
$senha = mysql_real_escape_string($_POST['senha']);

// Validaчуo do usuсrio/senha digitados
$sql = "SELECT `email`, `nome` FROM `users` WHERE (`email` = '". $usuario ."') AND (`senha` = '". sha1($senha) ."') LIMIT 1";
$query = mysql_query($sql);
if (mysql_num_rows($query) != 1) {
	// Mensagem de erro quando os dados sуo invсlidos e/ou o usuсrio nуo foi encontrado
	echo "Login invсlido!"; exit;
} else {
	// Salva os dados encontados na variсvel $resultado
	$resultado = mysql_fetch_assoc($query);

	// Se a sessуo nуo existir, inicia uma
	if (!isset($_SESSION)) session_start();

	// Salva os dados encontrados na sessуo
	$_SESSION['UsuarioID'] = $resultado['email'];
	$_SESSION['UsuarioNome'] = $resultado['nome'];

	// Redireciona o visitante
	header("Location: menu.php"); exit;
}

?>