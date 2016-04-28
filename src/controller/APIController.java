/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import model.APISistemaDesktop;
import model.CategoriaDespesa;
import model.CategoriaReceita;
import model.Despesa;
import model.Movimentacao;
import model.Receita;
import view.APIView;

public class APIController implements ActionListener {
    private APIView view;
    private APISistemaDesktop sistema;
    
    public APIController(){
        this.sistema = null;
        this.view = null;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.paramString());
        switch(e.getActionCommand()) {
            case "menuNovoMes":
                view.abrePopup();                
                break;
            case "menuAbrirMes":
                break;
            case "menuRelatorio":
                break;
            case "menuSobre":
                break;
            case "menuSair":
                view.fechar();
                break;
            case "novoMesSubmeter":
                this.submeteMovimentacao();
                break;
            case "novoMesVoltar":
                view.voltar();
                break;
            case "novoMesSalvar":
                this.salvaMes();
                break;
            case "novoMesExportar":
                break;
            case "popupOk":
                view.novoMes();
                this.criaMes();
                break;
            case "popupCancelar":
                view.popupCancelar();
                break;
            case "despesaCheckBox":
                view.popularComDespesas();
                break;
            case "receitaCheckBox":
                view.popularComReceitas();
                break;
            default:
                break;
        }
    }
    
    public void criaMes() {
        sistema.criaMes(this.getDate());
    }
    
    public void salvaMes() {
        sistema.salvaMes(this.getDate());
    } 
    
    public void exportaMes(String dir) {
        //sistema.exportaMes(this.dados, dir);
    }
    
    public void addModel(APISistemaDesktop model) {
        this.sistema = model;
    }
    
    public void addView(APIView view) {
        this.view = view;
    }
    
    private void submeteMovimentacao() {
        int valor = view.getValor();
        if(valor >= 0) {
            String cat = view.getCategoria();
            String tipo = view.getDouR();
            GregorianCalendar date = this.getDate();
            sistema.addMovimentacao(valor, cat, tipo, date);
            
            this.view.removeCatComboBox(cat, tipo);
            
            
        }
    }
    
    private GregorianCalendar getDate() {
        int ano = view.getAno();
        int mes = view.getMes();
        System.out.println("Mes "+ mes);
        GregorianCalendar date = new GregorianCalendar(ano, mes, 1);
        return date;
    }
}