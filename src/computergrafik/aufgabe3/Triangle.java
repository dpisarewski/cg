package computergrafik.aufgabe3;

/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 2, Aufgabe a.
 */

import computergrafik.framework.Vector3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Diese Klasse repraesentiert ein Dreieck.
 */
public class Triangle {
    /**
     * Indizes der Eckpunkte
     */
    private int[] indices;

    /**
     * Normale des Dreiecks
     */
    private Vector3 normal;

    /**
     * Konstruktor.
     */
    public Triangle(){
        this.indices = new int[]{0, 1, 2};
    }

    public Triangle(int[] indices){
        this.indices = indices;
    }

    /**
     * Getter
     */
    public Vector3 getNormal() {
        return normal;
    }

    /**
     * Setter
     */
    public void setNormal(Vector3 normal) {
        this.normal = normal;
    }

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    /**
     * Gibt ein Liste der Eckpunkten mit gespeicherte Indizes zurück
     * @return Liste der Eckpunnkte
     */
    public List<Vertex> getVertices(List<Vertex> vertices){
        List<Vertex> list = new ArrayList<Vertex>();

        for(int index: indices){
            list.add(vertices.get(index));
        }

        return list;
    }

    /**
     * Berechnet eine Normale für den Dreieck.
     * @return kalkulierte Normale
     */
    public void calculateNormal(List<Vertex> vertices){
        // Berechnet 2 Richtungsvektoren des Dreiecks
        Vector3 posVec1 = getVertices(vertices).get(0).getPosition();
        Vector3 posVec2 = getVertices(vertices).get(1).getPosition();
        Vector3 posVec3 = getVertices(vertices).get(2).getPosition();
        Vector3 vec1 = posVec2.subtract(posVec1);
        Vector3 vec2 = posVec3.subtract(posVec1);
        // Berechnet ortogonalen Vektor zur Dreiecksfläche
        normal = vec1.cross(vec2).getNormalized();
    }
}
