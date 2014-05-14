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
        Node transformation, material;

        mesh = ObjImporter.loadMesh("data/aufgabe3/bunny.obj");
        mesh.getRenderer().setShadingType(ShadingType.GOURAUD);
        material = new MaterialNode(0.1f, 0.1f, .5f, 1);
        material.addNode(mesh);
        scene.addNode(material);

        mesh = ObjImporter.loadMesh("data/aufgabe3/cube.obj");
        transformation = new TransformationNode(TransformationType.TRANSLATE, 1, 0, 0);
        transformation.addNode(mesh);
        scene.addNode(transformation);

        mesh = ObjImporter.loadMesh("data/aufgabe3/teddy.obj");
        mesh.getRenderer().setShadingType(ShadingType.GOURAUD);
        transformation = new TransformationNode(TransformationType.TRANSLATE, 0, 1, 0);
        material = new MaterialNode(0.1f, .5f, 0.1f, 1);
        material.addNode(mesh);
        transformation.addNode(material);
        scene.addNode(transformation);

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
        (new Aufgabe5Frame(1000)).construct();
    }
}
