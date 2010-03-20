/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package demopackage;

import cz.cvut.felk.cig.jcop.algorithm.Algorithm;
import cz.cvut.felk.cig.jcop.algorithm.graphsearch.dfs.DepthFirstSearch;
import cz.cvut.felk.cig.jcop.algorithm.simulatedannealing.SimulatedAnnealing;
import cz.cvut.felk.cig.jcop.problem.Problem;
import cz.cvut.felk.cig.jcop.problem.bucket.Bucket;
import cz.cvut.felk.cig.jcop.problem.knapsack.Knapsack;
import cz.cvut.felk.cig.jcop.problem.sat.SAT;
import cz.cvut.felk.cig.jcop.solver.SimpleSolver;
import cz.cvut.felk.cig.jcop.solver.condition.FoundSolutionCondition;
import cz.cvut.felk.cig.jcop.solver.condition.TimeoutCondition;

import java.io.File;
import java.io.IOException;

/**
 * @author Ondrej Skalicka
 */
public class Benchmark {
    public static void main(String[] args) throws IOException {
        Benchmark benchmark = new Benchmark();
//        benchmark.testBuckets();
        benchmark.testKnapsacks();
//        benchmark.testSAT();
//        benchmark.testSA();
    }

    public void testSA() throws IOException {
        SimpleSolver solver;
        Problem problem;
//        problem = new Knapsack("9101 15 200 5 111 3 237 46 230 1 71 12 235 2 62 6 93 14 199 17 252 28 31 45 3 23 77 47 62 15 97 34 12");
//        problem = new SAT(new File("data/sat/valid-standard.cnf"));
        problem = new SAT(new File("data/sat/uf100-0100.cnf"));
        solver = new SimpleSolver(new SimulatedAnnealing(0.999, 10), problem);

        solver.addStopCondition(new TimeoutCondition(5000));

        solver.run();

        solver.render();
    }

    public void testSAT() throws IOException {
        SimpleSolver solver;

        solver = new SimpleSolver(new DepthFirstSearch(), new SAT(new File("data/sat/easy.cnf")));

        solver.addStopCondition(new FoundSolutionCondition());

        solver.run();

        solver.render();
    }

    public void testBuckets() throws IOException {
        SimpleSolver solver;
//        solver = new SimpleSolver(new DepthFirstSearch(), new Bucket(new int[]{15, 12, 8, 4, 6}, new int[]{0, 0, 0, 0, 0}, new int[]{5, 5, 5, 0, 1}));
//        solver = new SimpleSolver(new DepthFirstSearch(), new Bucket(new int[]{15, 12, 8, 4, 6}, new int[]{0, 0, 0, 0, 0}, new int[]{12, 1, 3, 4, 5}));
        solver = new SimpleSolver(new DepthFirstSearch(), new Bucket(new int[]{14, 10, 12, 3, 8}, new int[]{0, 0, 0, 0, 0}, new int[]{1, 5, 5, 3, 4})); // 3.2

        solver.addStopCondition(new FoundSolutionCondition());

        solver.run();

        solver.render();

    }

    public void testKnapsacks() {
        SimpleSolver solver;
        Algorithm algorithm = new DepthFirstSearch();
        Problem problem;
        problem = new Knapsack("9101 15 200 5 111 3 237 46 230 1 71 12 235 2 62 6 93 14 199 17 252 28 31 45 3 23 77 47 62 15 97 34 12");
        solver = new SimpleSolver(algorithm, problem);
//        solver = new SimpleSolver(new BreadthFirstSearch(), new Knapsack("9101 15 200 5 111 3 237 46 230 1 71 12 235 2 62 6 93 14 199 17 252 28 31 45 3 23 77 47 62 15 97 34 12"));
//        solver = new SimpleSolver(new DepthFirstSearch(), new Knapsack("9150 20 250 22 175 4 131 2 30 7 11 26 135 6 71 1 249 16 141 43 138 15 164 40 252 21 172 3 9 19 88 48 70 18 42 49 146 8 182 41 68 27 67"));

//        double cpuTime = PreciseTime.getCpuTimeMili();
//        double systemTime = PreciseTime.getSystemTimeMili();
//        double userTime = PreciseTime.getUserTimeMili();
//        double currentTime = PreciseTime.getClockTimeMili();

        solver.run();

        /*System.out.println("CPU Time: " + (PreciseTime.getCpuTimeMili() - cpuTime) + " ms");
        System.out.println("System Time: " + (PreciseTime.getSystemTimeMili() - systemTime) + " ms");
        System.out.println("User Time: " + (PreciseTime.getUserTimeMili() - userTime) + " ms");
        System.out.println("Clock Time: " + (PreciseTime.getClockTimeMili() - currentTime) + " ms");
        System.out.println("Steps: " + solver.getOptimizeCounter());
        System.out.println("Steps/second (CPU): " + (solver.getOptimizeCounter() / (PreciseTime.getCpuTimeMili() - cpuTime) * 1000));
        System.out.println("Steps/second (Clock): " + (solver.getOptimizeCounter() / (PreciseTime.getClockTimeMili() - currentTime) * 1000));
        System.out.println("Depth: " + solver.getBestConfiguration().getOperationHistory().getCounter());
        System.out.println(solver.getBestConfiguration());
        System.out.println("Fitness: " + problem.getDefaultFitness().getValue(solver.getBestConfiguration()));*/

    }
}
