/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 7
 */

package computergrafik.aufgabe7;

import computergrafik.framework.ComputergrafikFrame;

import javax.media.opengl.GL2;

public class Aufgabe7Frame extends ComputergrafikFrame {

    public Aufgabe7Frame(int timerInverval) {
        super(timerInverval);
    }

    @Override
    public void drawGlContent(GL2 gl) {
    }

    /**
     * Erstellt Meshes und eine Szene aus diesen Meshes
     */
    private void construct(){

    }

    /**
     * Bewegt die Kamera durch den Kamerapfad
     */
    @Override
    protected void timerTick() {

    }

    /**
     * Program entry point.
     */
    public static void main(String[] args) {
        (new Aufgabe7Frame(50)).construct();
    }
}
