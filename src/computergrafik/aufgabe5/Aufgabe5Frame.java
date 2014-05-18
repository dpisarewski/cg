/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * Lecture demo program.
 */
package computergrafik.aufgabe5;

import computergrafik.framework.ComputergrafikFrame;

import javax.media.opengl.GL2;

/**
 * Application for the first exercise.
 * 
 * @author Philipp Jenke
 * 
 */
public class Aufgabe5Frame extends ComputergrafikFrame {
    Node scene = new Node();

    /**
     * Constructor.
     */
    public Aufgabe5Frame(int timerInverval) {
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
        scene.addNode(mesh);

        mesh = ObjImporter.loadMesh("data/aufgabe5/cube.obj");
        transformation = new TransformationNode(TransformationType.TRANSLATE, 2, 0, 0);
        transformation.addNode(mesh);
        scene.addNode(transformation);

        scene.calculateNormals();
        scene.setupRendering();
    }

    @Override
    protected void timerTick() {
        // System.out.println("Tick");
    }
    /**
     * Program entry point.
     */
    public static void main(String[] args) {
        (new Aufgabe5Frame(1000)).construct();
    }
}
