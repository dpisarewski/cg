/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 4
 */

package computergrafik.aufgabe4;

/**
 * Class fuer Boysche Fläche.
 */
public class BoysSurface implements ImplicitFunction{

    public double calculate(double x, double y, double z) {
        return  64 * Math.pow(1 - z, 3) * Math.pow(z, 3) - 48 * Math.pow(1 - z, 2) * Math.pow(z, 2) *
                (3 * Math.pow(x, 2) + 3 * Math.pow(y, 2) + 2 * Math.pow(z, 2)) + 12 * (1 - z) * z *
                (27 * Math.pow(Math.pow(x, 2) + Math.pow(y, 2), 2) - 24 * Math.pow(z, 2) *
                (Math.pow(x, 2) + Math.pow(y, 2)) + 36 * Math.sqrt(2) * y * z * (Math.pow(y, 2) - 3 * Math.pow(x, 2)) +
                4 * Math.pow(z, 4)) + (9 * Math.pow(x, 2) + 9 * Math.pow(y, 2) - 2 * Math.pow(z, 2)) *
                (-81 * Math.pow(Math.pow(x, 2) + Math.pow(y, 2), 2) - 72 * Math.pow(z, 2) *
                (Math.pow(x, 2) + Math.pow(y, 2)) + 108 * Math.sqrt(2) * x * z * (Math.pow(x, 2) - 3 * Math.pow(y, 2)) +
                4 * Math.pow(z, 4));
    }
}
