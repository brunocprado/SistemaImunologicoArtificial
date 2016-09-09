package ufrrj.bruno.ia;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import ufrrj.bruno.ia.renderizacao.Grafico2D;
import ufrrj.bruno.ia.renderizacao.OpenGL;
import ufrrj.bruno.ia.Telas.Janela;

public class Main{

    public static void main(String[] args) {

        SistemaImunologico sistema = new SistemaImunologico();
        
        Janela tela = new Janela("SIA",sistema);
        tela.setSize(Parametros.LARGURA,Parametros.ALTURA);
        
        System.out.println("Renderizando com " + Parametros.RENDERIZAR_COM);
        
        if(Parametros.RENDERIZAR_COM == Parametros.RENDER.OpenGL){       
            //====| Config OpenGL |====//
            final GLProfile glp = GLProfile.get(GLProfile.GL2);
            GLCapabilities cap = new GLCapabilities(glp);
            cap.setDoubleBuffered(true);
            cap.setHardwareAccelerated(true);

            GLCanvas canvas = new GLCanvas(cap);
            FPSAnimator fps = new FPSAnimator(canvas,Parametros.LIMITE_FPS);
            OpenGL gl = new OpenGL(sistema);
            canvas.addGLEventListener(gl);
            //==========================//

            tela.setOpenGLCanvas(canvas);
            tela.setGL(gl,fps);
            
            fps.start();
        } else {
            Grafico2D grafico = new Grafico2D(sistema);
            tela.getContentPane().add(grafico);
        }
        
        tela.setVisivel(true);
        
    }
}
