/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhods;

import controller.APIController;
import model.APISistemaDesktop;
import view.APIView;

/**
 *
 * @author Thainan
 */
public class Main {
       /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        APIController controlador;
        final APIView view;
        APISistemaDesktop sistema;
        
        controlador = new APIController();
        view = new APIView();
        sistema = new APISistemaDesktop();
        
        controlador.addModel(sistema);
        controlador.addView(view);
        view.addController(controlador);
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                view.menu();
            }
        });
    }
}
