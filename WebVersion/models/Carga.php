<?php

include '../util/ConexaoDB.php';

// function stripAccents($str) {
//     return strtr($str, utf8_decode('àáâãäçèéêëìíîïñòóôõöùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ'), 'aaaaaceeeeiiiinooooouuuuyyAAAAACEEEEIIIINOOOOOUUUUY');
// }

class Carga {

    var $conn;

    function __construct() {
        $this->conn = new ConexaoDB();
    }

    function carregaCategorias(){
        $conexao = $this->conn->abrirConexao();
        $sql = "SELECT nome FROM Categoria;";
        $result =  mysqli_query($conexao,$sql);
        if(!$result){
            $this->conn->fecharConexao();
            return array();
        } else {
            $rows = array();
            while($row = mysqli_fetch_assoc($result)) {
                $row["nome"] = utf8_encode($row["nome"]);
              $rows[]=$row;
            }

            $this->conn->fecharConexao();
            return $rows;
        }
    }

    function carregaMeses(){
        $conexao = $this->conn->abrirConexao();
        $sql = "SELECT mes, mesNum FROM Mes ORDER BY mesNum ASC;";
        $result =  mysqli_query($conexao,$sql);
        if(!$result){
            $this->conn->fecharConexao();
            return array();
        } else {
            $rows = array();
            while($row = mysqli_fetch_assoc($result)) {
                $row["mes"] = utf8_encode($row["mes"]);
              $rows[]=$row;
            }

            $this->conn->fecharConexao();
            return $rows;
        }
    }

    function carregaAnos(){
        $conexao = $this->conn->abrirConexao();
        $sql = "SELECT ano FROM Ano ORDER BY ano DESC;";
        $result =  mysqli_query($conexao,$sql);
        if(!$result){
            $this->conn->fecharConexao();
            return array();
        } else {
            $rows = array();
            while($row = mysqli_fetch_assoc($result)) {
              $rows[]=$row;
            }

            $this->conn->fecharConexao();
            return $rows;
        }
    }

    function carregaIdades(){
        $conexao = $this->conn->abrirConexao();
        $sql = "SELECT idade FROM Idade;";
        $result =  mysqli_query($conexao,$sql);
        if(!$result){
            $this->conn->fecharConexao();
            return array();

        } else {
            $rows = array();
            while($row = mysqli_fetch_assoc($result)) {
              $rows[]=$row;
            }

            $this->conn->fecharConexao();
            return $rows;
        }

    }

    function carregaPaises() {
        $conexao = $this->conn->abrirConexao();
        $sql = "SELECT nome FROM Pais;";
        $result =  mysqli_query($conexao,$sql);
        if(!$result){
            $this->conn->fecharConexao();
            return array();

        } else {
            $rows = array();
            while($row = mysqli_fetch_assoc($result)) {
              $rows[]=$row;
            }

            $this->conn->fecharConexao();
            return $rows;
        }

    }

    function carregaEstados($pais) {
        $conexao = $this->conn->abrirConexao();
        
        $sql = "SELECT countryCode FROM Pais WHERE nome = '{$pais}';";
        $result =  mysqli_query($conexao,$sql);
        if(!$result){
            $this->conn->fecharConexao();
            return array();

        } else {
            $row = mysqli_fetch_assoc($result);
            $sql = "SELECT estado FROM Estado WHERE paisCountryCode = '{$row['countryCode']}';";
		    $result =  mysqli_query($conexao,$sql);
		    if(!$result){
		        $this->conn->fecharConexao();
		        return array();

		    } else {
		        $rows = array();
		        while($row = mysqli_fetch_assoc($result)) {
		          $rows[]=$row;
		        }

		        $this->conn->fecharConexao();
		        return $rows;
		    }
        }

    }

