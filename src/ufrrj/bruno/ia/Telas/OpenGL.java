package ufrrj.bruno.ia.Telas;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.color.ColorSpace;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.ImageObserver;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.RenderableImage;
import java.beans.EventHandler;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.text.AttributedCharacterIterator;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import ufrrj.bruno.ia.celulas.Celula;
import ufrrj.bruno.ia.celulas.Posicao;
import ufrrj.bruno.ia.SistemaImunologico;

public class OpenGL implements GLEventListener{

    private static Random gerador = new Random();
    private SistemaImunologico sistema;
    BufferedImage bufferedImage = null;

    public OpenGL(SistemaImunologico sistema){      
        this.sistema = sistema;
        
        try {
            bufferedImage = ImageIO.read(getClass().getResource("/img/blood.jpg")); //The menu background
            //w=30;h=30;
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void desenhaImg(GL2 gl,BufferedImage bufferedImage){
        gl.glBlendFunc (GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA); 
        gl.glEnable (GL2.GL_BLEND);
    
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
    
        WritableRaster raster = 
                Raster.createInterleavedRaster (DataBuffer.TYPE_BYTE,
                        w,
                        h,
                        4,
                        null);
        ComponentColorModel colorModel=
                new ComponentColorModel (ColorSpace.getInstance(ColorSpace.CS_sRGB),
                        new int[] {8,8,8,8},
                        true,
                        false,
                        ComponentColorModel.TRANSLUCENT,
                        DataBuffer.TYPE_BYTE);
        BufferedImage img = 
                new BufferedImage (colorModel,
                        raster,
                        false,
                        null);

        java.awt.Graphics2D g = img.createGraphics();
        g.drawImage(bufferedImage, null, null);

        DataBufferByte imgBuf =
                (DataBufferByte)raster.getDataBuffer();
        byte[] imgRGBA = imgBuf.getData();
        ByteBuffer bb = ByteBuffer.wrap(imgRGBA);
        bb.position(0);
        bb.mark();

        gl.glBindTexture(GL2.GL_TEXTURE_2D, 13);
        gl.glPixelStorei(GL2.GL_UNPACK_ALIGNMENT, 1);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
        gl.glTexImage2D (GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, w, h, 0, GL2.GL_RGBA, 
                GL2.GL_UNSIGNED_BYTE, bb);

        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glBindTexture (GL2.GL_TEXTURE_2D, 13);
        gl.glBegin (GL2.GL_POLYGON);
        gl.glTexCoord2d (0, 0);
        gl.glVertex2d (0, 0);
        gl.glTexCoord2d(1,0);
        gl.glVertex2d (w, 0);
        gl.glTexCoord2d(1,1);
        gl.glVertex2d (w, h);
        gl.glTexCoord2d(0,1);
        gl.glVertex2d (0, h);
        gl.glEnd (); 
        
        gl.glDisable(GL2.GL_BLEND);
        
        
       // gl.glFlush();
    }
    
    @Override
    public void display(GLAutoDrawable drawable) {       
        final GL2 gl = drawable.getGL().getGL2();
//        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
//        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//  
        
//        gl.glMatrixMode(GL_PROJECTION);

        //gl.glOrtho(0, 300, 300, 0, 0, 1);
//        gl.glMatrixMode(GL_MODELVIEW);
//        gl.glDisable(GL_DEPTH_TEST);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 
        gl.glClear(GL_COLOR_BUFFER_BIT |  GL_DEPTH_BUFFER_BIT);
        
        //desenhaImg(gl,bufferedImage);
        
        
        
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
