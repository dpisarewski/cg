package computergrafik.aufgabe6;

/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 5
 */

/**
 * Klasse für 2D Texturkoordinaten
 */
public class TextureCoordinate {
    /**
     * Texturkoordinate U
     */
    private float u;

    /**
     * Texturkoordinate V
     */
    private float v;

    public TextureCoordinate(float u, float v){
        this.u = u;
        this.v = v;
    }

    public float getU() {
        return u;
    }

    public void setU(float u) {
        this.u = u;
    }

    public float getV() {
        return v;
    }

    public void setV(float v) {
        this.v = v;
    }
}
