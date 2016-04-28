/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.w3c.dom.*;
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
    static Conversor singleton;
    static String defaultSavePath = ".";
    
    public static Conversor getInstance() {
        if (singleton == null) {
            singleton = new Conversor(defaultSavePath);
        }
        return singleton;
    }
    
    // Atributos e métodos do objeto Conversor
    private String currentSavePath;
    
    private Conversor() {
        this.currentSavePath = ".";
    }
    
    private Conversor(String savePath) {
        this.currentSavePath = savePath;
    }
    
    public void converteParaXML(DadosMes dadosMes) {
        
        String mes = Integer.toString(
                dadosMes.getMes().get(GregorianCalendar.MONTH)
        );
        
        String ano = Integer.toString(
                dadosMes.getMes().get(GregorianCalendar.YEAR)
        );
        
        converteParaXML(dadosMes, currentSavePath + "/" + "dados_m" + mes + "_a" + ano + ".xml");
        
    }
    
    public void converteParaXML(DadosMes dadosMes, String path) {
        
        try {
            // Prepara o documento
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            
            // Elemento raiz
            Element financasElement = doc.createElement("financas");
            doc.appendChild(financasElement);
            
            // Elemento dadosMes
            Element dadosMesElement = doc.createElement("dadosmes");
            GregorianCalendar date = dadosMes.getMes();
            int mes = date.get(GregorianCalendar.MONTH);
            int ano = date.get(GregorianCalendar.YEAR);
            dadosMesElement.setAttribute("mes",Integer.toString(mes));
            dadosMesElement.setAttribute("ano",Integer.toString(ano));
            financasElement.appendChild(dadosMesElement);
            
            // Preenche o dadosMes com as movimentacoes
            ArrayList<Movimentacao> movimentacoes = dadosMes.getMovimentacoes();
            
            for (Movimentacao m : movimentacoes) {
                Element mElement = doc.createElement("movimentacao");
                mElement.setAttribute("valor", Integer.toString(m.getValor()));
                
                if (m instanceof Receita) {
                    mElement.setAttribute("tipo", "receita");
                    CategoriaReceita c = ((Receita) m).getCategoria();
                    mElement.setAttribute("categoria", CategoriaReceita.categoriaToString(c));
                } else if (m instanceof Despesa) {
                    mElement.setAttribute("tipo", "despesa");
                    CategoriaDespesa d = ((Despesa) m).getCategoria();
                    mElement.setAttribute("categoria", CategoriaDespesa.categoriaToString(d));
                } else {
                    mElement.setAttribute("categoria", "DEFAULT");
                }
                dadosMesElement.appendChild(mElement);
            }
            
            // Cria o arquivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult fileResult = new StreamResult(new File(path));
            transformer.transform(source, fileResult);
            
        } catch (Exception e) {
                System.out.println("Erro na escrita do arquivo " + path);
        }
        
    }
    
    public DadosMes converteParaDadosMes(String path) {
        
        DadosMes retval = new DadosMes();
        
        try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new File(path));
                
                Element financas = doc.getDocumentElement();
                NodeList dadosMensais = doc.getElementsByTagName("dadosmes");
                
                if (dadosMensais.getLength() > 1) {
                   System.out.println("Warning: arquivo com multiplos meses, lendo somente o primeiro...");
                }

                Element dadosMes = (Element) dadosMensais.item(0);
                int mes = Integer.parseInt(dadosMes.getAttribute("mes"));
                int ano = Integer.parseInt(dadosMes.getAttribute("ano"));
                NodeList movimentacoes = dadosMes.getElementsByTagName("movimentacao");
                
                GregorianCalendar date = new GregorianCalendar(ano, mes, 1);
                // Dia primeiro porque soh interessa o mes e o ano, os dados sao
                // mensais
                retval.setDate(date);
                
                // Não, não aceita foreach.
                for (int i = 0; i < movimentacoes.getLength(); i++) {
                    Element movimentacao = (Element) movimentacoes.item(i);
                    int valor = Integer.parseInt(movimentacao.getAttribute("valor"));
                    String categoria = movimentacao.getAttribute("categoria");
                    String tipo = movimentacao.getAttribute("tipo");
                    
                    if (tipo.equals("receita")) {
                        CategoriaReceita c = CategoriaReceita.stringToCategoria(categoria);
                        retval.addMovimentacao(new Receita(c, valor));
                    } else if (tipo.equals("despesa")) {
                        CategoriaDespesa c = CategoriaDespesa.stringToCategoria(categoria);
                        retval.addMovimentacao(new Despesa(c, valor));
                    } else {
                        retval = null;
                        throw new Exception();
                    }
                }
                
        } catch (Exception e) {
                System.out.println("Erro na leitura do arquivo " + path);
        }
        
        return retval;
    }
}
