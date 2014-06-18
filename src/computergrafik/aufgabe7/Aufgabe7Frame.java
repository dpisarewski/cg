/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 7
 */

package computergrafik.aufgabe7;

import computergrafik.aufgabe3.*;
import computergrafik.framework.ComputergrafikFrame;
import computergrafik.framework.Vector3;
import computergrafik.portalculling.PortalCell;
import computergrafik.portalculling.PortalScene2D;
import computergrafik.portalculling.PortalSceneImporter;
import computergrafik.portalculling.ViewVolume2D;

import javax.media.opengl.GL2;
import java.util.*;

public class Aufgabe7Frame extends ComputergrafikFrame {
    /**
     * Szenengraph
     */
    private Node scene = new Node();

    public Aufgabe7Frame(int timerInverval) {
        super(timerInverval);
    }

    @Override
    public void drawGlContent(GL2 gl) {
        scene.draw(gl);
    }

    /**
     * Erstellt Meshes und eine Szene aus diesen Meshes
     */
    private void construct(){
        Set<Integer> pvs;
        PortalScene2D portalScene = new PortalScene2D();
        PortalSceneImporter importer = new PortalSceneImporter();
        importer.importScene(portalScene, "data/aufgabe7/scene.scene");
        ViewVolume2D viewVolume = new ViewVolume2D(new Vector3(5,0,4), new Vector3(5,0,4.125), new Vector3(5.25,0,4.3));
        pvs = portalScene.computePvs(viewVolume);

        List<Vertex> vertices = new ArrayList<>();
        for(PortalCell cell : portalScene.getCells()){
            for(Vector3 vector : portalScene.getCellNodes(cell)){
                vertices.add(new Vertex(vector));
            }
        }

        List<Vertex> visibleVertices = new ArrayList<>();
        for(Integer index : pvs){
            for(Vector3 vector : portalScene.getCellNodes(portalScene.getCell(index))){
                visibleVertices.add(new Vertex(vector));
            }
        }

        List<Triangle> triangles = new ArrayList<>();
        for(int i = 0; i < vertices.size() - 3; i += 3){
            triangles.add(new Triangle(new int[]{i, i + 1, i + 2}));
        }

        List<Triangle> visibleTriangles = new ArrayList<>();
        for(int i = 0; i < visibleVertices.size() - 3; i += 3){
            visibleTriangles.add(new Triangle(new int[]{i, i + 1, i + 2}));
        }

        Node material;

        material = new MaterialNode(1f, 1f, 1f, 1f);
        TriangleMesh invisibleCells = new TriangleMesh();
        invisibleCells.setVertices(vertices);
        invisibleCells.setTriangles(triangles);
        invisibleCells.generateStructure();
        material.addNode(invisibleCells);

        material = new MaterialNode(.5f, 1f, .5f, 1f);
        TriangleMesh visibleCells = new TriangleMesh();
        visibleCells.setVertices(visibleVertices);
        visibleCells.setTriangles(visibleTriangles);
        visibleCells.generateStructure();
        material.addNode(visibleCells);

        scene.addNode(visibleCells);
        scene.addNode(invisibleCells);
    }

    /**
     * Bewegt die Kamera durch den Kamerapfad
     */
    @Override
    protected void timerTick() {
    }

    /**
     * Program entry point.
     */
    public static void main(String[] args) {
        (new Aufgabe7Frame(1000)).construct();
    }
}
