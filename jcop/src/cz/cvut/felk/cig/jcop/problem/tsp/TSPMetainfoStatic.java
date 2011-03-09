package cz.cvut.felk.cig.jcop.problem.tsp;

import java.util.List;

/**
 * TSPMetainfoStatic - class for calculation of static metainformation for TSP problem.
 *
 * @author oleg.kovarik@gmail.com
 */
public class TSPMetainfoStatic {
    List<City> cities;
    boolean available = false;  // statistics are available

    // statistics
    double average;     // average distance between cities
    double variance;    // variance of distances between cities
    long edgesCount;    // number of edges between cities (usually n*n)
    double variancenNNd; // variance of normalized nearest-neighbor distance

    public TSPMetainfoStatic(List<City> cities) {
        this.cities = cities;
        available = false;
    }

    public void calculateStatistics() {
        available = false;

        // average distance between cities
        average = 0;
        edgesCount = 0;
        for (City c: cities) {
            double nearest = Double.POSITIVE_INFINITY;
            for (Integer d: c.distances.values()) {
                nearest = Math.min(nearest, d);
                average += d;
                edgesCount++;
            }
        }
        average /= edgesCount;

        // variance of distances between cities
        variance = 0;
        for (City c: cities) {
            for (Integer d: c.distances.values()) {
                variance += (d - average) * (d - average);
            }
        }
        variance /= edgesCount;

        // variance of normalized nearest neighbor distance
        // min-max
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        double averageNNd = 0;
        for (City c: cities) {
            double nearest = Double.POSITIVE_INFINITY;
            for (Integer d: c.distances.values()) {
                nearest = Math.min(nearest, d);
            }
            min = Math.min(min, nearest);
            max = Math.max(max, nearest);
            averageNNd += nearest;
        }
        averageNNd /= edgesCount;

        // variance of normalized distances
        variancenNNd = 0;
        for (City c: cities) {
            double nearest = Double.POSITIVE_INFINITY;
            for (Integer d: c.distances.values()) {
                double x = (d-min)/(max-min);
                variancenNNd += (x - averageNNd) * (d - averageNNd);
            }
        }
        variancenNNd /= edgesCount;

        available = true;
    }

    public boolean isAvailable() { return available; }

}
