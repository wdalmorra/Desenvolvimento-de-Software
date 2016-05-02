/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author William
 */
public abstract class Movimentacao {
    
    private int valor;
    
    public Movimentacao() {
        this.valor = 0;
    }
    
    public Movimentacao(int valor) {
        this.valor = valor;
    }
    
    public Movimentacao(Movimentacao m) {
        this.valor = m.valor;
    }
    
    public int getValor(){
        return this.valor;
    }
    
    public void setValor(int v){
        this.valor = v;
    }
}
