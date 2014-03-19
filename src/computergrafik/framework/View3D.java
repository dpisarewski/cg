/**
 * Prof. Philipp Jenke
 * Hochschule f�r Angewandte Wissenschaften (HAW), Hamburg
 * CG1: Educational Java OpenGL framework with scene graph.
 */

package computergrafik.framework;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.GLReadBufferUtil;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.File;

/**
 * This class represents a view for 3D content
 * 
 * @author Philipp Jenke
 * 
 */
public class View3D extends JFrame implements GLEventListener, MouseListener,
        MouseMotionListener, KeyListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Size of the GL widget - width.
     */
    private static final int WIDGET_WIDTH = 640;

    /**
     * Size of the GL widget - height.
     */
    private static final int WIDGET_HEIGHT = 480;

    /**
     * Frame-rate of the rendering engine.
     */
    private static final int FPS = 60;

    /**
     * Renderer object
     */
    private final Renderer3D renderer3d;

    /**
     * Last coordinates of the mouse
     */
    private Vector3 lastMouseCoordinates = new Vector3(-1, -1, 0);

    /**
     * Camera object.
     */
    private Camera camera = new Camera();

    /**
     * Abstraction of the camera control
     */
    private CameraController cameraController = new CameraController(camera);

    /**
     * GL canvas.
     */
    private GLCanvas canvas = null;

    /**
     * Screenshot filename. A screenshot is saved, when this variable is not the
     * empty string.
     */
    private String screenshotFilename = "";

    /**
     * Remember the current button.
     */
    private int currentButton = -1;

    /**
     * Constructor
     */
    public View3D(ComputergrafikFrame renderFrame) {

        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        canvas = new GLCanvas(caps);

        renderer3d = new Renderer3D(camera, renderFrame);

        canvas.addGLEventListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        addKeyListener(this);
        canvas.addKeyListener(this);

        setTitle("Computer Graphics 1");
        setSize(WIDGET_WIDTH, WIDGET_HEIGHT);
        add(canvas);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        FPSAnimator animator = new FPSAnimator(canvas, FPS);
        animator.start();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        renderer3d.onDrawFrame(drawable);

        // Take sceenshot
        if (screenshotFilename.length() > 0) {
            GLReadBufferUtil screenshot = new GLReadBufferUtil(true, false);
            if (screenshot.readPixels(drawable.getGL(), false)) {
                screenshot.write(new File("test.png"));
                System.out.println("Saved screenshot to file "
                        + screenshotFilename);
            } else {
                System.out.println("Failed to write screenshot file.");
            }
            screenshotFilename = "";
        }
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        renderer3d.onSurfaceCreated(drawable);

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        renderer3d.onSurfaceChanged(drawable, w, h);
    }

    @Override
    public void mouseClicked(MouseEvent event) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent event) {
        currentButton = event.getButton();
        if (event.getButton() == MouseEvent.BUTTON1) {
            lastMouseCoordinates.set(0, event.getX());
            lastMouseCoordinates.set(1, event.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        lastMouseCoordinates = new Vector3(event.getX(), event.getY(), 0);
        currentButton = -1;
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        if (currentButton == MouseEvent.BUTTON1) {
            if ((lastMouseCoordinates.get(0) > 0)
                    && (lastMouseCoordinates.get(1) > 0)) {
                cameraController
                        .mouseDeltaXLeftButton((float) (event.getX() - lastMouseCoordinates
                                .get(0)));
                cameraController
                        .mouseDeltaYLeftButton((float) (event.getY() - lastMouseCoordinates
                                .get(1)));
            }
            lastMouseCoordinates.set(0, event.getX());
            lastMouseCoordinates.set(1, event.getY());
        } else if (currentButton == MouseEvent.BUTTON3) {
            if ((lastMouseCoordinates.get(0) > 0)
                    && (lastMouseCoordinates.get(1) > 0)) {
                cameraController
                        .mouseDeltaXRightButton((float) (event.getX() - lastMouseCoordinates
                                .get(0)));
                cameraController
                        .mouseDeltaYRightButton((float) (event.getY() - lastMouseCoordinates
                                .get(1)));
            }
            lastMouseCoordinates.set(0, event.getX());
            lastMouseCoordinates.set(1, event.getY());
        }
    }

    @Override
    public void mouseMoved(MouseEvent event) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == '+') {
            cameraController.mouseDeltaYRightButton(-1);
        } else if (e.getKeyChar() == '-') {
            cameraController.mouseDeltaYRightButton(1);
        } else if (e.getKeyChar() == 's') {
            takeScreenshot();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Take a screenshot of the GL canvas. If a file gets selected, the filename
     * is saved in the member variable screenshotFilename. The next time, the
     * scene is redrawn, the snapshot is taken and saved to that filename.
     */
    void takeScreenshot() {
        // Select file an save
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("PNG images",
                "PNG"));
        int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            final File screenshotFile = fc.getSelectedFile();
            if (screenshotFile != null) {
                screenshotFilename = screenshotFile.getAbsolutePath();
            }
        }
    }

    /**
     * Getter.
     */
    public Renderer3D getRenderer() {
        return renderer3d;
    }
}