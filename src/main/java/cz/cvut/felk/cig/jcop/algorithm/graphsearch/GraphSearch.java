/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm.graphsearch;

import cz.cvut.felk.cig.jcop.algorithm.BaseAlgorithm;
import cz.cvut.felk.cig.jcop.algorithm.CannotContinueException;
import cz.cvut.felk.cig.jcop.algorithm.InvalidProblemException;
import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.ObjectiveProblem;
import cz.cvut.felk.cig.jcop.problem.OperationIterator;

/**
 * Base for all search algorithms.
 * <p/>
 * Could be implemented as DFS, BFS etc.
 */
public abstract class GraphSearch extends BaseAlgorithm {
    /**
     * Queue to store opened configurations and check if configuration was ever opened yet.
     */
    protected GraphSearchQueue queue;

    public void init(ObjectiveProblem problem) throws InvalidProblemException {
        this.fitness = problem.getDefaultFitness();

        if (!problem.hasStartingConfiguration())
            throw new InvalidProblemException("Graph Search algorithms requires StartingConfigurationProblem");
        Configuration start = problem.getStartingConfiguration();
        this.problem = problem;

        /* Init queue */
        this.queue = this.initQueue();
        this.queue.add(start);

        this.bestConfiguration = start;
        this.bestFitness = this.fitness.getValue(start);
    }

    public void optimize() throws CannotContinueException {
        if (this.queue.size() < 1)
            throw new CannotContinueException("No more items in queue");

        Configuration headConfig = this.queue.fetch();

        double fitness = this.fitness.getValue(headConfig);
        if (fitness > this.bestFitness) {
            this.bestFitness = fitness;
            this.bestConfiguration = headConfig;
        }

        this.expand(headConfig);
    }

    /**
     * Expands one element.
     * <p/>
     * Adds new elements to queue (if they fail testPresence, eg. was not yet parsed).
     *
     * @param configuration attributes to expand
     */
    public void expand(Configuration configuration) {
        OperationIterator it = this.problem.getOperationIterator(configuration);
        while (it.hasNext()) {
            Configuration c = it.next().execute(configuration);
            if (!this.queue.testPresence(c)) this.queue.add(c);
        }
    }

    /**
     * Init and returns queue for this search algorithm.
     *
     * @return prepared empty (usually) queue
     */
    protected abstract GraphSearchQueue initQueue();

    /**
     * Gets size of queue
     *
     * @return size of queue
     */
    public int queueSize() {
        return this.queue.size();
    }

    /**
     * Returns queue of this graph search algorithm.
     *
     * @return queue currently used by this algorithm
     */
    public GraphSearchQueue getQueue() {
        return queue;
    }

    @Override
    public void cleanUp() {
        this.queue = null;
    }
}
