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
        Node cube = new Cube(0f,0f,0f);
        cube.addNode(new Node(-.5f,.5f,-.5f));
        cube.addNode(new Node(-.5f,.5f,.5f));
        cube.addNode(new Node(.5f,.5f,-.5f));
        cube.addNode(new Node(.5f,.5f,-.5f));
        cube.addNode(new Node(.5f,.5f,.5f));
        cube.addNode(new Node(-.5f,.5f,.5f));

        cube.addNode(new Node(-.5f,-.5f,.5f));
        cube.addNode(new Node(-.5f,.5f,.5f));
        cube.addNode(new Node(.5f,.5f,.5f));
        cube.addNode(new Node(-.5f,-.5f,.5f));
        cube.addNode(new Node(.5f,.5f,.5f));
        cube.addNode(new Node(.5f,-.5f,.5f));

        cube.addNode(new Node(-.5f,-.5f,.5f));
        cube.addNode(new Node(-.5f,-.5f,-.5f));
        cube.addNode(new Node(.5f,-.5f,-.5f));
        cube.addNode(new Node(-.5f,-.5f,.5f));
        cube.addNode(new Node(.5f,-.5f,-.5f));
        cube.addNode(new Node(.5f,-.5f,.5f));

        cube.addNode(new Node(-.5f,-.5f,-.5f));
        cube.addNode(new Node(-.5f,.5f,-.5f));
        cube.addNode(new Node(.5f,.5f,-.5f));
        cube.addNode(new Node(-.5f,-.5f,-.5f));
        cube.addNode(new Node(.5f,.5f,-.5f));
        cube.addNode(new Node(.5f,-.5f,-.5f));

        cube.addNode(new Node(.5f,-.5f,.5f));
        cube.addNode(new Node(.5f,.5f,.5f));
        cube.addNode(new Node(.5f,.5f,-.5f));
        cube.addNode(new Node(.5f,-.5f,.5f));
        cube.addNode(new Node(.5f,.5f,-.5f));
        cube.addNode(new Node(.5f,-.5f,-.5f));

        cube.addNode(new Node(-.5f,-.5f,.5f));
        cube.addNode(new Node(-.5f,.5f,.5f));
        cube.addNode(new Node(-.5f,.5f,-.5f));
        cube.addNode(new Node(-.5f,-.5f,.5f));
        cube.addNode(new Node(-.5f,.5f,-.5f));
        cube.addNode(new Node(-.5f,-.5f,-.5f));


        Ring ring = new Ring(0f,0f,0f);
//        ring.addNode(new Node(1.0f,                        0.5f,   0.0f));
//        ring.addNode(new Node(0.9659258262890683f,        -0.5f,   0.25881904510252074f));
//        ring.addNode(new Node(0.8660254037844387f,         0.5f,   0.49999999999999994f));
//        ring.addNode(new Node(0.7071067811865476f,        -0.5f,   0.7071067811865475f));
//        ring.addNode(new Node(0.5000000000000001f,         0.5f,   0.8660254037844386f));
//        ring.addNode(new Node(0.25881904510252074f,       -0.5f,   0.9659258262890683f));
//        ring.addNode(new Node(0f,                          0.5f,   1.0f));
//        ring.addNode(new Node(-0.25881904510252085f,      -0.5f,   0.9659258262890683f));
//        ring.addNode(new Node(-0.4999999999999998f,        0.5f,   0.8660254037844387f));
//        ring.addNode(new Node(-0.7071067811865475f,       -0.5f,   0.7071067811865476f));
//        ring.addNode(new Node(-0.8660254037844387f,        0.5f,   0.49999999999999994f));
//        ring.addNode(new Node(-0.9659258262890682f,       -0.5f,   0.258819045102521f));
//        ring.addNode(new Node(-1.0f,                       0.5f,   0f));
//        ring.addNode(new Node(-0.9659258262890684f,       -0.5f,   -0.25881904510252035f));
//        ring.addNode(new Node(-0.8660254037844386f,        0.5f,   -0.5000000000000001f));
//        ring.addNode(new Node(-0.7071067811865477f,       -0.5f,   -0.7071067811865475f));
//        ring.addNode(new Node(-0.5000000000000004f,        0.5f,   -0.8660254037844384f));
//        ring.addNode(new Node(-0.25881904510252063f,      -0.5f,   -0.9659258262890683f));
//        ring.addNode(new Node(0f,                          0.5f,   -1.0f));
//        ring.addNode(new Node(0.25881904510252113f,       -0.5f,   -0.9659258262890682f));
//        ring.addNode(new Node(0.5000000000000001f,         0.5f,   -0.8660254037844386f));
//        ring.addNode(new Node(0.7071067811865474f,        -0.5f,   -0.7071067811865477f));
//        ring.addNode(new Node(0.8660254037844384f,         0.5f,   -0.5000000000000004f));
//        ring.addNode(new Node(0.9659258262890683f,        -0.5f,   -0.2588190451025207f));
        ring.generateNodes(360);

        setGlMaterial(gl, 0.25f, 0.25f, 0.75f);
        gl.glColor3f(0.75f, 0.25f, 0.25f);
        //cube.draw(gl);
        ring.draw(gl);


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
