<?php

include '../util/ConexaoDB.php';

function stripAccents($str) {
    return strtr(utf8_decode($str), utf8_decode('àáâãäçèéêëìíîïñòóôõöùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ'), 'aaaaaceeeeiiiinooooouuuuyyAAAAACEEEEIIIINOOOOOUUUUY');
}

class Parser {
	var $xml;
	var $email;
	var $conn;

	function __construct($arquivo, $email) {
		$this->xml = simplexml_load_file($arquivo) or die("Error: Cannot create object");

		$this->conn = new ConexaoDB();
		$conexao = $this->conn->abrirConexao();
		$this->email = mysqli_real_escape_string($conexao, $email);
		$this->conn->fecharConexao();
	}

	function parse() {
		$logfile = "/var/www/html/WebVersion/log.txt";
		exec("echo {$this->email} >> $logfile");

		if(strcmp($this->xml->getName(), "financas") != 0) {
			return false;
		}

		foreach($this->xml->children() as $dadosMes) {
			// Identifica o mes sumbetido
			$ano = $dadosMes['ano'];
			$mes = $dadosMes['mes'];
			exec("echo Dados para $mes de $ano: >> $logfile");

			// Abre uma entrada para o mes submetido no DB
			$conexao = $this->conn->abrirConexao();
			$sql = "INSERT INTO DadosMes (mes, usersEmail) VALUES ('{$ano}-{$mes}-01', '{$this->email}');";
			mysqli_query($conexao,$sql);
			$this->conn->fecharConexao();

			// Percorre cada uma das movimentacoes registratdas
			foreach($dadosMes->children() as $movimentacao) {
				$conexao = $this->conn->abrirConexao();

				// Extrai os dados
				$categoria = stripAccents(mysqli_real_escape_string($conexao, $movimentacao['categoria']));
				$tipo = mysqli_real_escape_string($conexao, $movimentacao['tipo']);
				$valor = mysqli_real_escape_string($conexao, $movimentacao['valor']);
				exec("echo $categoria $tipo $valor >> $logfile");

				// Ve se eh despesa ou receita
				// $despesa = "RECEITA";
				// if(strcmp($tipo, "despesa") == 0) {
				// 	$despesa = "DESPESA";
				// }

				// Acha o id da categoria
				$sql = "SELECT idCategoria FROM Categoria WHERE Categoria.nome = '$categoria';";
				$resultado = mysqli_query($conexao,$sql);
				$this->conn->fecharConexao();

				if ($resultado->num_rows != 1) {
					exec("echo Erro na busca por categorias. >> $logfile");
				} else {
					$conexao = $this->conn->abrirConexao();
					$linha = $resultado->fetch_assoc();
					exec("echo Categoria {$linha['idCategoria']} >> $logfile");
					$idCategoria = $linha['idCategoria'];

					// Insere no DB
					$sql = "INSERT INTO Movimentacao (categoriaId, dadosMesMes, dadosMesUsersEmail, valor, tipo) VALUES ('{$idCategoria}', '{$ano}-{$mes}-01', '{$this->email}', '{$valor}', '{$tipo}');";
					mysqli_query($conexao,$sql);
					$this->conn->fecharConexao();
				}
			}
		}

		return true;
	}
}

?>
