/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Observer;
import java.util.Observable;


/**
 *
 * @author lxavier
 */
public class APISistemaDesktopTest implements Observer {
    
    private ArrayList<Integer> relatorio;
    
    public APISistemaDesktopTest() {
        relatorio = null;
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
     * Test of geraRelatorio method, of class APISistemaDesktop.
     */
    @Test
    public void testGeraRelatorio_3args() {
        System.out.println("geraRelatorio");
        
        APISistemaDesktop instance = new APISistemaDesktop();
        
        // Gera os dados para o teste
        instance.criaMes(new GregorianCalendar(2010,5,1));
        instance.addMovimentacao(100, "DESPESA1", "D", null);
        instance.addMovimentacao(200, "DESPESA2", "D", null);
        instance.salvaMes(new GregorianCalendar(2010,5,1));
        
        instance.criaMes(new GregorianCalendar(2010,6,1));
        instance.addMovimentacao(300, "DESPESA1", "D", null);
        instance.addMovimentacao(400, "DESPESA2", "D", null);
        instance.addMovimentacao(500, "RECEITA1", "R", null);
        instance.salvaMes(new GregorianCalendar(2010,6,1));
        
        instance.criaMes(new GregorianCalendar(2010,7,1));
        instance.addMovimentacao(600, "DESPESA1", "D", null);
        instance.salvaMes(new GregorianCalendar(2010,7,1));
        
        instance.addObserver(this);
        
        GregorianCalendar inicio;
        GregorianCalendar fim;
        CategoriaDespesa c;
        ArrayList<Integer> referencia;
        
        inicio = new GregorianCalendar(2010, 3, 1);
        fim = new GregorianCalendar(2010, 8, 1);
        c = CategoriaDespesa.Combustível;
        
        // Gera os dados de referencia para a resposta
        referencia = new ArrayList<>();
        referencia.add(0);
        referencia.add(0);
        referencia.add(100);
        referencia.add(300);
        referencia.add(600);
        referencia.add(0);
        
        // testa e compara
        instance.geraRelatorio(inicio, fim, "DESPESA1");
        assert(referencia.equals(relatorio));
    }
    
    @Override
    public void update(Observable obs, Object obj) {
        relatorio = (ArrayList<Integer>) obj;
    }
    
}
