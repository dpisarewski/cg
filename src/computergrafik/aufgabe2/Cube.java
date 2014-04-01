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
 * Diese Klasse repraesentiert einen Wuerfel.
 *
 */
public class Cube extends Node {
    private int lastIndex = 0;

    /**
     * Generiert Nodes für einen Würfel
     * @param size Größe des Würfels
     */
    public void generateNodes(float size){
        float halfSize = size/2;
        addVector(-halfSize,halfSize,-halfSize);
        addVector(-halfSize,halfSize,halfSize);
        addVector(halfSize,halfSize,-halfSize);
        addVector(halfSize,halfSize,-halfSize);
        addVector(halfSize,halfSize,halfSize);
        addVector(-halfSize,halfSize,halfSize);
        addVector(-halfSize,-halfSize,halfSize);
        addVector(-halfSize,halfSize,halfSize);
        addVector(halfSize,halfSize,halfSize);
        addVector(-halfSize,-halfSize,halfSize);
        addVector(halfSize,halfSize,halfSize);
        addVector(halfSize,-halfSize,halfSize);
        addVector(-halfSize,-halfSize,halfSize);
        addVector(-halfSize,-halfSize,-halfSize);
        addVector(halfSize,-halfSize,-halfSize);
        addVector(-halfSize,-halfSize,halfSize);
        addVector(halfSize,-halfSize,-halfSize);
        addVector(halfSize,-halfSize,halfSize);
        addVector(-halfSize,-halfSize,-halfSize);
        addVector(-halfSize,halfSize,-halfSize);
        addVector(halfSize,halfSize,-halfSize);
        addVector(-halfSize,-halfSize,-halfSize);
        addVector(halfSize,halfSize,-halfSize);
        addVector(halfSize,-halfSize,-halfSize);
        addVector(halfSize,-halfSize,halfSize);
        addVector(halfSize,halfSize,halfSize);
        addVector(halfSize,halfSize,-halfSize);
        addVector(halfSize,-halfSize,halfSize);
        addVector(halfSize,halfSize,-halfSize);
        addVector(halfSize,-halfSize,-halfSize);
        addVector(-halfSize,-halfSize,halfSize);
        addVector(-halfSize,halfSize,halfSize);
        addVector(-halfSize,halfSize,-halfSize);
        addVector(-halfSize,-halfSize,halfSize);
        addVector(-halfSize,halfSize,-halfSize);
        addVector(-halfSize,-halfSize,-halfSize);
    }

    private void addVector(float x, float y, float z){
        vectors.add(new Vector3(x, y, z));
        indices.add(lastIndex);
        lastIndex++;
    }

}
