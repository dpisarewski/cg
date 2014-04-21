package computergrafik.aufgabe3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pisare_d on 16.04.2014.
 */
public class ObjImporter{
    private String filename;
    private List<Vertex> vertices       = new ArrayList<Vertex>();
    private List<Triangle> triangles    = new ArrayList<Triangle>();

    public ObjImporter(String filename){
        this.filename = filename;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    public void load(){
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

    public TriangleMesh generateMesh(){
        TriangleMesh mesh = new TriangleMesh();
        mesh.setVertices(getVertices());
        mesh.setTriangles(getTriangles());
        mesh.generateStructure();
        return mesh;
    }

    public static TriangleMesh loadMesh(String filename){
        ObjImporter importer = new ObjImporter(filename);
        importer.load();
        return importer.generateMesh();
    }

    private void convert() {
        List<Vertex> newVertices = new ArrayList<Vertex>();
        for(int i = 0; i < triangles.size(); i++){
            newVertices.addAll(triangles.get(i).getVertices(vertices));
            triangles.set(i, new Triangle(new int[]{i * 3, i * 3 + 1, i * 3 + 2}));
        }
        vertices = newVertices;
    }

    private String[] parseLine(String line){
        return line.trim().split("\\s+");
    }

    private void processLine(String[] tokens){
        switch(tokens[0]){
            case "v" : addVertex(tokens[1], tokens[2], tokens[3]); break;
            case "f" : addTriangle(tokens[1], tokens[2], tokens[3]); break;
        }
    }

    private void addVertex(String x, String y, String z){
        vertices.add(new Vertex(Float.parseFloat(x), Float.parseFloat(y), Float.parseFloat(z)));
    }

    private void addTriangle(String index1, String index2, String index3){
        int i1 = Integer.parseInt(index1.split("/")[0]);
        int i2 = Integer.parseInt(index2.split("/")[0]);
        int i3 = Integer.parseInt(index3.split("/")[0]);
        triangles.add(new Triangle(new int[]{i1 - 1, i2 - 1, i3 - 1}));
    }

}
