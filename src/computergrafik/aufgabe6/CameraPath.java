package computergrafik.aufgabe6;

import computergrafik.framework.MathHelpers;
import computergrafik.framework.Vector3;

import java.util.List;

/**
 * Created by pisare_d on 04.06.2014.
 */
public class CameraPath {
    private List<Vector3> controlPoints;
    private float step;
    private float delta;
    private float distance = 0;
    private int count = 1;


    public CameraPath(List<Vector3> controlPoints, float step){
        this.controlPoints  = controlPoints;
        this.step           = step;
        this.delta          = 1.0f / (controlPoints.size() - 1);
    }

    public Vector3 next(){
        distance += step;
        if((Math.abs(1 - distance) < MathHelpers.EPSILON) || (distance > 1)) return null;
        return function(distance);
    }

    private Vector3 startPoint(float s){
        return controlPoints.get(index(s));
    }

    private Vector3 endPoint(float s){
        return controlPoints.get(index(s) + 1);
    }

    private int index(float s){
        return (int)(s / delta);
    }

    private float t (float s){
        return (s - index(s) * delta) / delta;
    }

    private Vector3 function(float s){
        Vector3 param0 = startPoint(s).multiply(h0(t(s)));
        Vector3 param1, param2;

        if(index(s) == 0){
            param1 = controlPoints.get(1).subtract(controlPoints.get(0));
            param2 = controlPoints.get(index(s) + 2).subtract(controlPoints.get(index(s)));
        }else if(index(s) == controlPoints.size() - 2){
            param1 = controlPoints.get(index(s) + 1).subtract(controlPoints.get(index(s) - 1));
            param2 = endPoint(s).subtract(controlPoints.get(index(s)));
        }else{
            param1 = controlPoints.get(index(s) + 1).subtract(controlPoints.get(index(s) - 1));
            param2 = controlPoints.get(index(s) + 2).subtract(controlPoints.get(index(s)));
        }
        param1 = param1.multiply(h1(t(s)));
        param2 = param2.multiply(h2(t(s)));

        Vector3 param3 = endPoint(s).multiply(h3(t(s)));
        return param0.add(param1).add(param2).add(param3);
    }

    private float h0(float t){
        return (float) Math.pow(1 - t, 2) * (1 + 2 * t);
    }

    private float h1(float t){
        return (float) (t * Math.pow(1 - t, 2));
    }

    private float h2(float t){
        return -(t * t) * (1 - t);
    }

    private float h3(float t){
        return (3 - 2 * t) * t * t;
    }
}
