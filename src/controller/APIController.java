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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.APISistemaDesktop;
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
         
        this.view.setAlterando(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.paramString());
        
        switch(e.getActionCommand()) {
            case "menuNovoMes":
                this.view.abrePopup(e.getActionCommand());
                break;
                
            case "menuAbrirMes":
                this.view.abrePopup(e.getActionCommand());
                break;
                
            case "menuRelatorio":
                break;
                
            case "menuSobre":
                break;
                
            case "menuSair":
                this.view.fechar();
                break;
                
            case "novoMesSubmeter":
                this.submeteMovimentacao(true);
                break;
                
            case "novoMesVoltar":
                this.view.voltar();
                break;
                
            case "novoMesSalvar":
                this.salvaMes();
                break;
                
            case "novoMesExportar":
                String filePath = this.view.exportarArquivo();
                if (filePath != null) {
                    this.sistema.exportaMes(this.getDate(), filePath);
                }
                break;
                
            case "novoMesDeletar":
                this.removeMovimentacao();
                this.view.setAlterando(false);
                break;
                
            case "novoMesCancelar":
                this.view.cancelarAlteracao();
                break;
                
            case "novoMesAlterar":
                this.submeteMovimentacao(false);
                this.view.setAlterando(false);
                break;
                
            case "popupOk":
                this.view.atualizaInfoMes();
                this.criaOuAbreMes(this.getDate(),
                        this.view.getCommand());
                break;
                
            case "popupCancelar":
                this.view.popupCancelar();
                break;
                
            case "despesaCheckBox":
                this.view.popularComDespesas();
                break;
                
            case "receitaCheckBox":
                this.view.popularComReceitas();
                break;
           
            default:
                break;
        }
    }
    
    public void criaMes() {
        this.sistema.criaMes(this.getDate());
    }
    
    public boolean visualizaMes(GregorianCalendar mes) {
        return this.sistema.visualizaMes(mes);
    }
    
    public void salvaMes() {
        this.sistema.salvaMes(this.getDate());
    } 
    
    public void exportaMes(String dir) {
        //sistema.exportaMes(this.dados, dir);
    }
    
    public boolean mesExiste(GregorianCalendar mes) {
        return this.visualizaMes(mes);
    }
    
    public void criaOuAbreMes(GregorianCalendar mes, String command) {
        if(command.equals("menuNovoMes")) {
            if(mesExiste(mes)) {
                this.view.notificaErro("popup", "Mês já existe.\n"
                        + "Use a opção ABRIR MÊS.");
            } else {
                this.view.mostraMes();
                this.criaMes();
            }
        } else {
            if(mesExiste(mes)) {
                this.view.mostraMes();
            } else {
                this.view.notificaErro("popup", "Mês não existente.");
            }
        }
    }
    
    public void addModel(APISistemaDesktop model) {
        this.sistema = model;
    }
    
    public void addView(APIView view) {
        this.view = view;
    }
    
    private void removeMovimentacao() { 
        this.sistema.removeMovimentacao(this.getDate(),view.movimentacaoAtual());
    }
    
    private void submeteMovimentacao(boolean novaSub) {
        int valor = this.view.getValor();
        
        if(valor >= 0) {
            String cat = this.view.getCategoria();
            String tipo = this.view.getDouR();
            GregorianCalendar date = this.getDate();
            
            if (novaSub) {
                this.sistema.addMovimentacao(valor, cat, tipo, date);
            } else {
                this.sistema.alteraMovimentacao(valor, cat, tipo, date);
            }
        }
        
        this.view.limpaValor();
    }
    
    private GregorianCalendar getDate() {
        int ano = this.view.getAno();
        int mes = this.view.getMes();
        GregorianCalendar date = new GregorianCalendar(ano, mes, 1);
        return date;
    }
}