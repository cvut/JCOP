/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.jobshop;

import cz.cvut.felk.cig.jcop.problem.*;
import cz.cvut.felk.cig.jcop.util.JcopRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Job Shop Scheduling problem.
 * <p/>
 * Definition:
 * <p/>
 * We are given n jobs J<sub>1</sub>, J<sub>2</sub>, .. J<sub>n</sub>, each takes different amount of time to finish
 * (time is integer).
 * <p/>
 * Then we have m identical machines, each capable of running single job at one time.
 * <p/>
 * Goal is to assign jobs to machines such that total time is minimal.
 * <p/>
 * Worse possible time is time(J<sub>1</sub>)+time(J<sub>2</sub>)+...+time(J<sub>n</sub>). Best time cannot be better
 * than (time(J<sub>1</sub>)+time(J<sub>2</sub>)+...+time(J<sub>n</sub>))/m (rounded up), where m is number of
 * machines.
 * <p/>
 * Total time for this definition of Job Shop Scheduling problem is highest sum of times on one machine across all
 * machines.
 * <p/>
 * Starting configuration for Job Shop is to have all jobs on first machine, eg. worst possible time.
 * <p/>
 * There are several extension to JobShop problem, for example J<sub>i</sub> cannot start before J<sub>j</sub> starts
 * (or finishes).
 *
 * @author Ondrej Skalicka
 */
public class JobShop extends BaseProblem implements StartingConfigurationProblem, RandomConfigurationProblem, GlobalSearchProblem {
    /**
     * List of all jobs in this JobShop problem.
     */
    protected List<Job> jobs;
    /**
     * Number of machines.
     */
    protected int machines;
    /**
     * Starting Job Shop configuration - all jobs on first machine.
     */
    protected Configuration startingConfiguration;
    /**
     * List of all possible move operations.
     * <p/>
     * First index is which job to move, second is to which machine to move. Therefore outer list has {@link #dimension}
     * elements, while every inner list has {@link #machines} elements.
     */
    protected List<List<MoveOperation>> moveOperations;

    /**
     * Creates new JobShop problem with given number of machines and times for every job.
     *
     * @param jobs     time to finish for every job
     * @param machines number of machines
     * @throws IllegalArgumentException if number of machines is lower than 2 or number of jobs is lower than 2
     */
    public JobShop(List<Integer> jobs, int machines) throws IllegalArgumentException {
        if (machines < 2)
            throw new IllegalArgumentException("Number of machines must be at least 2, " + machines + " found.");
        if (jobs.size() < 2)
            throw new IllegalArgumentException("Number of jobs must be at least 2, " + jobs.size() + " found.");

        this.machines = machines;
        this.jobs = new ArrayList<Job>(jobs.size());
        this.dimension = jobs.size();

        List<Integer> startingConfigurationAttributes = new ArrayList<Integer>(this.dimension);
        this.moveOperations = new ArrayList<List<MoveOperation>>(this.dimension);

        for (int i = 0; i < this.dimension; ++i) {
            Job job = new Job(i, jobs.get(i));
            this.jobs.add(job);
            startingConfigurationAttributes.add(0);
            List<MoveOperation> operationList = new ArrayList<MoveOperation>(this.machines);
            for (int j = 0; j < this.machines; ++j) {
                operationList.add(new MoveOperation(job, j));
            }
            moveOperations.add(operationList);
        }

        this.startingConfiguration = new Configuration(startingConfigurationAttributes, "JobShop created");
    }

    /* Problem interface */

    public JobShopIterator getOperationIterator(Configuration configuration) {
        return new JobShopIterator(configuration, this);
    }

    public boolean isSolution(Configuration configuration) {
        // just check that every job runs on existing machine
        try {
            for (int i = 0; i < this.dimension; ++i) {
                if (configuration.valueAt(i) < 0 || configuration.valueAt(i) >= this.machines)
                    return false;
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        return true;
    }

    public Fitness getDefaultFitness() {
        return new JobShopFitness(this);
    }

    /* StartingConfigurationProblem interface */

    public Configuration getStartingConfiguration() {
        return this.startingConfiguration;
    }

    /* RandomConfigurationProblem interface */

    public Configuration getRandomConfiguration() {
        List<Integer> tmp = new ArrayList<Integer>(this.dimension);
        for (int i = 0; i < this.dimension; ++i)
            tmp.add(JcopRandom.nextInt(this.machines));
        return new Configuration(tmp, "Empty JobShop created (random)");
    }

    /* GlobalSearchProblem interface */

    public Integer getMaximum(int index) {
        return machines - 1;
    }
}
