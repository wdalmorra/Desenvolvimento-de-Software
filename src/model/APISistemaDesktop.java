/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Observable;


/**
 * Classe de fachada que centraliza todas as operacoes de manipulacao das
 * estruturas de dados basicas. Abrange:
 * <ul>
 *  <li> Manutencao das estruturas de dados que representam despesas e receitas
 *  <li> Provimento da API para acesso a camada de modelo da aplicacao
 *  <li> Direcionamento das requisicoes recebidas pela API para as classes
 *       apropriadas.
 * </ul>
 * 
 * <p> Alem de manter estruturas de dados para informacoes historicas salvas pelo
 * usuario, mantem tambem uma estrutura temporaria para representar dados sendo
 * editados no momento.
 * 
 * <p> A classe implementa o padrao observable. Observers sao notificados de:
 * <ol>
 *  <li> Mudancas na estrutura de dados temporaria
 *  <li> Estruturas de dados condensando informacoes para geracao de relatorios,
 *       solicitados pelo metodo geraRelatorios.
 * </ol>
 * 
 * <p> Nota: mes corrente, ao longo da documentacao dos metodos desta classe, faz
 * referencia ao mes sendo editado pela interface da aplicacao (estrutura 
 * de dados temporaria), e nao necessariamente ao mes atual.
 * 
 * @author William
 */
public class APISistemaDesktop extends Observable{

    private ArrayList<DadosMes> dadosMes;
    private Conversor conversor;
    
    private DadosMes dadosDeTrabalho;

    
    /**
     * Localiza todos os arquivos existentes no diretorio padrao de backup e
     * importa as informacoes desses arquivos como estruturas de dados 
     * acessiveis a aplicacao.
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
    
    
    /**
     * Transforma em arquivos XML todos os dados disponiveis na estrutura
     * de dados que representa despesas e receitas. Efetivamente grava no
     * disco todos os dados trabalhados em memoria pela aplicacao.
     */
    private void salvaArquivos(){
        ArrayList<GregorianCalendar> listaDeArquivos = new ArrayList<>();
        
        for (DadosMes dm : dadosMes) {
            conversor.converteParaXML(dm);
            listaDeArquivos.add(dm.getMes());
        }
        
        conversor.salvaListaDeArquivos(listaDeArquivos);
    }
    

