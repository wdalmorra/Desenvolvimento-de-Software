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

	function carregaMediaPie($filtro, $isDespesa, $user) {
		$conexao = $this->conn->abrirConexao();

		$sql_medias = NULL;
		$tipo = "receita";

		if ($isDespesa) {
			$tipo = "despesa";
		}

		if ($user == "") {
			$sql_medias = "SELECT c.nome AS categoria, c.idCategoria AS idCategoria, AVG(m.valor) AS valor FROM Movimentacao AS m INNER JOIN Categoria as c on m.categoriaId=c.IdCategoria WHERE tipo='{$tipo}' GROUP BY categoriaId";
		} else {
			$sql_medias = "SELECT c.nome AS categoria, c.idCategoria AS idCategoria, AVG(m.valor) AS valor FROM Movimentacao AS m INNER JOIN Categoria as c on m.categoriaId=c.IdCategoria WHERE tipo='{$tipo}' AND  m.dadosMesUsersEmail='{$user}' GROUP BY categoriaId";
		}

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
