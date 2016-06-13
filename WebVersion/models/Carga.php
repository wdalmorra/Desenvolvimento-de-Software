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
}
?>