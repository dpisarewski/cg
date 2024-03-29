package computergrafik.aufgabe7;

/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 3
 */

import computergrafik.framework.Vector3;

import java.util.ArrayList;
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
     * Indizes der Texturcoordinaten
     */
    private int[] textureIndices;

    /**
     * Normale des Dreiecks
     */
    private Vector3 normal;

    public Triangle(){
        this.indices = new int[]{0, 1, 2};
    }

    public Triangle(int[] indices){
        this.indices = indices;
    }

    public Vector3 getNormal() {
        return normal;
    }

    public void setNormal(Vector3 normal) {
        this.normal = normal;
    }

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public int[] getTextureIndices() {
        return textureIndices;
    }

    public void setTextureIndices(int[] textureIndices) {
        this.textureIndices = textureIndices;
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
        normal = getOrthogonal(vertices).getNormalized();
    }

    /**
     * Berechnet Fläche des Dreiecks
     * @param vertices
     * @return Fläche
     */
    public double getArea(List<Vertex> vertices){
        return getOrthogonal(vertices).getNorm() / 2;
    }

    /**
     * Berechnet einen orthogonalen Vector zur Fläche des Dreiecks
     * @param vertices
     * @return
     */
    private Vector3 getOrthogonal(List<Vertex> vertices){
        // Berechnet 2 Richtungsvektoren des Dreiecks
        Vector3 posVec1 = getVertices(vertices).get(0).getPosition();
        Vector3 posVec2 = getVertices(vertices).get(1).getPosition();
        Vector3 posVec3 = getVertices(vertices).get(2).getPosition();
        Vector3 vec1 = posVec2.subtract(posVec1);
        Vector3 vec2 = posVec3.subtract(posVec1);
        // Berechnet ortogonalen Vektor zur Dreiecksfläche
        return vec1.cross(vec2);
    }
}