    function carregaCidades($pais, $estado) {
        $conexao = $this->conn->abrirConexao();
        $logfile = "/var/www/html/WebVersion/log.txt";
        
        $sql_pais = "SELECT countryCode FROM Pais WHERE nome = '{$pais}';";
        $result_pais =  mysqli_query($conexao,$sql_pais);
        if(!$result_pais){
            $this->conn->fecharConexao();
            return array();

        } else {
            $row_pais = mysqli_fetch_assoc($result_pais);
            $t = $row_pais["countryCode"];
            $sql = "SELECT nome FROM Cidade WHERE estadoCC = '{$t}' AND estadoEstado = '{$estado}';";
		    $result =  mysqli_query($conexao,$sql);
		    if(!$result){
		        $this->conn->fecharConexao();
		        return array();

		    } else {
		        $rows = array();
		        while($row = mysqli_fetch_assoc($result)) {
                    // $a = stripAccents(mysqli_real_escape_string($conexao, $row["nome"]));
                    $row["nome"] = utf8_encode($row["nome"]);
                    // exec("echo $a >> $logfile");
		          $rows[]=$row;
		        }

		        $this->conn->fecharConexao();
		        return $rows;
		    }
        }
    }

  function carregaReceita($mes , $ano, $email){
	$conexao = $this->conn->abrirConexao();
	$logfile = "/var/www/html/WebVersion/log.txt";

    $mes = utf8_decode($mes);
	
	$sql_mes = "SELECT mesNum from Mes WHERE mes='{$mes}';";
	$result_mes =  mysqli_query($conexao,$sql_mes);
	if(!$result_mes){
            $this->conn->fecharConexao();
            return array();
        } 
	
 	$row_mes = mysqli_fetch_assoc($result_mes);
	$t = $row_mes["mesNum"];


	$anoMesDia = $ano."-".$t."-01";

	$sql_receita = "SELECT  c.nome AS categoria , m.valor AS valor from Movimentacao AS m INNER JOIN Categoria AS c ON m.categoriaId=c.IdCategoria WHERE m.dadosMesMes='{$anoMesDia}' AND m.dadosMesUsersEmail='{$email}' AND tipo='receita';";
	$result_receita =  mysqli_query($conexao,$sql_receita);
	
        if(!$result_receita){
            $this->conn->fecharConexao();
            return array();
        } else {
		$rows = array();
		while($row = mysqli_fetch_assoc($result_receita)) {
	         	 $row["categoria"] = utf8_encode($row["categoria"]);
		         $rows[]=$row;
		}
		$this->conn->fecharConexao();
		return $rows;
	}
    }

    function carregaDespesa($mes, $ano, $email){
	$conexao = $this->conn->abrirConexao();
    $logfile = "/var/www/html/WebVersion/log.txt";

    $mes = utf8_decode($mes);

    $sql_mes = "SELECT mesNum from Mes WHERE mes='{$mes}';";
    $result_mes =  mysqli_query($conexao,$sql_mes);
    if(!$result_mes){
            $this->conn->fecharConexao();
            return array();
        } 
    
    $row_mes = mysqli_fetch_assoc($result_mes);
    $t = $row_mes["mesNum"];


    $anoMesDia = $ano."-".$t."-01";
    $sql_despesa= "SELECT  c.nome AS categoria , m.valor AS valor from Movimentacao AS m INNER JOIN Categoria AS c ON m.categoriaId=c.IdCategoria WHERE m.dadosMesMes='{$anoMesDia}' AND m.dadosMesUsersEmail='{$email}' AND tipo='despesa';";
    $result_despesa =  mysqli_query($conexao,$sql_despesa);
        if(!$result_despesa){
            $this->conn->fecharConexao();
            return array();
        } else {
        $rows = array();
        while($row = mysqli_fetch_assoc($result_despesa)) {
                  $row["categoria"] = utf8_encode($row["categoria"]);
                  $rows[]=$row;
        }
        $this->conn->fecharConexao();
        return $rows;
    }
    }

