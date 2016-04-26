/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author William
 */
public class DadosMes {
    private Date date;
    private ArrayList<Movimentacao> movimentacoes;
    
    DadosMes(){
        date = new Date();
        movimentacoes = new ArrayList<>();
    }
    
    DadosMes(Date m){
        date = m;
        movimentacoes = new ArrayList<>();
    }
    
    
    public Date getMes(){
        return this.date;
    }
    public void setDate(Date d){
        this.date = d;
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
