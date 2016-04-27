/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import model.APISistemaDesktop;
import model.CategoriaDespesa;
import model.CategoriaReceita;
import model.Despesa;
import model.Movimentacao;
import model.Receita;
import view.APIView;

public class APIController implements ActionListener {
    private APIView view;
    private APISistemaDesktop sistema;
    
    public APIController(){
        this.sistema = null;
        this.view = null;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        switch(e.getActionCommand()) {
            case "NOVO MÊS":
                view.novoMes();
                this.criaMes();
                break;
            case "ABRIR MÊS":
                break;
            case "RELATÓRIO GRÁFICO":
                break;
            case "SOBRE":
                break;
            case "SAIR":
                view.fechar();
                break;
            case "Submeter":
                this.submeteMovimentacao();
                break;
            case "Voltar":
                view.voltar();
                break;
            case "Salvar":
                this.salvaMes();
                break;
            case "Exportar":
                break;
            default:
                break;
        }
    }
    
    public void criaMes() {
        sistema.criaMes(this.getDate());
    }
    
    public void salvaMes() {
        sistema.salvaMes(this.getDate());
    } 
    
    public void exportaMes(String dir) {
        //sistema.exportaMes(this.dados, dir);
    }
    
    public void addModel(APISistemaDesktop model) {
        this.sistema = model;
    }
    
    public void addView(APIView view) {
        this.view = view;
    }
    
    private void submeteMovimentacao() {
//        int valor = view.getValor();
//        String cat = view.getCategoria();
//        String tipo = view.getDouR();
//        GregorianCalendar date = this.getDate();
//        sistema.addMovimentacao(valor, cat, tipo, date);
    }
    
    private GregorianCalendar getDate() {
//        int ano = view.getAno();
//        int mes = view.getMes();
//        GregorianCalendar date = new GregorianCalendar(ano, mes, 1);
//        return date;
        return null;
    }
}
