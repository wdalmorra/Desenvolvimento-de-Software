/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.MenuController;
import controller.NovoMesController;
import controller.PopUpController;
import controller.RelatorioController;
import controller.SobreController;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.APISistemaDesktop;
import model.Calendario;
import model.DadosMes;
import model.Movimentacao;

/**
 *
 * @author william
 */
public class APIView implements Observer{
    
    private Scene menuScene;
    private Scene novoMesScene;
    private Scene popUpScene;
    private Scene relatorioScene;
    private Scene sobreScene;
    private MenuController mc;
    private NovoMesController nmc;
    private PopUpController puc;
    private RelatorioController rc;
    private SobreController sc;
    
    
    private Stage stage;
    
    public APIView(Stage stage) throws IOException{
        this.stage = stage;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MenuView.fxml"));
        Parent root = (Parent)loader.load();
        menuScene = new Scene(root);
        mc = loader.<MenuController>getController();
        
        loader = new FXMLLoader(getClass().getResource("/view/NovoMesView.fxml"));
        root = (Parent)loader.load();
        novoMesScene = new Scene(root);
        nmc = loader.<NovoMesController>getController();
        nmc.setStage(stage);
        
        loader = new FXMLLoader(getClass().getResource("/view/PopUpView.fxml"));
        root = (Parent)loader.load();
        popUpScene = new Scene(root);
        puc = loader.<PopUpController>getController();
        
        loader = new FXMLLoader(getClass().getResource("/view/RelatorioView.fxml"));
        root = (Parent)loader.load();
        relatorioScene = new Scene(root);
        rc = loader.<RelatorioController>getController();
        
        loader = new FXMLLoader(getClass().getResource("/view/SobreView.fxml"));
        root = (Parent)loader.load();
        sobreScene = new Scene(root);
        sc = loader.<SobreController>getController();
        
        novoMesScene.getStylesheets().add("view/CoresTabela.css");
    }
    
    public void addController(EventHandler<ActionEvent> c){
        mc.addController(c);
        nmc.addController(c);
        puc.addController(c);
        rc.addController(c);
        sc.addCobtroller(c);
    }
    
    public void abreNovoMes(){
        stage.setScene(novoMesScene);
        stage.show();
    }
    public void abreMenu(){
        stage.setScene(menuScene);
        stage.show();
    }
    public void abrePopUp(String id){
        puc.setComando(id);
        puc.popularMenus();
        stage.setScene(popUpScene);
        stage.show();
    }
    
    public void abreRelatorio() {
        this.rc.popularMenus();
        stage.setScene(relatorioScene);
        stage.show();
    }
    
    public void abreSobre() {
        stage.setScene(sobreScene);
        stage.show();
    }
    
    public void atualizaInfoMes() {
        this.nmc.setMes(this.puc.getMesPopup());
        this.nmc.setAno(this.puc.getAnoPopup());       
    }
    
    public void cancelarAlteracao(){
        this.nmc.setAlterando(false);
    }
    
    public String exportarArquivo(){
        return this.nmc.exportarArquivo();
    }
    
    public void fechaPrograma() {
        System.exit(0);
    }
    
    public void fechaRelatorio() {
        this.abreMenu();
    }
    
    public void fechaSobre(){
        this.abreMenu();
    }

    public int getAno() {
        return this.nmc.getAno();
    }

    public String getCategoriaNovoMes() {
        return this.nmc.getCategoria();
    }
    
    public String getDouR() {
        return this.nmc.getDouR();
    }
    
    public int getMes() {
        return this.nmc.getMes();
    }
    
    public String getCategoriaRelatorio() {
        return this.rc.getCategoria();
    }
    
    public String getComando() {
        return this.puc.getComando();
    }
    
    public Movimentacao getMovimentacaoAtual() {
        return this.nmc.getMovimentacaoAtual();
    }
    
    public GregorianCalendar getMesAnoInicialRelatorio() {
        return new GregorianCalendar(Integer.parseInt(this.rc.getAnoInicial()),
                Calendario.mesToInt(this.rc.getMesInicial()), 1);
    }
    
