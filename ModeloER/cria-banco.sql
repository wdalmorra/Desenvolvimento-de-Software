SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `Drefinancas` ;
CREATE SCHEMA IF NOT EXISTS `Drefinancas` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `Drefinancas` ;


-- -----------------------------------------------------
-- Table `Drefinancas`.`Mes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Drefinancas`.`Mes` ;

CREATE TABLE IF NOT EXISTS `Drefinancas`.`Mes` (
  `mes` VARCHAR(15) NOT NULL,
  `mesNum` VARCHAR(4) ,
  PRIMARY KEY (`mesNum`),
  UNIQUE INDEX `mes_UNIQUE` (`mesNum` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `Drefinancas`.`Ano`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Drefinancas`.`Ano` ;

CREATE TABLE IF NOT EXISTS `Drefinancas`.`Ano` (
  `ano` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`ano`),
  UNIQUE INDEX `ano_UNIQUE` (`ano` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `Drefinancas`.`Idade`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Drefinancas`.`Idade` ;

CREATE TABLE IF NOT EXISTS `Drefinancas`.`Idade` (
  `idade` VARCHAR(5) NOT NULL,
  `idadeNum` VARCHAR(3),
  PRIMARY KEY (`idadeNum`),
  UNIQUE INDEX `idade_UNIQUE` (`idadeNum` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `Drefinancas`.`Pais`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Drefinancas`.`Pais` ;

CREATE TABLE IF NOT EXISTS `Drefinancas`.`Pais` (
  `countryCode` VARCHAR(2) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`countryCode`),
  UNIQUE INDEX `countryCode_UNIQUE` (`countryCode` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Drefinancas`.`Estado`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Drefinancas`.`Estado` ;

CREATE TABLE IF NOT EXISTS `Drefinancas`.`Estado` (
  `estado` VARCHAR(2) NOT NULL,
  `paisCountryCode` VARCHAR(2) NOT NULL,
  PRIMARY KEY (`estado`, `paisCountryCode`),
  INDEX `fk_Estado_Pais1_idx` (`paisCountryCode` ASC),
  CONSTRAINT `fk_Estado_Pais1`
    FOREIGN KEY (`paisCountryCode`)
    REFERENCES `Drefinancas`.`Pais` (`countryCode`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Drefinancas`.`Cidade`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Drefinancas`.`Cidade` ;

CREATE TABLE IF NOT EXISTS `Drefinancas`.`Cidade` (
  `idCidade` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `estadoEstado` VARCHAR(2) NOT NULL,
  `estadoCC` VARCHAR(2) NOT NULL,
  PRIMARY KEY (`idCidade`),
  UNIQUE INDEX `idCidade_UNIQUE` (`idCidade` ASC),
  INDEX `fk_Cidade_Estado1_idx` (`estadoEstado` ASC, `estadoCC` ASC),
  CONSTRAINT `fk_Cidade_Estado1`
    FOREIGN KEY (`estadoEstado` , `estadoCC`)
    REFERENCES `Drefinancas`.`Estado` (`estado` , `paisCountryCode`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Drefinancas`.`Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Drefinancas`.`Users` ;

CREATE TABLE IF NOT EXISTS `Drefinancas`.`Users` (
  `email` VARCHAR(50) NOT NULL,
  `nascimento` DATE NULL,
  `nome` VARCHAR(50) NOT NULL,
  `senha` VARCHAR(100) NOT NULL,
  `cidadeId` INT NULL,
  `status` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`email`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `fk_Users_Cidade1_idx` (`cidadeId` ASC),
  CONSTRAINT `fk_Users_Cidade1`
    FOREIGN KEY (`cidadeId`)
    REFERENCES `Drefinancas`.`Cidade` (`idCidade`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Drefinancas`.`DadosMes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Drefinancas`.`DadosMes` ;

CREATE TABLE IF NOT EXISTS `Drefinancas`.`DadosMes` (
  `mes` DATE NOT NULL,
  `usersEmail` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`mes`, `usersEmail`),
  INDEX `fk_dadosMes_Users_idx` (`usersEmail` ASC),
  CONSTRAINT `fk_dadosMes_Users`
    FOREIGN KEY (`usersEmail`)
    REFERENCES `Drefinancas`.`Users` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Drefinancas`.`movimentacoes`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `Drefinancas`.`movimentacoes` ;

-- CREATE TABLE IF NOT EXISTS `Drefinancas`.`movimentacoes` (
-- )
-- ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `Drefinancas`.`Categoria`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Drefinancas`.`Categoria` ;

CREATE TABLE IF NOT EXISTS `Drefinancas`.`Categoria` (
  `nome` VARCHAR(50) NOT NULL,
  `idCategoria` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idCategoria`),
  UNIQUE INDEX `idCategoria_UNIQUE` (`idCategoria` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Drefinancas`.`Movimentacao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Drefinancas`.`Movimentacao` ;

CREATE TABLE IF NOT EXISTS `Drefinancas`.`Movimentacao` (
  `categoriaId` INT NOT NULL,
  `dadosMesMes` DATE NOT NULL,
  `dadosMesUsersEmail` VARCHAR(50) NOT NULL,
  `valor` MEDIUMTEXT NOT NULL,
  `tipo` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`dadosMesMes`, `dadosMesUsersEmail`, `categoriaId`),
  INDEX `fk_Movimentacao_DadosMes1_idx` (`dadosMesMes` ASC, `dadosMesUsersEmail` ASC),
  INDEX `fk_Movimentacao_Categoria1_idx` (`categoriaId` ASC),
  CONSTRAINT `fk_Movimentacao_DadosMes1`
    FOREIGN KEY (`dadosMesMes` , `dadosMesUsersEmail`)
    REFERENCES `Drefinancas`.`DadosMes` (`mes` , `usersEmail`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Movimentacao_Categoria1`
    FOREIGN KEY (`categoriaId`)
    REFERENCES `Drefinancas`.`Categoria` (`idCategoria`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Drefinancas`.`Admin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Drefinancas`.`Admin` ;

CREATE TABLE IF NOT EXISTS `Drefinancas`.`Admin` (
  `usersEmail` VARCHAR(50) NOT NULL,
  INDEX `fk_Admin_Users1_idx` (`usersEmail` ASC),
  UNIQUE INDEX `usersEmail_UNIQUE` (`usersEmail` ASC),
  PRIMARY KEY (`usersEmail`),
  CONSTRAINT `fk_Admin_Users1`
    FOREIGN KEY (`usersEmail`)
    REFERENCES `Drefinancas`.`Users` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
