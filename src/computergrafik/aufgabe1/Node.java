/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 1, Aufgabe a.
 */
package computergrafik.aufgabe1;

import computergrafik.framework.Vector3;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse praesentiert einen Knoten auf Szenengraph.
 */
public class Node {
    List<Vector3> vectors;
    List<Node> children = new ArrayList<Node>();
    List<TransformationNode> transformations = new ArrayList<TransformationNode>();
    MaterialNode material;

    /**
     * Konstruktor.
     */
    public Node(){
        this.vectors = new ArrayList<Vector3>();
    }

    /**
     * Alternativ Konstruktor.
     * @param vectors
     */
    public Node(List<Vector3> vectors){
        this.vectors = vectors;
    }

    /**
     * Fuegt einen Kindknoten hin.
     * @param node: Jbjekt von Type Node.
     */
    public void addNode(Node node){
        children.add(node);
    }

    /**
     * Fuegt einen Vector fuer Knoten (Figur) hin.
     * @param vector: Objekt von Type Vector3.
     */
    public void addVector(Vector3 vector){
        vectors.add(vector);
    }

    /**
     * Die Methode wird verwendet falls eine Transformation noetig (gewuenscht) ist.
     * @param trans: Type und Parametern fuer Transformation.
     */
    public void addTransformation(TransformationNode trans){
        transformations.add(trans);
    }

    /**
     * Die Methode aendert den Stoff fuer Knoten.
     * @param material: Parameter fuer Material.
     */
    public void setMaterial(MaterialNode material){
        this.material = material;
    }

    /**
     * Die Methode prueft ob fuer den Objekt die Transformationen oder Stoffaenderungen
     * vorgenommen werden sollen und startet die Rendering.
     * @param gl: Objekt.
     */
    public void draw(GL2 gl){
    	// Falls die Transformationen vorgenommen werden sollen, wird das gemacht.
        for(TransformationNode trans : transformations){
            trans.start(gl);
        }
        // Muss den Stoff geaendert werden?
        if(material != null) material.start(gl);
        renderVectors(gl);
        for(Node child: children){
            child.draw(gl);
        }
        // Setze die Einstellungen fuer den Stoff zurueck.
        if(material != null) material.end(gl);
        // Setze die Einstellungen fuer die Transformation zurueck.
        for(TransformationNode trans : transformations){
            trans.end(gl);
        }
    }

    /**
     * Die Methode bildet aus Vectoren (Punkten) die Dreiecke (Jede 3 Punkten fuer
     * einen Dreieck) und fuegt die als Kindknoten ein.
     * @param vectors: List mit Vectoren.
     */
    public void makeNodes(List<Vector3> vectors){
        for(int i = 0; i < vectors.size(); i += 3){
            addNode(new Node(vectors.subList(i, i + 3)));
        }
    }

    /**
     * Die Methode visualisiert den Objekt. Zuerst wird ein Normalvektor zur Flaeche
     * gerechnet.
     * @param gl: Objekt.
     */
    protected void renderVectors(GL2 gl){
        if(!vectors.isEmpty()){
            Vector3 vec1 = vectors.get(0).subtract(vectors.get(1));
            Vector3 vec2 = vectors.get(0).subtract(vectors.get(2));
            Vector3 ortogonal = vec1.cross(vec2);
            gl.glNormal3f(ortogonal.floatData()[0], ortogonal.floatData()[1], ortogonal.floatData()[2]);
            gl.glBegin(GL.GL_TRIANGLES);
            for(Vector3 vector : vectors){
                gl.glVertex3f(vector.floatData()[0], vector.floatData()[1], vector.floatData()[2]);
            }
            gl.glEnd();
        }
    }
}