    function carregaMediaPie($filtro, $isDespesa, $user, $owner) {
        // TODO Alerta de seguranca: num sistema serio, teria que 'desarmar' os parametros pra impedir SQL injection. Nao eh o foco hoje.
    	$logfile = "/var/www/html/WebVersion/log2.txt";

        $conexao = $this->conn->abrirConexao();

        $sql_medias = NULL;
        $tipo = "receita";

        if ($isDespesa) {
            $tipo = "despesa";
        }

        if ($user == "") {
            $user = '%';
        }

        // Acha o country code do pais solicitado, se necessario
        $pais = '%';
        if ($filtro->pais != 'Todos') {
            $sql_aux = "SELECT countryCode FROM Pais WHERE nome='{$filtro->pais}'";
            $result_aux = mysqli_query($conexao,$sql_aux);
            $row = mysqli_fetch_assoc($result_aux);
            $pais = $row['countryCode'];
            $this->conn->fecharConexao();
            $conexao = $this->conn->abrirConexao();
        }

        $estado = '%';
        if ($filtro->estado != 'Todos') {
				$estado = $filtro->estado;
		}

		$cidade = '%';
		if ($estado != '%' && $filtro->cidade != 'Todos') {
			$cidade = $filtro->cidade;
		}

		// Transforma os meses de string para numero
        $filtro->mesMin = utf8_decode($filtro->mesMin);
		$sql_mes_min = "SELECT mesNum FROM Mes WHERE mes LIKE '{$filtro->mesMin}'";
		$result_mes_min = mysqli_query($conexao,$sql_mes_min);
		$row = mysqli_fetch_assoc($result_mes_min);
		$mesMin = $row['mesNum'];
		$this->conn->fecharConexao();
		$conexao = $this->conn->abrirConexao();

        $filtro->mesMax = utf8_decode($filtro->mesMax);
		$sql_mes_max = "SELECT mesNum FROM Mes WHERE mes LIKE '{$filtro->mesMax}'";
		$result_mes_max = mysqli_query($conexao,$sql_mes_max);
		$row = mysqli_fetch_assoc($result_mes_max);
		$mesMax = $row['mesNum'];
		$this->conn->fecharConexao();
		$conexao = $this->conn->abrirConexao();

		$sql_medias =
				"SELECT " . 
					"cat.nome AS categoria, " .
					"cat.idCategoria AS idCategoria, " .
					"AVG(m.valor) AS valor " .
				"FROM " . 
					"Movimentacao AS m " .
					"INNER JOIN Users AS u       ON       u.email=m.dadosMesUsersEmail " .
					"INNER JOIN Cidade AS cid    ON  cid.idCidade=u.cidadeId " .
					"INNER JOIN Categoria AS cat ON m.categoriaId=cat.IdCategoria " .
					"INNER JOIN DadosMes AS dm   ON m.dadosMesMes=dm.mes " .
				"WHERE " .
					"tipo='{$tipo}' " .
                    "AND u.nascimento <= STR_TO_DATE('{$filtro->idadeMin}', '%Y-%m-%d') ".
                    "AND u.nascimento >= STR_TO_DATE('{$filtro->idadeMax}', '%Y-%m-%d') ".
					"AND m.dadosMesUsersEmail LIKE '{$user}' " .
					"AND cid.nome LIKE '{$cidade}' " .
					"AND cid.estadoEstado LIKE '{$estado}' " .
					"AND cid.estadoCC LIKE '{$pais}' " .
					"AND dm.usersEmail = '{$owner}' " .
					"AND MONTH(m.dadosMesMes) >= '{$mesMin}' " .
					"AND MONTH(m.dadosMesMes) <= '{$mesMax}' " .
					"AND YEAR(m.dadosMesMes) >= '{$filtro->anoMin}' " .
					"AND YEAR(m.dadosMesMes) <= '{$filtro->anoMax}' " .
				"GROUP BY categoriaId";
#        exec("echo $sql_medias >> $logfile");

		/*
			* Nota: a semantica da selecao de datas nao seleciona um intervalo. Ela seleciona:
			* (a) dados de um dos meses pra um unico ano
			* (b) dados de todos os meses em um unico ano
			* (c) dados de um dos meses (e.g. janeiro) ao longo de um periodo de varios anos
					(e.g. media de todos os janeiros ao longo de todos os anos disponiveis)
			* (d) dados historicos totais para todos os meses que o usuario submeteu
			*
			* Em funcao disso, tem que acertar a parte das datas se for transpor a query para o
			*   grafico de barras
		*/

		$result_medias = mysqli_query($conexao,$sql_medias);

        if(!$result_medias){
            $this->conn->fecharConexao();
            return array();
        } else {
			$rows = array();
			while($row = mysqli_fetch_assoc($result_medias)) {
			      $row["categoria"] = utf8_encode($row["categoria"]);
			      $rows[]=$row;
			}
			$this->conn->fecharConexao();
			return $rows;
		}
	}

