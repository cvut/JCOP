/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

import cz.cvut.felk.cig.jcop.algorithm.simulatedannealing.SimulatedAnnealing;
import cz.cvut.felk.cig.jcop.problem.OperationHistory;
import cz.cvut.felk.cig.jcop.problem.Problem;
import cz.cvut.felk.cig.jcop.problem.sat.SAT;
import cz.cvut.felk.cig.jcop.result.render.ExceptionRender;
import cz.cvut.felk.cig.jcop.result.render.JFreeChartRender;
import cz.cvut.felk.cig.jcop.result.render.SimpleCompareRender;
import cz.cvut.felk.cig.jcop.result.render.SimpleRender;
import cz.cvut.felk.cig.jcop.solver.AlgorithmCompareSolver;
import cz.cvut.felk.cig.jcop.solver.BaseSolver;
import cz.cvut.felk.cig.jcop.solver.MedianSolver;
import cz.cvut.felk.cig.jcop.solver.SimpleSolver;
import cz.cvut.felk.cig.jcop.solver.condition.IterationCondition;
import cz.cvut.felk.cig.jcop.solver.condition.TimeoutCondition;
import cz.cvut.felk.cig.jcop.util.JcopRandom;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Savannah
 */
public class SATExperiment {
    public static void main(String[] args) throws IOException {
        OperationHistory.limit = OperationHistory.LIMIT_DISABLED;
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
        /*JcopRandom.setSeed(1);
        testD("uf075-0325", "uf75-01.cnf");
        testD("uf075-0325", "uf75-02.cnf");
        testD("uf075-0325", "uf75-03.cnf");*/
        /*JcopRandom.setSeed(1);
        testE("uf100-0430", "uf100-01.cnf");
        testE("uf100-0430", "uf100-02.cnf");
        testE("uf100-0430", "uf100-03.cnf");
        testE("uf125-0538", "uf125-01.cnf");
        testE("uf125-0538", "uf125-02.cnf");
        testE("uf125-0538", "uf125-03.cnf");*/
        Logger.getLogger(SimulatedAnnealing.class).setLevel(Level.OFF);
        Logger.getLogger(BaseSolver.class).setLevel(Level.OFF);
        Logger.getLogger(MedianSolver.class).setLevel(Level.DEBUG);
        Logger.getLogger(AlgorithmCompareSolver.class).setLevel(Level.OFF);
        JcopRandom.setSeed(1);
        int iterations = 15000;
//        testF("uf075-0325", "uf75-01.cnf", iterations);
        /*testF("uf075-0325", "uf75-02.cnf", iterations);
        testF("uf075-0325", "uf75-03.cnf", iterations);
        testF("uf075-0325", "uf75-04.cnf", iterations);
        JcopRandom.setSeed(1);
        iterations = 350000;
        testG("uf175-0753", "uf175-01.cnf", iterations);
        testG("uf175-0753", "uf175-02.cnf", iterations);
        testG("uf175-0753", "uf175-03.cnf", iterations);
        testG("uf175-0753", "uf175-04.cnf", iterations);*/
    }

//    public static void testA() throws IOException {
//        Problem problem = new SAT(new File("data/sat/uf50-0666.cnf"));
//        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(problem);
//
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.999));
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.9999));
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.99999));
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.999999));
//
//        solver.addStopCondition(new TimeoutCondition(30*1000));
//        // solver.addStopCondition(new FoundSolutionCondition());
//
//        solver.addListener(new JFreeChartRender("SAT uf50-0666.cnf"));
//
//        MedianSolver medianSolver = new MedianSolver(solver, 5);
//
//        medianSolver.addRender(new SimpleCompareRender());
//        medianSolver.addRender(new ExceptionRender());
//        medianSolver.addRender(new SimpleRender(new File("output.txt")));
//
//        medianSolver.run();
//
//        medianSolver.render();
//
//    }
//
//    public static void testB() throws IOException {
//        Problem problem = new SAT(new File("data/sat/uf50-0666.cnf"));
//        SimpleSolver solver = new SimpleSolver(new SimulatedAnnealing(0.05, 0.9999), problem);
//
////        solver.addStopCondition(new TimeoutCondition(3*1000));
//        solver.addStopCondition(new IterationCondition(500000));
//
//        solver.addListener(new JFreeChartRender("SAT uf50-0666.cnf EXP").useFormatA());
//
//        MedianSolver medianSolver = new MedianSolver(solver, 5);
//
//        medianSolver.addRender(new SimpleCompareRender());
//        medianSolver.addRender(new ExceptionRender());
//        medianSolver.addRender(new SimpleRender(new File("output.txt")));
//
//        medianSolver.run();
//
//        medianSolver.render();
//
//    }
//
//    public static void testC(String folderName, String fileName) throws IOException {
//        Problem problem = new SAT(new File("data/sat/" + folderName + "/" + fileName));
//        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(problem);
//
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.999));
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.9999));
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.99999));
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.999999));
//
//        solver.addStopCondition(new IterationCondition(500000));
//
//        solver.addListener(new JFreeChartRender("SAT " + folderName + "/" + fileName).useFormatA());
//        solver.addListener(new JFreeChartRender("SAT " + folderName + "/" + fileName).useFormatA().removeLegend());
//
//        MedianSolver medianSolver = new MedianSolver(solver, 5);
//
//        medianSolver.addRender(new SimpleCompareRender());
//        medianSolver.addRender(new ExceptionRender());
//        medianSolver.addRender(new SimpleRender(new File("output-" + folderName + "-" + fileName + ".txt")));
//
//        medianSolver.run();
//
//        medianSolver.render();
//
//    }
//
//    public static void testD(String folderName, String fileName) throws IOException {
//        Problem problem = new SAT(new File("data/sat/" + folderName + "/" + fileName));
//        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(problem);
//
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.999));
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.9999));
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.99994));
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.99999));
//
//        solver.addStopCondition(new IterationCondition(1000000));
//
//        solver.addListener(new JFreeChartRender("SAT " + folderName + "/" + fileName).useFormatA().removeLegend());
//
//        MedianSolver medianSolver = new MedianSolver(solver, 5);
//
//        medianSolver.addRender(new SimpleCompareRender());
//        medianSolver.addRender(new ExceptionRender());
//        medianSolver.addRender(new SimpleRender(new File("output-" + folderName + "-" + fileName + ".txt")));
//
//        medianSolver.run();
//
//        medianSolver.render();
//
//    }
//
//    public static void testE(String folderName, String fileName) throws IOException {
//        Problem problem = new SAT(new File("data/sat/" + folderName + "/" + fileName));
//        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(problem);
//
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.999));
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.9999));
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.99996));
//
//        solver.addAlgorithm(new SimulatedAnnealing(5, 0.999));
//        solver.addAlgorithm(new SimulatedAnnealing(5, 0.9999));
//        solver.addAlgorithm(new SimulatedAnnealing(5, 0.99996));
//
//        solver.addStopCondition(new IterationCondition(1000000));
//
//        solver.addListener(new JFreeChartRender("SAT " + folderName + "/" + fileName).useFormatA().removeLegend());
//        solver.addListener(new JFreeChartRender("SAT " + folderName + "/" + fileName).useFormatA());
//
//        MedianSolver medianSolver = new MedianSolver(solver, 5);
//
//        medianSolver.addRender(new SimpleCompareRender());
//        medianSolver.addRender(new ExceptionRender());
//        medianSolver.addRender(new SimpleRender(new File("output-" + folderName + "-" + fileName + ".txt")));
//
//        medianSolver.run();
//
//        medianSolver.render();
//
//    }
//
//    public static void testF(String folderName, String fileName, int iterations) throws IOException {
//        Problem problem = new SAT(new File("data/sat/" + folderName + "/" + fileName));
//        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(problem);
//
//        /*solver.addAlgorithm(new SimulatedAnnealing(5, 0.99));
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.999));
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.9994));*/
//        /*solver.addAlgorithm(new SimulatedAnnealing(5, 0.99));
//        solver.addAlgorithm(new SimulatedAnnealing(5, 0.9994));
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.99));
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.9994));*/
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.99));
//        solver.addAlgorithm(new SimulatedAnnealing(5, 0.99));
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.9994));
//        solver.addAlgorithm(new SimulatedAnnealing(5, 0.9994));
//
//
//        solver.addStopCondition(new IterationCondition(iterations));
//
//        solver.addListener(new JFreeChartRender("SAT " + folderName + "/" + fileName).useFormatB().removeLegend());
//        solver.addListener(new JFreeChartRender("SAT " + folderName + "/" + fileName).useFormatB());
//
//        MedianSolver medianSolver = new MedianSolver(solver, 30);
//
//        medianSolver.addRender(new SimpleCompareRender());
//        medianSolver.addRender(new SimpleCompareRender(new File("output-" + folderName + "-" + fileName + "-compare.txt")));
//        medianSolver.addRender(new ExceptionRender());
//        medianSolver.addRender(new SimpleRender(new File("output-" + folderName + "-" + fileName + ".txt")));
//
//        medianSolver.run();
//
//        medianSolver.render();
//
//    }
//
//    public static void testG(String folderName, String fileName, int iterations) throws IOException {
//        Problem problem = new SAT(new File("data/sat/" + folderName + "/" + fileName));
//        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(problem);
//
//        solver.addAlgorithm(new SimulatedAnnealing(5, 0.999));
//        solver.addAlgorithm(new SimulatedAnnealing(5, 0.9999));
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.999));
//        solver.addAlgorithm(new SimulatedAnnealing(0.05, 0.9999));
//
//
//        solver.addStopCondition(new IterationCondition(iterations));
//
//        solver.addListener(new JFreeChartRender("SAT " + folderName + "/" + fileName).useFormatA().removeLegend());
//        solver.addListener(new JFreeChartRender("SAT " + folderName + "/" + fileName).useFormatA());
//
//        MedianSolver medianSolver = new MedianSolver(solver, 10);
//
//        medianSolver.addRender(new SimpleCompareRender());
//        medianSolver.addRender(new SimpleCompareRender(new File("output-" + folderName + "-" + fileName + "-compare.txt")));
//        medianSolver.addRender(new ExceptionRender());
//        medianSolver.addRender(new SimpleRender(new File("output-" + folderName + "-" + fileName + ".txt")));
//
//        medianSolver.run();
//
//        medianSolver.render();
//
//    }
//
//
//
//    public JFreeChartRender useFormatA () {
//        return this.setBaseShapesVisible(true).
//                setBaseShapesFilled(false).
//                setLegendItemFont(new Font("Dialog", Font.PLAIN, 9)).
//                setBackgroundPaint(Color.white).
//                setGridPaint(Color.gray).
//                setInsertLast(false);
//    }
//
//    public JFreeChartRender useFormatB () {
//        return this.setBaseShapesVisible(true).
//                setBaseShapesFilled(false).
//                setBaseLinesVisible(false).
//                setLegendItemFont(new Font("Dialog", Font.PLAIN, 9)).
//                setBackgroundPaint(Color.white).
//                setGridPaint(Color.gray).
//                setInsertLast(false);
//    }
}
