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
 *
 * @author Thainan
 */
public class Conversor {
    
    // Métodos e atributos da classe, para implementar o padrão singleton
    private static Conversor singleton;
    private static String caminhoPadrao = ".";
    
    public static synchronized Conversor getInstance() {
        if (singleton == null) {
            singleton = new Conversor(caminhoPadrao);
        }
        return singleton;
    }
    
    // Atributos e métodos do objeto Conversor
    private String caminhoBase;
    
    private Conversor() {
        this.caminhoBase = ".";
    }
    
    private Conversor(String caminhoBase) {
        this.caminhoBase = caminhoBase;
    }
    
    private Document montaArvoreXML(DadosMes dadosMes) 
            throws ParserConfigurationException 
    {
        // Prepara o documento
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documentoXML = builder.newDocument();

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
    
    private Document leArquivoXML(String caminho)
            throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new File(caminho));
    }
    
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
    
    public void converteParaXML(DadosMes dadosMes, String caminho) {
        try {
            Document doc = montaArvoreXML(dadosMes);
            gravaArquivoXML(doc, caminho);
        } catch (ParserConfigurationException | TransformerException e) {
            System.out.println("Erro na escrita do arquivo " + caminho);
        }
    }
    
    public DadosMes converteParaDadosMes(GregorianCalendar infoData) {
        String caminho = 
                caminhoBase +
                "/" + 
                "dados_m" + infoData.get(GregorianCalendar.MONTH) +
                "_a" + infoData.get(GregorianCalendar.YEAR) +
                ".xml";
        
        return converteParaDadosMes(caminho);
    }
    
    public DadosMes converteParaDadosMes(String caminho) {
        
        DadosMes retval = null;
        
        try {
            Document doc = leArquivoXML(caminho);
            retval = parserArvoreXML(doc);
        } catch (Exception e) {
            System.out.println("Erro na leitura do arquivo " + caminho);
            retval = null;
        }
        
        return retval;
    }
}
