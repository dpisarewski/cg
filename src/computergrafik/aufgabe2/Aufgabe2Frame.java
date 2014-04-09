/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * Lecture demo program.
 */
package computergrafik.aufgabe2;

import computergrafik.framework.ComputergrafikFrame;

import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

/**
 * Application for the first exercise.
 * 
 * @author Philipp Jenke
 * 
 */
public class Aufgabe2Frame extends ComputergrafikFrame {
    Node scene = new Node();

    /**
     * Constructor.
     */
    public Aufgabe2Frame(int timerInverval) {
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
        setGlMaterial(gl, 0.25f, 0.25f, 0.75f);
        scene.draw(gl);
    }

    private void construct(){
        TriangleMesh mesh = new TriangleMesh();
        Cube cube = new Cube();
        cube.generateNodes(1);
        mesh.generateStructure(cube);

        scene.addNode(mesh);

        Cube cube1 = new Cube();
        cube1.generateNodes(1);
        mesh = new TriangleMesh();
        mesh.generateStructure(cube1);
        mesh.addTransformation(new TransformationNode(TransformationNode.SCALE, 1, 2, 1));
        mesh.addTransformation(new TransformationNode(TransformationNode.TRANSLATION, 2, 1, 1));
        mesh.setMaterial(new MaterialNode(.5f, 0f, 0f, 1f));
        scene.addNode(mesh);

        Ring ring = new Ring(.5f, 1);
        ring.generateNodes(360);
        mesh = new TriangleMesh();
        mesh.generateStructure(ring);
        mesh.addTransformation(new TransformationNode(TransformationNode.SCALE, .5f, .5f, 1));
        mesh.addTransformation(new TransformationNode(TransformationNode.TRANSLATION, 3, 0, -2));
        mesh.setMaterial(new MaterialNode(0, 0, .5f, 1f));
        scene.addNode(mesh);

        Ring ring1 = new Ring(.5f, .5f);
        ring1.generateNodes(120);
        mesh = new TriangleMesh();
        mesh.generateStructure(ring1);
        mesh.addTransformation(new TransformationNode(TransformationNode.TRANSLATION, 0, -3, -2));
        mesh.setMaterial(new MaterialNode(0, .5f, .5f, 1f));
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
        (new Aufgabe2Frame(1000)).construct();
    }
}
