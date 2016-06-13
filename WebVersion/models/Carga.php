<?php

include '../util/ConexaoDB.php';

class Carga {

    var $conn;

    function __construct() {
        $this->conn = new ConexaoDB();
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
		          $rows[]=$row;
		        }

		        $this->conn->fecharConexao();
		        return $row;
		    }
        }
    }

}
?>