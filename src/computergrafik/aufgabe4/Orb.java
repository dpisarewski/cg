package computergrafik.aufgabe4;

import java.util.Map;

/**
 * Created by pisare_d on 06.05.2014.
 */
public class Orb implements ImplicitFunction{

    private double radius;

    public Orb(double radius){
        this.radius = radius;
    }

    public double calculate(double x, double y, double z){
        return x * x + y * y + z * z - radius * radius;
    }
}
