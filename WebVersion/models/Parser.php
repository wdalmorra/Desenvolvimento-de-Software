<?php

include '../util/ConexaoDB.php';

class Parser {
	var $xml;
	var $email;
	var $conn;

	function __construct($arquivo, $email) {
		$this->xml = simplexml_load_file($arquivo) or die("Error: Cannot create object");
		$this->email = $email;

		$this->conn = new ConexaoDB();
	}

	function parse() {
		$logfile = "/var/www/html/WebVersion/log.txt";
		// exec("echo start >> $logfile");

		exec("echo {$this->email} >> $logfile");

		if(strcmp($this->xml->getName(), "financas") != 0) {
			return false;
		}

		foreach($this->xml->children() as $dadosMes) {
			$ano = $dadosMes['ano'];
			$mes = $dadosMes['mes'];
			exec("echo Dados para $mes de $ano: >> $logfile");
			foreach($dadosMes->children() as $movimentacao) {
				$categoria = $movimentacao['categoria'];
				$tipo = $movimentacao['tipo'];
				$valor = $movimentacao['valor'];
				exec("echo $categoria $tipo $valor >> $logfile");
			}
		}

		// Fazer as insercoes
		// Inserir primeiro em dadosmes: $email, $ano e $mes
		// Depois iterar em $movimentacoes e inserir
		return true;
	}
}

?>
