package basicusage.algorithm;

import cz.cvut.felk.cig.jcop.algorithm.BaseAlgorithm;
import cz.cvut.felk.cig.jcop.algorithm.CannotContinueException;
import cz.cvut.felk.cig.jcop.algorithm.InvalidProblemException;
import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.ObjectiveProblem;
import cz.cvut.felk.cig.jcop.problem.OperationIterator;

public class PrimitiveAlgorithm extends BaseAlgorithm {

    public void init(ObjectiveProblem objectiveProblem) throws InvalidProblemException {
        if (!objectiveProblem.hasStartingConfiguration())
            throw new InvalidProblemException("Our algorithm requires StartingConfigurationProblem interface");

        this.problem = objectiveProblem;

        this.fitness = objectiveProblem.getDefaultFitness();

        this.bestConfiguration = objectiveProblem.getStartingConfiguration();
        this.bestFitness = this.fitness.getValue(this.bestConfiguration);

    }

    // expand with every operation
    public void optimize() throws CannotContinueException {
        Configuration nextBestConfiguration = null;
        double nextBestFitness = this.bestFitness;
        OperationIterator it = this.problem.getOperationIterator(this.bestConfiguration);

        while (it.hasNext()) {
            // expand to next state, calculate fitness
            Configuration configuration = it.next().execute(this.bestConfiguration);
            double fitness = this.fitness.getValue(configuration);

            // found better configuration
            if (fitness > this.bestFitness) {
                nextBestConfiguration = configuration;
                nextBestFitness = fitness;
            }
        }

        if (nextBestConfiguration == null) {
            throw new CannotContinueException("Cannot continue, no better configuration");
        }

        this.bestConfiguration = nextBestConfiguration;
        this.bestFitness = nextBestFitness;
    }
}