    public GregorianCalendar getMesAnoFinalRelatorio() {
        return new GregorianCalendar(Integer.parseInt(this.rc.getAnoFinal()),
                Calendario.mesToInt(this.rc.getMesFinal()), 1);
    }
    
    public GregorianCalendar getMesAnoPie() {
        return new GregorianCalendar(Integer.parseInt(this.rc.getAnoPie()),
                Calendario.mesToInt(this.rc.getMesPie()), 1);
    }
    
    public int getValor() {
        String valor = this.nmc.getValor();
        if(valor.matches("\\d+")) {
            valor = valor + "00";
        } else if(valor.matches("\\d+[,]\\d")) {
            valor = valor + "0";
        } else if(valor.matches("\\d+[,]\\d\\d")) {
            valor = valor + "";
        } else {
            if (valor.contains(".")) {
                this.mostraMensagemDeErro("Por favor, use vírgula para os centavos.");
            } else {
                this.mostraMensagemDeErro("Por favor, insira um valor válido.");
            }
            
            return -1;
        }
        
        String valorComCentavos = valor.replaceFirst("[,]", "");
        
        if(isInteger(valorComCentavos)) {
            return Integer.parseInt(valorComCentavos);
        }
        
        return -1;
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
    
    public void limpaValor() {
        this.nmc.limpaValor();
    }
    
    public void modificaMovimentacao(Movimentacao m){
        this.nmc.modificaMovimentacao(m);
        
    }
    
    public void popularComDespesas() {
        this.nmc.popularComDespesas();
    }
    
    public void popularComReceitas() {
        this.nmc.popularComReceitas();
    }

    public void popUpCancelar() {
        this.abreMenu();
    }
    
    public void mostraMensagemDeErro(String mensagem){
//        Alert alert = new Alert(AlertType.ERROR);
//        alert.setTitle("Mensagem de Erro");
//        alert.setHeaderText("Erro!");
//        alert.setContentText(mensagem);
//        alert.show();
    }
    
    public void mostraMes() {
        this.abreNovoMes();
    }
    
    public void setAlterando(boolean b){
        this.nmc.setAlterando(b);
    }
    
    public void setMesesRelatorio(){
        this.rc.setMeses(this.getMesAnoInicialRelatorio(),
                this.getMesAnoFinalRelatorio());
    }
    
    public void setReceitaEDespesa() {
        this.rc.setReceitaEDespesa(this.rc.isReceita(), this.rc.isDespesa());
    }
    
    public void setSalvo(boolean b){
        this.nmc.setSalvo(b);
        
    }
    
    public void voltar() {
        this.nmc.limpaLista();
        this.nmc.limpaInfos();
        this.abreMenu();
    }

    @Override
    public void update(Observable sis, Object ob) {
        if (sis instanceof APISistemaDesktop) {
            // REFATORAR: provavelmente vai precisar de um tipo de objeto 'evento'
            // pra sinalizar se o que veio foi um relatorio ou um dado mensal.
            // Ou mudar a logica da interface pra reconhecer isso
            
            if (ob instanceof ArrayList<?>) {
                if (((ArrayList<?>)ob).size() == 0) {
                    
                } else {
                    if(((ArrayList<?>)ob).get(0) instanceof Movimentacao) {
                        // TODO: conferir se nao pode vir uma lista vazia;
                        this.nmc.setTableData((ArrayList<Movimentacao>)ob);
                        this.nmc.setSalvo(false);
                    } else if (((ArrayList<?>)ob).get(0) instanceof Integer) {
                        // TODO: implementar a logica de gerar a visualizacao dos
                         this.rc.criaDadosGraficoBarras((ArrayList<Integer>) ob);
                        // relatorios
                    }
                }
                
            } else if (ob instanceof DadosMes) {
                this.rc.criaDadosGraficoTorta((DadosMes)ob);
            }
        }
    }
    
}
