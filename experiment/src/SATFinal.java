/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

import cz.cvut.felk.cig.jcop.algorithm.simulatedannealing.SimulatedAnnealing;
import cz.cvut.felk.cig.jcop.problem.Problem;
import cz.cvut.felk.cig.jcop.problem.sat.SAT;
import cz.cvut.felk.cig.jcop.result.render.JFreeChartRender;
import cz.cvut.felk.cig.jcop.result.render.SimpleCompareRender;
import cz.cvut.felk.cig.jcop.solver.AlgorithmCompareSolver;
import cz.cvut.felk.cig.jcop.solver.MedianSolver;
import cz.cvut.felk.cig.jcop.solver.condition.IterationCondition;
import cz.cvut.felk.cig.jcop.util.JcopRandom;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Savannah
 */
public class SATFinal {
    public static void main(String[] args) throws IOException {
        JcopRandom.setSeed(1);

        int iterations = 15000;
        int medianCount = 10;
        String folderName = "uf075-0325";
        String fileName = "uf75-01.cnf";

        Problem problem = new SAT(new File("data/sat/" + folderName + "/" + fileName));
        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(problem);

        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.99));
        solver.addAlgorithm(new SimulatedAnnealing(5, 0.99));
        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.9994));
        solver.addAlgorithm(new SimulatedAnnealing(5, 0.9994));

        solver.addStopCondition(new IterationCondition(iterations));

        solver.addListener(new JFreeChartRender("SAT " + folderName + "/" + fileName).
                setBaseShapesVisible(true).
                setBaseShapesFilled(false).
                setBaseLinesVisible(false).
                setLegendItemFont(new Font("Dialog", Font.PLAIN, 9)).
                setBackgroundPaint(Color.white).
                setGridPaint(Color.gray).
                setInsertLast(false).
                setDomainAxis(0, 15250).
                setRangeAxis(-40, 55).
                removeLegend());

        MedianSolver medianSolver = new MedianSolver(solver, medianCount, new File(fileName + "-" + medianCount + "-median.txt"));

        medianSolver.addRender(new SimpleCompareRender(new File(fileName + "-" + medianCount + "-compare.txt")));
        
        medianSolver.run();

        medianSolver.render();
    }
}
