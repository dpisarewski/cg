/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 1, Aufgabe b.
 */
package computergrafik.aufgabe2;

import computergrafik.framework.Vector3;

import java.util.ArrayList;
import java.util.List;

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
        float x = 0f, y = 0f, z = 0f;
        float step = (360f / sections);

        for(float i = 0f; i < 360; i += step){
            x = (float) Math.cos(Math.toRadians(i)) * radius;
            // Die Dreiecken haben die Spitzen oben oder unten.
            y = ((i / step) % 2) == 0 ? width : -width;
            z = (float) Math.sin(Math.toRadians(i)) * radius;
            vectors.add(new Vector3(x, y, z));
        }

        for(int i = 0; i < vectors.size() - 2; i++){
            indices.add(i);
            indices.add(i + 1);
            indices.add(i + 2);
        }

        indices.add(vectors.size() - 2);
        indices.add(vectors.size() - 1);
        indices.add(0);

        indices.add(vectors.size() - 1);
        indices.add(0);
        indices.add(1);

        // Die Dreiecken in draw werden aus drei nacheinander folgenden Punkten gebildet.
        // Die Punkten werden aber mehrmals benutzt, da die Nachbarndreiecke gemeinsame
        // Punkte haben. Bei einem Durchlauf durch die Punkte wird nur jede 3. Dreieck
        // aus ganze Reihe gebildet. Deswegen werden alle Punkte 2 Mal mit der
        // Verschiebung 1 kopiert. So wird vollstaendigen Ring erreicht.
        List<Vector3> tmpVectors = new ArrayList<Vector3>();
        tmpVectors.addAll(vectors.subList(1, vectors.size() - 1));
        tmpVectors.add(vectors.get(vectors.size() - 1));
        tmpVectors.addAll(vectors.subList(0, 1));

        tmpVectors.addAll(vectors.subList(2, vectors.size() - 1));
        tmpVectors.add(vectors.get(vectors.size() - 1));
        tmpVectors.addAll(vectors.subList(0, 2));

        List<Integer> tmpIndices = new ArrayList<Integer>();
        tmpIndices.addAll(indices.subList(1, indices.size() - 1));
        tmpIndices.add(indices.get(indices.size() - 1));
        tmpIndices.addAll(indices.subList(0, 1));

        tmpIndices.addAll(indices.subList(2, indices.size() - 1));
        tmpIndices.add(indices.get(indices.size() - 1));
        tmpIndices.addAll(indices.subList(0, 2));

        vectors.addAll(tmpVectors);
        indices.addAll(tmpIndices);
    }
}
