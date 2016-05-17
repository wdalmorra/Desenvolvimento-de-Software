/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Calendario;
import model.CategoriaDespesa;
import model.CategoriaReceita;
import model.Despesa;
import model.Movimentacao;
import model.Receita;

/**
 * FXML Controller class
 *
 * @author william
 */
public class NovoMesController implements Initializable {

    @FXML
    private TableView<Movimentacao> novoMesTable;
    @FXML
    private Button novoMesSalvar;
    @FXML
    private Button novoMesExportar;
    @FXML
    private Label novoMesSaldo;
    @FXML
    private Button novoMesVoltar;
    @FXML
    private Label novoMesSalvo;
    @FXML
    private TextField novoMesValor;
    @FXML
    private Button novoMesSubmeter;
    @FXML
    private Button novoMesDeletar;
    @FXML
    private Button novoMesCancelar;
    @FXML
    private RadioButton novoMesDespesa;
    @FXML
    private ToggleGroup derGrupo;
    @FXML
    private RadioButton novoMesReceita;
    @FXML
    private ComboBox novoMesCategorias;
    @FXML
    private Button novoMesAlterar;
    @FXML
    private Label novoMesMes;
    @FXML
    private Label novoMesAno;
    
    private Stage stage;
    
    private String tempReceitas[];
    private String tempDespesas[];

    private Movimentacao movimentacaoAtual;

    private ObservableList<Movimentacao> tableData;
    
    private boolean salvo;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        salvo = false;

        CategoriaReceita[] receitas = CategoriaReceita.values();
        CategoriaDespesa[] despesas = CategoriaDespesa.values();
        
        tempReceitas = new String[receitas.length];
        tempDespesas = new String[despesas.length];
        
        int i = 0;
        for (CategoriaReceita cr : receitas){
            tempReceitas[i] = CategoriaReceita.categoriaToString(cr);
            i++;
        }
        
        i = 0;
        for (CategoriaDespesa cd : despesas){
            tempDespesas[i] = CategoriaDespesa.categoriaToString(cd);
            i++;
        }
        
        novoMesCategorias.getItems().addAll(novoMesReceita.isSelected() ? tempReceitas : tempDespesas);
        novoMesCategorias.getSelectionModel().selectFirst();
        
        tableData = FXCollections.observableArrayList();
        
        novoMesTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        ((TableColumn<Movimentacao,String>)novoMesTable.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<Movimentacao,String>("catp"));
        ((TableColumn<Movimentacao,String>)novoMesTable.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<Movimentacao,String>("valorp"));
        
