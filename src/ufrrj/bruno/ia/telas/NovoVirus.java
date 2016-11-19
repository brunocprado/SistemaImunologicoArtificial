/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufrrj.bruno.ia.telas;

import java.awt.Color;
import java.util.Random;
import javax.swing.JColorChooser;
import ufrrj.bruno.ia.SistemaImunologico;
import ufrrj.bruno.ia.celulas.Patogeno;
import ufrrj.bruno.ia.log.Virus;


/**
 *
 * @author bruno
 */
public class NovoVirus extends javax.swing.JFrame {

    private final SistemaImunologico sistema;
    
    public NovoVirus(SistemaImunologico sistema) {
        initComponents();
        this.sistema = sistema;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtNLados = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtIdentificador = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        painelCor = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Novo Virus");
        setName(""); // NOI18N
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        txtNLados.setName(""); // NOI18N

        jLabel1.setText("Número de lados");

        txtIdentificador.setToolTipText("");
        txtIdentificador.setName(""); // NOI18N

        jLabel2.setText("Identificador (op)");

        jLabel3.setText("Cor");

        jButton2.setText("Novo");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insere(evt);
            }
        });

        painelCor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        painelCor.setName("painelCor"); // NOI18N
        painelCor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                painelCorMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout painelCorLayout = new javax.swing.GroupLayout(painelCor);
        painelCor.setLayout(painelCorLayout);
        painelCorLayout.setHorizontalGroup(
            painelCorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 138, Short.MAX_VALUE)
        );
        painelCorLayout.setVerticalGroup(
            painelCorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtIdentificador, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtNLados, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(192, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(painelCor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdentificador, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNLados, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelCor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelCor.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void insere(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insere
        int nLados = 3,identificador;
        if(!txtNLados.getText().equals("")){ nLados = Integer.parseInt(txtNLados.getText()); }
        if(!txtIdentificador.getText().equals("")){ identificador = Integer.parseInt(txtNLados.getText()); } else { identificador = new Random().nextInt(Integer.MAX_VALUE); }
        Virus virus = new Virus(painelCor.getBackground(),nLados,identificador);
        sistema.getVirus().add(virus);
        for(int i=0;i<10;i++){
           sistema.adicionaCelula(new Patogeno(sistema,virus));
        }
        this.setVisible(false);
    }//GEN-LAST:event_insere

    private void painelCorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_painelCorMouseClicked
        Color c = JColorChooser.showDialog(null, "Escolha uma cor", Color.BLUE);
        if(c != null){
            painelCor.setBackground(c);
        } //else { insere(evt); }   
    }//GEN-LAST:event_painelCorMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel painelCor;
    private javax.swing.JTextField txtIdentificador;
    private javax.swing.JTextField txtNLados;
    // End of variables declaration//GEN-END:variables
}
