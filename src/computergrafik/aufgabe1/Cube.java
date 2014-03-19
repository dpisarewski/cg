package computergrafik.aufgabe1;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 * Created by pisare_d on 19.03.2014.
 */
public class Cube extends Node {


    public Cube(float x, float y, float z) {
        super(x, y, z);
    }

    public void draw(GL2 gl){
        gl.glBegin(GL.GL_TRIANGLES);
        for(Node child: children){
            child.draw(gl);
        }
        gl.glEnd();
    }
}
