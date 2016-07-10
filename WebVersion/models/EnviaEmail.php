<?php

include '../util/ConexaoDB.php';

class EnviaEmail {
	var $email;
	var $senha;
	var $conn;

	function __construct($email,$senha) {
		$this->conn = new ConexaoDB();
		$conexao = $this->conn->abrirConexao();
		
		$this->email = mysqli_real_escape_string($conexao, $email);
		$this->senha = mysqli_real_escape_string($conexao, $senha);

		$this->senha = sha1($this->senha);

		$this->conn->fecharConexao();
	}

	function mudaSenha() {
		$conexao = $this->conn->abrirConexao();
		
		$logfile = "/var/www/html/WebVersion/log.txt";
	
		$sql = "UPDATE Users SET senha = '{$this->senha}' WHERE email= '{$this->email}';";
		if(mysqli_query($conexao,$sql)){
			$this->conn->fecharConexao();
			return true;
		} else {
			$this->conn->fecharConexao();
			return false;
		}
	}
}

function stripAccents($str) {
    return strtr(utf8_decode($str), utf8_decode('àáâãäçèéêëìíîïñòóôõöùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ'), 'aaaaaceeeeiiiinooooouuuuyyAAAAACEEEEIIIINOOOOOUUUUY');
}
?>
