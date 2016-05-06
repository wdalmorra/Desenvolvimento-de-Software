/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;
import java.util.*;


/**
 * Classe do modelo que realiza as operacoes envolvendo arquivos XML. Abrange:
 * <ul>
 *  <li> Conversao entre a estrutura de dados basica que representa os dados
 *       de despesas e receitas e sua representacao em XML
 *  <li> Gravacao e leitura de arquivos XML
 *  <li> Manutencao de um arquivo-indice indexando todos os arquivos de dados
 *       disponiveis em um diretorio padrao
 * </ul>
 * A classe implementa um padrao singleton. Existe uma unica instancia, para a
 * qual devem ser direcionadas todas as requisicoes de operacoes envolvendo
 * arquivos.
 * 
 * @author lxavier
 */
public class Conversor {
    
    // Métodos e atributos da classe, para implementar o padrão singleton
    private static Conversor singleton;
    private static String caminhoPadrao = "./test/caminhoPadrao";
    private static String listaDeArquivosPadrao = "dados_lista.xml";
    
    
    /**
     * Retorna uma instancia (singleton) de um objeto Conversor
     * (xml <-> DadosMes). O metodo eh thread safe.
     * 
     * @return referencia para um Conversor
     */
    public static synchronized Conversor getInstance() {
        if (singleton == null) {
            singleton = new Conversor(caminhoPadrao);
        }
        return singleton;
    }
    
    
    /**
     * Caminho padrao assumido para leitura/escrita dos arquivos XML
     * caso nenhum caminho seja fornecido
     */
    private String caminhoBase;
    private String caminhoListaDeArquivos;
    
