/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 4
 */

package computergrafik.aufgabe4;

/**
 * Class fuer Torus
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
