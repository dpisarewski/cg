/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 6, Aufgabe a-b
 */

package computergrafik.aufgabe6;

import computergrafik.framework.MathHelpers;
import computergrafik.framework.Vector3;

import java.util.List;

/**
 * Diese Klasse repräsentiert die Kamerapfadberechnung
 */
public class CameraPath {
    /**
     * Liste der Kontrollpunkte
     */
    private List<Vector3> controlPoints;

    /**
     * Schrittgröße
     */
    private float step;

    /**
     * Länge eines Kurvensegments
     */
    private float delta;

    /**
     * Aktuelle Position auf der S-Achse
     */
    private float distance = 0;


    public CameraPath(List<Vector3> controlPoints, float step){
        this.controlPoints  = controlPoints;
        this.step           = step;
        this.delta          = 1.0f / (controlPoints.size() - 1);
    }

    /**
     * Kalkuliert die Kameraposition für nächsten Schritt
     * @return Ortsvektor der Position
     */
    public Vector3 next(){
        distance += step;
        if((Math.abs(1 - distance) < MathHelpers.EPSILON) || (distance > 1)) return null;
        return function(distance);
    }

    /**
     * Gibt den lokalen Anfangskontrollpunkt zurück
     * @param s Position auf der S-Achse
     * @return Position des Kontrollpunkts
     */
    private Vector3 startPoint(float s){
        return controlPoints.get(index(s));
    }

    /**
     * Gibt den lokalen Endkontrollpunkt zurück
     * @param s Position auf der S-Achse
     * @return Position des Kontrollpunkts
     */
    private Vector3 endPoint(float s){
        return controlPoints.get(index(s) + 1);
    }

    /**
     * Rechnet den Index des aktuellen Kontrollpunktes
     * @param s Position auf der S-Achse
     * @return Index des Kontrollpunktes
     */
    private int index(float s){
        return (int)(s / delta);
    }

    /**
     * Rechnet lokale Distanz(vom aktuellen Kontrollpunkt)
     * @param s Position auf der S-Achse
     * @return Lokale Entfernung
     */
    private float t (float s){
        return (s - index(s) * delta) / delta;
    }

    /**
     * Die Methode berechnet das Ergebnis für Hermitfunktion
     * @param s Position auf der S-Achse
     * @return Vektor mit Ergebnis der Funktion
     */
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

    /**
     * Berechnet Ergebnis für Basisfunktion H0
     * @param t
     * @return Wert von H0
     */
    private float h0(float t){
        return (float) Math.pow(1 - t, 2) * (1 + 2 * t);
    }

    /**
     * Berechnet Ergebnis für Basisfunktion H1
     * @param t
     * @return Wert von H1
     */
    private float h1(float t){
        return (float) (t * Math.pow(1 - t, 2));
    }

    /**
     * Berechnet Ergebnis für Basisfunktion H2
     * @param t
     * @return Wert von H2
     */
    private float h2(float t){
        return -(t * t) * (1 - t);
    }

    /**
     * Berechnet Ergebnis für Basisfunktion H3
     * @param t
     * @return Wert von H3
     */
    private float h3(float t){
        return (3 - 2 * t) * t * t;
    }
}
