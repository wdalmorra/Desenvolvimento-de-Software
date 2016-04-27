/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;


/**
 *
 * @author Thainan
 */
public class Conversor {
    
    static Conversor singleton;
    static String defaultSavePath = ".";
    
    public static Conversor getInstance() {
        if (singleton == null) {
            singleton = new Conversor(defaultSavePath);
        }
        return singleton;
    }
    
    private String currentSavePath;
    
    public String toXML(DadosMes dadosMes) {
        return "";
    }
    
    public DadosMes fromXML(String xml) {
        return null;
    }
    
    private Conversor() {
        this.currentSavePath = ".";
    }
    
    private Conversor(String savePath) {
        this.currentSavePath = savePath;
    }
    
    public void converteParaXML(DadosMes dadosMes) {
    }
    
    public void converteParaXML(DadosMes dadosMes, String path) {
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
                NodeList movimentacoes = dadosMes.getElementsByTagName("movimentacao");
                
                // Não, não aceita foreach.
                for (int i = 0; i < movimentacoes.getLength(); i++) {
                    Element movimentacao = (Element) movimentacoes.item(i);
                    int valor = Integer.parseInt(movimentacao.getAttribute("valor"));
                    String categoria = movimentacao.getAttribute("categoria");
                    String tipo = movimentacao.getAttribute("tipo");
                    
                    if (tipo.equals("receita")) {
                        retval.addMovimentacao(new Receita(CategoriaReceita.DEFAULT, valor));
                    } else if (tipo.equals("despesa")) {
                        retval.addMovimentacao(new Despesa(CategoriaDespesa.DEFAULT, valor));
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
