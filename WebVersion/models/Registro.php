<?php

include '../util/ConexaoDB.php';

class Registro {
	var $email;
	var $nome;
	var $nascimento;
	var $pais;
	var $estado;
	var $cidade;
	var $senha;
	var $conn;

	function __construct($email, $nome, $nascimento, $pais, $estado, $cidade, $senha) {
		$this->conn = new ConexaoDB();
		$conexao = $this->conn->abrirConexao();

		$nasc = explode("/", $nascimento);
		$nascimento = $nasc[2]."-".$nasc[1]."-".$nasc[0];

		// $logfile = "/var/www/html/WebVersion/log.txt";
		// exec("echo $nascimento >> $logfile");

		$this->email = mysqli_real_escape_string($conexao, $email);
		$this->nome = mysqli_real_escape_string($conexao, $nome);
		$this->nascimento = mysqli_real_escape_string($conexao, $nascimento);
		$this->pais = stripAccents(mysqli_real_escape_string($conexao, $pais));
		$this->estado = mysqli_real_escape_string($conexao, $estado);
		$this->cidade = stripAccents(mysqli_real_escape_string($conexao, $cidade));
		$this->senha = mysqli_real_escape_string($conexao, $senha);

		$this->senha = sha1($this->senha);

		$this->conn->fecharConexao();
	}

	function registraUsuario() {
		$conexao = $this->conn->abrirConexao();
		$sql_pais = "SELECT countryCode FROM Pais WHERE Pais.nome = '{$this->pais}';";
		$result_pais = mysqli_query($conexao,$sql_pais);

		if (mysqli_num_rows($result_pais) == 1) {
			$row_pais = mysqli_fetch_assoc($result_pais);
		}

		$pais = $row_pais["countryCode"];

		$sql_cidade = "SELECT idCidade FROM Cidade WHERE Cidade.estadoCC = '$pais' AND Cidade.estadoEstado = '{$this->estado}' AND Cidade.nome = '{$this->cidade}';";
		$result_cidade = mysqli_query($conexao,$sql_cidade);

		if (mysqli_num_rows($result_cidade) == 1) {
			$row_cidade = mysqli_fetch_assoc($result_cidade);
		}

		$idCidade = $row_cidade["idCidade"];
		$status = "ativo";

		$sql = "INSERT INTO Users (email,nascimento,nome,senha,cidadeId,status) VALUES ('{$this->email}','{$this->nascimento}','{$this->nome}','{$this->senha}','$idCidade','$status');";
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