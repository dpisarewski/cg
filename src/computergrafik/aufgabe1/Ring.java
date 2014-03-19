package computergrafik.aufgabe1;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pisare_d on 19.03.2014.
 */
public class Ring extends Node {

    final float WIDTH = 0.25f;

    public Ring(float x, float y, float z) {
        super(x, y, z);
    }

    public void draw(GL2 gl){
        gl.glBegin(GL.GL_TRIANGLES);
        for(Node child: children){
            child.draw(gl);
        }
        gl.glEnd();
    }

    public void generateNodes(int sections){
        List<Node> nodes  = new ArrayList<Node>();
        float x = 0f, y = 0f, z = 0f;
        float step = (360f / sections);
        for(float i = 0f; i < 360; i += step){
            x = (float) Math.cos(Math.toRadians(i));
            y = ((i / step) % 2) == 0 ? WIDTH : -WIDTH;
            z = (float) Math.sin(Math.toRadians(i));
            nodes.add(new Node(x, y, z));
        }
        List<Node> tmp = new ArrayList<Node>();
        tmp.addAll(nodes.subList(1, nodes.size() - 1));
        tmp.add(nodes.get(nodes.size() - 1));
        tmp.addAll(nodes.subList(0, 1));
        tmp.addAll(nodes.subList(2, nodes.size() -1));
        tmp.add(nodes.get(nodes.size() - 1));
        tmp.addAll(nodes.subList(0, 2));
        nodes.addAll(tmp);
        this.children = nodes;
    }

    public void makeTriangles(List<Node> nodes){

    }
}
