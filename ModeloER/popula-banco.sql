USE Drefinancas;

INSERT INTO `Drefinancas`.`Pais` VALUES ('br', 'Brasil');
INSERT INTO `Drefinancas`.`Pais` VALUES ('ar', 'Argentina');
INSERT INTO `Drefinancas`.`Estado` VALUES ('RS', 'br');
INSERT INTO `Drefinancas`.`Estado` VALUES ('PE', 'br');
INSERT INTO `Drefinancas`.`Estado` VALUES ('NA', 'ar');
INSERT INTO `Drefinancas`.`Cidade` (nome, estadoEstado, estadoCC) VALUES ('Pelotas', 'RS', 'br');
INSERT INTO `Drefinancas`.`Cidade` (nome, estadoEstado, estadoCC) VALUES ('Camaquã', 'RS', 'br');
INSERT INTO `Drefinancas`.`Cidade` (nome, estadoEstado, estadoCC) VALUES ('Recife', 'PE', 'br');
INSERT INTO `Drefinancas`.`Cidade` (nome, estadoEstado, estadoCC) VALUES ('Buenos Aires', 'NA', 'ar');
