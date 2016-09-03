/*
TODO:




 */
package ufrrj.bruno.ia;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import ufrrj.bruno.ia.Telas.GL;
import ufrrj.bruno.ia.Telas.Janela;

public class Main{
    public static void main(String[] args) {

        //====| Config OpenGL |====//
        final GLProfile glp = GLProfile.get(GLProfile.GL2);
        GLCapabilities cap = new GLCapabilities(glp);
        cap.setDoubleBuffered(true);
        cap.setHardwareAccelerated(true);
        
        GLCanvas canvas = new GLCanvas(cap);
        FPSAnimator fps = new FPSAnimator(canvas,60);
        GL gl = new GL();
        canvas.addGLEventListener(gl);
        //==========================//
        
        Janela tela2 = new Janela("SIA",fps,1312,738);
        tela2.setOpenGLCanvas(canvas);
        
        fps.start();
        tela2.setVisivel(true);
        
        



        
    }
}
