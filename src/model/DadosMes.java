/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 *
 * @author William
 */
public class DadosMes {
    private GregorianCalendar date;
    private ArrayList<Movimentacao> movimentacoes;
    
    DadosMes(){
        date = new GregorianCalendar();
        movimentacoes = new ArrayList<>();
    }
    
    DadosMes(GregorianCalendar c){
        date = c;
        movimentacoes = new ArrayList<>();
    }
    
    
    public GregorianCalendar getMes(){
        return this.date;
    }
    public void setDate(GregorianCalendar c){
        this.date = c;
    }
    public ArrayList<Movimentacao> getMovimentacoes(){
        return this.movimentacoes;
    }
    public void addMovimentacao(Movimentacao m){
        movimentacoes.add(m);
    }
    
    public void removeMovimentacao(CategoriaReceita cat){
        for (int i = 0; i < movimentacoes.size(); i++) {
            if (movimentacoes.get(i) instanceof Receita ) {
                if( ((Receita) movimentacoes.get(i)).getCategoria() == cat ) {
                    movimentacoes.remove(i);
                    break;
                }
            }
        }
    }
    
    public void removeMovimentacao(CategoriaDespesa cat){
        for (int i = 0; i < movimentacoes.size(); i++) {
            if (movimentacoes.get(i) instanceof Despesa ) {
                if( ((Despesa) movimentacoes.get(i)).getCategoria() == cat ) {
                    movimentacoes.remove(i);
                    break;
                }
            }
        }
    }
}
