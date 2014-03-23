/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 1, Aufgabe c.
 */
package computergrafik.aufgabe1;

import javax.media.opengl.GL2;

/**
 * Die Klasse fuer die Aenderung des Objects: Position und seine Form.
 */
public class TransformationNode {
    public static final int SCALE = 0;
    public static final int TRANSLATION = 1;
    private int type;
    private float a;
    private float b;
    private float c;

    /**
     * Konstruktor
     * @param type: Type der Aenderung (Verschieben oder Form aendern).
     * @param a: Scalierung/Verschiebung in x-Richtung.
     * @param b: Scalierung/Verschiebung in y-Richtung.
     * @param c: Scalierung/Verschiebung in z-Richtung.
     */
    public TransformationNode(int type, float a, float b, float c){
        this.type = type;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Die Methode merkt alte Transformationen und je nach dem type
     * nimmt die noetige aenderung vor.
     * @param gl: Objekt, fuer den die Aenderungen vorgenommen werden sollen.
     */
    public void start(GL2 gl){
        gl.glPushMatrix();

        switch (type){
            case SCALE :
                gl.glScalef(a, b, c);
                break;
            case TRANSLATION :
                gl.glTranslatef(a, b, c);
                break;
        }
    }

    /**
     * Die Methode setzt der Transformationen zurueck.
     * @param gl: Objekt, fuer den die Aenderungen vorgenommen werden sollen.
     */
    public void end(GL2 gl){
        gl.glPopMatrix();
    }
}
