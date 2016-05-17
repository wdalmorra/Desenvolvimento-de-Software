/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drefinancas;

import controller.APIController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.APISistemaDesktop;
import view.APIView;

/**
 *
 * @author william
 */
public class DreFinancas extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        
        APIView view = new APIView(stage);
        APISistemaDesktop sistema = new APISistemaDesktop();
        APIController controller = new APIController(view, sistema);
        view.addController(controller);
        view.abreMenu();
        sistema.addObserver(view);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
