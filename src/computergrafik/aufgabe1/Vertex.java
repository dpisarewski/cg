package computergrafik.aufgabe1;

import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pisare_d on 19.03.2014.
 */
public class Vertex {

    List<Vertex> children;

    public Vertex(List<Vertex> children){
        this.children = children;
    }

    public Vertex(){
        this.children = new ArrayList<Vertex>();
    }

    public void draw(GL2 gl){
        for(Vertex child: children){
            child.draw(gl);
        }
    }



}
