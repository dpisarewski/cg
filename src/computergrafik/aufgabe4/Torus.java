package computergrafik.aufgabe4;

/**
 * Created by pisare_d on 06.05.2014.
 */
public class Torus implements ImplicitFunction {

    private double externalRadius;
    private double internalRadius;

    public Torus(double externalRadius, double internalRadius){
        this.externalRadius = externalRadius;
        this.internalRadius = internalRadius;
    }

    public double calculate(double x, double y, double z) {
        return Math.pow(x * x + y * y + z * z + Math.pow(externalRadius, 2) - Math.pow(internalRadius, 2), 2) - 4 * Math.pow(externalRadius, 2) * (x * x + y * y);
    }
}