    private DocumentBuilder domBuilder;
    
    
    /**
     * Construtor privado. Assume um caminho base padrao,
     * definido quando da implementacao da classe.
     */
    private Conversor() {
        this.caminhoBase = ".";
        this.caminhoListaDeArquivos = listaDeArquivosPadrao;

        try {
            domBuilder = initDocumentBuilder();
        } catch (ParserConfigurationException e) {
            // Talvez devesse ser erro fatal
            domBuilder = null;
        }
    }
    
    
    /**
     * Construtor privado.
     * 
     * @param caminhoBase Pasta em que os arquivos .xml serao lidos ou salvos
     * caso nenhum outro caminho seja especificado
     */
    private Conversor(String caminhoBase) {
        this.caminhoBase = caminhoBase;
        this.caminhoListaDeArquivos = listaDeArquivosPadrao;
        
        try {
            domBuilder = initDocumentBuilder();
        } catch (ParserConfigurationException e) {
            // Talvez devesse ser erro fatal
            domBuilder = null;
        }
    }
    
    
    /**
     * Inicializa o gerador de documentos XML
     * 
     * @return um gerador de documentos XML
     * @throws ParserConfigurationException 
     */
    private DocumentBuilder initDocumentBuilder()
            throws ParserConfigurationException
    {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        return domFactory.newDocumentBuilder();
    }
    
    
    /**
     * Monta uma representacao em arvore para um documento XML a partir da
     * estrutura de dados mensais de receita e despesa fornecida.
     * 
     * @param dadosMes Estrutura modelando um vetor de despesas e receitas
     * que deve ser convertida em XML
     * @return representacao em arvore, de acordo com o Java DOM, para o
     * documento XML correspondente ao parametro <code>dadosMes</code>
     * @throws ParserConfigurationException 
     */
    private Document montaArvoreXML(DadosMes dadosMes) 
            throws ParserConfigurationException 
    {
        // Prepara o documento
        Document documentoXML = domBuilder.newDocument();

        // Elemento raiz: financas
        Element elemRaiz = documentoXML.createElement("financas");
        documentoXML.appendChild(elemRaiz);

        // Elemento dadosMes: acerta a data
        GregorianCalendar mesSubmetido = dadosMes.getMes();
        Element elemDadosMes = documentoXML.createElement("dadosmes");
        elemDadosMes.setAttribute(
                "mes",
                Integer.toString(mesSubmetido.get(GregorianCalendar.MONTH))
        );
        elemDadosMes.setAttribute(
                "ano",
                Integer.toString(mesSubmetido.get(GregorianCalendar.YEAR))
        );
        elemRaiz.appendChild(elemDadosMes);

        // Preenche o dadosMes com as movimentacoes
        ArrayList<Movimentacao> movimentacoes = dadosMes.getMovimentacoes();

        for (Movimentacao m : movimentacoes) {
            Element elemMovimentacao = documentoXML.createElement("movimentacao");
            elemMovimentacao.setAttribute("valor", Integer.toString(m.getValor()));

            if (m instanceof Receita) {
                elemMovimentacao.setAttribute("tipo", "receita");
                CategoriaReceita c = ((Receita) m).getCategoria();
                elemMovimentacao.setAttribute("categoria", CategoriaReceita.categoriaToString(c));
            } else if (m instanceof Despesa) {
                elemMovimentacao.setAttribute("tipo", "despesa");
                CategoriaDespesa d = ((Despesa) m).getCategoria();
                elemMovimentacao.setAttribute("categoria", CategoriaDespesa.categoriaToString(d));
            } else {
                elemMovimentacao.setAttribute("categoria", "DEFAULT");
            }
            elemDadosMes.appendChild(elemMovimentacao);
        }

        return documentoXML;
    }
    
    
    /**
     * Monta uma estrutura DadosMes a partir de uma representacao em arvore de
     * documento XML com despesas e receitas.
     * 
     * @param arvoreXML Representacao em arvore, de acordo com o Java DOM,
     * para um arquivo XML de despesas e receitas
     * @return Estrutura de dados <code>DadosMes</code> correspondente ao
     * XML recebido como parametro
     * @throws Exception 
     */
    private DadosMes parserArvoreXML(Document arvoreXML) throws Exception {
        
        DadosMes dadosMes = new DadosMes();
        Element financas = arvoreXML.getDocumentElement();
        NodeList elDadosMensais = arvoreXML.getElementsByTagName("dadosmes");

        if (elDadosMensais.getLength() > 1) {
           System.out.println("Warning: arquivo com multiplos meses, lendo somente o primeiro...");
        }

        Element elDadosMes = (Element) elDadosMensais.item(0);
        int mes = Integer.parseInt(elDadosMes.getAttribute("mes"));
        int ano = Integer.parseInt(elDadosMes.getAttribute("ano"));
        NodeList movimentacoes = elDadosMes.getElementsByTagName("movimentacao");

        GregorianCalendar date = new GregorianCalendar(ano, mes, 1);
        // Dia primeiro porque soh interessa o mes e o ano, os dados sao
        // mensais
        dadosMes.setDate(date);

        // Não, não aceita foreach.
        for (int i = 0; i < movimentacoes.getLength(); i++) {
            Element movimentacao = (Element) movimentacoes.item(i);
            int valor = Integer.parseInt(movimentacao.getAttribute("valor"));
            String categoria = movimentacao.getAttribute("categoria");
            String tipo = movimentacao.getAttribute("tipo");

            if (tipo.equals("receita")) {
                CategoriaReceita c = CategoriaReceita.stringToCategoria(categoria);
                dadosMes.addMovimentacao(new Receita(c, valor));
            } else if (tipo.equals("despesa")) {
                CategoriaDespesa c = CategoriaDespesa.stringToCategoria(categoria);
                dadosMes.addMovimentacao(new Despesa(c, valor));
            } else {
//                dadosMes = null;
              throw new Exception();
            }
        }
        
        return dadosMes;
    }
    
    
    /**
     * Cria e preenche um arquivo .xml no sistema de arquvios local a partir
     * de um DOM representando um arquivo XML.
     * 
     * @param arvoreXML Arvore DOM representando um arquivo XML com dados
     * de despesas e receitas
     * @param caminho Nome do arquivo a ser gravado, apropriadamente qualificado
     * com o diretorio onde o arquivo devera ser salvo.
     * @throws TransformerException 
     */
    private void gravaArquivoXML(Document arvoreXML, String caminho)
            throws TransformerException 
    {
        // Cria o arquivo
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource fonte = new DOMSource(arvoreXML);
        StreamResult fileResult = new StreamResult(new File(caminho));
        transformer.transform(fonte, fileResult);
    }
    
    
    /**
     * Le um arquivo .xml do sistema de arquivos local,
     * cria e preenche uma arvore DOM representando esse arquivo.
     * 
     * @param caminho Nome do arquivo a ser lido, apropriadamente qualificado
     * com sua localizacao
     * @return Arvore DOM representando o arquivo XML lido
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    private Document leArquivoXML(String caminho)
            throws ParserConfigurationException, SAXException, IOException
    {
        return domBuilder.parse(new File(caminho));
    }
    
    
    /**
     * Cria e preenche um arquivo .xml a partir de uma estrutura de dados
     * com despesas e receitas.
     * 
     * @param dadosMes Estrutura modelando um vetor de despesas e receitas
     * que deve ser convertida em XML
     */
    public void converteParaXML(DadosMes dadosMes) {
        // Monta o caminho do arquivo a partir dos padroes e do mes submetido
        GregorianCalendar infoData = dadosMes.getMes();
        String caminho = 
                caminhoBase +
                "/" + 
                "dados_m" + infoData.get(GregorianCalendar.MONTH) +
                "_a" + infoData.get(GregorianCalendar.YEAR) +
                ".xml";
        
        // Chama a funcao converteParaXML(DadosMes, String) com o caminho
        // montado como parametro
        converteParaXML(dadosMes, caminho);
    }
    
    
    /**
     * Cria e preenche um arquivo .xml a partir de uma estrutura de dados
     * com despesas e receitas.
     * 
     * @param dadosMes Estrutura modelando um vetor de despesas e receitas
     * que deve ser convertida em XML
     * @param caminho Localizacao para o arquivo .xml criado
     */
    public void converteParaXML(DadosMes dadosMes, String caminho) {
        try {
            Document doc = montaArvoreXML(dadosMes);
            gravaArquivoXML(doc, caminho);
        } catch (ParserConfigurationException | TransformerException e) {
            System.out.println("Erro na escrita do arquivo " + caminho);
        }
    }
    
    
    /**
     * Cria e preenche uma estrutura de dados DadosMes a partir de informacoes
     * extraidas de um .xml lido do sistema de arquivos local. Le o xml
     * do caminho padrao e determina o nome do arquivo a partir da data
     * fornecida como parametro.
     * 
     * @param infoData Mes e ano para recuperacao das informacoes de despesas
     * e receitas, fornecidos por meio de um <code>GregorianCalendar</code>.
     * Quaisquer informacoes alem de ano e mes sao ignoradas.
     * O arquivo .xml eh buscado no caminho padrao.
     * @return Estrutura de dados <code>DadosMes</code> representando o
     * arquivo lido, em caso de sucesso na leitura. <code>null</code> em
     * caso de falha na leitura.
     */
    public DadosMes converteParaDadosMes(GregorianCalendar infoData) {
        String caminho = 
                caminhoBase +
                "/" + 
                "dados_m" + infoData.get(GregorianCalendar.MONTH) +
                "_a" + infoData.get(GregorianCalendar.YEAR) +
                ".xml";
        
        return converteParaDadosMes(caminho);
    }
    
    
    /**
     * Cria e preenche uma estrutura de dados DadosMes a partir de informacoes
     * extraidas de um .xml lido do sistema de arquivos local. Le o xml
     * a partir de um caminho fornecido na chamada da funcao.
     * 
     * @param caminho Nome e caminho relativo ou absoluto do arquivo a partir 
     * do qual as informacoes devem ser extraidas.
     * @return Estrutura de dados <code>DadosMes</code> representando o
     * arquivo lido, em caso de sucesso na leitura. <code>null</code> em
     * caso de falha na leitura.
     */
    public DadosMes converteParaDadosMes(String caminho) {
        try {
            Document doc = leArquivoXML(caminho);
            return parserArvoreXML(doc);
        } catch (Exception e) {
            System.out.println("Erro na leitura do arquivo " + caminho);
            return null;
        }
    }
    
    
    /**
     * Grava uma lista em formato XML com a relação de todos os arquivos de
     * dados disponíveis. A lista é gravada em uma localização padrão.
     * 
     * @param listaDeArquivos Array com todos os meses para os quais existe uma
     * entrada com movimentações e um arquivo de dados correspondente
     */
    public void salvaListaDeArquivos(ArrayList<GregorianCalendar> listaDeArquivos) {
        Document listaArquivosXML = domBuilder.newDocument();
        Element elemRaiz = listaArquivosXML.createElement("financasLista");
        listaArquivosXML.appendChild(elemRaiz);
        
        for (GregorianCalendar gc : listaDeArquivos) {
            Element elemArquivo = listaArquivosXML.createElement("arquivo");
            
            elemArquivo.setAttribute(
                    "mes", 
                    Integer.toString(gc.get(GregorianCalendar.MONTH))
            );
            
            elemArquivo.setAttribute(
                    "ano", 
                    Integer.toString(gc.get(GregorianCalendar.YEAR))
            );
            
            elemRaiz.appendChild(elemArquivo);
        }
        
        String caminho = caminhoPadrao + '/' + caminhoListaDeArquivos;
        try {
            gravaArquivoXML(listaArquivosXML, caminho);
        } catch (TransformerException e) {
            // TODO: logar o erro apropriadamente
        }
    }
    
    
    /**
     * Lê a lista com a relação de todos os arquivos de dados disponíveis,
     * que deve estar em formato XML e em uma localização padrão.
     * 
     * @return Se existir, uma lista de arquivos de data contendo o mês e o ano
     * correspondentes aos arquivos existentes. Caso contrário,
     * <code>null</code>.
     */
    public ArrayList<GregorianCalendar> carregaListaDeArquivos () {
        String caminho = caminhoPadrao + '/' + caminhoListaDeArquivos;
        
        try {
            ArrayList<GregorianCalendar> listaArquivos = new ArrayList<>();
            
            Document listaArquivosXML = domBuilder.parse(caminho);
            Element financasLista = listaArquivosXML.getDocumentElement();
            NodeList arquivos = financasLista.getElementsByTagName("arquivo");
            
            // Não, não aceita foreach.
            for (int i = 0; i < arquivos.getLength(); i++) {
                Element arquivo = (Element) arquivos.item(i);
                int mes = Integer.parseInt(arquivo.getAttribute("mes"));
                int ano = Integer.parseInt(arquivo.getAttribute("ano"));
                GregorianCalendar gc = new GregorianCalendar(ano, mes, 1);
                listaArquivos.add(gc);
            }
            
            return listaArquivos;
        } catch (SAXException | IOException e) {
            return null;
        }        
    }
}
