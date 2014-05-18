package computergrafik.aufgabe5;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.List;

/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 2, Aufgabe c.
 */

public class VertexArrayRenderer {
    private float[] vertexArray     = new float[0];
    private float[] colorsArray     = new float[0];
    private float[] normalsArray    = new float[0];
    private float[] textureArray    = new float[0];
    private ShadingType shadingType = ShadingType.FLAT;
    private String textureFilename;
    private Texture texture;

    public VertexArrayRenderer(){}

    public ShadingType getShadingType() {
        return shadingType;
    }

    public void setShadingType(ShadingType shadingType) {
        this.shadingType = shadingType;
    }

    /**
     * Die Methode fügt Daten aus einem Mesh in 4 Puffer: Vertices, Normalen, Texturkoordinaten und Farben.
     * Die Vertices haben 3 Punkten. Deswegen vertexArray wird 3 Mal so lang, wie Anzahl der Vertices.
     * Jeder Dreieck besteht aus 3 Vertices. Die Normalen werden fuer jeder berechnet. Also normalsArray
     * wird 3x3 so lang, wie Anzahl der Dreiecken.
     * @param mesh
     */
    public void addData(TriangleMesh mesh){
        this.textureFilename = mesh.getTextureFilename();
        addTextureCoordinates(mesh.getTextureCoordinates());
        addVertices(mesh.getVertices());
        addNormals(mesh.getTriangles(), mesh.getVertices());
        setColors(mesh.getVertices());
    }

    /**
     * Konvertiert eine Liste mit Vertices in Array mit Koordinaten
     * @param vertices Vertices
     */
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

    /**
     * Fügt Normalen entsprechend dem Shading Type in den Puffer ein
     * @param triangles Dreiecke
     * @param vertices Vertices
     */
    private void addNormals(List<Triangle> triangles, List<Vertex> vertices){
        float[] newNormalsArray = extendArray(normalsArray, triangles.size() * 9);
        switch(shadingType){
            case FLAT:      addFlatNormals(newNormalsArray, normalsArray.length, triangles); break;
            case GOURAUD:   addGouraudNormals(newNormalsArray, normalsArray.length, vertices); break;
        }
        normalsArray = newNormalsArray;
    }

    /**
     * Fügt Texturcoordinaten in den Puffer ein
     * @param textureCoordinates
     */
    private void addTextureCoordinates(List<TextureCoordinate> textureCoordinates){
        float[] newTextureArray = extendArray(normalsArray, textureCoordinates.size() * 2);
        for(int i = 0; i < textureCoordinates.size(); i++){
            newTextureArray[i * 2]      = textureCoordinates.get(i).getU();
            newTextureArray[i * 2 + 1]  = textureCoordinates.get(i).getV();
        }
        textureArray = newTextureArray;
    }


    /**
     * Fügt die Normalen, wenn Flat Shading benutzt wird
     * @param array Array mit Normalen
     * @param from Index
     * @param triangles Dreiecke
     */
    private void addFlatNormals(float[] array, int from, List<Triangle> triangles){
        for(int i = 0; i < triangles.size(); i++){
            int offset = from + i * 9;

            for(int j = 0; j < 3; j++){
                pushNormalValues(array, offset, triangles.get(i).getNormal().floatData());
                offset += 3;
            }
        }
    }

    /**
     * Fügt die Normalen, wenn Gouraud Shading benutzt wird
     * @param array Array mit Normalen
     * @param from Index
     * @param vertices Vertices
     */
    private void addGouraudNormals(float[] array, int from, List<Vertex> vertices){
        for(int i = 0; i < vertices.size(); i++){
            int offset = from + i * 3;
            pushNormalValues(array, offset, vertices.get(i).getNormal().floatData());
        }
    }

    /**
     * Fügt eine Normale
     * @param array Array mit Normalen
     * @param offset Index
     * @param values Werte
     */
    private void pushNormalValues(float[] array, int offset, float[] values){
        for(int i = 0; i < values.length; i++){
            array[offset + i] = values[i];
        }
    }

    /**
     * Füllt den Array mit Farben
     * @param vertices
     */
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
        loadTexture();
        texture.bind(gl);
        texture.enable(gl);

        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);
        gl.glEnableClientState(GL2.GL_COLOR_ARRAY);
        gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);

        FloatBuffer verticesBuf = createBuffer(vertexArray);
        FloatBuffer colorsBuff  = createBuffer(colorsArray);
        FloatBuffer normalsBuff = createBuffer(normalsArray);
        FloatBuffer textureBuff = createBuffer(textureArray);

        gl.glTexCoordPointer(2, GL2.GL_FLOAT, 0, textureBuff);
        gl.glVertexPointer(3,   GL2.GL_FLOAT, 0, verticesBuf);
        gl.glColorPointer(3,    GL2.GL_FLOAT, 0, colorsBuff);
        gl.glNormalPointer(     GL2.GL_FLOAT, 0, normalsBuff);
    }

    /**
     * Beendet das Zeichnen der Objekte.
     * @param gl
     */
    private void end(GL2 gl){
        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);
        gl.glDisableClientState(GL2.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
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

    /**
     * Erweitert ein Array
     * @param array Array
     * @param size Größe der Erweiterung
     * @return neues Array
     */
    private float[] extendArray(float[] array, int size){
        return Arrays.copyOf(array, array.length + size);
    }

    /**
     * Lädt Textur aus der Datei(nur einmal)
     */

    private void loadTexture(){
        if(texture == null) try {
            texture = TextureIO.newTexture(new File(textureFilename), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
