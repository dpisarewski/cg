package computergrafik.aufgabe1;

import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pisare_d on 19.03.2014.
 */
public class Node {

    List<Node> children;
    float x;
    float y;
    float z;

    public Node(List<Node> children){
        this.x = 0f;
        this.y = 0f;
        this.z = 0f;
        this.children = children;
    }

    public Node(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
        this.children = new ArrayList<Node>();
    }

    public void addNode(Node node){
        this.children.add(node);
    }

    public void draw(GL2 gl){
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(x,y,z);
        for(Node child: children){
            child.draw(gl);
        }
    }
}
