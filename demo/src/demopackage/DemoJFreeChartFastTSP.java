/*
 * Copyright © 2010 by Oleg Kovarik, Ondrej Skalicka. All Rights Reserved
 */

package demopackage;

import cz.cvut.felk.cig.jcop.algorithm.noisingmethod.NoisingMethod;
import cz.cvut.felk.cig.jcop.algorithm.simulatedannealing.SimulatedAnnealing;
import cz.cvut.felk.cig.jcop.problem.tspfast.FastTSP;
import cz.cvut.felk.cig.jcop.result.render.ExceptionRender;
import cz.cvut.felk.cig.jcop.result.render.JFreeChartRender;
import cz.cvut.felk.cig.jcop.result.render.SimpleCompareRender;
import cz.cvut.felk.cig.jcop.solver.AlgorithmCompareSolver;
import cz.cvut.felk.cig.jcop.solver.condition.TimeoutCondition;

import java.io.File;
import java.io.IOException;

/**
 * @author Oleg Kovarik
 */
public class DemoJFreeChartFastTSP {
    public static void main(String[] args) throws IOException {
        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(new FastTSP(new File("data/tsp/dsj1000.tsp")));

        solver.addAlgorithm(new SimulatedAnnealing(100, 0.9));
        //solver.addAlgorithm(new GeneticAlgorithm(16, 0.2));
        solver.addAlgorithm(new NoisingMethod(1.0, 0.9));


        solver.addStopCondition(new TimeoutCondition(10000));
        solver.addListener(new JFreeChartRender("Demo JFreeChartRender"));
        solver.addRender(new SimpleCompareRender());
        solver.addRender(new ExceptionRender());

        solver.run();

        solver.render();
    }
}
