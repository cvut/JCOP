/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

/**
 * Fitness is used to give an attributes' quality a numeric value. Higher values means better configurations, while
 * lower means worse.
 * <p/>
 * Every problem has its default fitness, used as pitfall if no other is specified. This is retrieved by {@link
 * cz.cvut.felk.cig.jcop.problem.Problem#getDefaultFitness()}.
 * <p/>
 * Algorithm may however choose to use different fitness instead. Also, solver is able to change fitness used in
 * optimization process.
 * <p/>
 * Rule of thumb is that highest priority has solver setting. If no one is set, algorithm's own fitness is used. If
 * algorithm has no specific fitness for this problem, then problem's default fitness is used.
 * <p/>
 * In order to achieve this, every algorithm should either take defaultFitness or supply its own in {@link
 * cz.cvut.felk.cig.jcop.algorithm.Algorithm#init(ObjectiveProblem)}. Then {@link cz.cvut.felk.cig.jcop.solver.Solver}
 * may call {@link cz.cvut.felk.cig.jcop.algorithm.Algorithm#setFitness(Fitness)} to override its value.
 *
 * @author Ondrej Skalicka
 */
public interface Fitness {

    /**
     * Measures fitness for one configuration.
     *
     * @param configuration configuration to have is fitness measured
     * @return fitness of configuration
     */
    public double getValue(Configuration configuration);

    /**
     * Returns fitness scaled to 0..1.
     *
     * @param fitness to be normalized
     * @return fitness of configuration in range (0,1) (inclusive)
     * @throws IllegalStateException if fitness is unable to return normalized value
     */
    public double normalize(double fitness) throws IllegalStateException;
}