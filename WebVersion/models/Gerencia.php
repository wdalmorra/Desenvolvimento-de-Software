<?php

include '../util/ConexaoDB.php';

// function stripAccents($str) {
//     return strtr($str, utf8_decode('àáâãäçèéêëìíîïñòóôõöùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ'), 'aaaaaceeeeiiiinooooouuuuyyAAAAACEEEEIIIINOOOOOUUUUY');
// }

class Gerencia {

	var $conn;

	function __construct() {
		$this->conn = new ConexaoDB();
	}

	function verificaAdmin($email) {
		$conexao = $this->conn->abrirConexao();

		$email = mysqli_real_escape_string($conexao, $email);
		$sql = "SELECT * FROM Admin WHERE usersEmail = '{$email}' LIMIT 1;";
		$resultado =  mysqli_query($conexao, $sql);

		$this->conn->fecharConexao();

		if(mysqli_num_rows($resultado) == 1) {
			return true;
		} else {
			return false;
		}
	}

	function getUsuarios() {
		$conexao = $this->conn->abrirConexao();

		$sql = "SELECT email, status FROM Users ORDER BY email;";
		$resultado =  mysqli_query($conexao, $sql);

		if(!$resultado){
			$this->conn->fecharConexao();
			return array();
		} else {
			$rows = array();
			while($row = mysqli_fetch_assoc($resultado)) {
				$email = $row["email"];
				$sql_admin = "SELECT usersEmail FROM Admin WHERE usersEmail = '{$email}' LIMIT 1;";
				$resultado_admin = mysqli_query($conexao, $sql_admin);

				if(mysqli_num_rows($resultado_admin) == 1) {
					$row["admin"] = true;
				} else {
					$row["admin"] = false;
				}

				$rows[]=$row;
			}

			$this->conn->fecharConexao();
			return $rows;
		}
	}

	function addAdmin($email) {
		$conexao = $this->conn->abrirConexao();

		$email = mysqli_real_escape_string($conexao, $email);
		$sql = "INSERT INTO Admin (usersEmail) VALUES ('{$email}');";
		$resultado = mysqli_query($conexao, $sql);

		$this->conn->fecharConexao();
		if($resultado){
			return true;
		} else {
			return false;
		}
	}

	function delAdmin($email) {
		$conexao = $this->conn->abrirConexao();

		$email = mysqli_real_escape_string($conexao, $email);
		$sql = "DELETE FROM Admin WHERE usersEmail = '{$email}';";
		$resultado = mysqli_query($conexao, $sql);

		$this->conn->fecharConexao();
		if($resultado){
			return true;
		} else {
			return false;
		}
	}

	function addUser($email) {
		$conexao = $this->conn->abrirConexao();

		$email = mysqli_real_escape_string($conexao, $email);
		$status = "ativo";
		$sql = "UPDATE Users SET status = '{$status}' WHERE email = '{$email}';";
		$resultado = mysqli_query($conexao, $sql);

		$this->conn->fecharConexao();
		if($resultado){
			return true;
		} else {
			return false;
		}
	}

	function delUser($email) {
		$conexao = $this->conn->abrirConexao();

		$email = mysqli_real_escape_string($conexao, $email);
		$status = "inativo";
		$sql = "UPDATE Users SET status = '{$status}' WHERE email = '{$email}';";
		$resultado = mysqli_query($conexao, $sql);

		$this->conn->fecharConexao();
		if($resultado){
			return true;
		} else {
			return false;
		}
	}

}
?>