        novoMesTable.setRowFactory(new Callback<TableView<Movimentacao>, TableRow<Movimentacao>>() {
            @Override
            public TableRow<Movimentacao> call(TableView<Movimentacao> param) {
                return new TableRow<Movimentacao>() {  

                    @Override
                    public void updateItem(Movimentacao item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            this.getStyleClass().remove("despesa-row");
                            this.getStyleClass().remove("receita-row");
                            if(item instanceof Despesa) {
                                this.getStyleClass().add("despesa-row");
                            } else if(item instanceof Receita) {
                                this.getStyleClass().add("receita-row");
                            }
                        } 
                    }
                };
            }
        });
        
        novoMesTable.setItems(tableData);

        this.setAlterando(false);
        
        novoMesSalvo.setText("Não Salvo");
        novoMesSalvo.setTextFill(Color.RED);
    }
    
    public void addController(EventHandler<ActionEvent> c) {
        novoMesSalvar.setOnAction(c);
        novoMesExportar.setOnAction(c);
        novoMesVoltar.setOnAction(c);
        novoMesAlterar.setOnAction(c);
        novoMesSubmeter.setOnAction(c);
        novoMesDeletar.setOnAction(c);
        novoMesCancelar.setOnAction(c);
        novoMesDespesa.setOnAction(c);
        novoMesReceita.setOnAction(c);
        novoMesTable.getSelectionModel().selectedItemProperty().addListener((ChangeListener)c);
    }
    
    /**
     *
     * @param c
     */
    public void addController(ChangeListener c){
        System.out.println("OI");
        
    }
    
    public String exportarArquivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar Arquivo");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos XML (*.xml)", "*.xml"));
        fileChooser.setInitialFileName("export.xml");
        File file = fileChooser.showSaveDialog(stage);

        if(file == null) {
            return null;
        } else {
            return file.toPath().toString();
        }
    }
    
    public void setMes(String m) {
        novoMesMes.setText(m);
    }
    
    public void setAno(String m) {
        novoMesAno.setText(m);
    }
    
    
    public void setStage(Stage s) {
        this.stage = s;
    }
    
    public int getAno() {
        String ano;
        ano = novoMesAno.getText();
        
        return Integer.parseInt(ano);
    }
    
    public String getCategoria() {
        return (String)novoMesCategorias.getSelectionModel().getSelectedItem();
    }
    
    public String getDouR() {
        String ret = novoMesDespesa.isSelected() ? "D" : "R";
        return ret;
    }
    
    public int getMes() {
        String mes;
        mes = novoMesMes.getText();
        
        return Calendario.mesToInt(mes);
    }
    
    public Movimentacao getMovimentacaoAtual() {
        return this.movimentacaoAtual;
    }
    
    public boolean isSaved(){
        return salvo;
    }
    
    public String getValor() {
        return novoMesValor.getText();
    }
    
    public void limpaInfos() {
        novoMesDespesa.setSelected(true);
        novoMesReceita.setSelected(false);
        
        novoMesCategorias.getSelectionModel().selectFirst();
        
        this.limpaValor();
        popularComDespesas();
    }
    
    public void limpaLista() {
        tableData.clear();
    }
    
    public void limpaSelecao(){
        novoMesTable.getSelectionModel().clearSelection();
        this.limpaValor();
    }
    
    public void limpaValor() {
        novoMesValor.setText("");
    }
    
    public void modificaMovimentacao(Movimentacao m){
        movimentacaoAtual = m;
        
        NumberFormat n = NumberFormat.getCurrencyInstance(new Locale( "pt", "BR" )); 
        String s = n.format(m.getValor() / 100.0);
        s = s.replaceAll("[R. $]", "");
        novoMesValor.setText(s);
        
        // ARRUMAR AQUI
        
        
        if (m instanceof Receita) {
            novoMesReceita.setSelected(true);
        } else {
            novoMesDespesa.setSelected(true);
        }
        
        novoMesCategorias.getSelectionModel().select(m.getCatP());
    }
    
    public void popularComDespesas() {
        novoMesCategorias.getItems().clear();
        novoMesCategorias.getItems().addAll(tempDespesas);
        novoMesCategorias.getSelectionModel().selectFirst();
    }
    
    public void popularComReceitas() {
        novoMesCategorias.getItems().clear();
        novoMesCategorias.getItems().addAll(tempReceitas);
        novoMesCategorias.getSelectionModel().selectFirst();
    }
    
    public void setAlterando(boolean b){
        novoMesCancelar.setVisible(b);
        novoMesDeletar.setVisible(b);
        novoMesSubmeter.setVisible(!b);
        novoMesAlterar.setVisible(b);

        if (!b){
            this.limpaSelecao();
        }
        novoMesCategorias.setDisable(b);

        novoMesDespesa.setDisable(b);
        novoMesReceita.setDisable(b);
    }
    
    public void setSalvo(boolean s){
        this.salvo = s;
        if (s) {
            novoMesSalvo.setText("Salvo ✓");
            novoMesSalvo.setTextFill(Color.GREEN);
        } else {
            novoMesSalvo.setText("Não Salvo");
            novoMesSalvo.setTextFill(Color.RED);
        }

    }
    
    public void setTableData(ArrayList<Movimentacao> movs) {
        int saldo = 0;
        tableData.clear();

        for(Movimentacao m : movs) {
            tableData.add(m);
            if (m instanceof Receita) {
                saldo += m.getValor();
            } else {
                saldo -= m.getValor();
            }
        }
        DecimalFormat n = new DecimalFormat("R$ #,##0.00;R$ -#,##0.00"); 
        String s = n.format(saldo / 100.0);
        novoMesSaldo.setText(s);
        if (saldo < 0) {
            novoMesSaldo.setTextFill(Color.RED);
        } else if (saldo > 0) {
            novoMesSaldo.setTextFill(Color.GREEN);
        } else {
            novoMesSaldo.setTextFill(Color.BLACK);
        }
        System.out.println("PRONTO");
    }
    
}
