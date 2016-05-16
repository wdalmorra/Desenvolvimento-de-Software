/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import model.Calendario;

/**
 * FXML Controller class
 *
 * @author william
 */
public class PopUpController implements Initializable {
    
    @FXML
    private Button popUpOk;
    @FXML
    private Button popUpCancelar;
    @FXML
    private ComboBox popUpMesCb;
    @FXML
    private ComboBox popUpAnoCb;
    
    private String comando;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void addController(EventHandler<ActionEvent> c){
        popUpOk.setOnAction(c);
        popUpCancelar.setOnAction(c);
    }
    
    public void setComando(String comando){
        this.comando = comando;
    }
    
    public String getMesPopup() {
        return (String)popUpMesCb.getSelectionModel().getSelectedItem();
    }
    
    public String getAnoPopup() {
        return (String)popUpAnoCb.getSelectionModel().getSelectedItem();
    }
    public String getComando() {
        return this.comando;
    }
    
    public void popularMenus() {
        popUpMesCb.getItems().addAll(Calendario.listaMes);
        popUpMesCb.getSelectionModel().selectFirst();
        popUpAnoCb.getItems().addAll(Calendario.listaAno);
        popUpAnoCb.getSelectionModel().selectFirst();
    }
    
}
