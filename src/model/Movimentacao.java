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
abstract class Movimentacao {
    
    private int valor;
    
    public int getValor(){
        return this.valor;
    }
    
    public void setValor(int v){
        this.valor = v;
    }
    
}
