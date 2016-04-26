/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Thainan
 */
public class APISistemaDesktop {

    private ArrayList<DadosMes> dadosMes;
    private Conversor conversor;
    
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    /*
        Construtor da classe
        Inicializa o array de dadosMes vazio
        Inicializa o conversor a ser utilizado para salvar os dados em XML
    */
    public APISistemaDesktop(){
        dadosMes = new ArrayList<>();
        conversor = new Conversor();
    }
    
    /* 
        criaMes é responsavel por adicionar um novo mes com dados ao sistema
        e salvar esse novo mes no arquivo
    */
    public void criaMes(DadosMes dMes){
        dadosMes.add(dMes);
        this.salvaMes(dMes.getMes());
    }
    
    /* 
        visualizaMes encontra os dados de um determinado mes no sistema e envia
        para a interface para ser mostrado ao usuario
        retorna null caso o mes nao exista 
    */
    public DadosMes visualizaMes(Date mes){
        
        for (DadosMes dm : dadosMes){
            if (dm.getMes().equals(mes)) {
                return dm;
            }
        }
        
        return null;
    }
    
    /*
        exportaMes busca os dados do mes a ser exportado e chama o conversor
        para converter os dados para XML e o arquivo é salvo no dir recebido por
        parametro.
    */
    public void exportaMes(Date mes, String dir){
        for (DadosMes dm : dadosMes){
            if (dm.getMes().equals(mes)) {
                //TODO
//                conversor.converteParaXML(dm, dir);
            }
        }
    }
    
    /*
        salvaMes busca os dados do mes a ser exportado e chama o conversor
        para converter os dados para XML e o arquivo é salvo no dir padrao de
        backup dos dados.
    */
    public void salvaMes(Date mes){
        for (DadosMes dm : dadosMes){
            if (dm.getMes().equals(mes)) {
                //TODO
//                conversor.converteParaXML(dm);
            }
        }
    }
    
    /*
        deletaMes busca o mes selecionado e exclui ele do sistema apagando
        junto o arquivo correspondente, caso o usuario deseje apagar aqueles
        dados da máquina.
    */
    public void deletaMes(Date mes){
        for (DadosMes dm : dadosMes){
            if (dm.getMes().equals(mes)) {
                this.deletaArquivo(mes);
                dadosMes.remove(dm);
                
            }
        }
    }
    
    /*
        deletaArquivo exclui o arquivo correspondente ao mes selecionado.
    */
    public void deletaArquivo(Date mes){
        
    }
    
    /*
        carregaArquivos transforma todos os arquivos existentes no diretorio
        de backup e importa para dentro do sistema em estruturas DadosMes.
    */
    public void carregaArquivos(){
        
    }
    
    /*
        geraRelatorio
        A SER DEFINIDO
    */
    public ArrayList<DadosMes> geraRelatorio(){
        return null;
    }
}
