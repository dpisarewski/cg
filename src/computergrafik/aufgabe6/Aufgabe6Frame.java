/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * Lecture demo program.
 */
package computergrafik.aufgabe6;

import computergrafik.framework.ComputergrafikFrame;
import computergrafik.framework.Vector3;

import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

/**
 * Application for the first exercise.
 * 
 * @author Philipp Jenke
 * 
 */
public class Aufgabe6Frame extends ComputergrafikFrame {
    private Node scene = new Node();
    private CameraPath path;
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

    @Override
    protected void timerTick() {
        if(!isSetup){
            return;
        }

        Vector3 point = path.next();
        if(point != null){
            getCamera().setEye(point);
        }
    }
    /**
     * Program entry point.
     */
    public static void main(String[] args) {
        (new Aufgabe6Frame(50)).construct();
    }
}
