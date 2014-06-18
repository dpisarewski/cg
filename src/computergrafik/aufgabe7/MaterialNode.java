/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 3
 */
package computergrafik.aufgabe7;

import javax.media.opengl.GL2;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import java.nio.FloatBuffer;

/**
 * Die Klasse MaterialNode dient fuer die Aenderungen von Farben und Stoff.
 */
public class MaterialNode extends Node {
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
        gl.glGetMaterialfv(GL2.GL_FRONT_AND_BACK, GLLightingFunc.GL_DIFFUSE, oldMaterial);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GLLightingFunc.GL_DIFFUSE, color, 0);
    }

    /**
     * Die Methode stellt alte Einstellungen fuer Farbe zurueck.
     * @param gl: Objekt, fuer den die Aenderungen vorgenommen werden sollen.
     */
    public void end(GL2 gl){
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GLLightingFunc.GL_DIFFUSE, oldMaterial);
    }

    /**
     * Wendet Material an und startet Rendering von Kindknoten.
     * @param gl: Objekt.
     */
    public void draw(GL2 gl){
        start(gl);
        render(gl);
        end(gl);
    }
}
