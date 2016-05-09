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
    Combustível, Conta_de_água, Conta_de_luz, Conta_telefônica, 
    Educação, Estacionamento, Internet, Lazer, Manutenção_do_carro,
    Moradia, Reforma_da_casa, Restaurante, Saúde, Seguros, Serviços_domésticos,
    Supermercado, TV_por_assinatura, Viagens, Outras_despesas;
    
    static public String categoriaToString(CategoriaDespesa cat){
        return cat.toString().replaceAll("_", " ");
    }
    
    static public CategoriaDespesa stringToCategoria(String s){
        try {
            return CategoriaDespesa.valueOf(s.replaceAll(" ", "_"));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    
}
