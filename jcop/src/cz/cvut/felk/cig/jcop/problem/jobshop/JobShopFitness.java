/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.jobshop;

import cz.cvut.felk.cig.jcop.problem.BaseFitness;
import cz.cvut.felk.cig.jcop.problem.Configuration;

import java.util.Arrays;

/**
 * Calculates fitness for a JobShop problem configuration.
 * <p/>
 * Fitness is always positive number and is calculated in this way: 1. maximal possible time (maxTime) is such that all
 * jobs are in single queue. 2. minimal theoretical time is maximal divided by number of machines (round up). 3. fitness
 * is negative time of a configuration + maxTime
 * <p/>
 * (minimal possible fitness is then 0, maximal fitness is maxTime-minTime)
 *
 * @author Ondrej Skalicka
 */
public class JobShopFitness extends BaseFitness {
    /**
     * Problem for which to compute fitness.
     */
    protected JobShop problem;
    /**
     * Maximal possible time (all jobs in one queue).
     */
    protected int maxTime;

    /**
     * Creates new JobShopFitness for specified problem.
     *
     * @param problem problem to calculate fitness for
     */
    public JobShopFitness(JobShop problem) {
        this.problem = problem;
        this.maxTime = 0;
        for (Job job : problem.jobs) this.maxTime += job.time;
        this.maxFitness = this.maxTime - (Math.ceil(this.maxTime / (double) problem.machines));
        this.minFitness = 0;
    }

    public double getValue(Configuration configuration) {
        // time counter for every machine
        int[] times = new int[this.problem.machines];
        Arrays.fill(times, 0);
        for (int i = 0; i < this.problem.getDimension(); ++i) {
            times[configuration.valueAt(i)] += this.problem.jobs.get(i).getTime();
        }
        int max = 0;
        for (int i : times) max = i > max ? i : max;
        return this.maxTime - max;
    }
}
