package ufrrj.bruno.ia.Telas;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
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
import ufrrj.bruno.ia.Celula;
import ufrrj.bruno.ia.Mundo;
import ufrrj.bruno.ia.Posicao;

public class GL implements GLEventListener{
    private static final int TAMANHO = 2000;
    private Posicao[] pa = new Posicao[TAMANHO];
    private static Random gerador = new Random();
    private Mundo mundo;
    
    
    public GL(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(screenSize);
        
        mundo = new Mundo();

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
        
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        for(Celula celula : mundo.getCelulas()){
            gl.glBegin(GL2.GL_POINTS);
            switch(celula.getClass().getSimpleName()){
                case "Comum" :
                    gl.glColor3f(1f, 1f, 1f);
                    break;
                case "Invasor" :
                    gl.glColor3f(1f, 0, 0);
                    break;
                case "Linfocito" :
                    gl.glColor3f(0, 0, 1f);
                    break;
                case "Macrofago" :
                    gl.glColor3f(1f, 1f, 0);
                    break;
                case "Neutrofilo" :
                    gl.glColor3f(1f, 0, 1f);
                    break;
            }

            gl.glVertex2f(celula.getPosicao().getVx(),celula.getPosicao().getVy());
            
            gl.glEnd();
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }
    
}
