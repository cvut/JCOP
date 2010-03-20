/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package demopackage;

import cz.cvut.felk.cig.jcop.algorithm.simulatedannealing.SimulatedAnnealing;
import cz.cvut.felk.cig.jcop.problem.sat.SAT;
import cz.cvut.felk.cig.jcop.solver.SimpleSolver;
import cz.cvut.felk.cig.jcop.solver.condition.TimeoutCondition;

import java.io.File;
import java.io.IOException;

/**
 * @author Ondrej Skalicka
 */
public class DemoSimpleSolver {
    public static void main(String[] args) throws IOException {
        // create solver
        SimpleSolver solver = new SimpleSolver(new SimulatedAnnealing(), new SAT(new File("data/sat/valid-standard.cnf")));

        // let it stop after 500 ms
        solver.addStopCondition(new TimeoutCondition(500));

        // run!
        solver.run();

        // render result to console (default renderer)
        solver.render();
    }
}
