package computergrafik.aufgabe1;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import java.nio.FloatBuffer;

/**
 * Created by pisare_d on 19.03.2014.
 */
public class MaterialNode {
    float[] color;
    FloatBuffer oldMaterial;

    public MaterialNode(float r, float g, float b, float alpha){
        this.color = new float[] {r, g, b, alpha};
        this.oldMaterial = FloatBuffer.allocate(17);
    }

    public void start(GL2 gl){
        gl.glGetMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_DIFFUSE, oldMaterial);

        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_DIFFUSE, color, 0);
        float specularColor[] = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
        // Use smaller values when using multiple light sources!
        float ambientColor[] = new float[] { 0.2f * color[0], 0.2f * color[1], 0.2f * color[2], 1.0f };
        float shininess[] = { 20.0f };
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_AMBIENT, ambientColor, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_SPECULAR, specularColor, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_SHININESS, shininess, 0);
    }

    public void end(GL2 gl){
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_DIFFUSE, oldMaterial);
    }


}
