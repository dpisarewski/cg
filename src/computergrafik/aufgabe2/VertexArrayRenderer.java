package computergrafik.aufgabe2;

import javax.media.opengl.GL2;
import java.nio.FloatBuffer;
import java.util.List;

/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 2, Aufgabe c.
 */

public class VertexArrayRenderer {
    private int[] vertexIndices;
    private float[] vertexArray;

    public VertexArrayRenderer(){

    }

    public void setData(List<Vertex> vertices){
        vertexArray = new float[vertices.size()];
        for(int i = 0; i < vertices.size(); i++){
            Vertex vertex = vertices.get(i);
            vertexArray[i]      = (float) vertex.getPosition().get(0);
            vertexArray[i + 1]  = (float) vertex.getPosition().get(1);
            vertexArray[i + 2]  = (float) vertex.getPosition().get(2);
        }

        FloatBuffer tmpVerticesBuf = FloatBuffer.allocate(vertices.size());
        tmpVerticesBuf.put(vertexArray);
        tmpVerticesBuf.rewind();
    }

    public void start(GL2 gl){
        gl.glEnableClientState( GL2.GL_VERTEX_ARRAY );
        gl.glEnableClientState( GL2.GL_NORMAL_ARRAY );
    }

    public void end(GL2 gl){
        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);
    }
}
