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
    DEFAULT, RECEITA1, RECEITA2;
    
    static public String categoriaToString(CategoriaReceita cat){
        return cat.toString();
    }
    
    static public CategoriaReceita stringToCategoria(String s){
       try {
            return CategoriaReceita.valueOf(s);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
}
