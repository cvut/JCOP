/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package demopackage;

import cz.cvut.felk.cig.jcop.algorithm.simulatedannealing.SimulatedAnnealing;
import cz.cvut.felk.cig.jcop.problem.sat.SAT;
import cz.cvut.felk.cig.jcop.result.render.ExceptionRender;
import cz.cvut.felk.cig.jcop.result.render.JFreeChartRender;
import cz.cvut.felk.cig.jcop.result.render.SimpleCompareRender;
import cz.cvut.felk.cig.jcop.solver.AlgorithmCompareSolver;
import cz.cvut.felk.cig.jcop.solver.condition.IterationCondition;

import java.io.File;
import java.io.IOException;

/**
 * @author Ondrej Skalicka
 */
public class DemoJFreeChart {
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
        
        solver.addStopCondition(new IterationCondition(20000)); // every problem 20000 iterations
        solver.addListener(new JFreeChartRender("Demo JFreeChartRender"));
        solver.addRender(new SimpleCompareRender());
        solver.addRender(new ExceptionRender());

        solver.run();

        solver.render();
    }
}