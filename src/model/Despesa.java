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
public class Despesa extends Movimentacao {
    
    private CategoriaDespesa categoria;
    
    public CategoriaDespesa getCategoria(){
        return this.categoria;
    }
    
    public void setCategoria(CategoriaDespesa cat){
        this.categoria = cat;
    }
}
