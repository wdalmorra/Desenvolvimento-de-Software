/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
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
        ano = novoMesAnoLabel.getText();
        
        return Integer.parseInt(ano);
    }
    
    public int getMes() {
        String mes;
        mes = novoMesMesLabel.getText();
        
        return Calendario.mesToInt(mes);
    }
    
    public Movimentacao getMovimentacaoAtual() {
        return this.movimentacaoAtual;
    }
    
    public String getValor() {
        return novoMesValorText.getText();
    }
    
    public String getCategoria() {
        String cat;
        if( novoMesCategoriaCb.getSelectedItem() instanceof CategoriaDespesa ){
            cat = CategoriaDespesa.categoriaToString(((CategoriaDespesa) novoMesCategoriaCb.getSelectedItem()));
        } else {
            cat = CategoriaReceita.categoriaToString(((CategoriaReceita) novoMesCategoriaCb.getSelectedItem()));
        }
        return cat;
    }
    
    public String getDouR() {
        String ret = novoMesDespesaCheck.isSelected() ? "D" : "R";
        return ret;
    }
    
    public void setMes(String m) {
        novoMesMesLabel.setText(m);
    }
    
    public void setAno(String a) {
        novoMesAnoLabel.setText(a);
    }
    
    public void setModelList(ArrayList<Movimentacao> movs) {
        modelList.removeAllElements();
        
        for(Movimentacao m : movs) {
            modelList.addElement(m);
        }
    }
    
    public void popularComDespesas() {
        novoMesCategoriaCb.setModel(despesaModel);
    }
    
    public void popularComReceitas() {
        novoMesCategoriaCb.setModel(receitaModel);
    }
    
    public String exportarArquivo() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Exportar");
        chooser.setSelectedFile(new File("export.xml"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files", "xml");
        chooser.setFileFilter(filter);
        chooser.setApproveButtonText("Exportar");
        
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            if (f.exists()) {
                int result = JOptionPane.showConfirmDialog(null,"O arquivo já existe. Deseja sobreescrevê-lo?","Exportar",JOptionPane.YES_NO_OPTION);
                switch (result){
                    case JOptionPane.YES_OPTION:
                        return chooser.getSelectedFile().toString();
                    case JOptionPane.NO_OPTION:
                    case JOptionPane.CLOSED_OPTION:
                    default:
                        return null;
                }
            } else {
                return chooser.getSelectedFile().toString();
            }
            
        } else {
            return null;
        }
    }
    
    public void limpaSelecao(){
        novoMesLista.clearSelection();
        this.limpaValor();
    }
    
    public void limpaLista() {
        modelList.removeAllElements();
    }
    
    public void limpaValor() {
        novoMesValorText.setText("");
    }
    
    public void limpaInfos() {
        novoMesDespesaCheck.setSelected(true);
        novoMesReceitaCheck.setSelected(false);
        
        novoMesCategoriaCb.setSelectedIndex(0);
        
        this.limpaValor();
        popularComDespesas();
    }
    
    private void myInitComponents() {
        modelList = new DefaultListModel<>();
        receitaModel = new DefaultComboBoxModel<>(CategoriaReceita.values());
        despesaModel = new DefaultComboBoxModel<>(CategoriaDespesa.values());
        
        novoMesCategoriaCb.setModel(novoMesReceitaCheck.isSelected() ? receitaModel : despesaModel);
        
        
        novoMesLista.setModel(modelList);
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
        novoMesCategoriaCb.setEnabled(!b);
            
        novoMesDespesaCheck.setEnabled(!b);
        novoMesReceitaCheck.setEnabled(!b);
    }
    
    public void modificaMovimentacao(Movimentacao m){
        movimentacaoAtual = m;
        
        NumberFormat n = NumberFormat.getCurrencyInstance(new Locale( "pt", "BR" )); 
        String s = n.format(m.getValor() / 100.0);
        s = s.replaceAll("[R $]", "");
        novoMesValorText.setText(s);
        
        if (m instanceof Receita) {
            groupDeR.setSelected(novoMesReceitaCheck.getModel(), true);
            
             novoMesCategoriaCb.setSelectedItem(((Receita) m).getCategoria());
        } else {
            groupDeR.setSelected(novoMesDespesaCheck.getModel(), true);
            
            novoMesCategoriaCb.setSelectedItem(((Despesa) m).getCategoria());
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupDeR = new javax.swing.ButtonGroup();
        novoMesMesLabelFixa = new javax.swing.JLabel();
        novoMesListaScroll = new javax.swing.JScrollPane();
        novoMesLista = new javax.swing.JList<Movimentacao>();
        novoMesDespesaCheck = new javax.swing.JRadioButton();
        novoMesReceitaCheck = new javax.swing.JRadioButton();
        novoMesCategoriaCb = new javax.swing.JComboBox();
        novoMesSubmeter = new javax.swing.JButton();
        novoMesVoltar = new javax.swing.JButton();
        novoMesSalvar = new javax.swing.JButton();
        novoMesExportar = new javax.swing.JButton();
        novoMesRsLabel = new javax.swing.JLabel();
        novoMesMesLabel = new javax.swing.JLabel();
        novoMesAnoLabel = new javax.swing.JLabel();
        novoMesCancelar = new javax.swing.JButton();
        novoMesDeletar = new javax.swing.JButton();
        novoMesValorText = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("novoMes");
        setBackground(new java.awt.Color(255, 204, 204));
        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(350, 300));

        novoMesMesLabelFixa.setText("Mês");

        novoMesListaScroll.setViewportView(novoMesLista);

        groupDeR.add(novoMesDespesaCheck);
        novoMesDespesaCheck.setSelected(true);
        novoMesDespesaCheck.setText("Despesa");
        novoMesDespesaCheck.setActionCommand("despesaCheckBox");

        groupDeR.add(novoMesReceitaCheck);
        novoMesReceitaCheck.setText("Receita");
        novoMesReceitaCheck.setActionCommand("receitaCheckBox");

        novoMesCategoriaCb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        novoMesSubmeter.setText("Submeter");
        novoMesSubmeter.setActionCommand("novoMesSubmeter");

        novoMesVoltar.setText("Voltar");
        novoMesVoltar.setActionCommand("novoMesVoltar");

        novoMesSalvar.setText("Salvar");
        novoMesSalvar.setActionCommand("novoMesSalvar");

        novoMesExportar.setText("Exportar");
        novoMesExportar.setActionCommand("novoMesExportar");

        novoMesRsLabel.setText("R$");

        novoMesMesLabel.setText("Mes");

        novoMesAnoLabel.setText("2016");

        novoMesCancelar.setText("Cancelar");
        novoMesCancelar.setActionCommand("novoMesCancelar");

        novoMesDeletar.setText("Deletar");
        novoMesDeletar.setActionCommand("novoMesDeletar");

        novoMesValorText.setText("0,00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(novoMesMesLabelFixa, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(novoMesMesLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(novoMesListaScroll, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(novoMesSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(novoMesExportar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(6, 6, 6)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(novoMesVoltar, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(novoMesAnoLabel)
                                    .addGap(100, 100, 100))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(novoMesDespesaCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(novoMesReceitaCheck)
                                    .addGap(4, 4, 4)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(novoMesSubmeter)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(novoMesCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(12, 12, 12)
                                    .addComponent(novoMesRsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(novoMesValorText))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(23, 23, 23)
                                    .addComponent(novoMesCategoriaCb, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(novoMesDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(novoMesMesLabelFixa, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(novoMesMesLabel)
                            .addComponent(novoMesAnoLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(novoMesListaScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(novoMesDespesaCheck)
                            .addComponent(novoMesReceitaCheck))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(novoMesCategoriaCb, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(novoMesRsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(novoMesValorText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(novoMesSubmeter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(novoMesCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(novoMesDeletar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(novoMesExportar)
                    .addComponent(novoMesSalvar)
                    .addComponent(novoMesVoltar))
                .addContainerGap())
        );

        novoMesMesLabelFixa.getAccessibleContext().setAccessibleName("Mes");
        novoMesVoltar.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private Movimentacao movimentacaoAtual;
    private DefaultListModel<Movimentacao> modelList;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup groupDeR;
    private javax.swing.JLabel novoMesAnoLabel;
    private javax.swing.JButton novoMesCancelar;
    private javax.swing.JComboBox novoMesCategoriaCb;
    private javax.swing.JButton novoMesDeletar;
    private javax.swing.JRadioButton novoMesDespesaCheck;
    private javax.swing.JButton novoMesExportar;
    private javax.swing.JList<Movimentacao> novoMesLista;
    private javax.swing.JScrollPane novoMesListaScroll;
    private javax.swing.JLabel novoMesMesLabel;
    private javax.swing.JLabel novoMesMesLabelFixa;
    private javax.swing.JRadioButton novoMesReceitaCheck;
    private javax.swing.JLabel novoMesRsLabel;
    private javax.swing.JButton novoMesSalvar;
    private javax.swing.JButton novoMesSubmeter;
    private javax.swing.JTextField novoMesValorText;
    private javax.swing.JButton novoMesVoltar;
    // End of variables declaration//GEN-END:variables
}
