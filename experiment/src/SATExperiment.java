/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

import cz.cvut.felk.cig.jcop.algorithm.simulatedannealing.SimulatedAnnealing;
import cz.cvut.felk.cig.jcop.problem.sat.SAT;
import cz.cvut.felk.cig.jcop.result.render.JFreeChartRender;
import cz.cvut.felk.cig.jcop.result.render.SimpleCompareRender;
import cz.cvut.felk.cig.jcop.solver.AlgorithmCompareSolver;
import cz.cvut.felk.cig.jcop.solver.MedianSolver;
import cz.cvut.felk.cig.jcop.solver.condition.IterationCondition;
import cz.cvut.felk.cig.jcop.util.JcopRandom;

import java.io.File;
import java.io.IOException;

/**
 * @author Savannah
 */
public class SATExperiment {
    public static void main(String[] args) throws IOException {
        JcopRandom.setSeed(1);

        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(new SAT(new File("uf75-01.cnf")));

        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.99));
        solver.addAlgorithm(new SimulatedAnnealing(5, 0.99));
        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.9994));
        solver.addAlgorithm(new SimulatedAnnealing(5, 0.9994));

        solver.addStopCondition(new IterationCondition(15000));

        solver.addListener(new JFreeChartRender("Experiment A").
                setStyle(JFreeChartRender.STYLE_THESIS).
                setDomainAxis(0, 15250).
                setRangeAxis(-40, 55));

        MedianSolver medianSolver = new MedianSolver(solver, 100, new File("median.txt"));

        medianSolver.addRender(new SimpleCompareRender(new File("compare.txt")));
        
        medianSolver.run();

        medianSolver.render();
    }
}
