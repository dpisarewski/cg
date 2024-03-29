/**
 * Praktikum Computergrafik, SS2014
 * Gruppe: Dieter Pisarewski (dieter.pisarewski@haw-hamburg.de)
 * 		   Vasily Uchakin (vasily.uchakin@haw-hamburg.de)
 * Aufgabenblatt 4
 */

package computergrafik.aufgabe4;

import computergrafik.aufgabe2.*;
import computergrafik.framework.Vector3;

import java.util.*;

/**
 * Class implementiert Iterierung auf alle Voxelzellen. Volume und die Aufloesung kann man
 * aendern.
 */
public class MarchingCubes {

    /**
     * casesLookupTable
     */
    private static int faces[] = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, 0, 8, 3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, 0, 1, 9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1,
            8, 3, 9, 8, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 2, 11, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 8, 3, 1, 2, 11, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, 9, 2, 11, 0, 2, 9, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, 2, 8, 3, 2, 11, 8, 11, 9, 8, -1, -1, -1, -1,
            -1, -1, 3, 10, 2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            0, 10, 2, 8, 10, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 9, 0, 2,
            3, 10, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 10, 2, 1, 9, 10, 9,
            8, 10, -1, -1, -1, -1, -1, -1, 3, 11, 1, 10, 11, 3, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, 0, 11, 1, 0, 8, 11, 8, 10, 11, -1, -1, -1, -1,
            -1, -1, 3, 9, 0, 3, 10, 9, 10, 11, 9, -1, -1, -1, -1, -1, -1, 9, 8,
            11, 11, 8, 10, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, 7, 8, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, 3, 0, 7, 3, 4, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, 0, 1, 9, 8, 4, 7, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, 4, 1, 9, 4, 7, 1, 7, 3, 1, -1, -1, -1, -1, -1, -1,
            1, 2, 11, 8, 4, 7, -1, -1, -1, -1, -1, -1, -1, -1, -1, 3, 4, 7, 3,
            0, 4, 1, 2, 11, -1, -1, -1, -1, -1, -1, 9, 2, 11, 9, 0, 2, 8, 4, 7,
            -1, -1, -1, -1, -1, -1, 2, 11, 9, 2, 9, 7, 2, 7, 3, 7, 9, 4, -1,
            -1, -1, 8, 4, 7, 3, 10, 2, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10,
            4, 7, 10, 2, 4, 2, 0, 4, -1, -1, -1, -1, -1, -1, 9, 0, 1, 8, 4, 7,
            2, 3, 10, -1, -1, -1, -1, -1, -1, 4, 7, 10, 9, 4, 10, 9, 10, 2, 9,
            2, 1, -1, -1, -1, 3, 11, 1, 3, 10, 11, 7, 8, 4, -1, -1, -1, -1, -1,
            -1, 1, 10, 11, 1, 4, 10, 1, 0, 4, 7, 10, 4, -1, -1, -1, 4, 7, 8, 9,
            0, 10, 9, 10, 11, 10, 0, 3, -1, -1, -1, 4, 7, 10, 4, 10, 9, 9, 10,
            11, -1, -1, -1, -1, -1, -1, 9, 5, 4, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, 9, 5, 4, 0, 8, 3, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, 0, 5, 4, 1, 5, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, 8, 5,
            4, 8, 3, 5, 3, 1, 5, -1, -1, -1, -1, -1, -1, 1, 2, 11, 9, 5, 4, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, 3, 0, 8, 1, 2, 11, 4, 9, 5, -1, -1,
            -1, -1, -1, -1, 5, 2, 11, 5, 4, 2, 4, 0, 2, -1, -1, -1, -1, -1, -1,
            2, 11, 5, 3, 2, 5, 3, 5, 4, 3, 4, 8, -1, -1, -1, 9, 5, 4, 2, 3, 10,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 10, 2, 0, 8, 10, 4, 9, 5,
            -1, -1, -1, -1, -1, -1, 0, 5, 4, 0, 1, 5, 2, 3, 10, -1, -1, -1, -1,
            -1, -1, 2, 1, 5, 2, 5, 8, 2, 8, 10, 4, 8, 5, -1, -1, -1, 11, 3, 10,
            11, 1, 3, 9, 5, 4, -1, -1, -1, -1, -1, -1, 4, 9, 5, 0, 8, 1, 8, 11,
            1, 8, 10, 11, -1, -1, -1, 5, 4, 0, 5, 0, 10, 5, 10, 11, 10, 0, 3,
            -1, -1, -1, 5, 4, 8, 5, 8, 11, 11, 8, 10, -1, -1, -1, -1, -1, -1,
            9, 7, 8, 5, 7, 9, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9, 3, 0, 9,
            5, 3, 5, 7, 3, -1, -1, -1, -1, -1, -1, 0, 7, 8, 0, 1, 7, 1, 5, 7,
            -1, -1, -1, -1, -1, -1, 1, 5, 3, 3, 5, 7, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, 9, 7, 8, 9, 5, 7, 11, 1, 2, -1, -1, -1, -1, -1, -1, 11,
            1, 2, 9, 5, 0, 5, 3, 0, 5, 7, 3, -1, -1, -1, 8, 0, 2, 8, 2, 5, 8,
            5, 7, 11, 5, 2, -1, -1, -1, 2, 11, 5, 2, 5, 3, 3, 5, 7, -1, -1, -1,
            -1, -1, -1, 7, 9, 5, 7, 8, 9, 3, 10, 2, -1, -1, -1, -1, -1, -1, 9,
            5, 7, 9, 7, 2, 9, 2, 0, 2, 7, 10, -1, -1, -1, 2, 3, 10, 0, 1, 8, 1,
            7, 8, 1, 5, 7, -1, -1, -1, 10, 2, 1, 10, 1, 7, 7, 1, 5, -1, -1, -1,
            -1, -1, -1, 9, 5, 8, 8, 5, 7, 11, 1, 3, 11, 3, 10, -1, -1, -1, 5,
            7, 0, 5, 0, 9, 7, 10, 0, 1, 0, 11, 10, 11, 0, 10, 11, 0, 10, 0, 3,
            11, 5, 0, 8, 0, 7, 5, 7, 0, 10, 11, 5, 7, 10, 5, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, 11, 6, 5, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, 0, 8, 3, 5, 11, 6, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            9, 0, 1, 5, 11, 6, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 8, 3, 1,
            9, 8, 5, 11, 6, -1, -1, -1, -1, -1, -1, 1, 6, 5, 2, 6, 1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, 1, 6, 5, 1, 2, 6, 3, 0, 8, -1, -1, -1,
            -1, -1, -1, 9, 6, 5, 9, 0, 6, 0, 2, 6, -1, -1, -1, -1, -1, -1, 5,
            9, 8, 5, 8, 2, 5, 2, 6, 3, 2, 8, -1, -1, -1, 2, 3, 10, 11, 6, 5,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, 0, 8, 10, 2, 0, 11, 6, 5,
            -1, -1, -1, -1, -1, -1, 0, 1, 9, 2, 3, 10, 5, 11, 6, -1, -1, -1,
            -1, -1, -1, 5, 11, 6, 1, 9, 2, 9, 10, 2, 9, 8, 10, -1, -1, -1, 6,
            3, 10, 6, 5, 3, 5, 1, 3, -1, -1, -1, -1, -1, -1, 0, 8, 10, 0, 10,
            5, 0, 5, 1, 5, 10, 6, -1, -1, -1, 3, 10, 6, 0, 3, 6, 0, 6, 5, 0, 5,
            9, -1, -1, -1, 6, 5, 9, 6, 9, 10, 10, 9, 8, -1, -1, -1, -1, -1, -1,
            5, 11, 6, 4, 7, 8, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, 3, 0, 4,
            7, 3, 6, 5, 11, -1, -1, -1, -1, -1, -1, 1, 9, 0, 5, 11, 6, 8, 4, 7,
            -1, -1, -1, -1, -1, -1, 11, 6, 5, 1, 9, 7, 1, 7, 3, 7, 9, 4, -1,
            -1, -1, 6, 1, 2, 6, 5, 1, 4, 7, 8, -1, -1, -1, -1, -1, -1, 1, 2, 5,
            5, 2, 6, 3, 0, 4, 3, 4, 7, -1, -1, -1, 8, 4, 7, 9, 0, 5, 0, 6, 5,
            0, 2, 6, -1, -1, -1, 7, 3, 9, 7, 9, 4, 3, 2, 9, 5, 9, 6, 2, 6, 9,
            3, 10, 2, 7, 8, 4, 11, 6, 5, -1, -1, -1, -1, -1, -1, 5, 11, 6, 4,
            7, 2, 4, 2, 0, 2, 7, 10, -1, -1, -1, 0, 1, 9, 4, 7, 8, 2, 3, 10, 5,
            11, 6, -1, -1, -1, 9, 2, 1, 9, 10, 2, 9, 4, 10, 7, 10, 4, 5, 11, 6,
            8, 4, 7, 3, 10, 5, 3, 5, 1, 5, 10, 6, -1, -1, -1, 5, 1, 10, 5, 10,
            6, 1, 0, 10, 7, 10, 4, 0, 4, 10, 0, 5, 9, 0, 6, 5, 0, 3, 6, 10, 6,
            3, 8, 4, 7, 6, 5, 9, 6, 9, 10, 4, 7, 9, 7, 10, 9, -1, -1, -1, 11,
            4, 9, 6, 4, 11, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, 11, 6, 4, 9,
            11, 0, 8, 3, -1, -1, -1, -1, -1, -1, 11, 0, 1, 11, 6, 0, 6, 4, 0,
            -1, -1, -1, -1, -1, -1, 8, 3, 1, 8, 1, 6, 8, 6, 4, 6, 1, 11, -1,
            -1, -1, 1, 4, 9, 1, 2, 4, 2, 6, 4, -1, -1, -1, -1, -1, -1, 3, 0, 8,
            1, 2, 9, 2, 4, 9, 2, 6, 4, -1, -1, -1, 0, 2, 4, 4, 2, 6, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, 8, 3, 2, 8, 2, 4, 4, 2, 6, -1, -1, -1,
            -1, -1, -1, 11, 4, 9, 11, 6, 4, 10, 2, 3, -1, -1, -1, -1, -1, -1,
            0, 8, 2, 2, 8, 10, 4, 9, 11, 4, 11, 6, -1, -1, -1, 3, 10, 2, 0, 1,
            6, 0, 6, 4, 6, 1, 11, -1, -1, -1, 6, 4, 1, 6, 1, 11, 4, 8, 1, 2, 1,
            10, 8, 10, 1, 9, 6, 4, 9, 3, 6, 9, 1, 3, 10, 6, 3, -1, -1, -1, 8,
            10, 1, 8, 1, 0, 10, 6, 1, 9, 1, 4, 6, 4, 1, 3, 10, 6, 3, 6, 0, 0,
            6, 4, -1, -1, -1, -1, -1, -1, 6, 4, 8, 10, 6, 8, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, 7, 11, 6, 7, 8, 11, 8, 9, 11, -1, -1, -1, -1,
            -1, -1, 0, 7, 3, 0, 11, 7, 0, 9, 11, 6, 7, 11, -1, -1, -1, 11, 6,
            7, 1, 11, 7, 1, 7, 8, 1, 8, 0, -1, -1, -1, 11, 6, 7, 11, 7, 1, 1,
            7, 3, -1, -1, -1, -1, -1, -1, 1, 2, 6, 1, 6, 8, 1, 8, 9, 8, 6, 7,
            -1, -1, -1, 2, 6, 9, 2, 9, 1, 6, 7, 9, 0, 9, 3, 7, 3, 9, 7, 8, 0,
            7, 0, 6, 6, 0, 2, -1, -1, -1, -1, -1, -1, 7, 3, 2, 6, 7, 2, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, 2, 3, 10, 11, 6, 8, 11, 8, 9, 8, 6, 7,
            -1, -1, -1, 2, 0, 7, 2, 7, 10, 0, 9, 7, 6, 7, 11, 9, 11, 7, 1, 8,
            0, 1, 7, 8, 1, 11, 7, 6, 7, 11, 2, 3, 10, 10, 2, 1, 10, 1, 7, 11,
            6, 1, 6, 7, 1, -1, -1, -1, 8, 9, 6, 8, 6, 7, 9, 1, 6, 10, 6, 3, 1,
            3, 6, 0, 9, 1, 10, 6, 7, -1, -1, -1, -1, -1, -1, -1, -1, -1, 7, 8,
            0, 7, 0, 6, 3, 10, 0, 10, 6, 0, -1, -1, -1, 7, 10, 6, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, 7, 6, 10, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, 3, 0, 8, 10, 7, 6, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, 0, 1, 9, 10, 7, 6, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, 8, 1, 9, 8, 3, 1, 10, 7, 6, -1, -1, -1, -1, -1, -1, 11, 1, 2,
            6, 10, 7, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 2, 11, 3, 0, 8, 6,
            10, 7, -1, -1, -1, -1, -1, -1, 2, 9, 0, 2, 11, 9, 6, 10, 7, -1, -1,
            -1, -1, -1, -1, 6, 10, 7, 2, 11, 3, 11, 8, 3, 11, 9, 8, -1, -1, -1,
            7, 2, 3, 6, 2, 7, -1, -1, -1, -1, -1, -1, -1, -1, -1, 7, 0, 8, 7,
            6, 0, 6, 2, 0, -1, -1, -1, -1, -1, -1, 2, 7, 6, 2, 3, 7, 0, 1, 9,
            -1, -1, -1, -1, -1, -1, 1, 6, 2, 1, 8, 6, 1, 9, 8, 8, 7, 6, -1, -1,
            -1, 11, 7, 6, 11, 1, 7, 1, 3, 7, -1, -1, -1, -1, -1, -1, 11, 7, 6,
            1, 7, 11, 1, 8, 7, 1, 0, 8, -1, -1, -1, 0, 3, 7, 0, 7, 11, 0, 11,
            9, 6, 11, 7, -1, -1, -1, 7, 6, 11, 7, 11, 8, 8, 11, 9, -1, -1, -1,
            -1, -1, -1, 6, 8, 4, 10, 8, 6, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            3, 6, 10, 3, 0, 6, 0, 4, 6, -1, -1, -1, -1, -1, -1, 8, 6, 10, 8, 4,
            6, 9, 0, 1, -1, -1, -1, -1, -1, -1, 9, 4, 6, 9, 6, 3, 9, 3, 1, 10,
            3, 6, -1, -1, -1, 6, 8, 4, 6, 10, 8, 2, 11, 1, -1, -1, -1, -1, -1,
            -1, 1, 2, 11, 3, 0, 10, 0, 6, 10, 0, 4, 6, -1, -1, -1, 4, 10, 8, 4,
            6, 10, 0, 2, 9, 2, 11, 9, -1, -1, -1, 11, 9, 3, 11, 3, 2, 9, 4, 3,
            10, 3, 6, 4, 6, 3, 8, 2, 3, 8, 4, 2, 4, 6, 2, -1, -1, -1, -1, -1,
            -1, 0, 4, 2, 4, 6, 2, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 9, 0,
            2, 3, 4, 2, 4, 6, 4, 3, 8, -1, -1, -1, 1, 9, 4, 1, 4, 2, 2, 4, 6,
            -1, -1, -1, -1, -1, -1, 8, 1, 3, 8, 6, 1, 8, 4, 6, 6, 11, 1, -1,
            -1, -1, 11, 1, 0, 11, 0, 6, 6, 0, 4, -1, -1, -1, -1, -1, -1, 4, 6,
            3, 4, 3, 8, 6, 11, 3, 0, 3, 9, 11, 9, 3, 11, 9, 4, 6, 11, 4, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, 4, 9, 5, 7, 6, 10, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, 0, 8, 3, 4, 9, 5, 10, 7, 6, -1, -1, -1, -1, -1,
            -1, 5, 0, 1, 5, 4, 0, 7, 6, 10, -1, -1, -1, -1, -1, -1, 10, 7, 6,
            8, 3, 4, 3, 5, 4, 3, 1, 5, -1, -1, -1, 9, 5, 4, 11, 1, 2, 7, 6, 10,
            -1, -1, -1, -1, -1, -1, 6, 10, 7, 1, 2, 11, 0, 8, 3, 4, 9, 5, -1,
            -1, -1, 7, 6, 10, 5, 4, 11, 4, 2, 11, 4, 0, 2, -1, -1, -1, 3, 4, 8,
            3, 5, 4, 3, 2, 5, 11, 5, 2, 10, 7, 6, 7, 2, 3, 7, 6, 2, 5, 4, 9,
            -1, -1, -1, -1, -1, -1, 9, 5, 4, 0, 8, 6, 0, 6, 2, 6, 8, 7, -1, -1,
            -1, 3, 6, 2, 3, 7, 6, 1, 5, 0, 5, 4, 0, -1, -1, -1, 6, 2, 8, 6, 8,
            7, 2, 1, 8, 4, 8, 5, 1, 5, 8, 9, 5, 4, 11, 1, 6, 1, 7, 6, 1, 3, 7,
            -1, -1, -1, 1, 6, 11, 1, 7, 6, 1, 0, 7, 8, 7, 0, 9, 5, 4, 4, 0, 11,
            4, 11, 5, 0, 3, 11, 6, 11, 7, 3, 7, 11, 7, 6, 11, 7, 11, 8, 5, 4,
            11, 4, 8, 11, -1, -1, -1, 6, 9, 5, 6, 10, 9, 10, 8, 9, -1, -1, -1,
            -1, -1, -1, 3, 6, 10, 0, 6, 3, 0, 5, 6, 0, 9, 5, -1, -1, -1, 0, 10,
            8, 0, 5, 10, 0, 1, 5, 5, 6, 10, -1, -1, -1, 6, 10, 3, 6, 3, 5, 5,
            3, 1, -1, -1, -1, -1, -1, -1, 1, 2, 11, 9, 5, 10, 9, 10, 8, 10, 5,
            6, -1, -1, -1, 0, 10, 3, 0, 6, 10, 0, 9, 6, 5, 6, 9, 1, 2, 11, 10,
            8, 5, 10, 5, 6, 8, 0, 5, 11, 5, 2, 0, 2, 5, 6, 10, 3, 6, 3, 5, 2,
            11, 3, 11, 5, 3, -1, -1, -1, 5, 8, 9, 5, 2, 8, 5, 6, 2, 3, 8, 2,
            -1, -1, -1, 9, 5, 6, 9, 6, 0, 0, 6, 2, -1, -1, -1, -1, -1, -1, 1,
            5, 8, 1, 8, 0, 5, 6, 8, 3, 8, 2, 6, 2, 8, 1, 5, 6, 2, 1, 6, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, 1, 3, 6, 1, 6, 11, 3, 8, 6, 5, 6, 9, 8,
            9, 6, 11, 1, 0, 11, 0, 6, 9, 5, 0, 5, 6, 0, -1, -1, -1, 0, 3, 8, 5,
            6, 11, -1, -1, -1, -1, -1, -1, -1, -1, -1, 11, 5, 6, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, 5, 11, 7, 5, 10, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, 10, 5, 11, 10, 7, 5, 8, 3, 0, -1, -1,
            -1, -1, -1, -1, 5, 10, 7, 5, 11, 10, 1, 9, 0, -1, -1, -1, -1, -1,
            -1, 11, 7, 5, 11, 10, 7, 9, 8, 1, 8, 3, 1, -1, -1, -1, 10, 1, 2,
            10, 7, 1, 7, 5, 1, -1, -1, -1, -1, -1, -1, 0, 8, 3, 1, 2, 7, 1, 7,
            5, 7, 2, 10, -1, -1, -1, 9, 7, 5, 9, 2, 7, 9, 0, 2, 2, 10, 7, -1,
            -1, -1, 7, 5, 2, 7, 2, 10, 5, 9, 2, 3, 2, 8, 9, 8, 2, 2, 5, 11, 2,
            3, 5, 3, 7, 5, -1, -1, -1, -1, -1, -1, 8, 2, 0, 8, 5, 2, 8, 7, 5,
            11, 2, 5, -1, -1, -1, 9, 0, 1, 5, 11, 3, 5, 3, 7, 3, 11, 2, -1, -1,
            -1, 9, 8, 2, 9, 2, 1, 8, 7, 2, 11, 2, 5, 7, 5, 2, 1, 3, 5, 3, 7, 5,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 8, 7, 0, 7, 1, 1, 7, 5, -1,
            -1, -1, -1, -1, -1, 9, 0, 3, 9, 3, 5, 5, 3, 7, -1, -1, -1, -1, -1,
            -1, 9, 8, 7, 5, 9, 7, -1, -1, -1, -1, -1, -1, -1, -1, -1, 5, 8, 4,
            5, 11, 8, 11, 10, 8, -1, -1, -1, -1, -1, -1, 5, 0, 4, 5, 10, 0, 5,
            11, 10, 10, 3, 0, -1, -1, -1, 0, 1, 9, 8, 4, 11, 8, 11, 10, 11, 4,
            5, -1, -1, -1, 11, 10, 4, 11, 4, 5, 10, 3, 4, 9, 4, 1, 3, 1, 4, 2,
            5, 1, 2, 8, 5, 2, 10, 8, 4, 5, 8, -1, -1, -1, 0, 4, 10, 0, 10, 3,
            4, 5, 10, 2, 10, 1, 5, 1, 10, 0, 2, 5, 0, 5, 9, 2, 10, 5, 4, 5, 8,
            10, 8, 5, 9, 4, 5, 2, 10, 3, -1, -1, -1, -1, -1, -1, -1, -1, -1, 2,
            5, 11, 3, 5, 2, 3, 4, 5, 3, 8, 4, -1, -1, -1, 5, 11, 2, 5, 2, 4, 4,
            2, 0, -1, -1, -1, -1, -1, -1, 3, 11, 2, 3, 5, 11, 3, 8, 5, 4, 5, 8,
            0, 1, 9, 5, 11, 2, 5, 2, 4, 1, 9, 2, 9, 4, 2, -1, -1, -1, 8, 4, 5,
            8, 5, 3, 3, 5, 1, -1, -1, -1, -1, -1, -1, 0, 4, 5, 1, 0, 5, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, 8, 4, 5, 8, 5, 3, 9, 0, 5, 0, 3, 5, -1,
            -1, -1, 9, 4, 5, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4,
            10, 7, 4, 9, 10, 9, 11, 10, -1, -1, -1, -1, -1, -1, 0, 8, 3, 4, 9,
            7, 9, 10, 7, 9, 11, 10, -1, -1, -1, 1, 11, 10, 1, 10, 4, 1, 4, 0,
            7, 4, 10, -1, -1, -1, 3, 1, 4, 3, 4, 8, 1, 11, 4, 7, 4, 10, 11, 10,
            4, 4, 10, 7, 9, 10, 4, 9, 2, 10, 9, 1, 2, -1, -1, -1, 9, 7, 4, 9,
            10, 7, 9, 1, 10, 2, 10, 1, 0, 8, 3, 10, 7, 4, 10, 4, 2, 2, 4, 0,
            -1, -1, -1, -1, -1, -1, 10, 7, 4, 10, 4, 2, 8, 3, 4, 3, 2, 4, -1,
            -1, -1, 2, 9, 11, 2, 7, 9, 2, 3, 7, 7, 4, 9, -1, -1, -1, 9, 11, 7,
            9, 7, 4, 11, 2, 7, 8, 7, 0, 2, 0, 7, 3, 7, 11, 3, 11, 2, 7, 4, 11,
            1, 11, 0, 4, 0, 11, 1, 11, 2, 8, 7, 4, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, 4, 9, 1, 4, 1, 7, 7, 1, 3, -1, -1, -1, -1, -1, -1, 4, 9, 1,
            4, 1, 7, 0, 8, 1, 8, 7, 1, -1, -1, -1, 4, 0, 3, 7, 4, 3, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, 4, 8, 7, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, 9, 11, 8, 11, 10, 8, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, 3, 0, 9, 3, 9, 10, 10, 9, 11, -1, -1, -1, -1, -1, -1,
            0, 1, 11, 0, 11, 8, 8, 11, 10, -1, -1, -1, -1, -1, -1, 3, 1, 11,
            10, 3, 11, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 2, 10, 1, 10, 9,
            9, 10, 8, -1, -1, -1, -1, -1, -1, 3, 0, 9, 3, 9, 10, 1, 2, 9, 2,
            10, 9, -1, -1, -1, 0, 2, 10, 8, 0, 10, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, 3, 2, 10, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            2, 3, 8, 2, 8, 11, 11, 8, 9, -1, -1, -1, -1, -1, -1, 9, 11, 2, 0,
            9, 2, -1, -1, -1, -1, -1, -1, -1, -1, -1, 2, 3, 8, 2, 8, 11, 0, 1,
            8, 1, 11, 8, -1, -1, -1, 1, 11, 2, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, 1, 3, 8, 9, 1, 8, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, 0, 9, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 3,
            8, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };

    /**
     * Kanten zwichen den Eckpunkten
     */
    private int[] edges = {
            0, 1,   //e0
            1, 2,   //e1
            2, 3,   //e2
            3, 0,   //e3
            4, 5,   //e4
            5, 6,   //e5
            6, 7,   //e6
            7, 4,   //e7
            0, 4,   //e8
            1, 5,   //e9
            3, 7,   //e10
            2, 6,   //e11
    };

    /**
     * List mit vertices
     */
    private List<Vertex> vertices    = new ArrayList<>();

    /**
     * List mit Dreiecken
     */
    private List<Triangle> triangles = new ArrayList<>();

    /**
     * Implizite Funktion
     */
    private ImplicitFunction function;

    /**
     * Isowert
     */
    private double iso;

    /**
     * Konstruktor
     * @param function Implizite Funktion
     * @param iso Isowert
     */
    public MarchingCubes(ImplicitFunction function, double iso){
        this.function = function;
        this.iso = iso;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    /**
     * Hier werden die Dreiecke gebaut. Zuerst wird caseIndex gerechnet, danach
     * Anfangs- und Endindex in casesLookupTable. Die zugehoerige werte werden
     * in indices[] kopiert.
     * @param vectors List mit Vektoren
     * @param values List mit Daten
     */
    public void createTriangles(List<Vector3> vectors, List<Double> values) {
        int caseIndex = 0;

        for (int i = 0; i < values.size(); i++) {
            caseIndex += (values.get(i) > iso ? 1 : 0) * Math.pow(2, i);
        }

        int from = caseIndex * 15;
        int to =   (caseIndex + 1) * 15 - 1;

        int[] indices = Arrays.copyOfRange(faces, from, to + 1);
        makeVertices(vectors, values, indices);
    }

    /**
     * Hier wird TriangleMesh fuer jede einzelne Voxelzelle generiert.
     * @param width Breite
     * @param height Hoehe
     * @param length Laenge
     * @param resolutionX Aufloesung fuer X Achse
     * @param resolutionY Aufloesung fuer Y Achse
     * @param resolutionZ Aufloesung fuer Z Achse
     * @return TriangleMesh
     */
    public TriangleMesh createMesh(float width, float height, float length, float resolutionX, float resolutionY, float resolutionZ){
        float startX    = -width/2;
        float startY    = -height/2;
        float startZ    = -length/2;
        float endX      = width/2;
        float endY      = height/2;
        float endZ      = length/2;
        float stepX     = width / resolutionX;
        float stepY     = height / resolutionY;
        float stepZ     = length / resolutionZ;

        for(float i = startX; i < endX; i += width / resolutionX){
            for(float j = startY; j < endY; j += height / resolutionY){
                for(float k = startZ; k < endZ; k += length / resolutionZ){
                    createTriangles(calculateVectors(i, j, k, stepX, stepY, stepZ), calculateValues(i, j, k, stepX, stepY, stepZ));
                }
            }
        }
        makeTriangles();

        TriangleMesh mesh = new TriangleMesh();
        mesh.setVertices(getVertices());
        mesh.setTriangles(getTriangles());
        mesh.generateStructure();

        return mesh;
    }

    /**
     * Berechnung von Daten fuer eine Voxelzelle.
     * @param i Anfangskoordinate X
     * @param j Anfangskoordinate Y
     * @param k Anfangskoordinate Z
     * @param stepX Die Groesse des Voxelzelles auf X Achse
     * @param stepY Die Groesse des Voxelzelles auf Y Achse
     * @param stepZ Die Groesse des Voxelzelles auf Z Achse
     * @return Daten
     */
    private List<Double> calculateValues(float i, float j, float k, float stepX, float stepY, float stepZ) {
        List<Double> values = new ArrayList<>();
        values.add(function.calculate(i, j, k));
        values.add(function.calculate(i + stepX, j, k));
        values.add(function.calculate(i + stepX, j + stepY, k));
        values.add(function.calculate(i, j + stepY, k));
        values.add(function.calculate(i, j, k + stepZ));
        values.add(function.calculate(i + stepX, j, k + stepZ));
        values.add(function.calculate(i + stepX, j + stepY, k + stepZ));
        values.add(function.calculate(i, j + stepY, k + stepZ));
        return values;
    }

    /**
     * Berechnung von Vektoren fuer eine Voxelzelle.
     * @param i Anfangskoordinate X
     * @param j Anfangskoordinate Y
     * @param k Anfangskoordinate Z
     * @param stepX Die Groesse des Voxelzelles auf X Achse
     * @param stepY Die Groesse des Voxelzelles auf Y Achse
     * @param stepZ Die Groesse des Voxelzelles auf Z Achse
     * @return Vektoren
     */
    private List<Vector3> calculateVectors(float i, float j, float k, float stepX, float stepY, float stepZ){
        List<Vector3> vectors = new ArrayList<>();
        vectors.add(new Vector3(i, j, k));
        vectors.add(new Vector3(i + stepX, j, k));
        vectors.add(new Vector3(i + stepX, j + stepY, k));
        vectors.add(new Vector3(i, j + stepY, k));
        vectors.add(new Vector3(i, j, k + stepZ));
        vectors.add(new Vector3(i + stepX, j, k + stepZ));
        vectors.add(new Vector3(i + stepX, j + stepY, k + stepZ));
        vectors.add(new Vector3(i, j + stepY, k + stepZ));
        return vectors;
    }

    /**
     * Berechnung des Positions eines Dreieck-Eckpunktes auf einer Kante.
     * (Formel aus den Vorlesungsfolien)
     * @param vectors List mit Vektoren
     * @param values List mit Daten
     * @param indices Indexen
     */
    private void makeVertices(List<Vector3> vectors, List<Double> values, int[] indices) {
        for(int i = 0; i < indices.length; i++){
            int index = indices[i];
            if(index != -1){
                int i1 = edges[index * 2];
                int i2 = edges[index * 2 + 1];
                Vector3 p1 = vectors.get(i1);
                Vector3 p2 = vectors.get(i2);
                double t = (iso - values.get(i1)) / (values.get(i2) - values.get(i1));
                Vector3 p = p1.multiply(1 - t).add(p2.multiply(t));
                vertices.add(new Vertex(p));
            }
        }
    }

    /**
     * Die Methode bildet aus Vectices (Punkten) Dreiecke.
     */
    public void makeTriangles(){
        for(int i = 0; i < vertices.size(); i += 3){
            triangles.add(new Triangle(new int[]{i, i + 1, i + 2}));
        }
    }
}
