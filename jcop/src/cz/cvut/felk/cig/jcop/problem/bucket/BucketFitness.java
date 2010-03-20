/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.bucket;

import cz.cvut.felk.cig.jcop.problem.BaseFitness;
import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Fitness;

/**
 * Default fitness of a configuration is number of buckets successfully matched against destination problem. If more
 * destination problems are possible, takes the best match.
 *
 * @author Ondrej Skalicka
 */
public class BucketFitness extends BaseFitness implements Fitness {
    /**
     * Bucket problem to generate fitness for.
     */
    protected Bucket problem;

    /**
     * Creates new BucketFitness for given problem.
     *
     * @param problem problem to generate BucketFitness for
     */
    public BucketFitness(Bucket problem) {
        this.problem = problem;
        /* BaseFitness */
        this.minFitness = 0.0;
        this.maxFitness = this.problem.getDimension();
    }

    public double getValue(Configuration configuration) {
        int bestScore = 0;
        for (Configuration destination : this.problem.getDestinations()) {
            int localScore = 0;
            for (int i = 0; i < this.problem.getDimension(); ++i) {
                if (destination.valueAt(i).equals(configuration.valueAt(i)))
                    localScore++;
            }
            if (localScore > bestScore) bestScore = localScore;
        }
        return bestScore;
    }
}
