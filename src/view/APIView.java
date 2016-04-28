/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class APIView {
    private ViewMenu vm;
    private ViewNovoMes vnm;
    private ViewMenuPopUp vmp;
    
    public APIView() {
        this.vm = new ViewMenu();
        this.vnm = new ViewNovoMes();
        this.vmp = new ViewMenuPopUp();
    }
    
    public void menu() {
        this.vm.setVisible(true);
    }
    
    public void abrePopup() {
        this.vmp.pack();
        this.vmp.popularMenus();
        this.vmp.setVisible(true);
    }
    
    public void novoMes() {
        this.vnm.pack();
        this.vnm.setMes(this.vmp.getMesPopup());
        this.vnm.setAno(this.vmp.getAnoPopup());
        this.vnm.setVisible(true);
        this.vmp.dispose();
        this.vm.setVisible(false);        
    }
    
    public void fechar() {
        System.exit(0);
    }
    
    public void voltar() {
        this.vnm.dispose();
        this.vm.setVisible(true);
    }
    
    public void popupCancelar() {
        this.vmp.dispose();
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
        String valorComCentavos = valor.replaceFirst("[.]", "");
        
        if(isInteger(valorComCentavos)) {
            return Integer.parseInt(valorComCentavos);
        } else {
            this.mostraMensagemDeErro(vnm, "Por favor, insira um valor inteiro.");
        }
        
        return -1;
    }
    
    public String getCategoria() {
        return this.vnm.getCategoria();
    }
    
    public String getDouR() {
        return this.vnm.getDouR();
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
    
    void mostraMensagemDeErro(JFrame f, String mensagem){
        JOptionPane.showMessageDialog(f, mensagem);
    }
}
