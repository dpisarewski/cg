/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 4
 */
package computergrafik.aufgabe4;

import java.util.Map;

/**
 * Class fuer Kugel
 */
public class Orb implements ImplicitFunction{

    private double radius;

    public Orb(double radius){
        this.radius = radius;
    }

    public double calculate(double x, double y, double z){
        return (x * x) + (y * y) + (z * z) - (radius * radius);
    }
}
