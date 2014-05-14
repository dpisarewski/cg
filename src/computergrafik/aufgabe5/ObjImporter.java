package computergrafik.aufgabe5;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import java.awt.image.BufferedImage;
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
     * Liste mit Vertices
     */
    private List<Vertex> vertices       = new ArrayList<>();

    /**
     * Liste mit Dreiecken
     */
    private List<Triangle> triangles    = new ArrayList<>();

    /**
     * Liste mit Texturkoordinaten
     */
    private List<TextureCoordinate> textureCoordinates = new ArrayList<>();

    private String textureFilename;

    public String getTextureFilename() {
        return textureFilename;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    public List<TextureCoordinate> getTextureCoordinates() {
        return textureCoordinates;
    }

    /**
     * Liest aus OBJ-Datei die Vertices und Dreiecke.
     */
    public void load(String filename){
        try{
            File source = new File(filename);
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
     * Erstellt aus geladenen Vertices und Dreiecks einen Mesh
     * @return mesh
     */
    public TriangleMesh generateMesh(){
        TriangleMesh mesh = new TriangleMesh();
        mesh.setVertices(getVertices());
        mesh.setTriangles(getTriangles());
        mesh.setTextureCoordinates(getTextureCoordinates());
        mesh.generateStructure();
        return mesh;
    }

    /**
     * Lädt ein OBJ-Datei und erstellt ein Mesh daraus
     * @param filename Pfad und Name der Datei
     * @return
     */
    public static TriangleMesh loadMesh(String filename){
        ObjImporter importer = new ObjImporter();
        importer.load(filename);
        TriangleMesh mesh = importer.generateMesh();
        mesh.setTextureFilename(importer.getTextureFilename());
        return mesh;
    }

    /**
     * Konvertiert aus den geladenen Daten in innere repräsentation von Mesh
     */
    private void convert() {
        List<Vertex> newVertices = new ArrayList<Vertex>();
        for(int i = 0; i < triangles.size(); i++){
            newVertices.addAll(triangles.get(i).getVertices(vertices));
            triangles.set(i, new Triangle(new int[]{i * 3, i * 3 + 1, i * 3 + 2}));
        }
        vertices = newVertices;
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
     * Fügt die Vertices in die Liste
     * @param x Koordinate
     * @param y Koordinate
     * @param z Koordinate
     */
    private void addVertex(String x, String y, String z){
        vertices.add(new Vertex(Float.parseFloat(x), Float.parseFloat(y), Float.parseFloat(z)));
    }

    /**
     * Fügt die Dreiecke in die Liste
     * @param index1 für Vertex der Ecke 1
     * @param index2 für Vertex der Ecke 2
     * @param index3 für Vertex der Ecke 3
     */
    private void addTriangle(String index1, String index2, String index3){
        int i1 = Integer.parseInt(index1.split("/")[0]);
        int i2 = Integer.parseInt(index2.split("/")[0]);
        int i3 = Integer.parseInt(index3.split("/")[0]);
        triangles.add(new Triangle(new int[]{i1 - 1, i2 - 1, i3 - 1}));
    }

    private void addTextureCoordinates(String u, String v){
        textureCoordinates.add(new TextureCoordinate(Float.parseFloat(u), Float.parseFloat(v)));
    }

    private void loadMaterial(String filename){
        load("data/aufgabe5/" + filename);
    }

    private void setTextureFilename(String filename){
        textureFilename = "data/aufgabe5/" + filename;
    }
}
