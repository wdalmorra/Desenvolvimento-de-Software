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
public enum CategoriaReceita {
    Lucros, Rendimentos, Salário, Vendas, Outras_receitas;
    
    static public String categoriaToString(CategoriaReceita cat){
        return cat.toString().replaceAll("_", " ");
    }
    
    static public CategoriaReceita stringToCategoria(String s){
       try {
            return CategoriaReceita.valueOf(s.replaceAll(" ", "_"));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
}
