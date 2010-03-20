/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.knapsack;

import cz.cvut.felk.cig.jcop.problem.BaseFitness;
import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Fitness;

/**
 * Default fitness for Knapsack problem.
 *
 * @author Ondrej Skalicka
 */
public class KnapsackFitness extends BaseFitness implements Fitness {
    /**
     * Problem for which to calculate fitness.
     */
    protected Knapsack problem;

    /**
     * Fitness requires Knapsack problem instance to be able to calculate
     *
     * @param problem reference to problem so to be able to calculate
     */
    public KnapsackFitness(Knapsack problem) {
        this.problem = problem;
        /* BaseFitness */
        int maxPrice = 0;
        for (KnapsackItem knapsackItem : this.problem.knapsackItems) {
            maxPrice += knapsackItem.getPrice();
        }
        this.maxFitness = maxPrice;
        this.minFitness = -maxPrice - 1;
    }

    /**
     * Default fitness for knapsack algorithm.
     * <p/>
     * Returns positive number if weight if lower or equal to knapsack capacity. Returned number is price of
     * knapsackItems in knapsack.
     * <p/>
     * Returns negative number for overfilled knapsacks. Returned number is negative price of knapsackItems not in
     * knapsack -1. (eg. if all knapsackItems are in knapsack , -1 is returned).
     *
     * @param configuration attributes to compute fitness
     * @return fitness of attributes
     */
    public double getValue(Configuration configuration) {
        int pos = 0;
        int neg = -1;
        int capacity = this.problem.getCapacity();
        int weight = 0;
        KnapsackItem item;
        for (int i = 0; i < this.problem.getDimension(); i++) {
            item = this.problem.getKnapsackItems().get(i);
            if (configuration.valueAt(item.getIndex()) == 1) {
                pos += item.getPrice();
                weight += item.getWeight();
            } else neg -= item.getPrice();
        }
        if (weight > capacity) return neg;
        return pos;
    }
}
