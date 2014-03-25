package computergrafik.aufgabe2;

import com.jogamp.common.nio.Buffers;

import javax.media.opengl.GL2;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
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
    private float[] colorsArray;
    private FloatBuffer verticesBuf;
    private FloatBuffer colorsBuff;
    private IntBuffer indicesBuff;

    public VertexArrayRenderer(List<Vertex> vertices, List<Integer> indices){
        setData(vertices, indices);
    }

    public void setData(List<Vertex> vertices, List<Integer> indices){
        vertexArray = new float[vertices.size() * 3];
        for(int i = 0; i < vertices.size(); i++){
            Vertex vertex = vertices.get(i);
            int offset = i * 3;
            vertexArray[offset]      = (float) vertex.getPosition().get(0);
            vertexArray[offset + 1]  = (float) vertex.getPosition().get(1);
            vertexArray[offset + 2]  = (float) vertex.getPosition().get(2);
        }

        vertexIndices = new int[indices.size()];
        for(int i = 0; i < indices.size(); i++){
            vertexIndices[i] = indices.get(i);
        }

        colorsArray = new float[vertices.size() * 3];
        Arrays.fill(colorsArray, 0f);
    }

    public void draw(GL2 gl){
        gl.glEnableClientState( GL2.GL_VERTEX_ARRAY );
        //gl.glEnableClientState( GL2.GL_NORMAL_ARRAY );
        //gl.glEnableClientState(GL2.GL_COLOR_ARRAY);

        verticesBuf = Buffers.newDirectFloatBuffer(vertexArray);
        verticesBuf.put(vertexArray);
        verticesBuf.rewind();

        colorsBuff = Buffers.newDirectFloatBuffer(colorsArray);
        colorsBuff.put(colorsArray);
        colorsBuff.rewind();

        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, verticesBuf);
        //gl.glColorPointer(3, GL2.GL_FLOAT, 0, colorsBuff);

        indicesBuff = Buffers.newDirectIntBuffer(vertexIndices);
        indicesBuff.put(vertexIndices);
        indicesBuff.rewind();

        gl.glColor3f( 1f, 0f, 0f );
        gl.glDrawElements(gl.GL_TRIANGLES, vertexIndices.length, gl.GL_UNSIGNED_INT, indicesBuff);
        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
    }
}
