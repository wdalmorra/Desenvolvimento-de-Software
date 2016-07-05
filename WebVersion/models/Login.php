<?php

include '../util/ConexaoDB.php';

class Login {
	var $email;
	var $senha;
	var $conn;

	function __construct($email, $senha) {
		$this->conn = new ConexaoDB();
		$conexao = $this->conn->abrirConexao();

		$this->email = mysqli_real_escape_string($conexao, $email);
		$this->senha = mysqli_real_escape_string($conexao, $senha);

		$this->conn->fecharConexao();
	}

	function autentica() {
		$conexao = $this->conn->abrirConexao();
		$sql = "SELECT email, senha, nome FROM Users WHERE (`email` = '". $this->email ."') AND (`senha` = '". sha1($this->senha) ."') LIMIT 1";
		$result = mysqli_query($conexao,$sql);

		if(mysqli_num_rows($result) == 1){
			$linha = $result->fetch_assoc();
			$nome = $linha['nome'];
			$this->conn->fecharConexao();
			return array(true, $nome);
		} else {
			$this->conn->fecharConexao();
			return array(false, "false");
		}
	}
}

?>