    function carregaBar($filtro, $user, $owner) {
        $conexao = $this->conn->abrirConexao();

        $sql_medias = NULL;

        if ($user == "") {
            $user = '%';
        }

        // Acha o country code do pais solicitado, se necessario
        $pais = '%';
        $estado = '%';
        $cidade = '%';
        if ($filtro->pais != 'Todos') {
            $sql_aux = "SELECT countryCode FROM Pais WHERE nome='{$filtro->pais}'";
            $result_aux = mysqli_query($conexao,$sql_aux);
            $row = mysqli_fetch_assoc($result_aux);
            $pais = $row['countryCode'];
            $this->conn->fecharConexao();
            $conexao = $this->conn->abrirConexao();

            if ($filtro->estado != 'Todos') {
                $estado = $filtro->estado;

                if($filtro->cidade != 'Todos') {
                    $cidade = $filtro->cidade;
                }
            }
        }

        // Transforma os meses de string para numero
        $filtro->mesMin = utf8_decode($filtro->mesMin);
        $sql_mes_min = "SELECT mesNum FROM Mes WHERE mes LIKE '{$filtro->mesMin}'";
        $result_mes_min = mysqli_query($conexao,$sql_mes_min);
        $row = mysqli_fetch_assoc($result_mes_min);
        $mesMin = $row['mesNum'];
        $this->conn->fecharConexao();
        $conexao = $this->conn->abrirConexao();

        $filtro->mesMax = utf8_decode($filtro->mesMax);
        $sql_mes_max = "SELECT mesNum FROM Mes WHERE mes LIKE '{$filtro->mesMax}'";
        $result_mes_max = mysqli_query($conexao,$sql_mes_max);
        $row = mysqli_fetch_assoc($result_mes_max);
        $mesMax = $row['mesNum'];
        $this->conn->fecharConexao();
        $conexao = $this->conn->abrirConexao();

        // Filtro por categoria
        $filtro->categoria = utf8_decode($filtro->categoria);
        $filtroCat = "";

		$gambi = NULL;

        if ($filtro->categoria == "Total receitas") {
            $filtroCat = "AND m.tipo = 'receita' ";
			$gambi = "receitas";
        } else {
            if ($filtro->categoria == "Total despesas") {
                $filtroCat = "AND m.tipo = 'despesa' ";
				$gambi = "despesas";
            } else {
                if ($filtro->categoria != "Saldo") {
                    $filtroCat = "AND cat.nome = '{$filtro->categoria}' ";
					$gambi = "receitas-despesas";
                }
                // Se for saldo, nao precisa de filtro, pega tudo
            }
        }

		$sql_total = 
				"SELECT " .
					"MONTH(dadosMesMes) AS mes, " .
					"YEAR(dadosMesMes) AS ano, " .
					"AVG({$gambi}) AS valor, " .
					"'receita' AS tipo " .
				"FROM " . 
						"( " .
							"SELECT dadosMesMes, dadosMesUsersEmail, SUM(m1.valor) AS despesas " .
							"FROM Movimentacao AS m1 " .
						        "INNER JOIN Users AS u1       ON       u1.email=m1.dadosMesUsersEmail " .
						        "INNER JOIN Cidade AS cid1    ON  cid1.idCidade=u1.cidadeId " .
						        "INNER JOIN Categoria AS cat1 ON m1.categoriaId=cat1.idCategoria " .
						        "INNER JOIN DadosMes AS dm1   ON m1.dadosMesMes=dm1.mes " .
							"WHERE " .
								"m1.tipo='despesa' " .
						        "AND u1.nascimento <= STR_TO_DATE('{$filtro->idadeMin}', '%Y-%m-%d') ".
						        // "AND u.nascimento >= STR_TO_DATE('{$filtro->idadeMax}', '%Y-%m-%d') ".
						        "AND cid1.nome LIKE '{$cidade}' " .
						        "AND cid1.estadoEstado LIKE '{$estado}' " .
						        "AND cid1.estadoCC LIKE '{$pais}' " .
						        "AND dm1.usersEmail = '{$owner}' " .
						        "AND MONTH(m1.dadosMesMes) >= '{$mesMin}' " .
						        "AND MONTH(m1.dadosMesMes) <= '{$mesMax}' " .
						        "AND YEAR(m1.dadosMesMes) >= '{$filtro->anoMin}' " .
						        "AND YEAR(m1.dadosMesMes) <= '{$filtro->anoMax}' " .
							"GROUP BY dadosMesMes, dadosMesUsersEmail " .
						") AS t1 " .
					"NATURAL JOIN " .
						"( " .
							"SELECT m2.dadosMesMes, m2.dadosMesUsersEmail, SUM(m2.valor) as receitas " .
							"FROM Movimentacao AS m2 " . 
						        "INNER JOIN Users AS u2       ON       u2.email=m2.dadosMesUsersEmail " .
						        "INNER JOIN Cidade AS cid2    ON  cid2.idCidade=u2.cidadeId " .
						        "INNER JOIN Categoria AS cat2 ON m2.categoriaId=cat2.idCategoria " .
						        "INNER JOIN DadosMes AS dm2   ON m2.dadosMesMes=dm2.mes " .
							"WHERE " .
								"m2.tipo='receita' " .
						        "AND u2.nascimento <= STR_TO_DATE('{$filtro->idadeMin}', '%Y-%m-%d') ".
						        // "AND u.nascimento >= STR_TO_DATE('{$filtro->idadeMax}', '%Y-%m-%d') ".
						        "AND cid2.nome LIKE '{$cidade}' " .
						        "AND cid2.estadoEstado LIKE '{$estado}' " .
						        "AND cid2.estadoCC LIKE '{$pais}' " .
						        "AND dm2.usersEmail = '{$owner}' " .
						        "AND MONTH(m2.dadosMesMes) >= '{$mesMin}' " .
						        "AND MONTH(m2.dadosMesMes) <= '{$mesMax}' " .
						        "AND YEAR(m2.dadosMesMes) >= '{$filtro->anoMin}' " .
						        "AND YEAR(m2.dadosMesMes) <= '{$filtro->anoMax}' " .
							"GROUP BY m2.dadosMesMes, m2.dadosMesUsersEmail " .
						") AS t2 " .
				"GROUP BY t2.dadosMesMes;";

        $sql_medias =
                "SELECT " . 
                    "MONTH(m.dadosMesMes) AS mes, " .
                    "YEAR(m.dadosMesMes) AS ano, " .
                    "AVG(m.valor) AS valor, " .
                    "m.tipo AS tipo " .
                "FROM " . 
                    "Movimentacao AS m " .
                    "INNER JOIN Users AS u       ON       u.email=m.dadosMesUsersEmail " .
                    "INNER JOIN Cidade AS cid    ON  cid.idCidade=u.cidadeId " .
                    "INNER JOIN Categoria AS cat ON m.categoriaId=cat.idCategoria " .
                    "INNER JOIN DadosMes AS dm   ON m.dadosMesMes=dm.mes " .
                "WHERE " .
                    "m.dadosMesUsersEmail LIKE '%' " .
                    $filtroCat .
                    "AND u.nascimento <= STR_TO_DATE('{$filtro->idadeMin}', '%Y-%m-%d') ".
                    // "AND u.nascimento >= STR_TO_DATE('{$filtro->idadeMax}', '%Y-%m-%d') ".
                    "AND cid.nome LIKE '{$cidade}' " .
                    "AND cid.estadoEstado LIKE '{$estado}' " .
                    "AND cid.estadoCC LIKE '{$pais}' " .
                    "AND dm.usersEmail = '{$owner}' " .
                    "AND MONTH(m.dadosMesMes) >= '{$mesMin}' " .
                    "AND MONTH(m.dadosMesMes) <= '{$mesMax}' " .
                    "AND YEAR(m.dadosMesMes) >= '{$filtro->anoMin}' " .
                    "AND YEAR(m.dadosMesMes) <= '{$filtro->anoMax}' " .
                "GROUP BY dadosMesMes;";

        /*
            * Nota: a semantica da selecao de datas nao seleciona um intervalo. Ela seleciona:
            * (a) dados de um dos meses pra um unico ano
            * (b) dados de todos os meses em um unico ano
            * (c) dados de um dos meses (e.g. janeiro) ao longo de um periodo de varios anos
                    (e.g. media de todos os janeiros ao longo de todos os anos disponiveis)
            * (d) dados historicos totais para todos os meses que o usuario submeteu
            *
            * Em funcao disso, tem que acertar a parte das datas se for transpor a query para o
            *   grafico de barras
        */

		if ($gambi != NULL) {
	        $result_medias = mysqli_query($conexao,$sql_total);
		} else {
	        $result_medias = mysqli_query($conexao,$sql_medias);
		}

        $num_rows = mysqli_num_rows($result_medias);
        $logfile = "/var/www/html/WebVersion/log.txt";
        exec("echo $num_rows >> $logfile");

        if(!$result_medias){
            $this->conn->fecharConexao();
            return array();
        } else {
            $rows = array();

            $sql_user = 
                    "SELECT " .
                        "m.valor AS valor, " .
                        "m.tipo AS tipo " .
                    "FROM " . 
                        "Movimentacao AS m " .
                        "INNER JOIN Users AS u       ON       u.email=m.dadosMesUsersEmail " .
                        "INNER JOIN Cidade AS cid    ON  cid.idCidade=u.cidadeId " .
                        "INNER JOIN Categoria AS cat ON m.categoriaId=cat.IdCategoria " .
                        "INNER JOIN DadosMes AS dm   ON m.dadosMesMes=dm.mes " .
                    "WHERE " .
                        "m.dadosMesUsersEmail LIKE '{$user}' " .
                        $filtroCat .
                        "AND dm.usersEmail = '{$owner}' " .
                        "AND MONTH(m.dadosMesMes) >= '{$mesMin}' " .
                        "AND MONTH(m.dadosMesMes) <= '{$mesMax}' " .
                        "AND YEAR(m.dadosMesMes) >= '{$filtro->anoMin}' " .
                        "AND YEAR(m.dadosMesMes) <= '{$filtro->anoMax}' " .
                    "GROUP BY dadosMesMes;";
            $result_user = mysqli_query($conexao,$sql_user);

            if(!$result_user){
                $this->conn->fecharConexao();
                return array();
            } else {

                while(($row = mysqli_fetch_assoc($result_medias)) && ($rowu = mysqli_fetch_assoc($result_user))) {
                      // $row["categoria"] = utf8_encode($row["categoria"]);
                        // $rowu = mysqli_fetch_assoc($result_user);
                        $row["userValor"] = $rowu["valor"];
                        $row["usertipo"] = $rowu["tipo"];
                      $rows[]=$row;
                }
                $this->conn->fecharConexao();
                return $rows;
            }
        }
    }

