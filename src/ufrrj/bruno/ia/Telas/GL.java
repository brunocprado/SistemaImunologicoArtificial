package ufrrj.bruno.ia.Telas;

import static com.jogamp.opengl.GL.GL_LINES;
import static com.jogamp.opengl.GL.GL_POINTS;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import ufrrj.bruno.ia.Posicao;

public class GL implements GLEventListener{
    private static final int TAMANHO = 2000;
    private Posicao[] pa = new Posicao[TAMANHO];
    private static Random gerador = new Random();
    
    public GL(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(screenSize);
        //1312x738
        
        for(int i=0;i<TAMANHO;i++){
            pa[i] = new Posicao(
            gerador.nextInt(1312),gerador.nextInt(738),1312,738);
        }
    }
    
    @Override
    public void init(GLAutoDrawable drawable) {
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {       
        final GL2 gl = drawable.getGL().getGL2();
      
//        try {
//            File bg = new File("img/blood.jpg");
//            Texture t = TextureIO.newTexture(bg, true);
//        } catch (IOException ex) {
//            Logger.getLogger(GL.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (GLException ex) {
//            Logger.getLogger(GL.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        gl.glBegin(GL2.GL_QUADS);
//        
//        gl.glTexCoord2f(0f, 0f); gl.glVertex2f(0f, 0f);
//        gl.glTexCoord2f(0f, 1f); gl.glVertex2f(0f, 1f);
//        gl.glTexCoord2f(1f, 1f); gl.glVertex2f(1f, 1f);
//        gl.glTexCoord2f(1f, 0f); gl.glVertex2f(1f, 0f);
//        gl.glEnd();
        
        for(int i =0;i<TAMANHO;i++){
            gl.glBegin(GL2.GL_POINTS);

            gl.glColor3f(gerador.nextFloat(), gerador.nextFloat(), 0);
            gl.glVertex2f(pa[i].getVx(),pa[i].getVy());

            gl.glEnd();
        }
        //System.out.println(pa[0].getVx());
      
        //System.out.println("a");
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    
}
