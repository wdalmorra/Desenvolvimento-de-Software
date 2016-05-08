/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.APISistemaDesktop;
import model.Calendario;
import model.DadosMes;
import model.Movimentacao;


public class APIView implements Observer{
    private ViewMenu vm;
    private ViewNovoMes vnm;
    private ViewMenuPopUp vmp;
    private ViewRelatorios vr;
    private ViewSobre vs;
    
    public APIView() {
        this.vm = new ViewMenu();
        this.vnm = new ViewNovoMes();
        this.vmp = new ViewMenuPopUp();
        this.vr = new ViewRelatorios();
        this.vs = new ViewSobre();
    }

    public void abrePopup(String comando) {
        this.vmp.pack();
        this.vmp.setLocationRelativeTo(null);
        this.vmp.setComando(comando);
        this.vmp.popularMenus();
        this.vmp.setVisible(true);
    }
    
    public void abreRelatorio() {
        this.vr.pack();
        this.vr.setLocationRelativeTo(null);
        this.vr.popularMenus();
        this.vr.setVisible(true);
        this.vm.setVisible(false);
    }
    
    public void abreSobre() {
        this.vs.pack();
        this.vs.setLocationRelativeTo(null);
        this.vs.setVisible(true);
        this.vm.setVisible(false);
    }
    
    public void addController(ActionListener c) {
        this.vm.addController(c);
        this.vnm.addController(c);
        this.vmp.addController(c);
        this.vr.addController(c);
        this.vs.addController(c);
    }
    
    public void atualizaInfoMes() {
        this.vnm.pack();
        this.vnm.setLocationRelativeTo(null);
        this.vnm.setMes(this.vmp.getMesPopup());
        this.vnm.setAno(this.vmp.getAnoPopup());       
    }
    
    public String exportarArquivo(){
        return this.vnm.exportarArquivo();
    }
    
    public void fechaPrograma() {
        System.exit(0);
    }
    
    public void fechaRelatorio() {
        this.vr.setVisible(false);
        this.vm.setVisible(true);
    }
    
    public void fechaSobre(){
        this.vs.setVisible(false);
        this.vm.setVisible(true);
    }
    
    public int getAno() {
        return this.vnm.getAno();
    }
    
    public int getMes() {
        return this.vnm.getMes();
    }
    
    public int getValor() {
        String valor = this.vnm.getValor();
        if(valor.matches("\\d+")) {
            valor = valor + "00";
        } else if(valor.matches("\\d+[,]\\d")) {
            valor = valor + "0";
        } else if(valor.matches("\\d+[,]\\d\\d")) {
            valor = valor + "";
        } else {
            if (valor.contains(".")) {
                this.mostraMensagemDeErro(this.vnm, "Por favor, use vírgula para os centavos.");
            } else {
                this.mostraMensagemDeErro(this.vnm, "Por favor, insira um valor válido.");
            }
            
            return -1;
        }
        
        String valorComCentavos = valor.replaceFirst("[,]", "");
        
        if(isInteger(valorComCentavos)) {
            return Integer.parseInt(valorComCentavos);
        }
        
        return -1;
    }
    
    public void menu() {
        this.vm.pack();
        this.vm.setLocationRelativeTo(null);
        this.vm.setVisible(true);
    }
    
    public void mostraMes() {
        this.vnm.setVisible(true);
        this.vmp.setVisible(false);
        this.vm.setVisible(false); 
    }
    
    public void popularComDespesas() {
        this.vnm.popularComDespesas();
    }
    
    public void popularComReceitas() {
        this.vnm.popularComReceitas();
    }
    
    public void popupCancelar() {
        this.vmp.setVisible(false);
    }
    
    public String getCategoriaRelatorio() {
        return this.vr.getCategoria();
    }
    
    public GregorianCalendar getMesAnoInicialRelatorio() {
        return new GregorianCalendar(Integer.parseInt(this.vr.getAnoInicial()),
                Calendario.mesToInt(this.vr.getMesInicial()), 1);
    }
    
    public GregorianCalendar getMesAnoFinalRelatorio() {
        return new GregorianCalendar(Integer.parseInt(this.vr.getAnoFinal()),
                Calendario.mesToInt(this.vr.getMesFinal()), 1);
    }
    
    public GregorianCalendar getMesAnoPie() {
        return new GregorianCalendar(Integer.parseInt(this.vr.getAnoPie()),
                Calendario.mesToInt(this.vr.getMesPie()), 1);
    }
    
    public void setMesesRelatorio(){
        this.vr.setMeses(this.getMesAnoInicialRelatorio(),
                this.getMesAnoFinalRelatorio());
    }
    
    public void setReceitaEDespesa() {
        this.vr.setReceitaEDespesa(this.vr.isReceita(), this.vr.isDespesa());
    }
    
    public void voltar() {
        this.vnm.limpaLista();
        this.vnm.limpaInfos();
        this.vnm.setVisible(false);
        this.vm.setVisible(true);
    }
    
    public void limpaValor() {
        this.vnm.limpaValor();
    }
    
    public Movimentacao movimentacaoAtual() {
        return this.vnm.getMovimentacaoAtual();
    }
    
    public String getCategoriaNovoMes() {
        return this.vnm.getCategoria();
    }
    
    public String getDouR() {
        return this.vnm.getDouR();
    }
    
    public String getComando() {
        return this.vmp.getComando();
    }
    
    public void setAlterando(boolean b){
        this.vnm.setAlterando(b);
    }
    
    public void cancelarAlteracao(){
        this.vnm.setAlterando(false);
    }
    
    public void modificaMovimentacao(Movimentacao m){
        this.vnm.modificaMovimentacao(m);
        
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
    
    public void notificaErro(String frame, String msg) {
        switch(frame) {
            case "menu":
                this.mostraMensagemDeErro(this.vm, msg);
                break;
            case "novoMes":
                this.mostraMensagemDeErro(this.vnm, msg);
                break;
            case "popup":
                this.mostraMensagemDeErro(this.vmp, msg);
                break;
            case "relatorio":
                this.mostraMensagemDeErro(this.vr, msg);
                break;
            default:
                break;
        }
    }
    
    private void mostraMensagemDeErro(JFrame f, String mensagem){
        JOptionPane.showMessageDialog(f, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void update(Observable sis, Object ob) {
        if (sis instanceof APISistemaDesktop) {
            // REFATORAR: provavelmente vai precisar de um tipo de objeto 'evento'
            // pra sinalizar se o que veio foi um relatorio ou um dado mensal.
            // Ou mudar a logica da interface pra reconhecer isso
            
            if (ob instanceof ArrayList<?>) {
                if(((ArrayList<?>)ob).get(0) instanceof Movimentacao) {
                    // TODO: conferir se nao pode vir uma lista vazia;
                    this.vnm.setModelList((ArrayList<Movimentacao>)ob);
                } else if (((ArrayList<?>)ob).get(0) instanceof Integer) {
                    // TODO: implementar a logica de gerar a visualizacao dos
                    this.vr.criaDadosGraficoBarras((ArrayList<Integer>) ob);
                    // relatorios
                }
            } else if (ob instanceof DadosMes) {
                this.vr.criaDadosGraficoTorta((DadosMes)ob);
            }
        }
    }
}
