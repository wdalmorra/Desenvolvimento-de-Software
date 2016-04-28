/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import model.CategoriaDespesa;
import model.CategoriaReceita;
import model.Movimentacao;
import model.Calendario;
import model.Despesa;
import model.Receita;

/**
 *
 * @author Eliezer
 */
public class ViewNovoMes extends javax.swing.JFrame {

    
    DefaultComboBoxModel receitaModel;
    DefaultComboBoxModel despesaModel;
    
    
    /**
     * Creates new form ViewNovoMes
     */
    public ViewNovoMes() {
        initComponents();
        myInitComponents();
    }
    
    public void addController(ActionListener c) {
        novoMesVoltar.addActionListener(c);
        novoMesSubmeter.addActionListener(c);
        novoMesSalvar.addActionListener(c);
        novoMesExportar.addActionListener(c);
        novoMesDespesaCheck.addActionListener(c);
        novoMesReceitaCheck.addActionListener(c);
        novoMesCancelar.addActionListener(c);
        novoMesLista.addListSelectionListener((ListSelectionListener) c);
        novoMesDeletar.addActionListener(c);        
    }
    
    public int getAno() {
        String ano;
        ano = labelAno.getText();
        
        return Integer.parseInt(ano);
    }
    
    public int getMes() {
        String mes;
        mes = labelMes.getText();
        
        return Calendario.mesToInt(mes);
    }
    
    public String getValor() {
        return textValor.getText();
    }
    
    public String getCategoria() {
        String cat;
        if( menuCategoria.getSelectedItem() instanceof CategoriaDespesa ){
            cat = CategoriaDespesa.categoriaToString(((CategoriaDespesa) menuCategoria.getSelectedItem()));
        } else {
            cat = CategoriaReceita.categoriaToString(((CategoriaReceita) menuCategoria.getSelectedItem()));
        }
        return cat;
    }
    
    public String getDouR() {
        String ret = novoMesDespesaCheck.isSelected() ? "D" : "R";
        return ret;
    }
    
    public void setMes(String m) {
        labelMes.setText(m);
    }
    
    public void setAno(String a) {
        labelAno.setText(a);
    }
    
    public void setModelList(ArrayList<Movimentacao> movs) {
        DefaultListModel<Movimentacao> modelList = 
                new DefaultListModel<>();
        
        for(Movimentacao m : movs) {
            modelList.addElement(m);
        }
        
        novoMesLista.setModel(modelList);
    }
    
    public void popularComDespesas() {
        menuCategoria.setModel(despesaModel);
    }
    
    public void popularComReceitas() {
        menuCategoria.setModel(receitaModel);
    }
    
    public void limpaSelecao(){
        novoMesLista.clearSelection();
        textValor.setText("");
    }
    
    private void myInitComponents() {
        receitaModel = new DefaultComboBoxModel<>(CategoriaReceita.values());
        despesaModel = new DefaultComboBoxModel<>(CategoriaDespesa.values());
        
        menuCategoria.setModel(novoMesReceitaCheck.isSelected() ? receitaModel : despesaModel);
        
        
        novoMesLista.setModel(new DefaultListModel());
        novoMesLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        this.setAlterando(false);
    }
    
    public void setAlterando(boolean b){
        novoMesCancelar.setVisible(b);
        novoMesDeletar.setVisible(b);
        
        novoMesSubmeter.setText(b ? "Alterar" : "Submeter");

        novoMesSubmeter.setActionCommand(b ? "novoMesAlterar" : "novoMesSubmeter");
        
        if (!b){
            this.limpaSelecao();
        }
        menuCategoria.setEnabled(!b);
            
        novoMesDespesaCheck.setEnabled(!b);
        novoMesReceitaCheck.setEnabled(!b);
        
        
    }
    
