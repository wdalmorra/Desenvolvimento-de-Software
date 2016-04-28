/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lxavier
 */
public class ConversorTest {
    
    
    public ConversorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of toXML method, of class Conversor.
     */
    @Test
    public void testBothSpecificPath() {
        System.out.println("exporta-importa, path informado");
        
        // Prepara o caso de teste
        DadosMes testCase = new DadosMes();
        Movimentacao m1 = new Receita(CategoriaReceita.RECEITA1, 500);
        Movimentacao m2 = new Despesa(CategoriaDespesa.DESPESA2, 313);
        testCase.addMovimentacao(m1);
        testCase.addMovimentacao(m2);
        
        // Exporta o caso de teste e importa de volta
        Conversor instance = Conversor.getInstance();
        instance.converteParaXML(testCase, "test/resources/teste-01.out.xml");
        DadosMes dmResult = instance.converteParaDadosMes("test/resources/teste-01.out.xml");
        
        // Pega os conteudos do caso de teste e do resultado
        ArrayList<Movimentacao> result = dmResult.getMovimentacoes();
        ArrayList<Movimentacao> expectedResult = testCase.getMovimentacoes();
        
        // Confiro contra o que eu espero no XML
        if (result.size() != expectedResult.size()) {
            fail("Numero de movimentacoes incorreto");
        }
        
        // Preciso que o resultado das movimentações esteja em uma ordem
        // determinística. Ordeno as movimentações por valor.
        Comparator<Movimentacao> c = new Comparator<Movimentacao> () {
            @Override
            public int compare(Movimentacao m1, Movimentacao m2) {
                return (m1.getValor() - m2.getValor());
            }
        };
        Collections.sort(result, c);
        Collections.sort(expectedResult, c);
        
        for (int i = 0; i < 2; i++) {
            // Testa o valor
            Movimentacao er = expectedResult.get(i);
            Movimentacao r = result.get(i);
            assertEquals(er.getValor(), r.getValor());
            
            // Testa categorias
            if (er instanceof Receita) {
                if (r instanceof Receita)
                    assertEquals(((Receita) r).getCategoria(), ((Receita) er).getCategoria());
                else
                    fail("Expected: Receita");
            } else {
                if (r instanceof Despesa)
                    assertEquals(((Despesa) r).getCategoria(), ((Despesa) er).getCategoria());
                else
                    fail("Expected: Despesa");
            }
        }
    }
    
    
    @Test
    public void testBothDefaultPath() {
        System.out.println("exporta-importa, path padrao");
        
        // Prepara o caso de teste
        DadosMes testCase = new DadosMes(new GregorianCalendar(1993, 8, 1));
        Movimentacao m1 = new Receita(CategoriaReceita.RECEITA1, 500);
        Movimentacao m2 = new Despesa(CategoriaDespesa.DESPESA2, 313);
        testCase.addMovimentacao(m1);
        testCase.addMovimentacao(m2);
        
        // Exporta o caso de teste e importa de volta
        Conversor instance = Conversor.getInstance();
        instance.converteParaXML(testCase);
        DadosMes dmResult = instance.converteParaDadosMes(new GregorianCalendar(1993, 8, 1));
        
        // Pega os conteudos do caso de teste e do resultado
        ArrayList<Movimentacao> result = dmResult.getMovimentacoes();
        ArrayList<Movimentacao> expectedResult = testCase.getMovimentacoes();
        
        // Confiro contra o que eu espero no XML
        if (result.size() != expectedResult.size()) {
            fail("Numero de movimentacoes incorreto");
        }
        
        // Preciso que o resultado das movimentações esteja em uma ordem
        // determinística. Ordeno as movimentações por valor.
        Comparator<Movimentacao> c = new Comparator<Movimentacao> () {
            @Override
            public int compare(Movimentacao m1, Movimentacao m2) {
                return (m1.getValor() - m2.getValor());
            }
        };
        Collections.sort(result, c);
        Collections.sort(expectedResult, c);
        
        for (int i = 0; i < 2; i++) {
            // Testa o valor
            Movimentacao er = expectedResult.get(i);
            Movimentacao r = result.get(i);
            assertEquals(er.getValor(), r.getValor());
            
            // Testa categorias
            if (er instanceof Receita) {
                if (r instanceof Receita)
                    assertEquals(((Receita) r).getCategoria(), ((Receita) er).getCategoria());
                else
                    fail("Expected: Receita");
            } else {
                if (r instanceof Despesa)
                    assertEquals(((Despesa) r).getCategoria(), ((Despesa) er).getCategoria());
                else
                    fail("Expected: Despesa");
            }
        }
    }

    /**
     * Test of fromXML method, of class Conversor.
     */
    @Test
    public void testImportSpecificPath() {
        System.out.println("importa, path informado");
        String path = "test/resources/teste-01.in.xml";
        Conversor instance = Conversor.getInstance();
        
        // Importa o xml, pega o DadosMes para inspeção
        DadosMes result = instance.converteParaDadosMes(path);
        ArrayList<Movimentacao> movimentacoes = result.getMovimentacoes();
        
        // Preciso que o resultado das movimentações esteja em uma ordem
        // determinística. Ordeno as movimentações por valor.
        Collections.sort(movimentacoes, new Comparator<Movimentacao> () {
            @Override
            public int compare(Movimentacao m1, Movimentacao m2) {
                return (m1.getValor() - m2.getValor());
            }
        });
        
        // Confiro contra o que eu espero no XML
        if (movimentacoes.size() != 2) {
            fail("Numero de movimentacoes incorreto");
        }
        
        // Gabarito para os resultados
        int[] expectedValues = {313, 500};
        boolean[] expectedReceita = {false, true};
        CategoriaReceita[] expectedCategoriaReceita = {null, CategoriaReceita.RECEITA1};
        CategoriaDespesa[] expectedCategoriaDespesa = {CategoriaDespesa.DESPESA2, null};
        
        for (int i = 0; i < 2; i++) {
            // Testa o valor
            Movimentacao m = movimentacoes.get(i);
            assertEquals(m.getValor(), expectedValues[i]);
            
            // Testa categorias
            if (expectedReceita[i]) {
                Receita r = (Receita) m;
                assertEquals(r.getCategoria(), expectedCategoriaReceita[i]);
            } else {
                Despesa d = (Despesa) m;
                assertEquals(d.getCategoria(), expectedCategoriaDespesa[i]);
            }
        }
       
    }
}
