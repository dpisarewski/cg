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
    /**
     * Generiert Nodes für einen Würfel
     * @param size Größe des Würfels
     */
    public void generateNodes(float size){
        float halfSize = size/2;
        List<Vector3> vectors = new ArrayList<Vector3>();

        vectors.add(new Vector3(-halfSize,halfSize,-halfSize));
        vectors.add(new Vector3(-halfSize,halfSize,halfSize));
        vectors.add(new Vector3(halfSize,halfSize,-halfSize));
        vectors.add(new Vector3(halfSize,halfSize,-halfSize));
        vectors.add(new Vector3(halfSize,halfSize,halfSize));
        vectors.add(new Vector3(-halfSize,halfSize,halfSize));
        vectors.add(new Vector3(-halfSize,-halfSize,halfSize));
        vectors.add(new Vector3(-halfSize,halfSize,halfSize));
        vectors.add(new Vector3(halfSize,halfSize,halfSize));
        vectors.add(new Vector3(-halfSize,-halfSize,halfSize));
        vectors.add(new Vector3(halfSize,halfSize,halfSize));
        vectors.add(new Vector3(halfSize,-halfSize,halfSize));
        vectors.add(new Vector3(-halfSize,-halfSize,halfSize));
        vectors.add(new Vector3(-halfSize,-halfSize,-halfSize));
        vectors.add(new Vector3(halfSize,-halfSize,-halfSize));
        vectors.add(new Vector3(-halfSize,-halfSize,halfSize));
        vectors.add(new Vector3(halfSize,-halfSize,-halfSize));
        vectors.add(new Vector3(halfSize,-halfSize,halfSize));
        vectors.add(new Vector3(-halfSize,-halfSize,-halfSize));
        vectors.add(new Vector3(-halfSize,halfSize,-halfSize));
        vectors.add(new Vector3(halfSize,halfSize,-halfSize));
        vectors.add(new Vector3(-halfSize,-halfSize,-halfSize));
        vectors.add(new Vector3(halfSize,halfSize,-halfSize));
        vectors.add(new Vector3(halfSize,-halfSize,-halfSize));
        vectors.add(new Vector3(halfSize,-halfSize,halfSize));
        vectors.add(new Vector3(halfSize,halfSize,halfSize));
        vectors.add(new Vector3(halfSize,halfSize,-halfSize));
        vectors.add(new Vector3(halfSize,-halfSize,halfSize));
        vectors.add(new Vector3(halfSize,halfSize,-halfSize));
        vectors.add(new Vector3(halfSize,-halfSize,-halfSize));
        vectors.add(new Vector3(-halfSize,-halfSize,halfSize));
        vectors.add(new Vector3(-halfSize,halfSize,halfSize));
        vectors.add(new Vector3(-halfSize,halfSize,-halfSize));
        vectors.add(new Vector3(-halfSize,-halfSize,halfSize));
        vectors.add(new Vector3(-halfSize,halfSize,-halfSize));
        vectors.add(new Vector3(-halfSize,-halfSize,-halfSize));
    }

}
