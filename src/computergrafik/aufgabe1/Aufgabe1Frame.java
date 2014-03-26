/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * Lecture demo program.
 */
package computergrafik.aufgabe1;

import computergrafik.framework.ComputergrafikFrame;

import javax.media.opengl.GL2;

/**
 * Application for the first exercise.
 * 
 * @author Philipp Jenke
 * 
 */
public class Aufgabe1Frame extends ComputergrafikFrame {

    /**
     * Constructor.
     */
    public Aufgabe1Frame(int timerInverval) {
        super(timerInverval);
    }

    /*
     * (nicht-Javadoc)
     * 
     * @see
     * computergrafik.framework.IRenderFrame#drawGlContent(javax.media.opengl
     * .GL)
     */
    @Override
    public void drawGlContent(GL2 gl) {
        Cube cube = new Cube();
        cube.generateNodes(1);
        cube.setMaterial(new MaterialNode(0f, 0f, 0f, 1f));

        Cube cube1 = new Cube();
        cube1.generateNodes(1);
        cube1.addTransformation(new TransformationNode(TransformationNode.SCALE, 1, 2, 1));
        cube1.addTransformation(new TransformationNode(TransformationNode.TRANSLATION, 2, 0, 0));
        cube1.setMaterial(new MaterialNode(.5f, 0f, 0f, 1f));

        Ring ring = new Ring(.5f, .5f);
        ring.generateNodes(720);
        ring.addTransformation(new TransformationNode(TransformationNode.TRANSLATION, 0, 0, -2));
        ring.setMaterial(new MaterialNode(0, .5f, 0f, 1f));

        Ring ring1 = new Ring(.5f, 1);
        ring1.generateNodes(12);
        ring1.addTransformation(new TransformationNode(TransformationNode.SCALE, .5f, .5f, 1));
        ring1.addTransformation(new TransformationNode(TransformationNode.TRANSLATION, 3, 0, -2));
        ring1.setMaterial(new MaterialNode(0, 0, .5f, 1f));

        setGlMaterial(gl, 0.25f, 0.25f, 0.75f);

        cube.draw(gl);
        cube1.draw(gl);
        ring.draw(gl);
        ring1.draw(gl);
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
        new Aufgabe1Frame(1000);
    }
}
