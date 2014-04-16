/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 1, Aufgabe b.
 */
package computergrafik.aufgabe3;

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
        addVertex(-halfSize,halfSize,-halfSize);
        addVertex(-halfSize,halfSize,halfSize);
        addVertex(halfSize,halfSize,-halfSize);
        addVertex(halfSize,halfSize,-halfSize);
        addVertex(halfSize,halfSize,halfSize);
        addVertex(-halfSize,halfSize,halfSize);
        addVertex(-halfSize,-halfSize,halfSize);
        addVertex(-halfSize,halfSize,halfSize);
        addVertex(halfSize,halfSize,halfSize);
        addVertex(-halfSize,-halfSize,halfSize);
        addVertex(halfSize,halfSize,halfSize);
        addVertex(halfSize,-halfSize,halfSize);
        addVertex(-halfSize,-halfSize,halfSize);
        addVertex(-halfSize,-halfSize,-halfSize);
        addVertex(halfSize,-halfSize,-halfSize);
        addVertex(-halfSize,-halfSize,halfSize);
        addVertex(halfSize,-halfSize,-halfSize);
        addVertex(halfSize,-halfSize,halfSize);
        addVertex(-halfSize,-halfSize,-halfSize);
        addVertex(-halfSize,halfSize,-halfSize);
        addVertex(halfSize,halfSize,-halfSize);
        addVertex(-halfSize,-halfSize,-halfSize);
        addVertex(halfSize,halfSize,-halfSize);
        addVertex(halfSize,-halfSize,-halfSize);
        addVertex(halfSize,-halfSize,halfSize);
        addVertex(halfSize,halfSize,halfSize);
        addVertex(halfSize,halfSize,-halfSize);
        addVertex(halfSize,-halfSize,halfSize);
        addVertex(halfSize,halfSize,-halfSize);
        addVertex(halfSize,-halfSize,-halfSize);
        addVertex(-halfSize,-halfSize,halfSize);
        addVertex(-halfSize,halfSize,halfSize);
        addVertex(-halfSize,halfSize,-halfSize);
        addVertex(-halfSize,-halfSize,halfSize);
        addVertex(-halfSize,halfSize,-halfSize);
        addVertex(-halfSize,-halfSize,-halfSize);
    }

}
