/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author william
 */
public class MenuController implements Initializable {

    @FXML
    private Button menuNovoMes;
    @FXML
    private Button menuAbrirMes;
    @FXML
    private Button menuRelatorio;
    @FXML
    private Button menuSobre;
    @FXML
    private Button menuSair;
    
    private int data;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void addController(EventHandler<ActionEvent> c) {
        menuNovoMes.setOnAction(c);
        menuAbrirMes.setOnAction(c);
        menuRelatorio.setOnAction(c);
        menuSobre.setOnAction(c);
        menuSair.setOnAction(c);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
//        switch(((Button)event.getSource()).getId()) {
//            case "menuCriarMes":
////                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MenuView.fxml"));
//                
//                
//                break;
//            default:
//                break;
//        }
    }
    
//    public void addModel(int data) {
//        this.data = data;
//    }
    
}