    public void modificaMovimentacao(Movimentacao m){
                
        textValor.setText(String.valueOf(m.getValor()));
        
        if (m instanceof Receita) {
            groupDeR.setSelected(novoMesReceitaCheck.getModel(), true);
            
            CategoriaReceita.categoriaToString(((Receita) m).getCategoria());
        } else {
            groupDeR.setSelected(novoMesDespesaCheck.getModel(), true);
            
            
            menuCategoria.setSelectedItem(CategoriaDespesa.categoriaToString(((Despesa) m).getCategoria()));
            
        }
        
        
    }

//    public void removeCatCombobox(String cat, String tipo){
//
//        if (tipo == "D") {
//            despesaModel.removeElement(CategoriaDespesa.stringToCategoria(cat));
//        } else {
//            receitaModel.removeElement(CategoriaReceita.stringToCategoria(cat));
//        }
//    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupDeR = new javax.swing.ButtonGroup();
        novoMesMesTexto = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        novoMesLista = new javax.swing.JList<>();
        novoMesDespesaCheck = new javax.swing.JRadioButton();
        novoMesReceitaCheck = new javax.swing.JRadioButton();
        menuCategoria = new javax.swing.JComboBox();
        novoMesSubmeter = new javax.swing.JButton();
        novoMesVoltar = new javax.swing.JButton();
        novoMesSalvar = new javax.swing.JButton();
        novoMesExportar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        textValor = new javax.swing.JFormattedTextField();
        labelMes = new javax.swing.JLabel();
        labelAno = new javax.swing.JLabel();
        novoMesCancelar = new javax.swing.JButton();
        novoMesDeletar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("novoMes");
        setBackground(new java.awt.Color(255, 204, 204));
        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(350, 300));

        novoMesMesTexto.setText("Mês");

        jScrollPane1.setViewportView(novoMesLista);

        groupDeR.add(novoMesDespesaCheck);
        novoMesDespesaCheck.setSelected(true);
        novoMesDespesaCheck.setText("Despesa");
        novoMesDespesaCheck.setActionCommand("despesaCheckBox");

        groupDeR.add(novoMesReceitaCheck);
        novoMesReceitaCheck.setText("Receita");
        novoMesReceitaCheck.setActionCommand("receitaCheckBox");

        menuCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        novoMesSubmeter.setText("Submeter");
        novoMesSubmeter.setActionCommand("novoMesSubmeter");

        novoMesVoltar.setText("Voltar");
        novoMesVoltar.setActionCommand("novoMesVoltar");

        novoMesSalvar.setText("Salvar");
        novoMesSalvar.setActionCommand("novoMesSalvar");

        novoMesExportar.setText("Exportar");
        novoMesExportar.setActionCommand("novoMesExportar");

        jLabel1.setText("R$");

        textValor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##,##0.00"))));
        textValor.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        labelMes.setText("Mes");

        labelAno.setText("2016");

        novoMesCancelar.setText("Cancelar");
        novoMesCancelar.setActionCommand("novoMesCancelar");

        novoMesDeletar.setText("Deletar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(novoMesMesTexto, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(novoMesSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(novoMesExportar, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)))
                        .addGap(6, 6, 6)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(novoMesVoltar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(labelAno)
                        .addGap(100, 100, 100))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(novoMesDespesaCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(novoMesReceitaCheck)
                        .addGap(4, 4, 4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(novoMesDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(novoMesSubmeter, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(novoMesCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(menuCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(textValor, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(8, 8, 8))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(novoMesMesTexto, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelMes)
                            .addComponent(labelAno))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(novoMesDespesaCheck)
                            .addComponent(novoMesReceitaCheck))
                        .addGap(12, 12, 12)
                        .addComponent(menuCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(novoMesSubmeter)
                            .addComponent(novoMesCancelar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(novoMesDeletar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(novoMesExportar)
                    .addComponent(novoMesSalvar)
                    .addComponent(novoMesVoltar))
                .addContainerGap())
        );

        novoMesMesTexto.getAccessibleContext().setAccessibleName("Mes");
        novoMesVoltar.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup groupDeR;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelAno;
    private javax.swing.JLabel labelMes;
    private javax.swing.JComboBox menuCategoria;
    private javax.swing.JButton novoMesCancelar;
    private javax.swing.JButton novoMesDeletar;
    private javax.swing.JRadioButton novoMesDespesaCheck;
    private javax.swing.JButton novoMesExportar;
    private javax.swing.JList<Movimentacao> novoMesLista;
    private javax.swing.JLabel novoMesMesTexto;
    private javax.swing.JRadioButton novoMesReceitaCheck;
    private javax.swing.JButton novoMesSalvar;
    private javax.swing.JButton novoMesSubmeter;
    private javax.swing.JButton novoMesVoltar;
    private javax.swing.JFormattedTextField textValor;
    // End of variables declaration//GEN-END:variables
}
