package cz.cvut.felk.cig.jcop.algorithm.randomsearch;

import cz.cvut.felk.cig.jcop.algorithm.BaseAlgorithm;
import cz.cvut.felk.cig.jcop.algorithm.CannotContinueException;
import cz.cvut.felk.cig.jcop.algorithm.ChainAlgorithm;
import cz.cvut.felk.cig.jcop.algorithm.InvalidProblemException;
import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.ObjectiveProblem;
import org.apache.log4j.Logger;

/**
 * Random search algorithm.
 * <p />
 * In each step of {@link #init(cz.cvut.felk.cig.jcop.problem.ObjectiveProblem)}, algorithm generates random solution.
 * <p />
 * Useful as worst case benchmark.
 *
 * @author Oleg Kovarik
 */
public class RandomSearch extends BaseAlgorithm implements ChainAlgorithm {
    /**
     * Active configuration to be expanded
     */
    protected Configuration activeConfiguration;
    /**
     * Fitness of active configuration
     */
    protected double activeFitness;
    /**
     * Normalized fitness of active configuration
     */
    protected double activeNormalizedFitness;

    public void init(ObjectiveProblem problem) throws InvalidProblemException {
        this.problem = problem;

        // RS requires either startingConfiguration or RandomStartingConfiguration problem
        if (!problem.hasStartingConfiguration() && !problem.hasRandomConfiguration())
            throw new InvalidProblemException("RandomSearch algorithm requires either StartingConfigurationProblem or RandomConfigurationProblem");

        // fetch starting configuration
        if (problem.hasStartingConfiguration()) {
            this.activeConfiguration = problem.getStartingConfiguration();
        } else {
            this.activeConfiguration = problem.getRandomConfiguration();
        }

        this.initCommon();
    }

    public void init(ObjectiveProblem problem, Configuration activeConfiguration) {
        this.problem = problem;

        this.activeConfiguration = activeConfiguration;

        this.initCommon();
    }

    /**
     * Part of init common to all initialization types.
     */
    protected void initCommon () {
        this.fitness = problem.getDefaultFitness();

        this.bestConfiguration = this.activeConfiguration;
        this.bestFitness = this.fitness.getValue(this.activeConfiguration);

        // calculate fitness
        this.activeFitness = this.fitness.getValue(this.activeConfiguration);
        this.activeNormalizedFitness = this.fitness.normalize(activeFitness);
    }

    public void optimize() throws CannotContinueException {

        // generate new random configuration
        if (!problem.hasRandomConfiguration())
            throw new InvalidProblemException("RandomSearch algorithm requires RandomConfigurationProblem");
        Configuration newConfiguration = problem.getRandomConfiguration();

        // calculate fitness
        double newFitness = this.fitness.getValue(newConfiguration);
        double newNormalizedFitness = this.fitness.normalize(newFitness);

        // set new configuration as active
        this.activeConfiguration = newConfiguration;
        this.activeFitness = newFitness;
        this.activeNormalizedFitness = newNormalizedFitness;
        // if it is best, set it as best
        if (newFitness > this.bestFitness) {
            Logger.getLogger(this.getClass()).debug("Better solution " + newFitness + ", " + newConfiguration);
            this.bestConfiguration = newConfiguration;
            this.bestFitness = newFitness;
        }
    }
}
