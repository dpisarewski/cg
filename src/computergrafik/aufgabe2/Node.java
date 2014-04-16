/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 1, Aufgabe a.
 */
package computergrafik.aufgabe2;

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
    protected List<Vertex> vertices = new ArrayList<Vertex>();

    /**
     * Liste von Indizes, die Dreiecke beschreibt.
     */
    protected List<Integer> indices = new ArrayList<Integer>();

    /**
     * Liste von Nodes, aus dennen Node besteht.
     */
    protected List<Node> children = new ArrayList<Node>();

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
    }

    /**
     * Alternativ Konstruktor.
     * @param vertices
     */
    public Node(List<Vertex> vertices){
        this.vertices = vertices;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public List<Integer> getIndices() {
        return indices;
    }

    public void setIndices(List<Integer> indices) {
        this.indices = indices;
    }

    /**
     * Fuegt einen Vector fuer Knoten (Figur) hin.
     * @param vector: Objekt von Type Vertex.
     */
    public void addVertex(float x, float y, float z){
        vertices.add(new Vertex(x, y, z));
        indices.add(vertices.size() - 1);
    }

    /**
     * Fuegt einen Kindknoten hin.
     * @param node: Jbjekt von Type Node.
     */
    public void addNode(Node node){
        children.add(node);
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

        render(gl);

        // Setze die Einstellungen fuer den Stoff zurueck.
        if(material != null) {
            material.end(gl);
        }
        // Setze die Einstellungen fuer die Transformation zurueck.
        for(TransformationNode trans : transformations){
            trans.end(gl);
        }
    }

    protected void render(GL2 gl){
        for(Node child : children){
            child.draw(gl);
        }
    }

    public void setupRendering(){
        addDataToRenderer();

        for(Node child: children){
            child.addDataToRenderer();
        }
    }

    protected void addDataToRenderer(){
    }

    public void calculateNormals(){
        for(Node child : children){
            child.calculateNormals();
        }
    }
}
