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
public class SATExperimentB {
    public static void main(String[] args) throws IOException {
        JcopRandom.setSeed(1);

        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(new SAT(new File("uf175-03.cnf")));

        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.999));
        solver.addAlgorithm(new SimulatedAnnealing(5, 0.999));
        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.9999));
        solver.addAlgorithm(new SimulatedAnnealing(5, 0.9999));

        solver.addStopCondition(new IterationCondition(150000));

        solver.addListener(new JFreeChartRender("Experiment B").
                setStyle(JFreeChartRender.STYLE_THESIS).
                setDomainAxis(0, 510000).
                setRangeAxis(-80, 110));

        MedianSolver medianSolver = new MedianSolver(solver, 10, new File("Bmedian.txt"));

        medianSolver.addRender(new SimpleCompareRender(new File("Bcompare.txt")));

        medianSolver.run();

        medianSolver.render();
    }
}
