/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 2.
 */
package computergrafik.aufgabe3;

/**
 * Diese Klasse repraesentiert einen Ring. Der Ring besitzt zwei Parameter: Radius und Dicke.
 */
public class Ring extends Node {
    /**
     * Breite des Rings
     */
    private float width;

    /**
     * Radius des Rings
     */
    private float radius;

    /**
     * Konstruktor
     * @param radius
     * @param width
     */
    public Ring(float radius, float width){
        this.radius = radius;
        this.width  = width;
    }

    /**
     * Die Methode generiert die Bestandteile des Rings (Dreiecke). Je mehr Sections (Dreiecken),
     * desto besser wird die Figur abgerundet. Ganzen Ring (360 Grad) wird durch Sectionen
     * geteilt. So wird gerechnet wieviele Grad pro Dreieck ist.
     * Die Koordinaten werden dann folgendes berechnet:
     * x = cos(alpha) * radius
     * y = width
     * z = sin(alpha) * radius
     * @param sections: Anzahl des Dreiecks
     */
    public void generateNodes(int sections){
        float x1 = 0f, x2 = 0f, z1 = 0f, z2 = 0f;
        float step = (360f / sections);

        for(float i = 0f; i < 360; i += step){
            x1 =    (float) Math.cos(Math.toRadians(i)) * radius;
            x2 =    (float) Math.cos(Math.toRadians(i + step)) * radius;
            z1 =    (float) Math.sin(Math.toRadians(i)) * radius;
            z2 =    (float) Math.sin(Math.toRadians(i + step)) * radius;
            vertices.add(new Vertex(x1, width,  z1));
            vertices.add(new Vertex(x1, -width, z1));
            vertices.add(new Vertex(x2, -width, z2));
            vertices.add(new Vertex(x1, width,  z1));
            vertices.add(new Vertex(x2, -width, z2));
            vertices.add(new Vertex(x2, width,  z2));
        }
        for(int j = 0; j < sections * 6; j++){
            indices.add(j);
        }


    }
}
