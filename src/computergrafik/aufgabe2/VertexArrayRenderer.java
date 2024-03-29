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
    private float[] vertexArray = new float[0];
    private float[] colorsArray = new float[0];
    private float[] normalsArray = new float[0];
    private FloatBuffer verticesBuf;
    private FloatBuffer colorsBuff;
    private FloatBuffer normalsBuff;

    public VertexArrayRenderer(){
    }

    /**
     * Die Methode bildet 3 Arrays: mit Vertices, mit den Normalen und mit Colors.
     * Die Vertices haben 3 Punkten. Deswegen vertexArray wird 3 Mal so lang, wie Anzahl der Vertices.
     * Jeder Dreieck besteht aus 3 Vertices. Die Normalen werden fuer jeder berechnet. Also normalsArray
     * wird 3x3 so lang, wie Anzahl der Dreiecken.
     * @param vertices
     * @param triangles
     */
    public void addData(List<Vertex> vertices, List<Triangle> triangles){
        addVertices(vertices);
        addNormals(triangles);
        setColors(vertices);
    }

    private void addVertices(List<Vertex> vertices){
        float[] newVertexArray  = new float[vertexArray.length + vertices.size() * 3];
        for(int i = 0; i < vertexArray.length; i++){
            newVertexArray[i] = vertexArray[i];
        }

        for(int i = 0; i < vertices.size(); i++){
            Vertex vertex = vertices.get(i);
            int offset = vertexArray.length + i * 3;
            newVertexArray[offset]      = vertex.getPosition().floatData()[0];
            newVertexArray[offset + 1]  = vertex.getPosition().floatData()[1];
            newVertexArray[offset + 2]  = vertex.getPosition().floatData()[2];
        }
        vertexArray = newVertexArray;
    }

    private void addNormals(List<Triangle> triangles){
        float[] newNormalsArray  = new float[normalsArray.length + triangles.size() * 9];
        for(int i = 0; i < normalsArray.length; i++){
            newNormalsArray[i] = normalsArray[i];
        }

        for(int i = 0; i < triangles.size(); i++){
            int offset = normalsArray.length + i * 9;
            for(int j = 0; j < 3; j++){
                newNormalsArray[offset]     = triangles.get(i).getNormal().floatData()[0];
                newNormalsArray[offset + 1] = triangles.get(i).getNormal().floatData()[1];
                newNormalsArray[offset + 2] = triangles.get(i).getNormal().floatData()[2];
                offset += 3;
            }
        }
        normalsArray = newNormalsArray;
    }

    private void setColors(List<Vertex> vertices){
        colorsArray = new float[colorsArray.length + vertices.size() * 3];
        Arrays.fill(colorsArray, 0f);
    }

    /**
     * Ruft alle noetige Methoden fuer den Start und End des Zeichnen der Objekte auf.
     * @param gl
     */
    public void draw(GL2 gl){
        start(gl);
        gl.glDrawArrays(GL2.GL_TRIANGLES, 0, vertexArray.length / 3);
        end(gl);
    }

    /**
     * Die Methode dient fuer die Pruefung ob alle noetige Arrays existieren.
     * @return: true oder false.
     */
    public boolean isSetup(){
        return vertexArray.length != 0 && normalsArray.length != 0 && colorsArray.length != 0;
    }

    /**
     * Startet das Zeichnen der Objekte
     * @param gl
     */
    private void start(GL2 gl){
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);
        gl.glEnableClientState(GL2.GL_COLOR_ARRAY);

        verticesBuf = createBuffer(vertexArray);
        colorsBuff  = createBuffer(colorsArray);
        normalsBuff = createBuffer(normalsArray);

        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, verticesBuf);
        gl.glNormalPointer(GL2.GL_FLOAT, 0, normalsBuff);
        gl.glColorPointer(3, GL2.GL_FLOAT, 0, colorsBuff);
    }

    /**
     * Beendet das Zeichnen der Objekte.
     * @param gl
     */
    private void end(GL2 gl){
        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);
        gl.glDisableClientState(GL2.GL_COLOR_ARRAY);
    }

    /**
     * Bildet einen Buffer aus Array
     * @param array
     * @return Buffer
     */
    private FloatBuffer createBuffer(float[] array){
        FloatBuffer buffer = Buffers.newDirectFloatBuffer(array);
        buffer.put(array);
        buffer.rewind();
        return buffer;
    }
}
