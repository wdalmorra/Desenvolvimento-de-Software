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
        this.categoria = CategoriaReceita.Lucros;
    }
    
    public Receita(CategoriaReceita cat, int valor) {
        super(valor);
        this.categoria = cat;
    }
    
    public Receita(Receita r) {
        super(r);
        this.categoria = r.categoria;
    }
    
    public CategoriaReceita getCategoria(){
        return this.categoria;
    }
    
    public void setCategoria(CategoriaReceita cat){
        this.categoria = cat;
    }
    
    @Override
    public String toString(){
        String real = String.valueOf(this.getValor() / 100);
        int dec = this.getValor() % 100;
        String decimal = String.valueOf(dec);
        if (dec < 10) {
            decimal = "0" + decimal;
        }
        return CategoriaReceita.categoriaToString(categoria) +" R$:"+
                real + "," + decimal;
    }
}