/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.tsp;

import cz.cvut.felk.cig.jcop.problem.BaseFitness;
import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Fitness;

/**
 * Default fitness for TSP problem.
 *
 * @author Ondrej Skalicka
 */
public class TSPFitness extends BaseFitness implements Fitness {
    /**
     * TSP problem
     */
    protected TSP problem;
    /**
     * Maximal theoretical distance of tour. (actually maximal is usually lower, but for sure cannot be higher)
     */
    protected double maxDistance = 0;

    /**
     * Fitness requires Knapsack problem instance to be able to calculate
     *
     * @param problem reference to problem so to be able to calculate
     */
    public TSPFitness(TSP problem) {
        this.problem = problem;
        /* BaseFitness */
        for (City city : this.problem.cities) {
            double currentMaxDistance = 0;
            for (double distance : city.distances.values()) {
                currentMaxDistance = currentMaxDistance > distance ? currentMaxDistance : distance;
            }
            this.maxDistance += currentMaxDistance;
        }
        this.maxFitness = this.maxDistance;
        this.minFitness = -this.maxDistance;
    }

    /**
     * Default fitness for TSP algorithm.
     * <p/>
     * Returns positive number if configuration {@link TSP#isSolution(cz.cvut.felk.cig.jcop.problem.Configuration) is
     * solution}, number equals maxDistance-tourDistance.
     * <p/>
     * If configuration is not solution, returns negative tourDistance.
     *
     * @param configuration attributes to compute fitness
     * @return fitness of attributes
     */
    public double getValue(Configuration configuration) {
        double cost = this.problem.pathLength(configuration);
        if (this.problem.isSolution(configuration))
            return -cost;
        return Double.NEGATIVE_INFINITY;

        // if (this.problem.isSolution(configuration))
        //     return this.maxDistance - cost;
        // return -cost;
    }
}