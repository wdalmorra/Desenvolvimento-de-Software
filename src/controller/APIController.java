/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.GregorianCalendar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import model.APISistemaDesktop;
import model.CategoriaDespesa;
import model.CategoriaReceita;
import model.Despesa;
import model.Movimentacao;
import model.Receita;
import view.APIView;

/**
 *
 * @author william
 */
public class APIController implements EventHandler<ActionEvent>, ChangeListener{

    APIView view;
    APISistemaDesktop sistema;
    
    public APIController(APIView view, APISistemaDesktop sis){
        this.view = view;
        this.sistema = sis;
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() instanceof RadioButton) {
            System.out.println(((RadioButton)event.getSource()).getId());
            switch(((RadioButton)event.getSource()).getId()) {
                case "novoMesDespesa":
                    this.novoMesDespesa();
                    break;
                    
                case "novoMesReceita":
                    this.novoMesReceita();
                    break;
                    
                default:
                    break;
            }
            
        } else {
            System.out.println(((Button)event.getSource()).getId());
            switch(((Button)event.getSource()).getId()) {
                case "menuNovoMes":
                    this.menuNovoMes(((Button)event.getSource()).getId());
                    break;

                case "menuAbrirMes":
                    this.menuAbreMes(((Button)event.getSource()).getId());
                    break;

                case "menuRelatorio":
                    this.menuRelatorio();
                    break;

                case "menuSobre":
                    this.menuSobre();
                    break;

                case "menuSair":
                    this.menuSair();
                    break;

                case "novoMesSalvar":
                    this.novoMesSalvar();
                    break;

                case "novoMesExportar":
                    this.novoMesExportar();
                    break;

                case "novoMesVoltar":
                    this.novoMesVoltar();
                    break;

                case "novoMesAlterar":
                    this.novoMesAlterar();
                    break;

                case "novoMesSubmeter":
                    this.novoMesSubmeter();
                    break;

                case "novoMesCancelar":
                    this.novoMesCancelar();
                    break;
                case "novoMesDeletar":
                    this.novoMesDeletar();
                    break;

                case "popUpOk":
                    this.popUpOk();
                    break;

                case "popUpCancelar":
                    this.popUpCancelar();
                    break;
                
                case "relatorioAplicarFiltro":
                    this.relatorioAplicarFiltro();
                    break;
                
                case "relatorioSelecionarMes":
                    this.relatorioSelecionarMes();
                    break;

                case "relatorioVoltarTorta":
                case "relatorioVoltarBarras":
                    this.relatorioVoltar();
                    break;
                case "sobreVoltar":
                    this.sobreVoltar();
                    break;

                default:
                    break;

            }
        }
    }

    private GregorianCalendar getDate() {
        int ano = this.view.getAno();
        int mes = this.view.getMes();
        GregorianCalendar date = new GregorianCalendar(ano, mes, 1);
        return date;
    }
    
    private void criaOuAbreMes(GregorianCalendar mes, String command) {
        if(command.equals("menuNovoMes")) {
            if(mesExiste(mes)) {
                this.view.popUpCancelar();
                this.view.mostraMensagemDeErro( "Mês já existe.\n"
                        + "Use a opção ABRIR MÊS.");
            } else {
                this.view.mostraMes();
                this.sistema.criaMes(this.getDate());
            }
        } else {
            if(mesExiste(mes)) {
                this.view.mostraMes();
                this.sistema.visualizaMes(mes);
                this.view.setSalvo(true);
            } else {
                this.view.popUpCancelar();
                this.view.mostraMensagemDeErro("Mês não existente.");
            }
        }
    }
    
    private void novoMesDespesa() {
        this.view.popularComDespesas();
    }
    

    private void menuAbreMes(String id) {
        this.view.abrePopUp(id);
    }
    
    private void menuNovoMes(String id) {
        this.view.abrePopUp(id);
    }
    
    private void menuRelatorio() {
        this.view.abreRelatorio();
    }
    
    private void menuSair() {
        this.view.fechaPrograma();
    }
    
    private void menuSobre() {
        this.view.abreSobre();
    }
    
    private boolean mesExiste(GregorianCalendar mes) {
        return this.sistema.mesExiste(mes);
    }
    
    private void novoMesAlterar() {
        this.submeteMovimentacao(false);
        this.view.setAlterando(false);;
    }
    
    private void novoMesCancelar() {
        this.view.cancelarAlteracao();
    }
    
    private void novoMesDeletar() {
        this.removeMovimentacao();
        this.view.setAlterando(false);
    }
    
    private void novoMesExportar() {
        String filePath = this.view.exportarArquivo();
        if (filePath != null) {
            this.sistema.exportaMes(this.getDate(), filePath);
        }
    }
    
    private void novoMesSalvar() {
        this.view.setSalvo(true);
        this.sistema.salvaMes(this.getDate());
    }
    
    private void novoMesSubmeter() {
        this.submeteMovimentacao(true);
    }
    
    private void novoMesVoltar() {
        this.view.setSalvo(false);
        this.view.setAlterando(false);
        this.view.novoMesVoltar();
    }
    
    private void popUpCancelar() {
        this.view.popUpCancelar();
    }
    
    private void popUpOk() {
        this.view.atualizaInfoMes();
        this.criaOuAbreMes(this.getDate(), this.view.getComando());
        
    }
    
    private void novoMesReceita() {
        this.view.popularComReceitas();
    }
    
    private void relatorioAplicarFiltro() {
        this.view.setMesesRelatorio();
        this.sistema.geraRelatorio(this.view.getMesAnoInicialRelatorio(),
                this.view.getMesAnoFinalRelatorio(), this.view.getCategoriaRelatorio());        
    }

    private void relatorioSelecionarMes() {
        boolean mesExiste;
        this.view.setReceitaEDespesa();
        mesExiste = this.sistema.geraRelatorioMensal(this.view.getMesAnoPie());
        if(!mesExiste) {
            this.view.mostraMensagemDeErro("Mês não encontrado.");
        }
    }

    private void relatorioVoltar() {
        this.view.fechaRelatorio();
    }
    
    private void removeMovimentacao() { 
        this.sistema.removeMovimentacao(this.getDate(),view.getMovimentacaoAtual());
    }
    
    private void sobreVoltar(){
        this.view.fechaSobre();
    }
    
    private void submeteMovimentacao(boolean novaSub) {
        int valor = this.view.getValor();

        if(valor >= 0) {
            String cat = this.view.getCategoriaNovoMes();
            String tipo = this.view.getDouR();
            GregorianCalendar date = this.getDate();

            if (novaSub) {
                this.sistema.addMovimentacao(valor, cat, tipo, date);
            } else {
                this.sistema.alteraMovimentacao(valor, cat, tipo, date);
            }
        }
        this.view.limpaValor();
    }


    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue == null) {
            return;
        } else {
            if (((Movimentacao)newValue) instanceof Receita) {
                System.out.println(CategoriaReceita.categoriaToString(((Receita) newValue).getCategoria()));
                this.view.popularComReceitas();
            } else {
                System.out.println(CategoriaDespesa.categoriaToString(((Despesa) newValue).getCategoria()));
                this.view.popularComDespesas();
            }
            
            this.view.modificaMovimentacao((Movimentacao) newValue);
            this.view.setAlterando(true);
        }
        
    }

}
