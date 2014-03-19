/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * Lecture demo program.
 */
package computergrafik.aufgabe1;

import computergrafik.framework.ComputergrafikFrame;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 * Application for the first exercise.
 * 
 * @author Philipp Jenke
 * 
 */
public class Aufgabe1Frame extends ComputergrafikFrame {

    /**
     * Constructor.
     */
    public Aufgabe1Frame(int timerInverval) {
        super(timerInverval);
    }

    /*
     * (nicht-Javadoc)
     * 
     * @see
     * computergrafik.framework.IRenderFrame#drawGlContent(javax.media.opengl
     * .GL)
     */
    @Override
    public void drawGlContent(GL2 gl) {
        setGlMaterial(gl, 0.75f, 0.25f, 0.25f);

        gl.glColor3f(0.75f, 0.25f, 0.25f);
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(-0.5f, -0.5f, 0);
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(0.5f, -0.5f, 0);
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(0, 0.5f, 0);
        gl.glEnd();
    }

    /*
     * (nicht-Javadoc)
     * 
     * @see computergrafik.framework.ComputergrafikFrame#timerTick()
     */
    @Override
    protected void timerTick() {
        // System.out.println("Tick");
    }

    /**
     * Program entry point.
     */
    public static void main(String[] args) {
        new Aufgabe1Frame(1000);
    }
}
