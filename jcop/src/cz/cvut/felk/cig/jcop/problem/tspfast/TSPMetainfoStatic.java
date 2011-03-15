/*
 * Copyright © 2010 by Oleg Kovarik. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.tspfast;
import java.util.ArrayList;

/**
 * TSPMetainfoStatic - class for calculation of static metainformation for TSP problem.
 *
 * @author oleg.kovarik@gmail.com
 */
public class TSPMetainfoStatic {
    ArrayList<Double[]> coordinates;
    int[][] distances;
    boolean available = false;  // statistics are available

    // statistics
    double average;     // average distance between cities
    double variance;    // variance of distances between cities
    long edgesCount;    // number of edges between cities (usually n*n)
    double variancenNNd; // variance of normalized nearest-neighbor distance

    public TSPMetainfoStatic(ArrayList<Double[]> coordinates, int[][] distances) {
        this.distances = distances;
        this.coordinates = coordinates;
        available = false;
    }

    public void calculateStatistics() {
        available = false;

        // average distance between cities
        average = 0;
        edgesCount = 0;
        for (int i = 0; i<distances.length; i++) {
            double nearest = Double.POSITIVE_INFINITY;
            for (int j = 0; j<distances.length; j++) {
                if (i != j) {
                    nearest = Math.min(nearest, distances[i][j]);
                    average += distances[i][j];
                    edgesCount++;
                }
            }
        }
        average /= edgesCount;

        // variance of distances between cities
        variance = 0;
        for (int i = 0; i<distances.length; i++) {
            for (int j = 0; j<distances.length; j++) {
                if (i != j) {
                    variance += (distances[i][j] - average) * (distances[i][j] - average);
                }
            }
        }
        variance /= edgesCount;

        // variance of normalized nearest neighbor distance
        // min-max
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        double averageNNd = 0;
        for (int i = 0; i<distances.length; i++) {
            double nearest = Double.POSITIVE_INFINITY;
            for (int j = 0; j<distances.length; j++) {
                if (i != j) {
                    nearest = Math.min(nearest, distances[i][j]);
                }
            }
            min = Math.min(min, nearest);
            max = Math.max(max, nearest);
            averageNNd += nearest;
        }
        averageNNd /= edgesCount;

        // variance of normalized distances
        variancenNNd = 0;
        for (int i = 0; i<distances.length; i++) {
            double nearest = Double.POSITIVE_INFINITY;
            for (int j = 0; j<distances.length; j++) {
                if (i != j) {
                    double x = (distances[i][j]-min)/(max-min);
                    variancenNNd += (x - averageNNd) * (distances[i][j] - averageNNd);
                }
            }
        }
        variancenNNd /= edgesCount;

        available = true;
    }

    public boolean isAvailable() { return available; }

}
