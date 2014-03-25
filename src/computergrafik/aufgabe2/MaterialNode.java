/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 1, Aufgabe d.
 */
package computergrafik.aufgabe2;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import java.nio.FloatBuffer;

/**
 * Die Klasse MaterialNode dient fuer die Aenderungen von Farben und Stoff.
 */
public class MaterialNode {
    /**
     * Farbe
     */
    private float[] color;

    /**
     * Früherer Wert von Material
     */
    private FloatBuffer oldMaterial;

    /**
     * Konstruktor
     */
    public MaterialNode(float r, float g, float b, float alpha){
        this.color = new float[] {r, g, b, alpha};
        this.oldMaterial = FloatBuffer.allocate(17);
    }

    /**
     * Die Methode speichert gueltige Einstellungen fuer Farbe, dann aendert die Farbe
     * auf gegebene.
     * @param gl: Objekt, fuer den die Aenderungen vorgenommen werden sollen.
     */
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

    /**
     * Die Methode stellt alte Einstellungen fuer Farbe zurueck.
     * @param gl: Objekt, fuer den die Aenderungen vorgenommen werden sollen.
     */
    public void end(GL2 gl){
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_DIFFUSE, oldMaterial);
    }
}
