/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 4
 */

package computergrafik.aufgabe4;

/**
 * Class implementiert ein Interface fuer verschiedene 3D Objekte
 */
public interface ImplicitFunction {

    public double calculate(double x, double y, double z);

}
