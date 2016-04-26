/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Thainan
 */
public class Receita extends Movimentacao {
    
    private CategoriaReceita categoria;
    
    public Receita() {
        super();
        this.categoria = CategoriaReceita.DEFAULT;
    }
    
    public Receita(CategoriaReceita categoria, int valor) {
        super(valor);
        this.categoria = categoria;
    }
    
    public CategoriaReceita getCategoria(){
        return this.categoria;
    }
    
    public void setCategoria(CategoriaReceita cat){
        this.categoria = cat;
    }
}
