<?php
include 'config.php';

class ConexaoDB {

	var $host;
	var $username;
	var $password;
	var $database;
	var $conn;


	function __construct(){
		$this->host = HOSTNAME;
		$this->username = DB_USER;
		$this->password = DB_PASSWORD;
		$this->database = DB_NAME;
	}

	function abrirConexao(){
		$this->conn = mysqli_connect("$this->host","$this->username", "$this->password", "$this->database");
		return $this->conn;
	}

	function fecharConexao(){
		mysqli_close($this->conn);
	}

	
}

?>