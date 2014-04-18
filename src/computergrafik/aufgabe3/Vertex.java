package computergrafik.aufgabe3;

/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 2, Aufgabe a.
 */

import computergrafik.framework.Vector3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Diese Klasse repraesentiert einen Punkt
 */
public class Vertex {
    private Vector3 position;

    private Vector3 normal = new Vector3();

    /**
     * Konstruktor.
     */
    public Vertex(float x, float y, float z){
        this.position   = new Vector3(x, y, z);
    }

    /**
     * Konstruktor.
     */
    public Vertex(Vector3 vector){
        this.position   = vector;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Vector3 getNormal() {
        return normal;
    }

    public void setNormal(Vector3 normal) {
        this.normal = normal;
    }

    /**
     * Berechnet eine Normale für den Eckpunkt
     * @param triangles
     */
    public void calculateNormal(List<Triangle> triangles){
        Vector3 vec = new Vector3();

        for(Triangle triangle: triangles){
            if(vec.isNull()){
                vec = triangle.getNormal();
            }else{
                vec = vec.add(triangle.getNormal());
            }
        }

        normal = vec.getNormalized();
    }

    /**
     * Die Methode sucht aus allen Dreiecken nur die Dreiecke, die gemeinsame Punkte haben.
     * @return: Ein Liste mit Ausgewaehlten Dreiecken
     */
    public List<Triangle> selectTriangles(Map<Vertex, Set<Triangle>> associations){
        return new ArrayList<>(associations.get(this));
    }
}