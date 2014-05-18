package computergrafik.aufgabe5;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 3
 */

/**
 * Importer für OBJ-Format
 */
public class ObjImporter {
    /**
     * TriangleMesh, in den Daten geladen werden
     */
    TriangleMesh mesh;

    public ObjImporter(TriangleMesh mesh){
        this.mesh = mesh;
    }

    public TriangleMesh getMesh() {
        return mesh;
    }

    private String workingDirectory;

    /**
     * Liest aus OBJ-Datei die Vertices und Dreiecke.
     */
    public void load(String filename){
        try{
            File source = new File(filename);
            workingDirectory = source.getParent();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(source)));

            String line;
            while ((line = br.readLine()) != null){
                processLine(parseLine(line));
            }
            br.close();

            convert();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lädt ein OBJ-Datei und erstellt ein Mesh daraus
     * @param filename Pfad und Name der Datei
     * @return
     */
    public static TriangleMesh loadMesh(String filename){
        ObjImporter importer = new ObjImporter(new TriangleMesh());
        importer.load(filename);
        importer.getMesh().generateStructure();
        return importer.getMesh();
    }

    /**
     * Konvertiert geladene Daten in innere repräsentation von Mesh
     */
    private void convert() {
        mesh.setVertices(convertVertices());
        if(!mesh.getVertices().isEmpty()){
            mesh.setTextureCoordinates(convertTextureCoordinates());
        }
        mesh.setTriangles(convertTriangles());
    }

    /**
     * Konvertiert Vertices
     * @return konvertierte List mit Vertices
     */
    private List<Vertex> convertVertices(){
        List<Vertex> newVertices = new ArrayList<>();
        for(Triangle triangle : mesh.getTriangles()){
            newVertices.addAll(triangle.getVertices(mesh.getVertices()));
        }
        return newVertices;
    }

    /**
     * Konvertiert Dreiecke
     * @return konvertierte List mit Dreiecken
     */
    private List<Triangle> convertTriangles(){
        List<Triangle> newTriangles = new ArrayList<>();
        for(int i = 0; i < mesh.getTriangles().size(); i++){
            Triangle triangle = new Triangle(new int[]{i * 3, i * 3 + 1, i * 3 + 2});
            triangle.setTextureIndices(mesh.getTriangles().get(i).getTextureIndices());
            newTriangles.add(triangle);
        }
        return newTriangles;
    }

    /**
     * Konvertiert Texturcoordinaten
     * @return konvertierte List mit Texturkoordinaten
     */
    private List<TextureCoordinate> convertTextureCoordinates(){
        List<TextureCoordinate> newTextureCoordinates = new ArrayList<>();
        for(Triangle triangle : mesh.getTriangles()){
            int[] indices = triangle.getTextureIndices();
            List<TextureCoordinate> coordinates = new ArrayList<>();
            for(int index : indices){
                coordinates.add(mesh.getTextureCoordinates().get(index));
            }
            newTextureCoordinates.addAll(coordinates);
        }
        return newTextureCoordinates;
    }

    /**
     * Parst eine Zeile.
     * @param line Zeile
     * @return Array von Tokens
     */
    private String[] parseLine(String line){
        return line.trim().split("\\s+");
    }

    /**
     * Prüft den Typ der Zeile und ruft entsprechende Methode auf, die die Textdaten deserialisiert
     * @param tokens Array mit Tokens
     */
    private void processLine(String[] tokens){
        switch(tokens[0]){
            case "v" : addVertex(tokens[1], tokens[2], tokens[3]);      break;
            case "f" : addTriangle(tokens[1], tokens[2], tokens[3]);    break;
            case "vt": addTextureCoordinates(tokens[1], tokens[2]);     break;
            case "mtllib" : loadMaterial(tokens[1]);                    break;
            case "map_Kd" : setTextureFilename(tokens[1]);              break;
        }
    }

    /**
     * Fügt die Vertices in die Liste ein
     * @param x Koordinate
     * @param y Koordinate
     * @param z Koordinate
     */
    private void addVertex(String x, String y, String z){
        mesh.getVertices().add(new Vertex(Float.parseFloat(x), Float.parseFloat(y), Float.parseFloat(z)));
    }

    /**
     * Fügt die Dreiecke in die Liste ein
     * @param index1 für Vertex der Ecke 1
     * @param index2 für Vertex der Ecke 2
     * @param index3 für Vertex der Ecke 3
     */
    private void addTriangle(String index1, String index2, String index3){
        int i1 = Integer.parseInt(index1.split("/")[0]);
        int i2 = Integer.parseInt(index2.split("/")[0]);
        int i3 = Integer.parseInt(index3.split("/")[0]);

        int t1 = Integer.parseInt(index1.split("/")[1]);
        int t2 = Integer.parseInt(index2.split("/")[1]);
        int t3 = Integer.parseInt(index3.split("/")[1]);
        Triangle triangle = new Triangle(new int[]{i1 - 1, i2 - 1, i3 - 1});
        triangle.setTextureIndices(new int[]{t1 - 1, t2 - 1, t3 - 1});
        mesh.getTriangles().add(triangle);
    }

    /**
     * Fügt Texturkoordinaten in die Liste ein
     * @param u U Koordinate
     * @param v V Koordinate
     */
    private void addTextureCoordinates(String u, String v){
        mesh.getTextureCoordinates().add(new TextureCoordinate(Float.parseFloat(u), Float.parseFloat(v)));
    }

    /**
     * Lädt eine Materialdatei
     * @param filename Name der Datei
     */
    private void loadMaterial(String filename){
        load(new File(workingDirectory, filename).toString());
    }

    /**
     * Speichert den Namen der Texturdatei
     * @param filename
     */
    private void setTextureFilename(String filename){
        mesh.setTextureFilename(new File(workingDirectory, filename).toString());
    }
}
