/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

//import java.util.Collections;
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
    public void testToXML() {
        System.out.println("toXML");
        
        DadosMes testCase = new DadosMes();
        Movimentacao m1 = new Receita(CategoriaReceita.RECEITA1, 500);
        Movimentacao m2 = new Despesa(CategoriaDespesa.DESPESA2, 313);
        testCase.addMovimentacao(m1);
        testCase.addMovimentacao(m2);
        Conversor instance = Conversor.getInstance();
        instance.converteParaXML(testCase, "test/resources/teste-01.out.xml");
        DadosMes result = instance.converteParaDadosMes("test/resources/teste-01.out.xml");
//        assertEquals(expResult, result);
            //fail("The test case is a prototype.");
    }

    /**
     * Test of fromXML method, of class Conversor.
     */
    @Test
    public void testFromXML() {
        System.out.println("fromXML");
        String path = "test/resources/teste-01.in.xml";
        Conversor instance = Conversor.getInstance();
        
        Comparator<Movimentacao> c = new Comparator<Movimentacao> () {
            @Override
            public int compare(Movimentacao o1, Movimentacao o2) {
                return (o1.getValor() - o2.getValor());
            }
        };
        
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
        int[] expectedValues = {313, 500};
        boolean[] expectedReceita = {false, true};
        CategoriaReceita[] expectedCategoriaReceita = {CategoriaReceita.RECEITA1, null};
        CategoriaDespesa[] expectedCategoriaDespesa = {null, CategoriaDespesa.DESPESA2};
        for (int i = 0; i < 2; i++) {
            Movimentacao m = movimentacoes.get(i);
            assertEquals(m.getValor(), expectedValues[i]);
            // DESCOMENTAR QUANDO a lógica de categorias estiver implementada
//            if (expectedReceita[i]) {
//                Receita r = (Receita) m;
//                assertEquals(r.getCategoria(), expectedCategoriaReceita[i]);
//            } else {
//                Despesa d = (Despesa) m;
//                assertEquals(d.getCategoria(), expectedCategoriaDespesa[i]);
//            }
        }
       
    }
}
