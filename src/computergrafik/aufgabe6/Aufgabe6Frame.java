/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 6
 */

package computergrafik.aufgabe6;

import computergrafik.framework.ComputergrafikFrame;
import computergrafik.framework.Vector3;

import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;


public class Aufgabe6Frame extends ComputergrafikFrame {
    /**
     * Szenengraph
     */
    private Node scene = new Node();

    /**
     * Kamerapfad
     */
    private CameraPath path;

    /**
     * Flag. Signalisiert, ob der Rendering gestartet werden darf
     */
    private boolean isSetup;

    /**
     * Constructor.
     */
    public Aufgabe6Frame(int timerInverval) {
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
        TriangleMesh mesh;
        Node transformation, material;

        mesh = ObjImporter.loadMesh("data/aufgabe5/square.obj");
        transformation = new TransformationNode(TransformationType.TRANSLATE, -2, 0, 0);
        transformation.addNode(mesh);
        scene.addNode(transformation);

        mesh = ObjImporter.loadMesh("data/aufgabe5/sphere.obj");
        mesh.getRenderer().setShadingType(ShadingType.GOURAUD);
        scene.addNode(mesh);

        mesh = ObjImporter.loadMesh("data/aufgabe5/cube.obj");
        transformation = new TransformationNode(TransformationType.TRANSLATE, 2, 0, 0);
        transformation.addNode(mesh);
        scene.addNode(transformation);

        scene.calculateNormals();
        scene.setupRendering();

        List<Vector3> controlPoints = new ArrayList<>();
        controlPoints.add(new Vector3(-4,3,2));
        controlPoints.add(new Vector3(5,3,2));
        controlPoints.add(new Vector3(5,3,-2));
        controlPoints.add(new Vector3(-4,3,-2));
        path = new CameraPath(controlPoints, 0.005f);

        isSetup = true;
    }

    /**
     * Bewegt die Kamera durch den Kamerapfad
     */
    @Override
    protected void timerTick() {
        if(isSetup){
            Vector3 point = path.next();

            if(point != null){
                getCamera().setEye(point);
            }
        }
    }

    /**
     * Program entry point.
     */
    public static void main(String[] args) {
        (new Aufgabe6Frame(50)).construct();
    }
}
