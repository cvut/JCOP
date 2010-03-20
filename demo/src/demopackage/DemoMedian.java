/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package demopackage;

import cz.cvut.felk.cig.jcop.algorithm.simulatedannealing.SimulatedAnnealing;
import cz.cvut.felk.cig.jcop.problem.sat.SAT;
import cz.cvut.felk.cig.jcop.solver.AlgorithmCompareSolver;
import cz.cvut.felk.cig.jcop.solver.MedianSolver;
import cz.cvut.felk.cig.jcop.solver.condition.TimeoutCondition;

import java.io.File;
import java.io.IOException;

/**
 * @author Ondrej Skalicka
 */
public class DemoMedian {
    public static void main(String[] args) throws IOException {
        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(new SAT(new File("data/sat/uf100-0100.cnf")));

        solver.addAlgorithm(new SimulatedAnnealing(10, 0.09));
        solver.addAlgorithm(new SimulatedAnnealing(10, 0.9));
        solver.addAlgorithm(new SimulatedAnnealing(10, 0.99));
        solver.addAlgorithm(new SimulatedAnnealing(10, 0.999));
        solver.addAlgorithm(new SimulatedAnnealing(10, 0.9999));
        solver.addAlgorithm(new SimulatedAnnealing(100, 0.09));
        solver.addAlgorithm(new SimulatedAnnealing(100, 0.9));
        solver.addAlgorithm(new SimulatedAnnealing(100, 0.99));
        solver.addAlgorithm(new SimulatedAnnealing(100, 0.999));
        solver.addAlgorithm(new SimulatedAnnealing(100, 0.9999));

        solver.addStopCondition(new TimeoutCondition(1000)); // every problem 250ms

        MedianSolver medianSolver = new MedianSolver(solver, 6);

        medianSolver.run();

        medianSolver.render();
    }
}
