/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.APISistemaDesktop;
import model.Movimentacao;


public class APIView implements Observer{
    private ViewMenu vm;
    private ViewNovoMes vnm;
    private ViewMenuPopUp vmp;
    
    public APIView() {
        this.vm = new ViewMenu();
        this.vnm = new ViewNovoMes();
        this.vmp = new ViewMenuPopUp();
    }

    public void menu() {
        this.vm.pack();
        this.vm.setLocationRelativeTo(null);
        this.vm.setVisible(true);
    }
    
    public void abrePopup(String comm) {
        this.vmp.pack();
        this.vmp.setLocationRelativeTo(null);
        this.vmp.setCommand(comm);
        this.vmp.popularMenus();
        this.vmp.setVisible(true);
    }
    
    public void atualizaInfoMes() {
        this.vnm.pack();
        this.vnm.setLocationRelativeTo(null);
        this.vnm.setMes(this.vmp.getMesPopup());
        this.vnm.setAno(this.vmp.getAnoPopup());       
    }
    
    public void mostraMes() {
        this.vnm.setVisible(true);
        this.vmp.dispose();
        this.vm.setVisible(false); 
    }
    
    public void fechar() {
        System.exit(0);
    }
    
    public void voltar() {
        this.vnm.limpaLista();
        this.vnm.limpaInfos();
        this.vnm.dispose();
        this.vm.setVisible(true);
    }
    
    public void popupCancelar() {
        this.vmp.dispose();
    }
    
    public void popularComDespesas() {
        this.vnm.popularComDespesas();
    }
    
    public void popularComReceitas() {
        this.vnm.popularComReceitas();
    }
    
    public void addController(ActionListener c) {
        this.vm.addController(c);
        this.vnm.addController(c);
        this.vmp.addController(c);
    }
    
    public int getAno() {
        return this.vnm.getAno();
    }
    
    public int getMes() {
        return this.vnm.getMes();
    }
    
    public int getValor() {
        String valor = this.vnm.getValor();
        String valorComCentavos = valor.replaceAll("[.,]", "");
        
        if(isInteger(valorComCentavos)) {
            return Integer.parseInt(valorComCentavos);
        } else {
            this.mostraMensagemDeErro(this.vnm, "Por favor, insira um valor inteiro.");
        }
        
        return -1;
    }
    
    public void limpaValor() {
        this.vnm.limpaValor();
    }
    
    public Movimentacao movimentacaoAtual() {
        return this.vnm.getMovimentacaoAtual();
    }
    
    
    public String getCategoria() {
        return this.vnm.getCategoria();
    }
    
    public String getDouR() {
        return this.vnm.getDouR();
    }
    
    public String getCommand() {
        return this.vmp.getCommand();
    }
    
    public void setAlterando(boolean b){
        this.vnm.setAlterando(b);
    }
    
    public void cancelarAlteracao(){
        this.vnm.setAlterando(false);
    }
    
    public void modificaMovimentacao(Movimentacao m){
        this.vnm.modificaMovimentacao(m);
        
    }
    public boolean isInteger(String valor) {
        try {
            Integer.parseInt(valor);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }
    
    public void notificaErro(String frame, String msg) {
        switch(frame) {
            case "menu":
                this.mostraMensagemDeErro(this.vm, msg);
                break;
            case "novoMes":
                this.mostraMensagemDeErro(this.vnm, msg);
                break;
            case "popup":
                this.mostraMensagemDeErro(this.vmp, msg);
                break;
            default:
                break;
        }
    }
    
    public void mostraMensagemDeErro(JFrame f, String mensagem){
        JOptionPane.showMessageDialog(f, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void update(Observable sis, Object ob) {
        if (sis instanceof APISistemaDesktop) {
            this.vnm.setModelList((ArrayList<Movimentacao>)ob);
        }
    }
}
