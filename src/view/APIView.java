/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

//import controller.APIController;
import java.awt.event.ActionListener;

/**
 *
 * @author Thainan
 */
public class APIView {
//    private APIController controlador;
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
        this.vnm.setVisible(true);
    }
    
    public void addController(ActionListener controller) {
        this.vm.addController(controller);
    }
}
