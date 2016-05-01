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
    public void criaMes(GregorianCalendar gc){
        DadosMes dMes = new DadosMes();
        dMes.setDate(gc);
        dadosMes.add(dMes);
    }
    
    /* 
        addMovimentacao adiciona uma movimentacao a um determinado mes.
        Observer nota essa modificacao e eh notificado.
    */
    public void addMovimentacao(int valor, String cat, String tipo, GregorianCalendar mes){
        Movimentacao mov = (tipo.equals("D")) ? 
            new Despesa(CategoriaDespesa.stringToCategoria(cat.toUpperCase()), valor) :
            new Receita(CategoriaReceita.stringToCategoria(cat.toUpperCase()), valor);
        boolean alt = false;
        for (DadosMes dm : dadosMes){
            if (comparaMeses(dm.getMes(),mes)) {
                for (Movimentacao m: dm.getMovimentacoes()) {
                    if (m instanceof Receita) {
                        if (CategoriaReceita.categoriaToString(((Receita)m).getCategoria()).equals(cat) &&  tipo.equals("R")) {
                            m.setValor(m.getValor()+ valor);
                            alt = true;
                            break;
                        }
                    } else {
                        if (CategoriaDespesa.categoriaToString(((Despesa)m).getCategoria()).equals(cat) &&  tipo.equals("D")) {
                            m.setValor(m.getValor()+ valor);
                            alt = true;
                            break;
                        }
                    }
                }
                if (!alt) {
                    dm.addMovimentacao(mov);
                }
                this.setChanged();
                this.notifyObservers(dm.getMovimentacoes());
                break;
            }
        }        
    }
    
    /* 
        alteraMovimentacao altera uma movimentacao a um determinado mes.
        Observer nota essa modificacao e eh notificado.
    */
    public void alteraMovimentacao(int valor, String cat, String tipo, GregorianCalendar mes){
        Movimentacao mov = (tipo.equals("D")) ? 
            new Despesa(CategoriaDespesa.stringToCategoria(cat.toUpperCase()), valor) :
            new Receita(CategoriaReceita.stringToCategoria(cat.toUpperCase()), valor);
        for (DadosMes dm : dadosMes){
            if (comparaMeses(dm.getMes(),mes)) {
                for (Movimentacao m: dm.getMovimentacoes()) {
                    if (m instanceof Receita) {
                        if (CategoriaReceita.categoriaToString(((Receita)m).getCategoria()).equals(cat)) {
                            m.setValor(valor);
                            break;
                        }
                    } else {
                        if (CategoriaDespesa.categoriaToString(((Despesa)m).getCategoria()).equals(cat)) {
                            m.setValor(valor);
                            break;
                        }
                    }
                }
                this.setChanged();
                this.notifyObservers(dm.getMovimentacoes());
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
                this.setChanged();
                this.notifyObservers(dm.getMovimentacoes());
                break;
            }
        }
    }

    /* 
        visualizaMes encontra os dados de um determinado mes no sistema e envia
        para a interface para ser mostrado ao usuario
        retorna null caso o mes nao exista 
    */
    public boolean visualizaMes(GregorianCalendar mes){
        for (DadosMes dm : dadosMes){
            if (comparaMeses(dm.getMes(),mes)) {
                
               this.setChanged();
               
               notifyObservers(dm.getMovimentacoes());
               
               return true;
            }
        }

        return false;
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