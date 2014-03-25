/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 1, Aufgabe a.
 */
package computergrafik.aufgabe2;

import computergrafik.aufgabe2.TransformationNode;
import computergrafik.framework.Vector3;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse praesentiert einen Knoten auf Szenengraph.
 */
public class Node {
    /**
     * Liste von Vektoren, aus dennen Node besteht.
     */
    protected List<Vector3> vectors;

    /**
     * Liste von Indizes, die Dreiecke beschreibt.
     */
    protected List<Integer> indices;

    /**
     * Transformationsobjekt, das beim Rendering verwendet wird.
     */
    protected List<TransformationNode> transformations = new ArrayList<TransformationNode>();

    /**
     * Material, das beim Rendering verwendet wird.
     */
    protected MaterialNode material;

    /**
     * Konstruktor.
     */
    public Node(){
        this.vectors = new ArrayList<Vector3>();
        this.indices = new ArrayList<Integer>();
    }

    /**
     * Alternativ Konstruktor.
     * @param vectors
     */
    public Node(List<Vector3> vectors){
        this.vectors = vectors;
    }

    public List<Vector3> getVectors() {
        return vectors;
    }

    public void setVectors(List<Vector3> vectors) {
        this.vectors = vectors;
    }

    public List<Integer> getIndices() {
        return indices;
    }

    public void setIndices(List<Integer> indices) {
        this.indices = indices;
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
        if(material != null) {
            material.start(gl);
        }

        // Setze die Einstellungen fuer den Stoff zurueck.
        if(material != null) {
            material.end(gl);
        }
        // Setze die Einstellungen fuer die Transformation zurueck.
        for(TransformationNode trans : transformations){
            trans.end(gl);
        }
    }
}
