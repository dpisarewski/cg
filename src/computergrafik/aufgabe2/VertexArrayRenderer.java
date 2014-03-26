package computergrafik.aufgabe2;

import com.jogamp.common.nio.Buffers;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.fixedfunc.GLLightingFunc;
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
    private int[]   vertexIndices;
    private float[] vertexArray;
    private float[] colorsArray;
    private float[] normalsArray;
    private FloatBuffer verticesBuf;
    private FloatBuffer colorsBuff;
    private FloatBuffer normalsBuff;
    private IntBuffer indicesBuff;

    public VertexArrayRenderer(){
    }

    public void setData(List<Vertex> vertices, List<Triangle> triangles){
        vertexArray     = new float[vertices.size() * 3];
        for(int i = 0; i < vertices.size(); i++){
            Vertex vertex = vertices.get(i);
            int offset = i * 3;
            vertexArray[offset]      = vertex.getPosition().floatData()[0];
            vertexArray[offset + 1]  = vertex.getPosition().floatData()[1];
            vertexArray[offset + 2]  = vertex.getPosition().floatData()[2];
        }

        vertexIndices = new int[triangles.size() * 3];
        for(int i = 0; i < triangles.size(); i++){
            int offset = i * 3;
            vertexIndices[offset]       = triangles.get(i).getIndices()[0];
            vertexIndices[offset + 1]   = triangles.get(i).getIndices()[1];
            vertexIndices[offset + 2]   = triangles.get(i).getIndices()[2];
        }

        normalsArray = new float[triangles.size() * 9];
        for(int i = 0; i < triangles.size(); i++){
            int offset = i * 9;
            for(int j = 0; j < 3; j++){
                normalsArray[offset]     = triangles.get(i).getNormal().floatData()[0];
                normalsArray[offset + 1] = triangles.get(i).getNormal().floatData()[1];
                normalsArray[offset + 2] = triangles.get(i).getNormal().floatData()[2];
                offset += 3;
            }
        }

        colorsArray = new float[vertices.size() * 3];
        Arrays.fill(colorsArray, 0f);
    }

    public void draw(GL2 gl){
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);
        //gl.glEnableClientState(GL2.GL_COLOR_ARRAY);

        verticesBuf = Buffers.newDirectFloatBuffer(vertexArray);
        verticesBuf.put(vertexArray);
        verticesBuf.rewind();

        colorsBuff = Buffers.newDirectFloatBuffer(colorsArray);
        colorsBuff.put(colorsArray);
        colorsBuff.rewind();

        indicesBuff = Buffers.newDirectIntBuffer(vertexIndices);
        indicesBuff.put(vertexIndices);
        indicesBuff.rewind();

        normalsBuff = Buffers.newDirectFloatBuffer(normalsArray);
        normalsBuff.put(normalsArray);
        normalsBuff.rewind();

        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, verticesBuf);
        gl.glNormalPointer(GL2.GL_FLOAT, 0, normalsBuff);
        //gl.glColorPointer(3, GL2.GL_FLOAT, 0, colorsBuff);

        gl.glColor3f( 1f, 0f, 0f );
        gl.glDrawArrays(GL2.GL_TRIANGLES, 0, vertexArray.length / 3);

        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);
    }

    public boolean isSetup(){
        return vertexArray != null && normalsArray != null && colorsArray != null;
    }
}
