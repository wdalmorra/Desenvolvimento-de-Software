<?php

include '../util/ConexaoDB.php';

class Registro {
	var $email;
	var $nome;
	var $senha;
	var $conn;

	function __construct($email, $nome, $senha) {
		$this->conn = new ConexaoDB();
		$conexao = $this->conn->abrirConexao();

		$this->email = mysqli_real_escape_string($conexao, $email);
		$this->senha = mysqli_real_escape_string($conexao, $senha);
		$this->nome = mysqli_real_escape_string($conexao, $nome);

		$this->senha = sha1($this->senha);

		$this->conn->fecharConexao();
	}

	function registraUsuario() {
		$conexao = $this->conn->abrirConexao();
		$sql = "INSERT INTO Users (email,nascimento,nome,senha,cidadeId) VALUES ('{$this->email}','2016-06-10','{$this->nome}','{$this->senha}','1');";
		if(mysqli_query($conexao,$sql)){
			$this->conn->fecharConexao();
			return true;
		} else {
			$this->conn->fecharConexao();
			return false;
		}

	}
}
?>