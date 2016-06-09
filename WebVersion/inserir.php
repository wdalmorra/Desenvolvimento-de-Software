<?php

// Verifica se houve POST e se o usuário ou a senha é(são) vazio(s)
if (!empty($_POST) AND (empty($_POST['usuario']) OR empty($_POST['senha']))) {
	header("Location: index.php"); exit;
}

//Senhas diferentes
if ($_POST['txConfSenha']!=$_POST['senha']) {
	header("Location: index.php"); exit; 
}
// Tenta se conectar ao servidor MySQL
mysql_connect('localhost', 'root', '123') or trigger_error(mysql_error());
// Tenta se conectar a um banco de dados MySQL
mysql_select_db('drefinancas') or trigger_error(mysql_error());

$cadastro = date('Y-m-d H:i:s');

$usuario = mysql_real_escape_string($_POST['usuario']);
$senha = mysql_real_escape_string($_POST['senha']);
$txNome = mysql_real_escape_string($_POST['txNome']);
$cidadeId = '123';

// Montamos a consulta SQL
$query = "INSERT INTO `users` (`email`, `nascimento`, `senha`  , `nome` , `cidadeId`) VALUES ('".$txNome."','".$cadastro."' , '".sha1($senha)."','".$txNome."', '1')";
// Executa a query
$inserir = mysql_query($query);
if ($inserir) {
	// Salva os dados encontados na variável $resultado
	$resultado = mysql_fetch_assoc($query);

	// Se a sessão não existir, inicia uma
	if (!isset($_SESSION)) session_start();
	
	// Salva os dados encontrados na sessão
	$_SESSION['UsuarioID'] = $usuario;
	$_SESSION['UsuarioNome'] = $txNome;
	
	// Redireciona o visitante
	header("Location: menu.php"); exit;
	
} else {
	echo "Não foi possível inserir a notícia, tente novamente.";
	// Exibe dados sobre o erro:
	echo "Dados sobre o erro:" . mysql_error();
}



?>