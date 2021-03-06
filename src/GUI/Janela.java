/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Funções.Função;
import Funções.Logistica;
import Instancias.Instancias;
import RedeNeural.RedeNeural;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author mortz
 */
public class Janela extends javax.swing.JFrame {
    private Instancias treinamento=null;
    private Instancias teste=null;
    private RedeNeural rede;
    
    private int numNeuroniosEntrada;
    private int numNeuroniosSaida;
    private int numNeuroniosOcultos;
    private int numCamadasOcultas;

    public RedeNeural getRede() {
        return rede;
    }

    public void setRede(RedeNeural rede) {
        this.rede = rede;
    }
    
    public Janela() {
        initComponents();
        matbox.setEditable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        matbox = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        matbox.setColumns(20);
        matbox.setRows(5);
        jScrollPane1.setViewportView(matbox);

        jLabel1.setText("Matriz de Confusão:");

        jMenu1.setText("Conjuntos");

        jMenuItem1.setText("Abrir Conjunto de Treinamento");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem3.setText("Abrir Conjunto de Testes");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem8.setText("Normalizar Conjuntos");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem8);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Rede Neural");

        jMenuItem5.setText("Criar Rede Neural");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem2.setText("Treinar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem4.setText("Teste");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Pesos");

        jMenuItem7.setText("Ajustar Pesos");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFileChooser j = new JFileChooser();
        if(j.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
            File arquivo = j.getSelectedFile();
            treinamento = new Instancias();
            treinamento.abrirArquivo(arquivo);
            treinamento.embaralhar();
            numNeuroniosEntrada = treinamento.getInstancia(0).atributos.length;
            numNeuroniosSaida = treinamento.getNumClasses();
            numNeuroniosOcultos = (int)Math.round(Math.sqrt(numNeuroniosEntrada * numNeuroniosSaida));
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if(treinamento == null || rede == null){
            JOptionPane.showMessageDialog(this, "Conjunto de treinamento não carregado ou Rede neural não criada!!");
        }
        else{
            Treinamento t = new Treinamento(this, true, this);
            t.toFront();
            t.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        JFileChooser j = new JFileChooser();
        if(j.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
            File arquivo = j.getSelectedFile();
            teste = new Instancias();
            teste.abrirArquivo(arquivo);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        if(teste == null){
            matbox.setText("Não foi encontrado nenhum teste!!");
        }
        else{
            int[][] matriz = rede.testarRede(teste);
            this.setMatriz(matriz);
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        if(treinamento == null){
            JOptionPane.showMessageDialog(this, "Conjunto de treinamento não carregado!!");
        }
        else{
            numNeuroniosOcultos = (int)Math.round(Math.sqrt(numNeuroniosEntrada * numNeuroniosSaida));
            CriarRedeNeural c = new CriarRedeNeural(this, true, this, this.numNeuroniosEntrada, this.numNeuroniosOcultos, this.numNeuroniosSaida);
            c.toFront();
            c.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        if(rede == null){
            JOptionPane.showMessageDialog(this, "Rede neural não foi criada!!");
        }
        else{
            AjustarPesos p= new AjustarPesos(this, true, this);
            p.toFront();
            p.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        if(teste == null || treinamento == null){
            JOptionPane.showMessageDialog(this, "É necessário carregar o Conjunto de Testes e Conjunto de Treinamento primeiro!!");
        }
        else{
            this.normalizar();
        }
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    
    public static void main(String[] args){
        Janela j = new Janela();
        j.toFront();
        j.setVisible(true);
    }
    
    public void criarRedeNeural(int numeroCamadasOcultas, int numeroNeuronicosCamadaOculta, Função f){
        rede = new RedeNeural(numNeuroniosEntrada,numeroNeuronicosCamadaOculta,numeroCamadasOcultas,numNeuroniosSaida,f);
        this.numCamadasOcultas =numeroCamadasOcultas;
        this.numNeuroniosOcultos= numeroNeuronicosCamadaOculta;
    }

    public int getNumNeuroniosEntrada() {
        return numNeuroniosEntrada;
    }

    public void setNumNeuroniosEntrada(int numNeuroniosEntrada) {
        this.numNeuroniosEntrada = numNeuroniosEntrada;
    }

    public int getNumNeuroniosSaida() {
        return numNeuroniosSaida;
    }

    public void setNumNeuroniosSaida(int numNeuroniosSaida) {
        this.numNeuroniosSaida = numNeuroniosSaida;
    }

    public int getNumNeuroniosOcultos() {
        return numNeuroniosOcultos;
    }

    public void setNumNeuroniosOcultos(int numNeuroniosOcultos) {
        this.numNeuroniosOcultos = numNeuroniosOcultos;
    }

    public int getNumCamadasOcultas() {
        return numCamadasOcultas;
    }

    public void setNumCamadasOcultas(int numCamadasOcultas) {
        this.numCamadasOcultas = numCamadasOcultas;
    }
    
    public double[] getPesos(int camada, int neuronio){
        return rede.getPesos(camada, neuronio);
    }
    
    public void setMatriz(int [][] m){
        String s = "";
        for(int i=0;i<m.length;i++){
            for(int j=0;j<m[0].length;j++){
                s += "\t"+m[i][j];
            }
            s += "\n";
        }
        matbox.setText(s);
    }
    
    public void treinarRede(){
        treinamento.embaralhar();
        rede.treinamento(treinamento);
    }
    
    private void normalizar() {
       treinamento.normalizar(0, 1, teste);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea matbox;
    // End of variables declaration//GEN-END:variables

}
