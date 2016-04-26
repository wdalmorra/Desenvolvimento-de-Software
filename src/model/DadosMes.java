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
    
    public DadosMes() {
        this.date = new Date();
        this.movimentacoes = new ArrayList<>();
    }
    
    public DadosMes(Date date) {
        this.date = date;
        this.movimentacoes = new ArrayList<>();
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
}
