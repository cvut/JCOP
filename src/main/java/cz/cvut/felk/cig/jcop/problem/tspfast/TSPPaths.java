/*
 * Copyright © 2010 by Oleg Kovarik. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.tspfast;

/**
 * UTiols for TSP paths
 */
public class TSPPaths {

    // generates random permutation of cities
    public static void randomPathFast(int result[], int size) {
        //Random generator = new Random();

        for (int i = 0; i < size; i++) result[i] = i;
        for (int k = size - 1; k > 0; k--) {
            int w = (int)Math.floor(Math.random() * (k+1));
            int temp = result[w];
            result[w] = result[k];
            result[k] = temp;
        }
    }

}
