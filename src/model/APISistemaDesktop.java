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
    
    private DadosMes dadosDeTrabalho;

    public static void main(String[] args) {
        
        
    }
    
    /*
        carregaArquivos transforma todos os arquivos existentes no diretorio
        de backup e importa para dentro do sistema em estruturas DadosMes.
    */
    private void carregaArquivos(){
        ArrayList<GregorianCalendar> listaDeArquivos = 
                conversor.carregaListaDeArquivos();
        
        if (listaDeArquivos != null) {
            for (GregorianCalendar gc : listaDeArquivos) {
                DadosMes dm = conversor.converteParaDadosMes(gc);
                if (dm != null) {
                    dadosMes.add(dm);
                }
            }
        }
    }
    
    /*
        transforma a estrutura <code>Array<DadosMes></code> em arquivos xml
    */
    private void salvaArquivos(){
        ArrayList<GregorianCalendar> listaDeArquivos = new ArrayList<>();
        
        for (DadosMes dm : dadosMes) {
            conversor.converteParaXML(dm);
            listaDeArquivos.add(dm.getMes());
        }
        
        conversor.salvaListaDeArquivos(listaDeArquivos);
    }
    

    /*
        Construtor da classe
        Inicializa o array de dadosMes vazio
        Inicializa o conversor a ser utilizado para salvar os dados em XML
    */
    public APISistemaDesktop(){
        dadosMes = new ArrayList<>();
        conversor = Conversor.getInstance();
        dadosDeTrabalho = new DadosMes();
        carregaArquivos();
    }

    /*
        criaMes é responsavel por adicionar um novo mes com dados ao sistema.
    */
    public void criaMes(GregorianCalendar gc){
        dadosDeTrabalho = new DadosMes();
        dadosDeTrabalho.setDate(gc);
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
        for (Movimentacao m: dadosDeTrabalho.getMovimentacoes()) {
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
            dadosDeTrabalho.addMovimentacao(mov);
        }
        this.setChanged();
        this.notifyObservers(dadosDeTrabalho.getMovimentacoes());
        
//        for (DadosMes dm : dadosMes){
//            if (comparaMeses(dm.getMes(),mes)) {
//                for (Movimentacao m: dm.getMovimentacoes()) {
//                    if (m instanceof Receita) {
//                        if (CategoriaReceita.categoriaToString(((Receita)m).getCategoria()).equals(cat) &&  tipo.equals("R")) {
//                            m.setValor(m.getValor()+ valor);
//                            alt = true;
//                            break;
//                        }
//                    } else {
//                        if (CategoriaDespesa.categoriaToString(((Despesa)m).getCategoria()).equals(cat) &&  tipo.equals("D")) {
//                            m.setValor(m.getValor()+ valor);
//                            alt = true;
//                            break;
//                        }
//                    }
//                }
//                if (!alt) {
//                    dm.addMovimentacao(mov);
//                }
//                this.setChanged();
//                this.notifyObservers(dm.getMovimentacoes());
//                break;
//            }
//        }        
    }
    
    /* 
        alteraMovimentacao altera uma movimentacao a um determinado mes.
        Observer nota essa modificacao e eh notificado.
    */
    public void alteraMovimentacao(int valor, String cat, String tipo, GregorianCalendar mes){
        Movimentacao mov = (tipo.equals("D")) ? 
            new Despesa(CategoriaDespesa.stringToCategoria(cat.toUpperCase()), valor) :
            new Receita(CategoriaReceita.stringToCategoria(cat.toUpperCase()), valor);
        for (Movimentacao m: dadosDeTrabalho.getMovimentacoes()) {
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
        this.notifyObservers(dadosDeTrabalho.getMovimentacoes());

//        for (DadosMes dm : dadosMes){
//            if (comparaMeses(dm.getMes(),mes)) {
//                for (Movimentacao m: dm.getMovimentacoes()) {
//                    if (m instanceof Receita) {
//                        if (CategoriaReceita.categoriaToString(((Receita)m).getCategoria()).equals(cat)) {
//                            m.setValor(valor);
//                            break;
//                        }
//                    } else {
//                        if (CategoriaDespesa.categoriaToString(((Despesa)m).getCategoria()).equals(cat)) {
//                            m.setValor(valor);
//                            break;
//                        }
//                    }
//                }
//                this.setChanged();
//                this.notifyObservers(dm.getMovimentacoes());
//                break;
//            }
//        }        
    }
    
    /* 
        addMovimentacao remove uma movimentacao a um determinado mes.
        Observer nota essa modificacao e eh notificado.
    */
    public void removeMovimentacao(GregorianCalendar mes, Movimentacao mov) {
        dadosDeTrabalho.getMovimentacoes().remove(mov);
        this.setChanged();
        this.notifyObservers(dadosDeTrabalho.getMovimentacoes());

//        for (DadosMes dm : dadosMes){
//            if (comparaMeses(dm.getMes(),mes)) {
//                dm.getMovimentacoes().remove(mov);
//                this.setChanged();
//                this.notifyObservers(dm.getMovimentacoes());
//                break;
//            }
//        }
    }

    /* 
        visualizaMes encontra os dados de um determinado mes no sistema e envia
        para a interface para ser mostrado ao usuario
        retorna null caso o mes nao exista 
    */
    public boolean visualizaMes(GregorianCalendar mes){
        for (DadosMes dm : dadosMes){
            if (comparaMeses(dm.getMes(),mes)) {
                
                dadosDeTrabalho = new DadosMes(dm);

                this.setChanged();
               
                notifyObservers(dadosDeTrabalho.getMovimentacoes());
               
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

        conversor.converteParaXML(dadosDeTrabalho, dir);
        
//        for (DadosMes dm : dadosMes){
//            if (comparaMeses(dm.getMes(),mes)) {
//                conversor.converteParaXML(dm, dir);
//                break;
//            }
//        }
    }

    /*
        salvaMes busca os dados do mes a ser exportado e chama o conversor
        para converter os dados para XML e o arquivo é salvo no dir padrao de
        backup dos dados.
    */
    public void salvaMes(GregorianCalendar mes){
        
        conversor.converteParaXML(dadosDeTrabalho);
        
        DadosMes dados = new DadosMes(dadosDeTrabalho);
        
        for (DadosMes dm : dadosMes){
            if (comparaMeses(dm.getMes(),mes)) {
                dadosMes.remove(dm);
                break;
            }
        }
        
        dadosMes.add(dados);
        
        // TODO: o local mais apropriado para atualizar a lista de arquivos
        // talvez não seja aqui e sim em um destrutor da classe,
        // chamado no momento de saída do programa.
        salvaArquivos();
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
    
    
    /* Tentativa de criacao de um metodo que gere os relatorios de barra gerais.
       Nao ta pronto ainda. Termina ele ou apaga e faz outro.
    */
//    public void geraRelatorioGeral(GregorianCalendar inicio, GregorianCalendar fim, String cat) {
//        int i, j;
//        int anoInicial, anoFinal, mesInicial, mesFinal;
//        anoInicial = inicio.get(GregorianCalendar.YEAR);
//        anoFinal = fim.get(GregorianCalendar.YEAR);
//        mesInicial = inicio.get(GregorianCalendar.MONTH);
//        mesFinal = 11;
//        
//        if(anoInicial > anoFinal) {
//            return;
//        }
//        for(i = anoInicial; i <= anoFinal; i++) {
//            if(i != anoInicial) {
//                mesInicial = 0;
//            }
//            if(i == anoFinal) {
//                mesFinal = fim.get(GregorianCalendar.MONTH);
//            }
//            for(j = mesInicial; j <= mesFinal; j++) {
//                for (DadosMes dm : dadosMes){
//                    if (comparaMeses(dm.getMes(), new GregorianCalendar(i, j, 1))) {
//                        
//                        break;
//                    }
//                }
//            }
//        }
//    }
}