    function carregaUltimoMes($user){
        $conexao = $this->conn->abrirConexao();
        
        $logfile = "/var/www/html/WebVersion/log2.txt";
        $sql_max_month = "SELECT mes from DadosMes WHERE mes = (SELECT  max(subp.mes) from DadosMes subp WHERE usersEmail = '{$user}');";
        $result_max_month = mysqli_query($conexao,$sql_max_month);
        
        if(!$result_max_month){
            $this->conn->fecharConexao();
            return array();
        } else {
            while($row = mysqli_fetch_assoc($result_max_month)) {
                $row["mes"] = utf8_encode($row["mes"]);
                // exec("echo $row >> $logfile");
                $rows[]=$row;
            }
            $this->conn->fecharConexao();
            return $rows;
        }
    }

    function carregaPieChartDashboard($user, $tipo){

        $conexao = $this->conn->abrirConexao();

        $sql_medias = "SELECT c.nome AS categoria, c.idCategoria AS idCategoria, m.valor AS valor FROM Movimentacao AS m INNER JOIN Users AS u ON u.email=m.dadosMesUsersEmail INNER JOIN Categoria AS c ON m.categoriaId=c.IdCategoria WHERE tipo='{$tipo}' AND m.dadosMesUsersEmail LIKE '{$user}' AND m.dadosMesMes = (SELECT  max(subp.mes) from DadosMes subp WHERE usersEmail = '{$user}') GROUP BY categoriaId;";
        $result_medias = mysqli_query($conexao,$sql_medias);

        if(!$result_medias){
            $this->conn->fecharConexao();
            return array();
        } else {
            $rows = array();
            while($row = mysqli_fetch_assoc($result_medias)) {
                  $row["categoria"] = utf8_encode($row["categoria"]);
                  $rows[]=$row;
            }
            
            $this->conn->fecharConexao();
            return $rows;
        }

    }
}
?>
