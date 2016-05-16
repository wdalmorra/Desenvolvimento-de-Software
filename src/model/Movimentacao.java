/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author William
 */
public abstract class Movimentacao {
    
    private int valor;
    private SimpleStringProperty valorp;
    private SimpleStringProperty catp;
    
    public Movimentacao() {
        this.valor = 0;
        valorp = new SimpleStringProperty();
        catp = new SimpleStringProperty();
    }
    
    public Movimentacao(int valor) {
        this.valor = valor;
        valorp = new SimpleStringProperty();
        catp = new SimpleStringProperty();
        this.setValorP(valor);
    }
    
    public Movimentacao(Movimentacao m) {
        this.valor = m.valor;
        valorp = new SimpleStringProperty();
        catp = new SimpleStringProperty();
        this.setValorP(m.valor);

    }
    
    public int getValor(){
        return this.valor;
    }
    
    public void setValor(int v){
        this.valor = v;
        this.valorp.set(valorToString(v));
    }
    
    public final void setValorP(int v){
        this.valorp.set(valorToString(v));
    }
    
    public final void setCatP(String c){
        this.catp.set(c);
    }
    
    public final String getValorP(){
        return this.valorp.get();
    }
    
    public final String getCatP(){
        return this.catp.get();
    }
    
    public StringProperty valorpProperty() {
        return valorp;
    }
    
    public StringProperty catpProperty() {
        return catp;
    }
    
    public abstract String valorToString(int value);
}
