/*
 * Copyright © 2010 by Oleg Kovarik. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.tspfast;

import cz.cvut.felk.cig.jcop.problem.BaseFitness;
import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Fitness;
import cz.cvut.felk.cig.jcop.problem.tsp.TSP;

/**
 * Default fitness for Fast TSP problem.
 *
 * @author Oleg Kovarik
 */
public class FastTSPFitness extends BaseFitness implements Fitness {
    /**
     * TSP problem
     */
    protected FastTSP problem;
    /**
     * Maximal theoretical distance of tour. (actually maximal is usually lower, but for sure cannot be higher)
     */
    protected double maxDistance = 0;

    /**
     * Fitness requires Knapsack problem instance to be able to calculate
     *
     * @param problem reference to problem so to be able to calculate
     */
    public FastTSPFitness(FastTSP problem) {
        this.problem = problem;
        /* BaseFitness */
        for (int i = 0; i<problem.distances.length; i++) {
            double currentMaxDistance = 0;
            for (int j = 0; j<problem.distances.length; j++) {
                if (i != j) {
                    currentMaxDistance = Math.max(currentMaxDistance, problem.distances[i][j]);
                }
            }
            this.maxDistance += currentMaxDistance;
        }
        this.maxFitness = this.maxDistance;
        this.minFitness = -this.maxDistance;
    }

    /**
     * Default fitness for FastTSP algorithm.
     * <p/>
     * Returns positive number if configuration {@link TSP#isSolution(cz.cvut.felk.cig.jcop.problem.Configuration) is
     * solution}, number equals -tourDistance.
     * <p/>
     * If configuration is not solution, returns negative infinity.
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