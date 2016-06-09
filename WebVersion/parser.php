<?php
include 'config.php';


function parse($file_name, $user) {
	$logfile = "/var/www/html/t/commands.txt";
	// exec("echo start >> $logfile");

	$email = $user;

	exec("echo $email >> $logfile");

 	$xml = simplexml_load_file($file_name) or die("Error: Cannot create object");

	if(strcmp($xml->getName(), "financas") != 0) {
		return "Broken XML file - nao eh financas";
	}

	$dm = $xml->children()[0];

	exec("echo $dm >> $logfile");
	$ano = "";
	$mes = "";

	$movimentacao = array();

	foreach($xml->children() as $child) {
		exec("echo $child >> $logfile");
		switch($child->getName()) {
			case 'dadosmes':
				break;
			case "ano":
				$ano = $child;
				exec("echo $ano >> $logfile");
				break;

			case "mes":
				$mes = $child;
				exec("echo $mes >> $logfile");
				break;

			case "movimentacao":
				foreach($child->children() as $grandchild) {
					$tmp = array();
					switch($grandchild->getName()) {

						case "categoria":
							array_push($tmp, $grandchild);
							break;

						case "tipo":
							array_push($tmp, $grandchild);
							break;

						case "valor":
							array_push($tmp, $grandchild);
							break;

						default:
							return "Broken XML file - ". $grandchild->getName();
							break;
					}
					array_push($movimentacao, $tmp);
				}
				break;

			default:
				return "Broken XML file - ". $child->getName();
				break;
		}
	}

	// if(strlen($mes) == 1) {
	// 	$mes = "0".$mes;
	// }

	// $date = $ano."-".$mes."-01";

	// // Tenta se conectar ao servidor MySQL
	// mysql_connect(HOSTNAME, DB_USER, DB_PASSWORD) or trigger_error(mysql_error());
	// // Tenta se conectar a um banco de dados MySQL
	// mysql_select_db(DB_NAME) or trigger_error(mysql_error());

	// $query = "INSERT INTO `DadosMes` (`mes`, `usersEmail`) VALUES ('".$date."','".$email."')";
	// // Executa a query
	// $dadosmes = mysql_query($query);

	// exec("echo $query >> $logfile");

	// exec("echo job >> $logfile");

	// foreach ($movimentacao as $m) {
	// 	$cat = $m[0];
	// 	$tipo = $m[1];
	// 	$valor = $m[2];

	// 	$t = 0;

	// 	if(strcmp($tipo, "despesa") == 0) {
	// 		$t = 1;
	// 	}

	// 	$query = "INSERT INTO `Movimentacao` (`categoria`, `dadosMesMes`, `dadosMesUsersEmail`, `valor`, `despesa`) VALUES ('RECEITA1','".$date."','".$email."','".$valor."','".$t."')";

	// 	$mov = mysql_query($query);
	// }

	// Fazer as insercoes
	// Inserir primeiro em dadosmes: $email, $ano e $mes
	// Depois iterar em $movimentacoes e inserir
	return $xml;
}

?>