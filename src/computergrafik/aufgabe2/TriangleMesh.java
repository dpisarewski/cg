package computergrafik.aufgabe2;

/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 2, Aufgabe a.
 */

import computergrafik.framework.Vector3;

import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse repraesentiert ein Dreiecksnetz.
 */
public class TriangleMesh extends Node{
    /**
     * Liste von Dreiecken.
     */
    private List<Triangle> triangles = new ArrayList<Triangle>();

    /**
     * Liste von Punkten.
     */
    private List<Vertex> vertices;

    private VertexArrayRenderer renderer = new VertexArrayRenderer();

    /**
     * Konstruktor.
     */
    public TriangleMesh(){
    }

    /**
     * Getter
     */
    public List<Triangle> getTriangles() {
        return triangles;
    }

    /**
     * Setter
     */
    public void setTriangles(List<Triangle> triangles) {
        this.triangles = triangles;
    }

    /**
     * Getter
     */
    public List<Vertex> getVertices() {
        return vertices;
    }

    /**
     * setter
     */
    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public VertexArrayRenderer getRenderer() {
        return renderer;
    }

    public void setRenderer(VertexArrayRenderer renderer) {
        this.renderer = renderer;
    }

    /**
     * Ergaenzt den Vektor in die Liste.
     * @param vertex
     */
    public void addVertex(Vertex vertex){
        vertices.add(vertex);
    }

    public void addIndices(Integer index){
        indices.add(index);
    }

    /**
     * Ergaenzt den Dreieck in die Liste.
     * @param triangle
     */
    public void addTriangle(Triangle triangle){
        triangles.add(triangle);
    }

    /**
     * Die Methode generiert die Dreiecken aus 3 Punkten.
     * @param vertices
     * @param indices

    public void generateStructure(List<Vertex> vertices, List<Integer> indices){
        this.vertices = vertices;
        this.indices  = indices;
        for(int i = 0; i < indices.size(); i += 3){
            int[] incides = new int[]{indices.get(i), indices.get(i + 1), indices.get(i + 2)};
            addTriangle(new Triangle(incides));
        }
    }*/

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

    public void generateStructure(List<Vertex> newVertices, List<Integer> indices){
        if (this.vertices != null) {
            for (Vertex vert : newVertices) {
                this.vertices.add(vert);
            }
        }else{
            this.vertices = newVertices;
        }

        if(this.indices != null) {
            for (Integer ind : indices) {
                this.indices.add(ind);
            }
        }else{
            this.indices = indices;
        }
        for(int i = 0; i < indices.size(); i += 3){
            int[] incides = new int[]{indices.get(i), indices.get(i + 1), indices.get(i + 2)};
            addTriangle(new Triangle(incides));
        }
    }

    /**
     * Die Methode ruft die Erzeugung von Dreiecken auf.
     * @param node:
     */
    public void generateStructure(Node node){
        generateStructure(verticesFromVectors(node.getVectors()), node.getIndices());
    }

    /**
     * Getter
     */
    public List<Integer> getIndices(){
        return indices;
    }

    /**
     * Die Methode startet die Berechnung von Normalen fuer Dreiecke und Vertices.
     */
    public void calculateNormals(){
        for(Triangle triangle : triangles){
            triangle.calculateNormal(vertices);
        }

        for(Vertex vertex : vertices){
            vertex.calculateNormal(vertex.selectTriangles(triangles, vertices));
        }
    }

    /**
     * Die Methode gibt zurueck die Vertices, aus welche die Vektoren bestehen.
     * @param vectors
     * @return
     */
    public static List<Vertex> verticesFromVectors(List<Vector3> vectors){
        List<Vertex> vertices = new ArrayList<Vertex>();
        for(Vector3 vector : vectors){
            vertices.add(new Vertex(vector));
        }
        return vertices;
    }

    @Override
    protected void addDataToRenderer(){
        renderer.addData(getVertices(), getTriangles());
    }

    @Override
    protected void render(GL2 gl){
        if(renderer != null && renderer.isSetup()) {
            renderer.draw(gl);
        }
    }

}
