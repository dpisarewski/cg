package computergrafik.aufgabe1;

import computergrafik.framework.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pisare_d on 19.03.2014.
 */
public class Cube extends Node {

    public void generateNodes(float size){
        float halfSize = size/2;
        List<Vector3> vectors = new ArrayList<Vector3>();
        vectors.add(new Vector3(-halfSize,halfSize,-halfSize));
        vectors.add(new Vector3(-halfSize,halfSize,halfSize));
        vectors.add(new Vector3(halfSize,halfSize,-halfSize));
        vectors.add(new Vector3(halfSize,halfSize,-halfSize));
        vectors.add(new Vector3(halfSize,halfSize,halfSize));
        vectors.add(new Vector3(-halfSize,halfSize,halfSize));
        vectors.add(new Vector3(-halfSize,-halfSize,halfSize));
        vectors.add(new Vector3(-halfSize,halfSize,halfSize));
        vectors.add(new Vector3(halfSize,halfSize,halfSize));
        vectors.add(new Vector3(-halfSize,-halfSize,halfSize));
        vectors.add(new Vector3(halfSize,halfSize,halfSize));
        vectors.add(new Vector3(halfSize,-halfSize,halfSize));
        vectors.add(new Vector3(-halfSize,-halfSize,halfSize));
        vectors.add(new Vector3(-halfSize,-halfSize,-halfSize));
        vectors.add(new Vector3(halfSize,-halfSize,-halfSize));
        vectors.add(new Vector3(-halfSize,-halfSize,halfSize));
        vectors.add(new Vector3(halfSize,-halfSize,-halfSize));
        vectors.add(new Vector3(halfSize,-halfSize,halfSize));
        vectors.add(new Vector3(-halfSize,-halfSize,-halfSize));
        vectors.add(new Vector3(-halfSize,halfSize,-halfSize));
        vectors.add(new Vector3(halfSize,halfSize,-halfSize));
        vectors.add(new Vector3(-halfSize,-halfSize,-halfSize));
        vectors.add(new Vector3(halfSize,halfSize,-halfSize));
        vectors.add(new Vector3(halfSize,-halfSize,-halfSize));
        vectors.add(new Vector3(halfSize,-halfSize,halfSize));
        vectors.add(new Vector3(halfSize,halfSize,halfSize));
        vectors.add(new Vector3(halfSize,halfSize,-halfSize));
        vectors.add(new Vector3(halfSize,-halfSize,halfSize));
        vectors.add(new Vector3(halfSize,halfSize,-halfSize));
        vectors.add(new Vector3(halfSize,-halfSize,-halfSize));
        vectors.add(new Vector3(-halfSize,-halfSize,halfSize));
        vectors.add(new Vector3(-halfSize,halfSize,halfSize));
        vectors.add(new Vector3(-halfSize,halfSize,-halfSize));
        vectors.add(new Vector3(-halfSize,-halfSize,halfSize));
        vectors.add(new Vector3(-halfSize,halfSize,-halfSize));
        vectors.add(new Vector3(-halfSize,-halfSize,-halfSize));
        makeNodes(vectors);
    }

}