    /**
     * Construtor da classe.
     */
    public APISistemaDesktop(){
        dadosMes = new ArrayList<>();
        conversor = Conversor.getInstance();
        dadosDeTrabalho = new DadosMes();
        carregaArquivos();
    }

    
    /**
     * Prepara o sistema para receber dados de movimentacao para um novo mes.
     * Deve ser chamado antes da primeira chamada a addMovimentacao para
     * inicializar apropriadamente as estruturas de dados necessarias.
     * 
     * @param gc Informa o mes e o ano para os novos dados.
     */
    public void criaMes(GregorianCalendar gc){
        dadosDeTrabalho = new DadosMes();
        dadosDeTrabalho.setDate(gc);
    }

    
    /**
     * Adiciona uma nova entrada movimentacao no mes corrente, com informacoes
     * de categoria e valor. Para incorporacao correta dos dados, chamadas a
     * este metodo devem ser precedidas por pelo menos uma chamada a criaMes
     * ou visualizaMes. Notifica observers com uma versao atualizada da
     * estrutura de dados que representa as despesas e receitas para o mes
     * corrente.
     * 
     * @param valor Quantia gasta (ou recebida) referente a categoria em
     *              questao.
     * @param cat   Categoria de despesa ou receita a qual os dados sao 
     *              referentes.
     * @param tipo  "D" para despesa ou "R" para receita.
     * @param mes   Estrutura calendario indicando o mes e o ano sob os quais a
     *              despesa deve ser registrada.
     * @see criaMes
     * @see visualizaMes
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
    }
    
    
    /**
     * Altera dados de uma entrada movimentacao ja existente, alterando
     * informacoes de valor referentes a uma categoria para a qual ja existem
     * dados. Para incorporacao correta dos dados, chamadas a
     * este metodo devem ser precedidas por pelo menos uma chamada a criaMes
     * ou visualizaMes. Notifica observers com uma versao atualizada da
     * estrutura de dados que representa as despesas e receitas para o mes
     * corrente.
     * 
     * @param valor Quantia gasta (ou recebida) referente a categoria em
     *              questao.
     * @param cat   Categoria de despesa ou receita a qual os dados sao 
     *              referentes.
     * @param tipo  "D" para despesa ou "R" para receita.
     * @param mes   Estrutura calendario indicando o mes e o ano sob os quais a
     *              despesa deve ser registrada.
     * @see criaMes
     * @see visualizaMes
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
    }
    
    
    /**
     * Suprime uma entrada de movimentacao do mes corrente, eliminando
     * informacoes de valor e categoria. Notifica observers com uma versao
     * atualizada da estrutura de dados que representa as despesas e receitas
     * para o mes corrente.
     * 
     * @param mes Indica mes e ano do qual as informacoes devem ser suprimidas
     * @param mov Entrada que deve ser suprimida da estrutura de dados.
     */
    public void removeMovimentacao(GregorianCalendar mes, Movimentacao mov) {
        dadosDeTrabalho.getMovimentacoes().remove(mov);
        this.setChanged();
        this.notifyObservers(dadosDeTrabalho.getMovimentacoes());
    }

    
    /**
     * Busca dados de um determinado mes e torna o mes especificado o mes
     * corrente, se existirem dados para o mes especificado. Notifica
     * observers com os dados de movimentacao para o mes corrente.
     * 
     * @param mes Indica o mes e o ano que devem ser buscados na base de dados.
     * @return true, caso o mes especificado seja encontrado; false, caso
     *         contrario.
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

    
    /**
     * Produz um arquivo XML representando os dados de despesa e receita para
     * um mes especifico. A localizacao do arquivo e arbitraria.
     * 
     * @param mes Inidica o mes e o ano a partir do qual deve ser produzido
     *            o arquivo XML.
     * @param dir Inidica o caminho para gravacao do arquivo (diretorio, nome
     *            de arquivo e extensao).
     */
    public void exportaMes(GregorianCalendar mes, String dir){
        conversor.converteParaXML(dadosDeTrabalho, dir);
    }

    
    /**
     * Atualiza os arquivos com os dados de despesas e receitas do mes corrente,
     * tornando persistentes as alteracoes realizadas. Atualiza tambem as
     * estruturas de dados internas da camada de modelo. Os arquivos sao salvos
     * no diretorio padrao de backup.
     * 
     * @param mes Indica o mes e o ano a que se referem os dados de trabalho.
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

    
    /**
     * Exclui as informacoes de um mes especifico, removendo elas das estruturas
     * de dados internas e suprimindo o arquivo em disco da lista de arquivos
     * indexados.
     * 
     * @param mes Informa mes e ano a que se referem os dados que devem ser
     *            suprimidos.
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

    
    /**
     * Nao implementado nesse momento.
     * 
     * @param mes Informa mes e ano a que se referem os dados que devem ser
     *            suprimidos.
     */
    public void deletaArquivo(GregorianCalendar mes){
        
    }

    
    /**
     * Verifica se duas datas sao equivalentes levando em consideracao apenas
     * o mes e o ano.
     * 
     * @param a Primeiro operando.
     * @param b Segundo operando.
     * @return  true, se a e b tem o ano e mes identicos. false, caso contrario.
     */
    static public boolean comparaMeses(GregorianCalendar a, GregorianCalendar b){
        return (a.get(GregorianCalendar.YEAR) == b.get(GregorianCalendar.YEAR))
                && (a.get(GregorianCalendar.MONTH) == b.get(GregorianCalendar.MONTH));
    }
    
    
    /**
     * Gera um vetor com o historico de despesas/receitas para uma dada 
     * categoria ao longo de um periodo especifico. O resultado e notificado
     * aos observers, em vez de ser retornado.
     * 
     * @param inicio Mes e ano iniciais para o intervalo a ser consultado
     *               (inicio faz parte do intervalo).
     * @param fim    Mes e ano finais para o intervalo a ser consultado
     *               (fim faz parte do intervalo).
     * @param categoria Categoria para a despesa ou receita a ser consultada.
     */
    public void geraRelatorio(GregorianCalendar inicio, GregorianCalendar fim, String categoria) {
        
        // Verifico a entrada
        if (inicio.compareTo(fim) > 0) {
            throw new IllegalArgumentException();
        }
        
        CategoriaDespesa cd;
        CategoriaReceita cr;
        boolean receita = false;
        boolean despesa = false;
        
        cd = CategoriaDespesa.stringToCategoria(categoria);
        if (cd != null) {
            despesa = true;
        }
        
        cr = CategoriaReceita.stringToCategoria(categoria);
        if (cr != null) {
            receita = true;
        }
        
        if (cd == null && cr == null) {
            throw new IllegalArgumentException();
        }
        
        
        // Acho todos os meses dentro do intervalo pedido
        ArrayList<DadosMes> meses = new ArrayList<>();
        for (DadosMes dm : dadosMes) {
            if (dm.getMes().compareTo(inicio) >= 0 
                    && dm.getMes().compareTo(fim) <= 0) {
                meses.add(dm);
            }
        }
        
        // Ordeno os meses cronologicamente
        Comparator<DadosMes> comp = new Comparator<DadosMes> () {
            @Override
            public int compare(DadosMes dm1, DadosMes dm2) {
                return (dm1.getMes().compareTo(dm2.getMes()));
            }
        };
        Collections.sort(meses, comp);
        
        // Extraio apenas os valores correspondentes a categoria que foi pedida,
        // completo com zeros se nao der
        ArrayList<Integer> resultado = new ArrayList<>();
        int i = 0;
        GregorianCalendar ic = new GregorianCalendar(
                inicio.get(GregorianCalendar.YEAR), 
                inicio.get(GregorianCalendar.MONTH),
                1
        );
        GregorianCalendar lc = new GregorianCalendar(
                fim.get(GregorianCalendar.YEAR), 
                fim.get(GregorianCalendar.MONTH),
                1
        );
        lc.add(GregorianCalendar.MONTH, 1);
        
        do {
            int deleteme;
            if (i >= meses.size() ||
                    !comparaMeses(ic, meses.get(i).getMes())) {
                resultado.add(0);
            } else {
                if (despesa) {
                    resultado.add(meses.get(i).getMovimentacao(cd));
                } else {
                    resultado.add(meses.get(i).getMovimentacao(cr));
                }
                i++;
            }
            ic.add(GregorianCalendar.MONTH, 1);
        } while (!comparaMeses(ic, lc));
        
        // return resultado;
        this.setChanged();
        notifyObservers(resultado);
    }
}