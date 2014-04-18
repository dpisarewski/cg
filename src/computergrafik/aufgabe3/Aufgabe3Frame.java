/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * Lecture demo program.
 */
package computergrafik.aufgabe3;

import computergrafik.framework.ComputergrafikFrame;

import javax.media.opengl.GL2;

/**
 * Application for the first exercise.
 * 
 * @author Philipp Jenke
 * 
 */
public class Aufgabe3Frame extends ComputergrafikFrame {
    Node scene = new Node();

    /**
     * Constructor.
     */
    public Aufgabe3Frame(int timerInverval) {
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
        ObjImporter importer = new ObjImporter("data/aufgabe3/bunny.obj");
        importer.load();
        mesh.setVertices(importer.getVertices());
        mesh.setTriangles(importer.getTriangles());
        scene.addNode(mesh);

        mesh = new TriangleMesh();
        importer = new ObjImporter("data/aufgabe3/cube.obj");
        importer.load();
        mesh.setVertices(importer.getVertices());
        mesh.setTriangles(importer.getTriangles());
        mesh.addTransformation(new TransformationNode(TransformationNode.TRANSLATION, 1, 0, 0));
        scene.addNode(mesh);

        mesh = new TriangleMesh();
        importer = new ObjImporter("data/aufgabe3/teddy.obj");
        importer.load();
        mesh.setVertices(importer.getVertices());
        mesh.setTriangles(importer.getTriangles());
        mesh.addTransformation(new TransformationNode(TransformationNode.TRANSLATION, 0, 1, 0));
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
        (new Aufgabe3Frame(1000)).construct();
    }
}
