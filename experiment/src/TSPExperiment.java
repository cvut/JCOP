/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

import cz.cvut.felk.cig.jcop.algorithm.simulatedannealing.SimulatedAnnealing;
import cz.cvut.felk.cig.jcop.problem.OperationHistory;
import cz.cvut.felk.cig.jcop.problem.tsp.TSP;
import cz.cvut.felk.cig.jcop.problem.tsp.inverting.TSPInverting;
import cz.cvut.felk.cig.jcop.result.ResultEntry;
import cz.cvut.felk.cig.jcop.result.render.ExceptionRender;
import cz.cvut.felk.cig.jcop.result.render.JFreeChartRender;
import cz.cvut.felk.cig.jcop.result.render.SimpleCompareRender;
import cz.cvut.felk.cig.jcop.result.render.SimpleRender;
import cz.cvut.felk.cig.jcop.solver.MedianSolver;
import cz.cvut.felk.cig.jcop.solver.MultiSolver;
import cz.cvut.felk.cig.jcop.solver.condition.TimeoutCondition;

import java.io.File;
import java.io.IOException;

/**
 * @author Ondrej Skalicka
 */
public class TSPExperiment {
    public static void main(String[] args) throws IOException {
        TSP problem = new TSPInverting(new File("data/eil51.tsp"));
//        TSP problem = new TSPInverting(new File("data/berlin52.tsp"));
//        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(problem);
        OperationHistory.limit = OperationHistory.LIMIT_UNLIMITED;
        MultiSolver solver = new MultiSolver();
        solver.addProblem(problem);
        solver.addProblem(new TSP(new File("data/eil51.tsp")));

        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.999));
        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.9999));
        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.99999));
        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.999999));
                        
        solver.addStopCondition(new TimeoutCondition(30*1000));
//        solver.addStopCondition(new IterationCondition(100000));

        MedianSolver medianSolver = new MedianSolver(solver, 5);

        /*solver.addRender(new SimpleCompareRender());
        solver.addRender(new ExceptionRender());
        solver.addRender(new SimpleRender(new File("output.txt")));*/
        solver.addListener(new JFreeChartRender(""));

        medianSolver.addRender(new SimpleCompareRender());
        medianSolver.addRender(new ExceptionRender());
        medianSolver.addRender(new SimpleRender(new File("output.txt")));

        medianSolver.run();

        medianSolver.render();

        System.out.println();
        for (ResultEntry resultEntry : medianSolver.getResult().getResultEntries()) {
            System.out.println("Algorithm: " + resultEntry.getAlgorithm() + ", length : " + problem.pathLength(resultEntry.getBestConfiguration()));
        }
    }
}
