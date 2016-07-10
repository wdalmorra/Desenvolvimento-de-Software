<?php
class Filtro {

    public $idadeMin;
    public $idadeMax;
    public $anoMin;
    public $anoMax;
    public $mesMin;
    public $mesMax;
	public $categoria;
	public $cidade;
	public $estado;
	public $pais;

    function __construct() {
        $this->idadeMin = NULL;
        $this->idadeMax = NULL;
        $this->anoMin = NULL;
        $this->anoMax = NULL;
        $this->mesMin = NULL;
        $this->mesMax = NULL;
        $this->categoria = NULL;
        $this->cidade = NULL;
        $this->estado = NULL;
        $this->pais = NULL;
    }
}
?>
