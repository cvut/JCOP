/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

import cz.cvut.felk.cig.jcop.algorithm.simulatedannealing.SimulatedAnnealing;
import cz.cvut.felk.cig.jcop.problem.Problem;
import cz.cvut.felk.cig.jcop.problem.sat.SAT;
import cz.cvut.felk.cig.jcop.result.render.ExceptionRender;
import cz.cvut.felk.cig.jcop.result.render.JFreeChartRender;
import cz.cvut.felk.cig.jcop.result.render.SimpleCompareRender;
import cz.cvut.felk.cig.jcop.result.render.SimpleRender;
import cz.cvut.felk.cig.jcop.solver.AlgorithmCompareSolver;
import cz.cvut.felk.cig.jcop.solver.MedianSolver;
import cz.cvut.felk.cig.jcop.solver.SimpleSolver;
import cz.cvut.felk.cig.jcop.solver.condition.IterationCondition;
import cz.cvut.felk.cig.jcop.solver.condition.TimeoutCondition;
import cz.cvut.felk.cig.jcop.util.JcopRandom;

import java.io.File;
import java.io.IOException;

/**
 * @author Savannah
 */
public class SATExperiment {
    public static void main(String[] args) throws IOException {
        /*testC("uf050-0218", "uf50-0666.cnf");
        testC("uf050-0218", "uf50-02.cnf");
        testC("uf050-0218", "uf50-03.cnf");*/
        /*JcopRandom.setSeed(1);
        testC("uf200-0860", "uf200-01.cnf");
        testC("uf200-0860", "uf200-02.cnf");
        testC("uf200-0860", "uf200-03.cnf");*/
        /*JcopRandom.setSeed(1);
        testC("uf100-0430", "uf100-01.cnf");
        testC("uf100-0430", "uf100-02.cnf");
        testC("uf100-0430", "uf100-03.cnf");*/
        JcopRandom.setSeed(1);
        testD("uf075-0325", "uf75-01.cnf");
        testD("uf075-0325", "uf75-02.cnf");
        testD("uf075-0325", "uf75-03.cnf");
    }

    public static void testA() throws IOException {
        Problem problem = new SAT(new File("data/sat/uf50-0666.cnf"));
        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(problem);

        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.999));
        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.9999));
        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.99999));
        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.999999));

        solver.addStopCondition(new TimeoutCondition(30*1000));
        // solver.addStopCondition(new FoundSolutionCondition());

        solver.addListener(new JFreeChartRender("SAT uf50-0666.cnf"));

        MedianSolver medianSolver = new MedianSolver(solver, 5);

        medianSolver.addRender(new SimpleCompareRender());
        medianSolver.addRender(new ExceptionRender());
        medianSolver.addRender(new SimpleRender(new File("output.txt")));

        medianSolver.run();

        medianSolver.render();

    }

    public static void testB() throws IOException {
        Problem problem = new SAT(new File("data/sat/uf50-0666.cnf"));
        SimpleSolver solver = new SimpleSolver(new SimulatedAnnealing(0.05, 0.9999), problem);

//        solver.addStopCondition(new TimeoutCondition(3*1000));
        solver.addStopCondition(new IterationCondition(500000));

        solver.addListener(new JFreeChartRender("SAT uf50-0666.cnf EXP").useFormatA());

        MedianSolver medianSolver = new MedianSolver(solver, 5);

        medianSolver.addRender(new SimpleCompareRender());
        medianSolver.addRender(new ExceptionRender());
        medianSolver.addRender(new SimpleRender(new File("output.txt")));

        medianSolver.run();

        medianSolver.render();

    }

    public static void testC(String folderName, String fileName) throws IOException {
        Problem problem = new SAT(new File("data/sat/" + folderName + "/" + fileName));
        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(problem);

        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.999));
        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.9999));
        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.99999));
        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.999999));

        solver.addStopCondition(new IterationCondition(500000));

        solver.addListener(new JFreeChartRender("SAT " + folderName + "/" + fileName).useFormatA());
        solver.addListener(new JFreeChartRender("SAT " + folderName + "/" + fileName).useFormatA().removeLegend());

        MedianSolver medianSolver = new MedianSolver(solver, 5);

        medianSolver.addRender(new SimpleCompareRender());
        medianSolver.addRender(new ExceptionRender());
        medianSolver.addRender(new SimpleRender(new File("output-" + folderName + "-" + fileName + ".txt")));

        medianSolver.run();

        medianSolver.render();

    }

    public static void testD(String folderName, String fileName) throws IOException {
        Problem problem = new SAT(new File("data/sat/" + folderName + "/" + fileName));
        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(problem);

        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.999));
        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.9999));
        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.99994));
        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.99999));

        solver.addStopCondition(new IterationCondition(1000000));

        solver.addListener(new JFreeChartRender("SAT " + folderName + "/" + fileName).useFormatA().removeLegend());

        MedianSolver medianSolver = new MedianSolver(solver, 5);

        medianSolver.addRender(new SimpleCompareRender());
        medianSolver.addRender(new ExceptionRender());
        medianSolver.addRender(new SimpleRender(new File("output-" + folderName + "-" + fileName + ".txt")));

        medianSolver.run();

        medianSolver.render();

    }

    public static void testE(String folderName, String fileName) throws IOException {
        Problem problem = new SAT(new File("data/sat/" + folderName + "/" + fileName));
        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(problem);

        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.999));
        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.9999));
        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.99996));
        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.99999));

        solver.addStopCondition(new IterationCondition(1000000));

        solver.addListener(new JFreeChartRender("SAT " + folderName + "/" + fileName).useFormatA().removeLegend());

        MedianSolver medianSolver = new MedianSolver(solver, 5);

        medianSolver.addRender(new SimpleCompareRender());
        medianSolver.addRender(new ExceptionRender());
        medianSolver.addRender(new SimpleRender(new File("output-" + folderName + "-" + fileName + ".txt")));

        medianSolver.run();

        medianSolver.render();

    }
}
