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
            case "despesaCheckBox":
                this.despesaCheckBox();
                break;
                
            case "menuAbrirMes":
                this.menuAbrirMes(e.getActionCommand());
                break;
                
            case "menuNovoMes":
                this.menuNovoMes(e.getActionCommand());
                break;
                
            case "menuRelatorio":
                this.menuRelatorio();
                break;
                
            case "menuSair":
                this.menuSair();
                break;
                
            case "menuSobre":
                this.menuSobre();
                break;
            
            case "novoMesAlterar":
                this.novoMesAlterar();
                break;    
            
            case "novoMesCancelar":
                this.novoMesCancelar();
                break;
            
            case "novoMesDeletar":
                this.novoMesDeletar();
                break;  
            
            case "novoMesExportar":
                this.novoMesExportar();
                break;
                
            case "novoMesSalvar":
                this.novoMesSalvar();
                break;
                
            case "novoMesSubmeter":
               this.novoMesSubmeter();
                break;
                
            case "novoMesVoltar":
                this.novoMesVoltar();
                break;
                
            case "popupCancelar":
                this.popupCancelar();
                break;
                
            case "popupOk":
                this.popupOk();
                break;
                
            case "receitaCheckBox":
                this.receitaCheckBox();
                break;
           
            case "relatorioAplicarFiltro":
                this.relatorioAplicarFiltro();
                break;
                
            case "relatorioSelecionarMes":
                this.relatorioSelecionarMes();
                break;
                
            case "relatorioVoltar":
                this.relatorioVoltar();
                break;
            
            case "sobreVoltar":
                this.sobreVoltar();
                break;
            
            default:
                break;
        }
    }
    
    public void addModel(APISistemaDesktop model) {
        this.sistema = model;
    }
    
    public void addView(APIView view) {
        this.view = view;
    }
    
    private void criaOuAbreMes(GregorianCalendar mes, String command) {
        if(command.equals("menuNovoMes")) {
            if(mesExiste(mes)) {
                this.view.popupCancelar();
                this.view.notificaErro("popup", "Mês já existe.\n"
                        + "Use a opção ABRIR MÊS.");
            } else {
                this.view.mostraMes();
                this.sistema.criaMes(this.getDate());
            }
        } else {
            if(mesExiste(mes)) {
                this.view.mostraMes();
                this.sistema.visualizaMes(mes);
            } else {
                this.view.popupCancelar();
                this.view.notificaErro("popup", "Mês não existente.");
            }
        }
    }
    
    private void despesaCheckBox() {
        this.view.popularComDespesas();
    }
    
    private GregorianCalendar getDate() {
        int ano = this.view.getAno();
        int mes = this.view.getMes();
        GregorianCalendar date = new GregorianCalendar(ano, mes, 1);
        return date;
    }
    
    private void menuAbrirMes(String comando) {
        this.view.abrePopup(comando);
    }
    
    private void menuNovoMes(String comando) {
        this.view.abrePopup(comando);
    }
    
    private void menuRelatorio() {
        this.view.abreRelatorio();
    }
    
    private void menuSair() {
        this.view.fechaPrograma();
    }
    
    private void menuSobre() {
        this.view.abreSobre();
        
    }
    
    private boolean mesExiste(GregorianCalendar mes) {
        return this.sistema.mesExiste(mes);
    }
    
    private void novoMesAlterar() {
        this.submeteMovimentacao(false);
        this.view.setAlterando(false);
    }
    
    private void novoMesCancelar() {
        this.view.cancelarAlteracao();
    }
    
    private void novoMesDeletar() {
        this.removeMovimentacao();
        this.view.setAlterando(false);
    }
    
    private void novoMesExportar() {
        String filePath = this.view.exportarArquivo();
        if (filePath != null) {
            this.sistema.exportaMes(this.getDate(), filePath);
        }
    }
    
    private void novoMesSalvar() {
        this.sistema.salvaMes(this.getDate());
    }
    
    private void novoMesSubmeter() {
        this.submeteMovimentacao(true);
    }
    
    private void novoMesVoltar() {
        this.view.setAlterando(false);
        this.view.voltar();
    }
    
    private void popupCancelar() {
        this.view.popupCancelar();
    }
    
    private void popupOk() {
        this.view.atualizaInfoMes();
        this.criaOuAbreMes(this.getDate(), this.view.getComando());
    }
    
    private void receitaCheckBox() {
        this.view.popularComReceitas();
    }
    
    private void relatorioAplicarFiltro() {
        this.view.setMesesRelatorio();
        this.sistema.geraRelatorio(this.view.getMesAnoInicialRelatorio(),
                this.view.getMesAnoFinalRelatorio(), this.view.getCategoriaRelatorio());        
    }

    private void relatorioSelecionarMes() {
        boolean mesExiste;
        this.view.setReceitaEDespesa();
        mesExiste = this.sistema.geraRelatorioMensal(this.view.getMesAnoPie());
        if(!mesExiste) {
            this.view.notificaErro("relatorio", "Mês não encontrado.");
        }
    }

    private void relatorioVoltar() {
        this.view.fechaRelatorio();
    }

    private void removeMovimentacao() { 
        this.sistema.removeMovimentacao(this.getDate(),view.movimentacaoAtual());
    }
    
    private void sobreVoltar(){
        this.view.fechaSobre();
    }

    private void submeteMovimentacao(boolean novaSub) {
        int valor = this.view.getValor();

        if(valor >= 0) {
            String cat = this.view.getCategoriaNovoMes();
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
}