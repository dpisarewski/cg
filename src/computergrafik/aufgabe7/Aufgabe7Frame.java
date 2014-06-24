/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 7
 */

package computergrafik.aufgabe7;

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

    private ViewVolume2D viewVolume;

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
        Node transformation1, transformation2;
        viewVolume = new ViewVolume2D(new Vector3(3,0,6), new Vector3(6,0,3), new Vector3(6,0,-3));

        PortalScene2D portalScene = getPortalScene2D();
        Set<Integer> pvs = portalScene.computePvs(viewVolume);

        List<Vertex> vertices = getVerticesFromCells(portalScene, portalScene.getCells());

        List<PortalCell> visibleCells = new ArrayList<>();
        for(Integer index : pvs){
            visibleCells.add(portalScene.getCell(index));
        }
        List<Vertex> visibleVertices = getVerticesFromCells(portalScene, visibleCells);

        transformation1 = new TransformationNode(TransformationType.SCALE, .2f, .2f, .2f);
        transformation1.addNode(constructViewBoundaries());
        transformation1.addNode(constructMeshForCellBorders(vertices));
        transformation1.addNode(constructMeshForVisibles(visibleVertices));
        transformation1.addNode(constructMeshForInvisibles(vertices));
        transformation2 = new TransformationNode(TransformationType.TRANSLATE, -1, 0, -1);
        transformation2.addNode(transformation1);

        scene.addNode(transformation2);
        scene.calculateNormals();
        scene.setupRendering();
        setupCamera();
    }

    /**
     * Erstellt einen Node für Beobachtungsfeld
     * @return Node mit einem Mesh
     */
    private Node constructViewBoundaries() {
        List<Vertex> boundaries = constructBoundaries();

        Node material = new MaterialNode(0, 0, 1, 1);
        TriangleMesh viewBoundaries = new TriangleMesh();
        viewBoundaries.setVertices(boundaries);
        viewBoundaries.generateStructure();
        viewBoundaries.getRenderer().setDrawMode(GL2.GL_LINE_STRIP);
        material.addNode(viewBoundaries);
        return material;
    }

    /**
     * Erstellt einen Node mit sichtbaren Zellen
     * @param visibleVertices Liste mit Vertices, die sichtbar sind
     * @return Node mit einem Mesh
     */
    private Node constructMeshForVisibles(List<Vertex> visibleVertices) {
        Node material = new MaterialNode(.2f, 1f, .2f, 1f);
        TriangleMesh visibleCells = new TriangleMesh();
        visibleCells.setVertices(visibleVertices);
        visibleCells.generateStructure();
        material.addNode(visibleCells);
        return material;
    }

    /**
     * Erstellt einen Node mit den Gränzen zwischen den Zellen
     * @param vertices Liste mit Vertices, die die Gränzen zwischen den Zellen sind
     * @return Node mit einem Mesh
     */
    private Node constructMeshForCellBorders(List<Vertex> vertices) {
        Node material = new MaterialNode(0, 0, 0, 1);
        TriangleMesh cellBorders = new TriangleMesh();
        cellBorders.setVertices(vertices);
        cellBorders.generateStructure();
        cellBorders.getRenderer().setPolygonMode(GL2.GL_LINE);
        material.addNode(cellBorders);
        return material;
    }

    /**
     * Erstellt einen Node mit unsichtbaren Zellen
     * @param vertices Liste mit Vertices, die unsichtbar sind
     * @return Node mit einem Mesh
     */
    private Node constructMeshForInvisibles(List<Vertex> vertices) {
        Node material = new MaterialNode(1f, 1f, 1f, 1f);
        TriangleMesh invisibleCells = new TriangleMesh();
        invisibleCells.setVertices(vertices);
        invisibleCells.generateStructure();
        material.addNode(invisibleCells);
        return material;
    }

    /**
     * Importiert eine Portalszene
     * @return Portalszene
     */
    private PortalScene2D getPortalScene2D() {
        PortalScene2D portalScene = new PortalScene2D();
        PortalSceneImporter importer = new PortalSceneImporter();
        importer.importScene(portalScene, "data/aufgabe7/scene.scene");
        return portalScene;
    }

    /**
     * Gibt die Ecken von Zellen zurück
     * @param portalScene
     * @param cells
     * @return Liste mit den Ecken
     */
    private List<Vertex> getVerticesFromCells(PortalScene2D portalScene, List<PortalCell> cells) {
        List<Vertex> vertices = new ArrayList<>();
        for(PortalCell cell : cells){
            for(Vector3 vector : portalScene.getCellNodes(cell)){
                vertices.add(new Vertex(vector));
            }
        }
        return vertices;
    }

    /**
     * Gibt die sichtbaren Gränzen zurück
     * @return Liste mit den Ecken
     */
    private List<Vertex> constructBoundaries() {
        List<Vertex> boundaries = new ArrayList<>();
        boundaries.add(new Vertex(viewVolume.getOrigin().add(viewVolume.getLeftBoundary())));
        boundaries.add(new Vertex(viewVolume.getOrigin()));
        boundaries.add(new Vertex(viewVolume.getOrigin().add(viewVolume.getRightBoundary())));
        return boundaries;
    }

    /**
     * Stellt die Kamera auf Position unter der Szene
     */
    private void setupCamera(){
        getCamera().setEye(new Vector3(0, -5, 0));
        getCamera().setUp(new Vector3(0, 0, 1));
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
