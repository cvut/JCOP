/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package demopackage;

import cz.cvut.felk.cig.jcop.algorithm.graphsearch.bfs.BreadthFirstSearch;
import cz.cvut.felk.cig.jcop.algorithm.graphsearch.dfs.DepthFirstSearch;
import cz.cvut.felk.cig.jcop.algorithm.simulatedannealing.SimulatedAnnealing;
import cz.cvut.felk.cig.jcop.problem.sat.SAT;
import cz.cvut.felk.cig.jcop.result.render.SimpleRender;
import cz.cvut.felk.cig.jcop.result.render.XMLRender;
import cz.cvut.felk.cig.jcop.solver.AlgorithmCompareSolver;
import cz.cvut.felk.cig.jcop.solver.condition.TimeoutCondition;

import java.io.File;
import java.io.IOException;

/**
 * @author Ondrej Skalicka
 */
public class DemoRender {
    public static void main(String[] args) throws IOException {
        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(new SAT(new File("data/sat/valid-standard.cnf")));

        // add algorithms
        solver.addAlgorithm(new DepthFirstSearch());
        solver.addAlgorithm(new BreadthFirstSearch());
        solver.addAlgorithm(new SimulatedAnnealing());
        solver.addAlgorithm(new SimulatedAnnealing(100, 0.999));
        solver.addAlgorithm(new SimulatedAnnealing(10, 0.90));

        // add stop condition
        solver.addStopCondition(new TimeoutCondition(100)); // every algorithm 100 ms

        solver.run();

        // output to console
        solver.addRender(new SimpleRender(SimpleRender.OUTPUT_MINI));

        // output to xml file
        solver.addRender(new XMLRender(new File("../demo/data/demo-output.xml")));

        // render results
        solver.render();
    }
}
