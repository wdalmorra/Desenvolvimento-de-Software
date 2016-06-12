<?php

include '../util/ConexaoDB.php';

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
				// Extrai os dados
				$categoria = $movimentacao['categoria'];
				$tipo = $movimentacao['tipo'];
				$valor = $movimentacao['valor'];
				exec("echo $categoria $tipo $valor >> $logfile");

				// Ve se eh despesa ou receita
				$despesa = 0;
				if(strcmp($tipo, "despesa") == 0) {
					$despesa = 1;
				}

				// Insere no DB
				$conexao = $this->conn->abrirConexao();
				$sql = "INSERT INTO Movimentacao (categoria, dadosMesMes, dadosMesUsersEmail, valor, despesa) VALUES ('{$categoria}', '{$ano}-{$mes}-01', '{$this->email}', '{$valor}', '{$despesa}');";
				mysqli_query($conexao,$sql);
				$this->conn->fecharConexao();
			}
		}

		return true;
	}
}

?>
