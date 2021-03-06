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
    
    public Despesa() {
        super();
        this.categoria = CategoriaDespesa.Combustível;
        this.setCatP(CategoriaDespesa.categoriaToString(this.categoria));
    }
    
    public Despesa(CategoriaDespesa cat, int valor) {
        super(valor);
        this.categoria = cat;
        this.setCatP(CategoriaDespesa.categoriaToString(cat));
    }
    
    public Despesa(Despesa r) {
        super(r);
        this.categoria = r.categoria;
        this.setCatP(CategoriaDespesa.categoriaToString(r.categoria));
    }
    
    public CategoriaDespesa getCategoria(){
        return this.categoria;
    }
    
    public void setCategoria(CategoriaDespesa cat){
        this.categoria = cat;
        this.setCatP(CategoriaDespesa.categoriaToString(cat));
    }
    
    @Override
    public String valorToString(int value) {
        String real = String.valueOf(value / 100);
        int dec = value % 100;
        String decimal = String.valueOf(dec);
        if (dec < 10) {
            decimal = "0" + decimal;
        }
        
        return real + "," + decimal;
    }
    
    @Override
    public String toString(){
        String real = String.valueOf(this.getValor() / 100);
        int dec = this.getValor() % 100;
        String decimal = String.valueOf(dec);
        if (dec < 10) {
            decimal = "0" + decimal;
        }
        return CategoriaDespesa.categoriaToString(categoria) +" R$:"+
                real + "," + decimal;
    }
}
