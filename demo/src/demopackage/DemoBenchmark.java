/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package demopackage;

import cz.cvut.felk.cig.jcop.algorithm.geneticalgorithm.GeneticAlgorithm;
import cz.cvut.felk.cig.jcop.algorithm.simulatedannealing.SimulatedAnnealing;
import cz.cvut.felk.cig.jcop.problem.sat.SAT;
import cz.cvut.felk.cig.jcop.result.render.ExceptionRender;
import cz.cvut.felk.cig.jcop.result.render.SimpleCompareRender;
import cz.cvut.felk.cig.jcop.solver.AlgorithmCompareSolver;
import cz.cvut.felk.cig.jcop.solver.condition.TimeoutCondition;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Ondrej Skalicka
 */
public class DemoBenchmark {
    public static void main(String[] args) throws IOException {
        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(new SAT(new File("data/sat/uf100-0100.cnf")));

        solver.addAlgorithm(new SimulatedAnnealing(10, 0.09));
        solver.addAlgorithm(new GeneticAlgorithm(16, 0.2));

        solver.addStopCondition(new TimeoutCondition(2000)); // every problem 5s

        solver.addRender(new SimpleCompareRender());
        solver.addRender(new ExceptionRender());

        solver.run();

        solver.render();
    }

}
