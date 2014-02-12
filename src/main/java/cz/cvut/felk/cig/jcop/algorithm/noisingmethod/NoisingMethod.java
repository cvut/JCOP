package cz.cvut.felk.cig.jcop.algorithm.noisingmethod;

import cz.cvut.felk.cig.jcop.algorithm.BaseAlgorithm;
import cz.cvut.felk.cig.jcop.algorithm.CannotContinueException;
import cz.cvut.felk.cig.jcop.algorithm.ChainAlgorithm;
import cz.cvut.felk.cig.jcop.algorithm.InvalidProblemException;
import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.ObjectiveProblem;
import cz.cvut.felk.cig.jcop.problem.Operation;
import cz.cvut.felk.cig.jcop.util.JcopRandom;
import org.apache.log4j.Logger;

/**
 * Noising method.
 * <p/>
 * Negative noise is added to the fitness function. Value of the noise variable is decreasing - in the beginning
 * the algorithm tries to explore fitness surface with large jumps. As the noise decreases, algorithm
 * has more accurate information and tends to move only to better solutions.
 *
 * Source:
 * Handbook of Metaheuristics 
 * http://www.springer.com/business+%26+management/operations+research/book/978-1-4419-1663-1
 *
 * @author Oleg Kovarik
 */
public class NoisingMethod extends BaseAlgorithm implements ChainAlgorithm {
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
    /**
     * Current noise level
     */
    protected double noise;
    /**
     * Noise coefficient
     */
    protected double noiseCoefficient;
    /**
     * Backup of {@link #noise} in case algorithm will be used repeatedly
     */
    protected double startingNoise;

    /**
     * Creates a new noising method instance with given noise coefficient and noise level.
     *
     * @param noiseCoefficient  noise coefficient
     * @param startingNoise     starting noise
     */
    public NoisingMethod(double startingNoise, double noiseCoefficient) {
        this.startingNoise = startingNoise;
        this.noiseCoefficient = noiseCoefficient;
    }

    /**
     * Creates new annealing with default anneal coefficient (0.999) and starting temperature (10.0).
     */
    public NoisingMethod() {
        this(1.0, 0.9);
    }

    public void init(ObjectiveProblem problem) throws InvalidProblemException {
        this.problem = problem;

        // NM requires either startingConfiguration or RandomStartingConfiguration problem
        if (!problem.hasStartingConfiguration() && !problem.hasRandomConfiguration())
            throw new InvalidProblemException("NoisingMethod algorithm requires either StartingConfigurationProblem or RandomConfigurationProblem");

        // fetch starting configuration
        if (problem.hasRandomConfiguration()) {
            this.activeConfiguration = problem.getRandomConfiguration();
        } else {
            this.activeConfiguration = problem.getStartingConfiguration();
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

        this.noise = this.startingNoise;
        this.setLabel("N=" + this.noise + ", C=" + this.noiseCoefficient);

        this.bestConfiguration = this.activeConfiguration;
        this.bestFitness = addNoise(this.fitness.getValue(this.activeConfiguration));

        // calculate fitness
        this.activeFitness = this.bestFitness;
        this.activeNormalizedFitness = this.fitness.normalize(activeFitness);
    }

    public double addNoise(double fitness) {
        double result;
        if (fitness > 0) {
            result = ((JcopRandom.nextDouble() * noise) * fitness);
        } else {
            result = (1.0 + (JcopRandom.nextDouble() * noise)) * fitness;
        }
        return result;
    }

    public void optimize() throws CannotContinueException {
        // fetch random operation
        Operation operation = this.problem.getOperationIterator(this.activeConfiguration).getRandomOperation();
        if (operation == null)
            throw new CannotContinueException("Unable to get random operation");

        // expand to new configuration
        Configuration newConfiguration = operation.execute(this.activeConfiguration);

        // calculate fitness
        double newFitnessNoNoise = this.fitness.getValue(newConfiguration);
        double newFitness = addNoise(newFitnessNoNoise);
        double newNormalizedFitness = this.fitness.normalize(newFitness);

        // new configuration is better or passes temperature test
        if (newNormalizedFitness > this.activeNormalizedFitness) {
            // set new configuration as active
            this.activeConfiguration = newConfiguration;
            this.activeFitness = newFitness;
            this.activeNormalizedFitness = newNormalizedFitness;
            // if it is best, set it as best
        }

        if (newFitnessNoNoise > this.bestFitness) {
            Logger.getLogger(this.getClass()).debug("Better solution " + newFitnessNoNoise + ", " + newConfiguration);
            this.bestConfiguration = newConfiguration;
            this.bestFitness = newFitnessNoNoise;
        }

        // anneal
        this.noise *= this.noiseCoefficient;
    }
}
