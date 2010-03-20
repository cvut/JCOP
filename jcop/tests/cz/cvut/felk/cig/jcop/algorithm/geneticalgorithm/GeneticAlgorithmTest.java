/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm.geneticalgorithm;

import cz.cvut.felk.cig.jcop.algorithm.InvalidProblemException;
import cz.cvut.felk.cig.jcop.problem.BaseObjectiveProblem;
import cz.cvut.felk.cig.jcop.problem.knapsack.Knapsack;
import cz.cvut.felk.cig.jcop.problem.tsp.TSP;
import org.testng.annotations.Test;

/**
 * Tests proper behavior of {@link cz.cvut.felk.cig.jcop.algorithm.geneticalgorithm.GeneticAlgorithm} result.
 *
 * @author Ondrej Skalicka
 */
public class GeneticAlgorithmTest {
    @Test
    public void testInit() throws Exception {
        GeneticAlgorithm geneticAlgorithm;

        try {
            geneticAlgorithm = new GeneticAlgorithm(11, 0.1);
            assert false : "Expected exception, but none was risen.";
        } catch (IllegalArgumentException ignore) {

        }
        try {
            geneticAlgorithm = new GeneticAlgorithm(0, 0.1);
            assert false : "Expected exception, but none was risen.";
        } catch (IllegalArgumentException ignore) {

        }
        try {
            geneticAlgorithm = new GeneticAlgorithm(-1, 0.1);
            assert false : "Expected exception, but none was risen.";
        } catch (IllegalArgumentException ignore) {

        }
        try {
            geneticAlgorithm = new GeneticAlgorithm(10, 0.1);
            geneticAlgorithm.init(new BaseObjectiveProblem(new TSP(new Integer[][]{{1, 2}, {3, 4}})));
            assert false : "Expected exception, but none was risen.";
        } catch (InvalidProblemException ignore) {

        }

        geneticAlgorithm = new GeneticAlgorithm(10, 0.1);
        geneticAlgorithm.init(new BaseObjectiveProblem(new Knapsack("9035 4 100 4 236 68 237 74 121 22 112")));
        assert geneticAlgorithm.population.size() == 10 : "Expected population size to be 10, " + geneticAlgorithm.population.size() + " found";
    }

}
