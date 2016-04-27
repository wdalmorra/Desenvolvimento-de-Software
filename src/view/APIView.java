/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.event.ActionListener;

public class APIView {
    private ViewMenu vm;
    private ViewNovoMes vnm;
    
    public APIView() {
        this.vm = new ViewMenu();
        this.vnm = new ViewNovoMes();
    }
    
    public void menu() {
        this.vm.setVisible(true);
    }
    
    public void novoMes() {
        this.vnm.pack();
        this.vnm.setVisible(true);
        this.vm.setVisible(false);
    }
    
    public void fechar() {
        System.exit(0);
    }
    
    public void voltar() {
        this.vnm.dispose();
        this.vm.setVisible(true);
    }
    
    public void addController(ActionListener controller) {
        this.vm.addController(controller);
        this.vnm.addController(controller);
    }
}
