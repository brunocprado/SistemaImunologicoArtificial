package ufrrj.bruno.ia.Telas;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_LINES;
import static com.jogamp.opengl.GL.GL_POINTS;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.awt.AWTGLAutoDrawable;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.EventHandler;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import ufrrj.bruno.ia.celulas.Celula;
import ufrrj.bruno.ia.celulas.Posicao;
import ufrrj.bruno.ia.SistemaImunologico;

public class GL implements GLEventListener{

    private static Random gerador = new Random();
    private SistemaImunologico sistema;
    
    public GL(){      
        sistema = new SistemaImunologico();
    }
    
    @Override
    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();
        
        AWTGLAutoDrawable a= (AWTGLAutoDrawable)  drawable;
        a.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                zoom(drawable);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {       
        final GL2 gl = drawable.getGL().getGL2();
        
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        for(Celula celula : sistema.getCelulas()){
            gl.glBegin(GL2.GL_QUADS);
            
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
            gl.glVertex2f(celula.getPosicao().getVx() + 0.01f,celula.getPosicao().getVy());
            gl.glVertex2f(celula.getPosicao().getVx() + 0.01f,celula.getPosicao().getVy() + 0.01f);
            gl.glVertex2f(celula.getPosicao().getVx() ,celula.getPosicao().getVy() + 0.01f); 
            
            gl.glEnd();
        }
    }
    
    public void zoom(GLAutoDrawable drawable){
        final GL2 gl = drawable.getGL().getGL2();
        gl.glScaled(1.1, 1.1, 1);  
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();
        zoom(drawable);
        //gl.glScaled(1.1, 1.1, 1);        //gl.glTranslatef(0.0f, 0.0f, -0.05f);
        //gl.glScalef(0f, 0f, 1f);
    }
    
    public SistemaImunologico getSistema(){
        return sistema;
    }
    
}
