package computergrafik.aufgabe1;

import javax.media.opengl.GL2;

/**
 * Created by pisare_d on 19.03.2014.
 */
public class TransformationNode {

    public static final int SCALE = 0;
    public static final int TRANSLATION = 1;
    int type;
    float a;
    float b;
    float c;

    public TransformationNode(int type, float a, float b, float c){
        this.type = type;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public void start(GL2 gl){
        gl.glPushMatrix();
        switch (type){
            case SCALE :
                gl.glScalef(a, b, c);
                break;
            case TRANSLATION :
                gl.glTranslatef(a, b, c);
                break;
        }
    }

    public void end(GL2 gl){
        gl.glPopMatrix();
    }
}
