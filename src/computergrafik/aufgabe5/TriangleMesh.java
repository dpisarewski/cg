package computergrafik.aufgabe5;

/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 3
 */

import javax.media.opengl.GL2;
import java.util.*;

/**
 * Diese Klasse repraesentiert ein Dreiecksnetz.
 */
public class TriangleMesh extends Node {
    /**
     * Liste von Vektoren, aus dennen Node besteht.
     */
    protected List<Vertex> vertices = new ArrayList<Vertex>();

    /**
     * Liste von Dreiecken.
     */
    private List<Triangle> triangles = new ArrayList<Triangle>();

    /**
     * Liste mit Texturkoordinaten
     */
    private List<TextureCoordinate> textureCoordinates = new ArrayList();

    /**
     * Hashmap, der Vertices mit Triangles assoziiert
     */
    private Map<Vertex, Set<Triangle>> assotiations = new HashMap();

    /**
     * Renderer, der Daten und Einstellungen für ein Mesh enthält
     */
    private VertexArrayRenderer renderer = new VertexArrayRenderer();

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    public void setTriangles(List<Triangle> triangles) {
        this.triangles = triangles;
    }

    public VertexArrayRenderer getRenderer() {
        return renderer;
    }

    public void setRenderer(VertexArrayRenderer renderer) {
        this.renderer = renderer;
    }

    public List<TextureCoordinate> getTextureCoordinates() {
        return textureCoordinates;
    }

    public void setTextureCoordinates(List<TextureCoordinate> textureCoordinates) {
        this.textureCoordinates = textureCoordinates;
    }

    /**
     * Fuegt einen Vector fuer Knoten (Figur) hin.
     */
    public void addVertex(float x, float y, float z){
        vertices.add(new Vertex(x, y, z));
    }

    /**
     * Ergaenzt den Dreieck in die Liste.
     * @param triangle
     */
    public void addTriangle(Triangle triangle){
        triangles.add(triangle);
    }

    /**
     * Speichert zu jedem Vertex Triangles, die ihn enthalten in einem Map
     */
    public void generateStructure(){
        for(Triangle triangle: triangles){
            for(Vertex vertex : triangle.getVertices(vertices)){
                Set<Triangle> set = assotiations.get(vertex);

                if(set == null){
                    set = new HashSet<Triangle>();
                    assotiations.put(vertex, set);
                }

                set.add(triangle);
            }
        }
    }

    /**
     * Die Methode startet die Berechnung von Normalen fuer Dreiecke und Vertices.
     */
    public void calculateNormals(){
        for(Triangle triangle : triangles){
            triangle.calculateNormal(vertices);
        }

        for(Vertex vertex : vertices){
            vertex.calculateNormal(vertex.selectTriangles(assotiations), vertices);
        }
    }

    /**
     * Übergibt dem Renderer Vertices und Triangles zum Zeichnen
     */
    @Override
    protected void addDataToRenderer(){
        renderer.addData(getVertices(), getTriangles());
    }

    /**
     * Prüft, ob der Renderer Puffer ausgefüllt hat und startet Rendering
     * @param gl GL2 Objekt
     */
    @Override
    protected void render(GL2 gl){
        if(renderer != null && renderer.isSetup()) {
            renderer.draw(gl);
        }
    }

}
