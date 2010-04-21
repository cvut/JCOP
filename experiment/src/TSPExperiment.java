/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

import cz.cvut.felk.cig.jcop.algorithm.graphsearch.bfs.BreadthFirstSearch;
import cz.cvut.felk.cig.jcop.algorithm.simulatedannealing.SimulatedAnnealing;
import cz.cvut.felk.cig.jcop.problem.tsp.TSP;
import cz.cvut.felk.cig.jcop.result.ResultEntry;
import cz.cvut.felk.cig.jcop.result.render.ExceptionRender;
import cz.cvut.felk.cig.jcop.result.render.SimpleRender;
import cz.cvut.felk.cig.jcop.solver.AlgorithmCompareSolver;
import cz.cvut.felk.cig.jcop.solver.condition.TimeoutCondition;

import java.io.File;
import java.io.IOException;

/**
 * @author Savannah
 */
public class TSPExperiment {
    public static void main(String[] args) throws IOException {
        TSP problem = new TSP(new File("data/dsj1000.tsp"));
        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(problem);

        solver.addAlgorithm(new SimulatedAnnealing(10, 0.999995));
        solver.addAlgorithm(new SimulatedAnnealing(100, 0.99999));
        solver.addAlgorithm(new SimulatedAnnealing(100, 0.99));

        solver.addStopCondition(new TimeoutCondition(10000));

        solver.addRender(new SimpleRender());
        solver.addRender(new ExceptionRender());

        solver.run();

        solver.render();

        for (ResultEntry resultEntry : solver.getResult().getResultEntries()) {
            System.out.println("Algorithm: " + resultEntry.getAlgorithm() + ", length : " + problem.pathLength(resultEntry.getBestConfiguration()));
        }
    }
}
