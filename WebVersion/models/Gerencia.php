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

		// $email = mysqli_real_escape_string($conexao, $email);
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
		
	}

}
?>