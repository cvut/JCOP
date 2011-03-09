/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package demopackage;

import cz.cvut.felk.cig.jcop.algorithm.simulatedannealing.SimulatedAnnealing;
import cz.cvut.felk.cig.jcop.problem.bucket.Bucket;
import cz.cvut.felk.cig.jcop.problem.knapsack.Knapsack;
import cz.cvut.felk.cig.jcop.problem.sat.SAT;
import cz.cvut.felk.cig.jcop.problem.tsp.TSP;
import cz.cvut.felk.cig.jcop.solver.ProblemCompareSolver;
import cz.cvut.felk.cig.jcop.solver.condition.TimeoutCondition;

import java.io.File;
import java.io.IOException;

/**
 * @author Ondrej Skalicka
 */
public class DemoProblemCompare {
    public static void main(String[] args) throws IOException {
        ProblemCompareSolver solver = new ProblemCompareSolver(new SimulatedAnnealing());

        solver.addProblem(new Bucket(new int[]{14, 10, 6, 2, 8}, new int[]{0, 0, 1, 0, 0}, new int[]{12, 6, 4, 1, 8}));
        solver.addProblem(new Knapsack(new File("data/knapsack/knap_4.txt")));
        solver.addProblem(new SAT(new File("data/sat/valid-standard.cnf")));
        solver.addProblem(new TSP(new Integer[][]{{0, 5, 2}, {4, 0, 2}, {5, 6, 0},}));

        solver.addStopCondition(new TimeoutCondition(250)); // every problem 250ms

        solver.run();

        solver.render();
    }
}
