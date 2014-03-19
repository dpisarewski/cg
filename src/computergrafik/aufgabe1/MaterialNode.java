package computergrafik.aufgabe1;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.fixedfunc.GLLightingFunc;

/**
 * Created by pisare_d on 19.03.2014.
 */
public class MaterialNode {
    float[] color;
    float[] oldColor;


    public MaterialNode(float r, float g, float b, float alpha){
        color = new float[] {r, g, b, alpha};
    }

    public void start(GL2 gl){
        gl.glGetMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_DIFFUSE, oldColor, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_DIFFUSE, color, 0);
    }

    public void end(GL2 gl){
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_DIFFUSE, oldColor, 0);
    }


}
