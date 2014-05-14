package computergrafik.aufgabe5;

/**
 * Created by pisare_d on 14.05.2014.
 */
public class TextureCoordinate {
    private float u;
    private float v;

    public TextureCoordinate(float u, float v){
        this.u = u;
        this.v = v;
    }

    public float getU() {
        return u;
    }

    public void setU(float u) {
        this.u = u;
    }

    public float getV() {
        return v;
    }

    public void setV(float v) {
        this.v = v;
    }
}
