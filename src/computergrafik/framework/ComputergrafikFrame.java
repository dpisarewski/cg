/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * Lecture demo program.
 */
package computergrafik.framework;

import java.util.Timer;
import java.util.TimerTask;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.fixedfunc.GLLightingFunc;

/**
 * Central frame for all applications - derive from this class.
 * 
 * @author Philipp Jenke
 * 
 */
public abstract class ComputergrafikFrame extends TimerTask {

    /**
     * 3D view object.
     */
    private final View3D view;

    /**
     * Timer object to create a game loop.
     */
    Timer timer = new Timer();

    /**
     * Constructor
     */
    public ComputergrafikFrame(int timerInterval) {
        view = new View3D(this);
        timer.schedule(this, timerInterval, timerInterval);
    }

    /**
     * Draw the GL content here.
     */
    public abstract void drawGlContent(GL2 gl);

    /**
     * Getter.
     */
    protected Camera getCamera() {
        return view.getRenderer().getCamera();
    }

    @Override
    public void run() {
        // Timer event - call callback method.
        timerTick();
    }

    /**
     * This callback method is called with each timer event - game loop.
     */
    protected abstract void timerTick();

    /**
     * Set the material color of a material.
     * 
     * @param r Red color channel.
     * @param g Green color channel.
     * @param b Blue color channel.
     */
    protected void setGlMaterial(GL2 gl, float r, float g, float b) {
        // Use color material
        float diffuseColor[] = new float[] { r, g, b, 1.0f };
        float specularColor[] = new float[] { 0.3f, 0.3f, 0.3f, 1.0f };
        // Use smaller values when using multiple light sources!
        float ambientColor[] = new float[] { 0.3f * r, 0.3f * g, 0.3f * b, 1.0f };
        float shininess[] = { 100.0f };
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_DIFFUSE,
                diffuseColor, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_AMBIENT,
                ambientColor, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_SPECULAR,
                specularColor, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_SHININESS,
                shininess, 0);
    }
}
