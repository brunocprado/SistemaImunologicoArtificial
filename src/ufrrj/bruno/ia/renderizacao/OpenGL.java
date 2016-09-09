package ufrrj.bruno.ia.renderizacao;

import com.jogamp.common.nio.Buffers;
import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLException;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.awt.Color;
import java.awt.Composite;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.text.AttributedCharacterIterator;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import ufrrj.bruno.ia.celulas.Celula;
import ufrrj.bruno.ia.celulas.Posicao;
import ufrrj.bruno.ia.SistemaImunologico;

public class OpenGL implements GLEventListener{

    private static Random gerador = new Random();
    private SistemaImunologico sistema;

    public OpenGL(SistemaImunologico sistema){      
        this.sistema = sistema;
    }
    
    @Override
    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();         
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    public void desenhaImg(GL2 gl,Texture mTest){
        
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_ONE, GL2.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);

        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glLoadIdentity();

        final float[] coordData = {
                0, 0, //
                0, 1, //
                1, 0, //
                1, 1,
        };
        final float[] vertices = {
                0.0f, 0.0f, 0, // Left Bottom
                0.0f, 1f, 0, // Left Top
                1f, 0.0f, 0, // Right Bottom
                1f, 1f, 0
        };
        // Setup the vertices into the buffer
        FloatBuffer verts = Buffers.newDirectFloatBuffer(vertices.length);
        verts.put(vertices).position(0);

        // Setup the texture coordinates
        FloatBuffer coords = Buffers.newDirectFloatBuffer(coordData.length);
        coords.put(coordData).position(0);

        mTest.enable(gl);
        mTest.bind(gl);
        gl.glLoadIdentity();
        gl.glTranslatef(100, 100, 0);
        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, verts);
        gl.glTexCoordPointer(2, GL2.GL_FLOAT, 0, coords);
        gl.glDrawArrays(GL2.GL_TRIANGLE_STRIP, 0, 4);
        
        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
    }
    
    @Override
    public void display(GLAutoDrawable drawable) {       
        final GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 
        gl.glClear(GL_COLOR_BUFFER_BIT |  GL_DEPTH_BUFFER_BIT);
        
        //desenhaImg(gl,a);

        for(Celula celula : sistema.getCelulas()){
            gl.glBegin(GL2.GL_QUADS);
            
            switch(celula.getTipo()){
                case Comum :
                    gl.glColor3f(1f, 1f, 1f);
                    break;
                case Patogeno :
                    gl.glColor3f(1f, 0, 0);
                    break;
                case Linfocito :
                    gl.glColor3f(0, 0, 1f);
                    break;
                case Macrofago :
                    gl.glColor3f(1f, 1f, 0);
                    break;
                case Neutrofilo :
                    gl.glColor3f(1f, 0, 1f);
                    break;
            }
            
            gl.glVertex2f(celula.getPosicao().getVx(),celula.getPosicao().getVy());
            gl.glVertex2f(celula.getPosicao().getVx() + 0.01f,celula.getPosicao().getVy());
            gl.glVertex2f(celula.getPosicao().getVx() + 0.01f,celula.getPosicao().getVy() + 0.01f);
            gl.glVertex2f(celula.getPosicao().getVx() ,celula.getPosicao().getVy() + 0.01f); 
            
            gl.glEnd();
        }
        gl.glFlush();
    }
    
    public void zoom(GLAutoDrawable drawable){
        final GL2 gl = drawable.getGL().getGL2();
        gl.glScaled(1.1, 1.1, 1);  
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();
        
        //zoom(drawable);
        //gl.glScaled(1.1, 1.1, 1);        //gl.glTranslatef(0.0f, 0.0f, -0.05f);
        //gl.glScalef(0f, 0f, 1f);
    }
    
    public SistemaImunologico getSistema(){
        return sistema;
    }
    
}
