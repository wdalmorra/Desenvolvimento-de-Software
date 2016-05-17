/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import model.APISistemaDesktop;
import model.Calendario;
import model.CategoriaDespesa;
import model.CategoriaReceita;
import model.DadosMes;
import model.Despesa;
import model.Movimentacao;
import model.Receita;

/**
 * FXML Controller class
 *
 * @author william
 */
public class RelatorioController implements Initializable {

    @FXML
    private ComboBox relatorioMesInicialCb;
    @FXML
    private ComboBox relatorioAnoInicialCb;
    @FXML
    private ComboBox relatorioMesFinalCb;
    @FXML
    private ComboBox relatorioAnoFinalCb;
    @FXML
    private ComboBox relatorioCategoriaCb;
    @FXML
    private Button relatorioAplicarFiltro;
    @FXML
    private ComboBox relatorioMesCb;
    @FXML
    private ComboBox relatorioAnoCb;
    @FXML
    private CheckBox relatorioDespesasChb;
    @FXML
    private CheckBox relatorioReceitasChb;
    @FXML
    private Button relatorioSelecionarMes;
    @FXML
    private Button relatorioVoltarTorta;
    @FXML
    private Button relatorioVoltarBarras;
    @FXML
    private BarChart relatorioGraficoBarras;
    @FXML
    private PieChart relatorioGraficoTorta;
    
    private GregorianCalendar iniciogc;
    private GregorianCalendar fimgc;

    private XYChart.Series series;
    
    ObservableList<PieChart.Data> pieChartData;
    
    private boolean isSelectedReceita;
    private boolean isSelectedDespesa;
    
    public void addController(EventHandler<ActionEvent> c){
        relatorioAplicarFiltro.setOnAction(c);
        relatorioSelecionarMes.setOnAction(c);
        relatorioVoltarBarras.setOnAction(c);
        relatorioVoltarTorta.setOnAction(c);
    }
    
    public void criaDadosGraficoBarras(ArrayList<Integer> dados) {
        GregorianCalendar gc;
        int it = 0;
        series.setName(this.getCategoria());
        series.getData().clear();
        // TODO: Laco tem que ir ate um a mais, senao eh pouca coisa impressa
        for (gc = iniciogc; !APISistemaDesktop.comparaMeses(gc, fimgc); gc.add(GregorianCalendar.MONTH, 1)) {
            System.out.println(it);
            if (dados.get(it) != 0) {
                String mes = String.valueOf(gc.get(GregorianCalendar.MONTH)) + "/" + String.valueOf(gc.get(GregorianCalendar.YEAR));
                series.getData().add(new XYChart.Data(mes, dados.get(it)/100));
            }
            it++;
        }
    }

    public  void criaDadosGraficoTorta(DadosMes dm){
        pieChartData.clear();
        for (Movimentacao mov : dm.getMovimentacoes()){
            if (mov instanceof Receita && isSelectedReceita) {
                String cr = CategoriaReceita.categoriaToString(((Receita) mov).getCategoria());
                pieChartData.add(new PieChart.Data(cr, mov.getValor()));
            } else {
                if (mov instanceof Despesa && isSelectedDespesa) {
                    String cd = CategoriaDespesa.categoriaToString(((Despesa) mov).getCategoria());
                    pieChartData.add(new PieChart.Data(cd, mov.getValor()));
                }
            }
        }
    }

    public String getMesInicial(){
        return (String) relatorioMesInicialCb.getSelectionModel().getSelectedItem();
    }
    public String getAnoInicial(){
        return (String) relatorioAnoInicialCb.getSelectionModel().getSelectedItem();
    }
    public String getMesFinal(){
        return (String) relatorioMesFinalCb.getSelectionModel().getSelectedItem();
    }
    public String getAnoFinal(){
        return (String) relatorioAnoFinalCb.getSelectionModel().getSelectedItem();
    }
    public String getCategoria(){
        return (String) relatorioCategoriaCb.getSelectionModel().getSelectedItem();
    }
    public String getMesPie(){
        return (String) relatorioMesCb.getSelectionModel().getSelectedItem();
    }
    public String getAnoPie(){
        return (String) relatorioAnoCb.getSelectionModel().getSelectedItem();
    }
    public boolean isReceita(){
        return relatorioReceitasChb.isSelected();
    }
    public boolean isDespesa(){
        return relatorioDespesasChb.isSelected();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        series = new XYChart.Series();
        relatorioGraficoBarras.getData().addAll(series);
        
        pieChartData = FXCollections.observableArrayList();
        
        relatorioGraficoTorta.setData(pieChartData);
        
    }
    
    public void popularMenus() {
        relatorioMesInicialCb.getItems().addAll(Calendario.listaMes);
        relatorioAnoInicialCb.getItems().addAll(Calendario.listaAno);
        relatorioMesFinalCb.getItems().addAll(Calendario.listaMes);
        relatorioAnoFinalCb.getItems().addAll(Calendario.listaAno);
        relatorioMesCb.getItems().addAll(Calendario.listaMes);
        relatorioAnoCb.getItems().addAll(Calendario.listaAno);
        
        ArrayList<String> cats = new ArrayList<>();
        cats.add("Saldo");
        cats.add("Total Despesas");
        cats.add("Total Receitas");

        for (CategoriaDespesa cd : CategoriaDespesa.values()) {
            cats.add(CategoriaDespesa.categoriaToString(cd));
        }
        
        for (CategoriaReceita cr : CategoriaReceita.values()) {
            cats.add(CategoriaReceita.categoriaToString(cr));
        }
        
        relatorioCategoriaCb.getItems().addAll(cats);
        
        relatorioMesInicialCb.getSelectionModel().selectFirst();
        relatorioAnoInicialCb.getSelectionModel().selectFirst();
        relatorioMesFinalCb.getSelectionModel().selectFirst();
        relatorioAnoFinalCb.getSelectionModel().selectFirst();
        relatorioMesCb.getSelectionModel().selectFirst();
        relatorioAnoCb.getSelectionModel().selectFirst();
        relatorioCategoriaCb.getSelectionModel().selectFirst();
        
    }
    
    public void setMeses(GregorianCalendar igc, GregorianCalendar lgc){
        iniciogc = igc;
        fimgc = lgc;
        fimgc.add(GregorianCalendar.MONTH, 1);
    }
    
    public void setReceitaEDespesa(boolean isR, boolean isD){
        isSelectedReceita = isR;
        isSelectedDespesa = isD;
    }
    
}
