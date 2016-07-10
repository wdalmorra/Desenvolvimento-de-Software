<?php

include '../models/Carga.php';

$carga = new Carga();
echo json_encode($carga->carregaMediaPie(NULL, true));

?>
