/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm.simulatedannealing;

import cz.cvut.felk.cig.jcop.problem.BaseObjectiveProblem;
import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.knapsack.Knapsack;
import cz.cvut.felk.cig.jcop.solver.SimpleSolver;
import cz.cvut.felk.cig.jcop.solver.Solver;
import cz.cvut.felk.cig.jcop.solver.condition.IterationCondition;
import org.testng.annotations.Test;

/**
 * Tests proper behavior of {@link SimulatedAnnealing} algorithm.
 *
 * @author Ondrej Skalicka
 */
public class SimulatedAnnealingTest {
    @Test
    public void testOptimize() throws Exception {
        Solver solver = new SimpleSolver(new SimulatedAnnealing(), new Knapsack("9035 4 100 4 236 68 237 74 121 22 112"));

        solver.addStopCondition(new IterationCondition(1000));

        solver.run();
    }

    @Test
    public void testInitChain() throws Exception {
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing();
        Knapsack knapsack = new Knapsack("9035 4 100 4 236 68 237 74 121 22 112");
        Configuration configuration = knapsack.getRandomConfiguration();
        double fitness = knapsack.getDefaultFitness().getValue(configuration);

        simulatedAnnealing.init(new BaseObjectiveProblem(knapsack), configuration);

        assert simulatedAnnealing.getBestFitness() == fitness : "Expected fitness to be " + fitness + ", got " + simulatedAnnealing.getBestFitness();
        assert simulatedAnnealing.getBestConfiguration() == configuration : "Expected solution to be " + configuration + ", got " + simulatedAnnealing.getBestConfiguration();

        // try to perform optimize step - SA should not raise an exception
        simulatedAnnealing.optimize();
    }
}
