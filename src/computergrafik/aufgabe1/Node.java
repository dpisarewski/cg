package computergrafik.aufgabe1;

import computergrafik.framework.Vector3;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pisare_d on 19.03.2014.
 */
public class Node {
    List<Vector3> vectors;
    List<Node> children = new ArrayList<Node>();
    List<TransformationNode> transformations = new ArrayList<TransformationNode>();
    MaterialNode material;

    public Node(){
        this.vectors = new ArrayList<Vector3>();
    }

    public Node(List<Vector3> vectors){
        this.vectors = vectors;
    }

    public void addNode(Node node){
        children.add(node);
    }

    public void addVector(Vector3 vector){
        vectors.add(vector);
    }

    public void addTransformation(TransformationNode trans){
        transformations.add(trans);
    }

    public void setMaterial(MaterialNode material){
        this.material = material;
    }

    public void draw(GL2 gl){
        for(TransformationNode trans : transformations){
            trans.start(gl);
        }
        if(material != null) material.start(gl);
        renderVectors(gl);
        for(Node child: children){
            child.draw(gl);
        }
        if(material != null) material.end(gl);
        for(TransformationNode trans : transformations){
            trans.end(gl);
        }
    }

    public void makeNodes(List<Vector3> vectors){
        for(int i = 0; i < vectors.size(); i += 3){
            addNode(new Node(vectors.subList(i, i + 3)));
        }
    }

    protected void renderVectors(GL2 gl){
        if(!vectors.isEmpty()){
            Vector3 vec1 = vectors.get(0).subtract(vectors.get(1));
            Vector3 vec2 = vectors.get(0).subtract(vectors.get(2));
            Vector3 ortogonal = vec1.cross(vec2);
            gl.glNormal3f(ortogonal.floatData()[0], ortogonal.floatData()[1], ortogonal.floatData()[2]);
            gl.glBegin(GL.GL_TRIANGLES);
            for(Vector3 vector : vectors){
                gl.glVertex3f(vector.floatData()[0], vector.floatData()[1], vector.floatData()[2]);
            }
            gl.glEnd();
        }
    }
}
