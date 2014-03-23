package computergrafik.aufgabe2;

/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 2, Aufgabe a.
 */

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
     * Konstruktor.
     */
    public TriangleMesh(List<Vertex> vertices){
        this.vertices = vertices;
    }

    public void addVectex(Vertex vertex){
        vertices.add(vertex);
    }

    public void addTriangle(Triangle triangle){
        triangles.add(triangle);
    }

    public void generateStructure(){
        for(int i = 0; i < vertices.size(); i += 3){
            addTriangle(new Triangle(new int[]{i, i + 1, i + 2}));
        }
    }

    public void calculateNormals(){
        for(Triangle triangle : triangles){
            triangle.calculateNormal(vertices);
        }

        for(Vertex vertex : vertices){
            vertex.calculateNormal(vertex.selectTriangles(triangles, vertices));
        }
    }

    public void draw(){

    }


}
