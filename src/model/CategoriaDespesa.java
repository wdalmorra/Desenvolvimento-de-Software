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
public enum CategoriaDespesa {
    DEFAULT, DESPESA1, DESPESA2;
    
    static public String categoriaToString(CategoriaDespesa cat){
        return cat.toString();
    }
    
    static public CategoriaDespesa stringToCategoria(String s){
        return CategoriaDespesa.valueOf(s);
    }
    
    
}
