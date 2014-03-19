package computergrafik.aufgabe1;

import computergrafik.framework.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pisare_d on 19.03.2014.
 */
public class Ring extends Node {

    float width;
    float radius;

    public Ring(float radius, float width){
        this.radius = radius;
        this.width  = width;
    }

    public void generateNodes(int sections){
        List<Vector3> vectors  = new ArrayList<Vector3>();
        float x = 0f, y = 0f, z = 0f;
        float step = (360f / sections);
        for(float i = 0f; i < 360; i += step){
            x = (float) Math.cos(Math.toRadians(i)) * radius;
            y = ((i / step) % 2) == 0 ? width : -width;
            z = (float) Math.sin(Math.toRadians(i)) * radius;
            vectors.add(new Vector3(x, y, z));
        }
        List<Vector3> tmp = new ArrayList<Vector3>();
        tmp.addAll(vectors.subList(1, vectors.size() - 1));
        tmp.add(vectors.get(vectors.size() - 1));
        tmp.addAll(vectors.subList(0, 1));

        tmp.addAll(vectors.subList(2, vectors.size() -1));
        tmp.add(vectors.get(vectors.size() - 1));
        tmp.addAll(vectors.subList(0, 2));

        vectors.addAll(tmp);
        makeNodes(vectors);
    }
}
