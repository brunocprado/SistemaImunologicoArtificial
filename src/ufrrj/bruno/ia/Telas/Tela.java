
package ufrrj.bruno.ia.Telas;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ufrrj.bruno.ia.Mundo;
import ufrrj.bruno.ia.celulas.Comum;
import ufrrj.bruno.ia.celulas.Invasor;
import ufrrj.bruno.ia.graficos.Fatia;
import ufrrj.bruno.ia.graficos.GraficoPizza;

public class Tela extends javax.swing.JFrame {
    private FPSAnimator fps;
    
    public Tela() {
        initComponents();
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent arg0){
                if(fps.isStarted()){
                    fps.stop();
                }
                System.exit(0);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema Imunológico Artificial");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                redimensiona(evt);
            }
        });

        jMenu1.setText("Novo");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                novo(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        jMenu3.setText("Editar");
        jMenuBar1.add(jMenu3);

        jMenu2.setText("Inserir");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                insere(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        jMenu4.setText("Estatisticas");
        jMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                abreEstatisticas(evt);
            }
        });
        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 784, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    Mundo mundo = null;
    //Visual visual = null;
    Image fundo = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/blood.jpg"));
    private void novo(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_novo
        //MATAR as threads
        if(mundo != null){
           return;
//           for(Celula celula : mundo.getCelulas()){
//                celula.ativa = false;
//                celula = null;
//                //celula.mundo = null;
//            } 
        }
        
        //mundo = new Mundo(getWidth(),getHeight());
        
        //====| Config OpenGL |====//
        final GLProfile glp = GLProfile.get(GLProfile.GL4);
        GLCapabilities cap = new GLCapabilities(glp);
        cap.setDoubleBuffered(true);
        cap.setHardwareAccelerated(true);
        
        GLCanvas canvas = new GLCanvas(cap);
        fps = new FPSAnimator(canvas,60);
        GL gl = new GL();
        canvas.addGLEventListener(gl);
        //==========================//
        this.setLayout(new BorderLayout());
        //jMenuBar1.setB;
        JPanel tela = new JPanel(new BorderLayout());
        this.add(tela,BorderLayout.SOUTH);
        //tela.setSize(getWidth(),getHeight());
        tela.add(canvas);   
        canvas.display();
    }//GEN-LAST:event_novo

    private void redimensiona(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_redimensiona
       // if(visual != null) { visual.setSize(getWidth(),getHeight()); mundo.redimensiona(getWidth(), getHeight());}
    }//GEN-LAST:event_redimensiona

    private void insere(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_insere
        mundo.adicionaCelula(new Invasor(mundo));
    }//GEN-LAST:event_insere

    private void abreEstatisticas(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_abreEstatisticas
        if(mundo == null){ return; }
        JFrame tela = new JFrame("Estatisticas");
        Estatisticas e = new Estatisticas(mundo);
        tela.setSize(300,600);
        e.setSize(tela.getSize());

        GraficoPizza grafico = new GraficoPizza(mundo,new Rectangle(50,40,200,200));
        //grafico.setSize(200,200);
        tela.add(e);
        tela.add(grafico);     
        tela.setVisible(true);
    }//GEN-LAST:event_abreEstatisticas


    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tela().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
}
