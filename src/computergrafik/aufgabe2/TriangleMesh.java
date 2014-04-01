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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Diese Klasse repraesentiert ein Dreiecksnetz.
 */
public class TriangleMesh {
    /**
     * Liste von Dreiecken.
     */
    private List<Triangle> triangles = new ArrayList<Triangle>();

    /**
     * Liste von Punkten.
     */
    private List<Vertex> vertices;

    /**
     * Liste von Indices.
     */
    private List<Integer> indices;


    /**
     * Konstruktor.
     */
    public TriangleMesh(){
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    public void setTriangles(List<Triangle> triangles) {
        this.triangles = triangles;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public void addVertex(Vertex vertex){
        vertices.add(vertex);
    }

    public void addTriangle(Triangle triangle){
        triangles.add(triangle);
    }

    public void generateStructure(List<Vertex> vertices, List<Integer> indices){
        this.vertices = vertices;
        this.indices  = indices;
        for(int i = 0; i < indices.size(); i += 3){
            int[] incides = new int[]{indices.get(i), indices.get(i + 1), indices.get(i + 2)};
            addTriangle(new Triangle(incides));
        }
    }

    public void generateStructure(Node node){
        generateStructure(verticesFromVectors(node.getVectors()), node.getIndices());
    }

    public List<Integer> getIndices(){
        return indices;
    }

    public void calculateNormals(){
        for(Triangle triangle : triangles){
            triangle.calculateNormal(vertices);
        }

        for(Vertex vertex : vertices){
            vertex.calculateNormal(vertex.selectTriangles(triangles, vertices));
        }
    }

    public void draw(GL2 gl, VertexArrayRenderer renderer){
        renderer.draw(gl);
    }



    public static List<Vertex> verticesFromVectors(List<Vector3> vectors){
        List<Vertex> vertices = new ArrayList<Vertex>();
        for(Vector3 vector : vectors){
            vertices.add(new Vertex(vector));
        }
        return vertices;
    }

}
