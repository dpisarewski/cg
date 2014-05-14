/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 3
 */
package computergrafik.aufgabe5;

import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse praesentiert einen Knoten auf Szenengraph.
 */
public class Node {
    /**
     * Liste von Nodes, aus dennen Node besteht.
     */
    protected List<Node> children = new ArrayList<Node>();

    /**
     * Fuegt einen Kindknoten hin.
     * @param node: Jbjekt von Type Node.
     */
    public void addNode(Node node){
        children.add(node);
    }


    /**
     * Zeichnet alle Kindknoten
     * @param gl Objekt GL
     */
    protected void render(GL2 gl){
        for(Node child : children){
            child.draw(gl);
        }
    }

    public void draw(GL2 gl){
        render(gl);
    }

    /**
     * Übergibt die Daten dem Renderer
     */
    public void setupRendering(){
        addDataToRenderer();

        for(Node child: children){
            child.setupRendering();
        }
    }

    /**
     * Hook Methode
     */
    protected void addDataToRenderer(){
    }

    /**
     * Rechnet die Normalen
     */
    public void calculateNormals(){
        for(Node child : children){
            child.calculateNormals();
        }
    }
}
