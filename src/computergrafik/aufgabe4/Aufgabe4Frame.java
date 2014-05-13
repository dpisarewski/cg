/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * Lecture demo program.
 */
package computergrafik.aufgabe4;

import computergrafik.aufgabe3.ObjImporter;
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
public class Aufgabe4Frame extends ComputergrafikFrame {
    Node scene = new Node();

    /**
     * Constructor.
     */
    public Aufgabe4Frame(int timerInverval) {
        super(timerInverval);
    }

    /*
     * (nicht-Javadoc)
     * 
     * @see
     * computergrafik.framework.IRenderFrame#drawGlContent(javax.media.opengl.GL)
     */
    @Override
    public void drawGlContent(GL2 gl) {
        setGlMaterial(gl, 0.25f, 0.25f, 0.25f);
        scene.draw(gl);
    }

    /**
     * Erstellt Meshes und eine Szene aus diesen Meshes
     */
    private void construct(){
        TriangleMesh mesh;
        MarchingCubes alg;
        alg = new MarchingCubes(new Torus(0.5, 0.3), 0);
        mesh = alg.createMesh(2, 2, 2, 25, 25, 25);
        scene.addNode(mesh);

        alg = new MarchingCubes(new Orb(0.8), 0);
        mesh = alg.createMesh(2, 2, 2, 25, 25, 25);
        mesh.addTransformation(new TransformationNode(TransformationType.TRANSLATE, -2, 0, 0));
        scene.addNode(mesh);

        alg = new MarchingCubes(new BoysSurface(), 0);
        mesh = alg.createMesh(4, 4, 4, 100, 100, 100);
        mesh.addTransformation(new TransformationNode(TransformationType.TRANSLATE, 2, 0, 0));
        mesh.addTransformation(new TransformationNode(TransformationType.SCALE, 0.5f, 0.5f, 0.5f));
        scene.addNode(mesh);

        scene.calculateNormals();
        scene.setupRendering();
    }

    /*
     * (nicht-Javadoc)
     * 
     * @see computergrafik.framework.ComputergrafikFrame#timerTick()
     */
    @Override
    protected void timerTick() {
        // System.out.println("Tick");
    }
    /**
     * Program entry point.
     */
    public static void main(String[] args) {
        (new Aufgabe4Frame(1000)).construct();
    }
}
