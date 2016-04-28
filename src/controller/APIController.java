/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.APISistemaDesktop;
import model.CategoriaDespesa;
import model.CategoriaReceita;
import model.Despesa;
import model.Movimentacao;
import model.Receita;
import view.APIView;

public class APIController implements ActionListener, ListSelectionListener{
    private APIView view;
    private APISistemaDesktop sistema;
    
    public APIController(){
        this.sistema = null;
        this.view = null;
    }
    
     @Override
    public void valueChanged(ListSelectionEvent lse) {
        JList lsm = (JList) lse.getSource();
         
         if (lsm.getSelectedValue() == null) {
             return;
         }
        
        this.view.modificaMovimentacao((Movimentacao) lsm.getSelectedValue());
        
        
         if (((Movimentacao)lsm.getSelectedValue()) instanceof Receita) {
             this.view.popularComReceitas();
         } else {
             this.view.popularComDespesas();
         }
         
         view.setAlterando(true);
        
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
                this.submeteMovimentacao(true);
                break;
            case "novoMesVoltar":
                view.voltar();
                break;
            case "novoMesSalvar":
                this.salvaMes();
                break;
            case "novoMesExportar":
                break;
            case "novoMesDeletar":
              break;
            case "novoMesCancelar":
                view.cancelarAlteracao();
                break;
            case "novoMesAlterar":
                this.submeteMovimentacao(false);
                view.setAlterando(false);
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
    
    private void submeteMovimentacao(boolean novaSub) {
        int valor = view.getValor();
        if(valor >= 0) {
            String cat = view.getCategoria();
            String tipo = view.getDouR();
            GregorianCalendar date = this.getDate();
            if (novaSub) {
                sistema.addMovimentacao(valor, cat, tipo, date);
            } else {
                sistema.alteraMovimentacao(valor, cat, tipo, date);
            }
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