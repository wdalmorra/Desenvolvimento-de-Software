/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Observable;

/**
 *
 * @author William
 */
public class APISistemaDesktop extends Observable{

    private ArrayList<DadosMes> dadosMes;
    private Conversor conversor;
    
    
    public static void main(String[] args) {
        APISistemaDesktop sisDesk = new APISistemaDesktop();
        DadosMes dm = new DadosMes();
        dm.setDate(new GregorianCalendar(2016,04,26));
        Despesa d = new Despesa();
        d.setValor(15);
        d.setCategoria(CategoriaDespesa.DESPESA1);
        dm.addMovimentacao(d);
        Receita r = new Receita(CategoriaReceita.RECEITA1,30);
        dm.addMovimentacao(r);
        sisDesk.criaMes(dm);
        
        System.out.println(sisDesk.visualizaMes(new GregorianCalendar(2016,04,26)).getMovimentacoes().get(0).getValor());
        System.out.println(sisDesk.visualizaMes(new GregorianCalendar(2016,04,26)).getMovimentacoes().get(1).getValor());
        
        System.out.println(new GregorianCalendar(2016,04,27).get(GregorianCalendar.YEAR));
        
    }

    /*
        Construtor da classe
        Inicializa o array de dadosMes vazio
        Inicializa o conversor a ser utilizado para salvar os dados em XML
    */
    public APISistemaDesktop(){
        dadosMes = new ArrayList<>();
        conversor = Conversor.getInstance();
    }

    /*
        criaMes é responsavel por adicionar um novo mes com dados ao sistema.
    */
    public void criaMes(DadosMes dMes){
        dadosMes.add(dMes);
    }
    
    /* 
        addMovimentacao adiciona uma movimentacao a um determinado mes.
        Observer nota essa modificacao e eh notificado.
    */
    public void addMovimentacao(GregorianCalendar mes, Movimentacao mov){
        
        for (DadosMes dm : dadosMes){
            if (comparaMeses(dm.getMes(),mes)) {
                dm.addMovimentacao(mov);
                notifyObservers();
                break;
            }
        }        
    }
    
    /* 
        addMovimentacao remove uma movimentacao a um determinado mes.
        Observer nota essa modificacao e eh notificado.
    */
    public void removeMovimentacao(GregorianCalendar mes, Movimentacao mov) {
        for (DadosMes dm : dadosMes){
            if (comparaMeses(dm.getMes(),mes)) {
                
                dm.getMovimentacoes().remove(mov);
                notifyObservers();
                break;
            
            }
        }
    }

    /* 
        visualizaMes encontra os dados de um determinado mes no sistema e envia
        para a interface para ser mostrado ao usuario
        retorna null caso o mes nao exista 
    */
    public DadosMes visualizaMes(GregorianCalendar mes){
        
        for (DadosMes dm : dadosMes){
            if (comparaMeses(dm.getMes(),mes)) {
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
    public void exportaMes(GregorianCalendar mes, String dir){
        for (DadosMes dm : dadosMes){
            if (comparaMeses(dm.getMes(),mes)) {
                conversor.converteParaXML(dm, dir);
                break;
            }
        }
    }

    /*
        salvaMes busca os dados do mes a ser exportado e chama o conversor
        para converter os dados para XML e o arquivo é salvo no dir padrao de
        backup dos dados.
    */
    public void salvaMes(GregorianCalendar mes){
        for (DadosMes dm : dadosMes){
            if (comparaMeses(dm.getMes(),mes)) {
                conversor.converteParaXML(dm);
                break;
            }
        }
    }

    /*
        deletaMes busca o mes selecionado e exclui ele do sistema apagando
        junto o arquivo correspondente, caso o usuario deseje apagar aqueles
        dados da máquina.
    */
    public void deletaMes(GregorianCalendar mes){
        for (DadosMes dm : dadosMes){
            if (comparaMeses(dm.getMes(),mes)) {
                this.deletaArquivo(mes);
                dadosMes.remove(dm);
                break;
            }
        }
    }

    /*
        deletaArquivo exclui o arquivo correspondente ao mes selecionado.
    */
    public void deletaArquivo(GregorianCalendar mes){
        
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
    
    /*
        comparaMeses simplesmente verifica se dadas duas datas, os meses e anos
        sao iguais
    */
    private boolean comparaMeses(GregorianCalendar a, GregorianCalendar b){
        return (a.get(GregorianCalendar.YEAR) == b.get(GregorianCalendar.YEAR))
                && (a.get(GregorianCalendar.MONTH) == b.get(GregorianCalendar.MONTH));
    }
}