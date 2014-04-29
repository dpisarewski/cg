/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * Lecture demo program.
 */
package computergrafik.aufgabe4;

import computergrafik.framework.ComputergrafikFrame;

import javax.media.opengl.GL2;

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

        mesh = ObjImporter.loadMesh("data/aufgabe3/bunny.obj");
        mesh.setMaterial(new MaterialNode(0.1f, 0.1f, .5f, 1));
        mesh.getRenderer().setShadingType(ShadingType.GOURAUD);
        scene.addNode(mesh);

        mesh = ObjImporter.loadMesh("data/aufgabe3/cube.obj");
        mesh.addTransformation(new TransformationNode(TransformationType.TRANSLATE, 1, 0, 0));
        scene.addNode(mesh);

        mesh = ObjImporter.loadMesh("data/aufgabe3/teddy.obj");
        mesh.addTransformation(new TransformationNode(TransformationType.TRANSLATE, 0, 1, 0));
        mesh.setMaterial(new MaterialNode(0.1f, .5f, 0.1f, 1));
        mesh.getRenderer().setShadingType(ShadingType.GOURAUD);
        scene.addNode(mesh);

        mesh = ObjImporter.loadMesh("data/aufgabe3/sphere.obj");
        mesh.addTransformation(new TransformationNode(TransformationType.TRANSLATE, 1, 1, 0));
        mesh.setMaterial(new MaterialNode(1, 0, 0, 1));
        mesh.getRenderer().setShadingType(ShadingType.GOURAUD);
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
