<?php

include '../util/ConexaoDB.php';

function stripAccents($str) {
    return strtr($str, utf8_decode('àáâãäçèéêëìíîïñòóôõöùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ'), 'aaaaaceeeeiiiinooooouuuuyyAAAAACEEEEIIIINOOOOOUUUUY');
}

class Usuario {

	var $conn;

	function __construct() {
		$this->conn = new ConexaoDB();
	}

	function getInfos($email) {
		$conexao = $this->conn->abrirConexao();

		$email = mysqli_real_escape_string($conexao, $email);

		$sql = "SELECT nascimento, cidadeId FROM Users WHERE email = '{$email}' LIMIT 1;";
		$resultado =  mysqli_query($conexao, $sql);

		if(mysqli_num_rows($resultado) == 1) {
			$linha = $resultado->fetch_assoc();
			$nascimento = utf8_encode($linha['nascimento']);
			$cidadeId = $linha['cidadeId'];
			$this->conn->fecharConexao();
			return array(true, $nascimento, $cidadeId);
		} else {
			$this->conn->fecharConexao();
			return array(false);
		}
	}

	function getLocal($cidadeId) {
		$conexao = $this->conn->abrirConexao();

		$cidadeId = mysqli_real_escape_string($conexao, $cidadeId);

		$sql = "SELECT * FROM Cidade WHERE idCidade = '{$cidadeId}' LIMIT 1;";
		$resultado =  mysqli_query($conexao, $sql);

		if(mysqli_num_rows($resultado) == 1) {
			$linha = $resultado->fetch_assoc();
			$cidade = utf8_encode($linha['nome']);
			$estado = $linha['estadoEstado'];
			$cc = $linha['estadoCC'];
			$this->conn->fecharConexao();
			return array($cidade, $estado, $cc);
		} else {
			$this->conn->fecharConexao();
			return array(null);
		}
	}

	function getPais($cc) {
		$conexao = $this->conn->abrirConexao();

		$cc = mysqli_real_escape_string($conexao, $cc);

		$sql = "SELECT nome FROM Pais WHERE countryCode = '{$cc}' LIMIT 1;";
		$resultado =  mysqli_query($conexao, $sql);

		if(mysqli_num_rows($resultado) == 1) {
			$linha = $resultado->fetch_assoc();
			$pais = $linha['nome'];
			$this->conn->fecharConexao();
			return $pais;
		} else {
			$this->conn->fecharConexao();
			return null;
		}
	}

	function alteraDados($email, $nome, $nascimento, $pais, $estado, $cidade) {
		$conexao = $this->conn->abrirConexao();

		$email = mysqli_real_escape_string($conexao, $email);
		$nome = mysqli_real_escape_string($conexao, $nome);
		$nascimento = mysqli_real_escape_string($conexao, $nascimento);
		$pais = stripAccents(mysqli_real_escape_string($conexao, $pais));
		$estado = mysqli_real_escape_string($conexao, $estado);
		$cidade = stripAccents(mysqli_real_escape_string($conexao, $cidade));

		$idCidade = "";

		if($nascimento != "") {
			$nasc = explode("/", $nascimento);
			$nascimento = $nasc[2]."-".$nasc[1]."-".$nasc[0];
		}

		if($cidade != "" && $estado != "" && $pais != "") {
			$sql_pais = "SELECT countryCode FROM Pais WHERE Pais.nome = '{$pais}';";
			$result_pais = mysqli_query($conexao,$sql_pais);

			if (mysqli_num_rows($result_pais) == 1) {
				$row_pais = mysqli_fetch_assoc($result_pais);
			}

			$pais = $row_pais["countryCode"];

			$sql_cidade = "SELECT idCidade FROM Cidade WHERE Cidade.estadoCC = '{$pais}' AND Cidade.estadoEstado = '{$estado}' AND Cidade.nome = '{$cidade}';";
			$result_cidade = mysqli_query($conexao,$sql_cidade);

			if (mysqli_num_rows($result_cidade) == 1) {
				$row_cidade = mysqli_fetch_assoc($result_cidade);
			}

			$idCidade = $row_cidade["idCidade"];
		}

		$sql_check = "SELECT * FROM Users WHERE email = '{$email}' LIMIT 1;";
		$result_check = mysqli_query($conexao,$sql_check);

		if (mysqli_num_rows($result_check) == 1) {
			$row_check = mysqli_fetch_assoc($result_check);
		}

		if($nome == "") $nome = $row_check['nome'];
		if($nascimento == "") $nascimento = $row_check['nascimento'];
		if($idCidade == "") $idCidade = $row_check['cidadeId'];

		$sql_update = "UPDATE Users SET nome = '{$nome}', nascimento = '{$nascimento}', cidadeId = '{$idCidade}' WHERE email = '{$email}';";
		$result_update = mysqli_query($conexao, $sql_update);

		$this->conn->fecharConexao();
		if($result_update) {
			return array(true, $nome);
		} else {
			return array(false);
		}
	}

	function alteraSenha($email, $senhaAtual, $senhaNova) {
		$conexao = $this->conn->abrirConexao();

		$email = mysqli_real_escape_string($conexao, $email);
		$senhaAtual = mysqli_real_escape_string($conexao, $senhaAtual);
		$senhaNova = mysqli_real_escape_string($conexao, $senhaNova);

		$sql = "SELECT * FROM Users WHERE email = '{$email}' AND senha = '{$senhaAtual}' LIMIT 1;";
		$resultado = mysqli_query($conexao, $sql);

		if (mysqli_num_rows($resultado) == 1) {
			$sql_update = "UPDATE Users SET senha = '{$senhaNova}' WHERE email = '{$email}';";
			$result_update = mysqli_query($conexao, $sql_update);

			$this->conn->fecharConexao();
			if($result_update) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}

?>