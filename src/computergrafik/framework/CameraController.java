/**
 * Prof. Philipp Jenke
 * Hochschule f�r Angewandte Wissenschaften (HAW), Hamburg
 * CG1: Educational Java OpenGL framework with scene graph.
 */

package computergrafik.framework;

/**
 * This class accepts user inputs and uses them to control the camera movement.
 * 
 * @author pjenke
 * 
 */
public class CameraController {

    /**
     * Speed of the camera movement.
     */
    private static final double SPEED = 0.03f;

    /**
     * Reference to the camera object.
     */
    private final Camera camera;

    /**
     * Constructor.
     * 
     * @param c
     */
    public CameraController(Camera c) {
        camera = c;
    }

    /**
     * Mouse was moved (left button clicked) in X direction.
     * 
     * @param deltaX
     */
    public void mouseDeltaXLeftButton(final float deltaX) {
        camera.rotateVertically(deltaX * SPEED);
    }

    /**
     * Mouse was moved (left button down in Y direction.
     * 
     * @param deltaY
     */
    public void mouseDeltaYLeftButton(final float deltaY) {
        camera.rotateHorizontally(-deltaY * SPEED);
    }

    /**
     * Mouse was moved (right button clicked) in X direction.
     * 
     * @param deltaX
     */
    public void mouseDeltaXRightButton(final float deltaX) {
    }

    /**
     * Mouse was moved (right button down in Y direction.
     * 
     * @param deltaY
     */
    public void mouseDeltaYRightButton(final float deltaY) {
        camera.zoom(-deltaY * SPEED);
    }
}
