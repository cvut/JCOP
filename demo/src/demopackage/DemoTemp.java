/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package demopackage;

import cz.cvut.felk.cig.jcop.algorithm.graphsearch.bfs.BreadthFirstSearch;
import cz.cvut.felk.cig.jcop.problem.knapsack.Knapsack;
import cz.cvut.felk.cig.jcop.result.render.XMLRender;
import cz.cvut.felk.cig.jcop.solver.SimpleSolver;
import cz.cvut.felk.cig.jcop.solver.Solver;

import java.io.File;
import java.io.IOException;

/**
 * @author Ondrej Skalicka
 */
public class DemoTemp {
    public static void main(String[] args) throws IOException {
        /*MultiSolver solver = new MultiSolver();

        // add some algorithms
        solver.addAlgorithm(new DepthFirstSearch());
        solver.addAlgorithm(new SimulatedAnnealing());

        // add some problems
        solver.addProblem(new JobShop(Arrays.asList(3, 2, 1, 2), 3));
        solver.addProblem(new TSP(new Integer[][]{{0, 5, 2}, {4, 0, 2}, {5, 6, 0},}));

        // add some stop conditions
        solver.addStopCondition(new TimeoutCondition(1000)); // top 1 sec

        // run it
        solver.run();

        // render to console
        solver.render();*/

        /*Problem problem = new Bucket(new int[]{14, 10, 6, 2, 8}, new int[]{0, 0, 1, 0, 0}, new int[]{12, 6, 4, 1, 8});
        List<Integer> jobs = new ArrayList<Integer>(5);
        jobs.add(5);
        jobs.add(4);
        jobs.add(6);
        jobs.add(2);
        jobs.add(2);
        JobShop jobShop = new JobShop(jobs, 3);*/

//        Knapsack knapsack = new Knapsack("9000 4 100 18 114 42 136 88 192 3 223");
//        SAT sat = new SAT(new File("data/sat/easy.cnf"));
        /*TSP tsp = new TSP(new Integer[][]{
                { 0, 20, 42, 35},
                {20,  0, 30, 34},
                {42, 30,  0, 53},
                {35, 34, 53,  0},
        });

        SimpleSolver simpleSolver = new SimpleSolver(new BreadthFirstSearch(), tsp);
        simpleSolver.run();
        simpleSolver.render();*/

/*        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(new SAT(new File("data/sat/valid-standard.cnf")));

        // add algorithms
        solver.addAlgorithm(new DepthFirstSearch());
        solver.addAlgorithm(new BreadthFirstSearch());
        solver.addAlgorithm(new SimulatedAnnealing());
        solver.addAlgorithm(new SimulatedAnnealing(100, 0.999));
        solver.addAlgorithm(new SimulatedAnnealing(10, 0.90));

        // add stop condition
        solver.addStopCondition(new TimeoutCondition(100)); // every algorithm 100 ms

        solver.addRender(new SimpleCompareRender());

        solver.run();

        solver.render();*/

//        AlgorithmCompareSolver solver = new AlgorithmCompareSolver(new Knapsack("9000 4 100 18 114 42 136 88 192 3 223"));
//
//        // add algorithms
//        solver.addAlgorithm(new DepthFirstSearch());
//        solver.addAlgorithm(new BreadthFirstSearch());
//        solver.addAlgorithm(new SimulatedAnnealing());
//        solver.addAlgorithm(new SimulatedAnnealing(100, 0.999));
//        solver.addAlgorithm(new SimulatedAnnealing(10, 0.90));
//
//        // add stop condition
//        solver.addStopCondition(new TimeoutCondition(100)); // every algorithm 100 ms
//
//        solver.run();
//
//        // output to console
//        solver.addRender(new SimpleRender(SimpleRender.OUTPUT_STANDARD));
//        solver.addRender(new SimpleRender(SimpleRender.OUTPUT_MINI));
//
//        // output to txt file
//        solver.addRender(new SimpleRender(SimpleRender.OUTPUT_FULL, new File("../demo/data/demo-output-full.txt")));
//        solver.addRender(new SimpleRender(SimpleRender.OUTPUT_STANDARD, new File("../demo/data/demo-output-standard.txt")));
//        solver.addRender(new SimpleRender(SimpleRender.OUTPUT_MINI, new File("../demo/data/demo-output-mini.txt")));
//
//        // output to xml file
//        solver.addRender(new XMLRender(new File("../demo/data/demo-output.xml")));
//
//        // output to csv file
//        solver.addRender(new CSVRender(new File("../demo/data/demo-output.csv")));
//
//        // output to csv file in MS Office format
//        CSVRender csvRender = new CSVRender(new File("../demo/data/demo-output-ms.csv"));
//        csvRender.setDelimiter(';');
//        solver.addRender(csvRender);
//
//
//        // render results
//        solver.render();
/*        Solver solver = new SimpleSolver(new BreadthFirstSearch(), new Knapsack("9000 4 100 18 114 42 136 88 192 3 223"));

        // Output to System.out
        solver.addRender(new CSVRender());
        // Output to file
        solver.addRender(new CSVRender(new File("output-file.csv")));
        // Output to directly specified OutputStream
        solver.addRender(new CSVRender(System.out));

        // ISO-8859-1 charset, ';' delimiter
        CSVRender csvRender = new CSVRender();
        csvRender.setDelimiter(';');
        csvRender.setCharset(Charset.forName("ISO-8859-1"));

        solver.run();
        solver.render();*/

        /*Solver solver = new SimpleSolver(new BreadthFirstSearch(), new Knapsack("9000 4 100 18 114 42 136 88 192 3 223"));
        solver.addRender(new ExceptionRender());
        // Output to console
        solver.addRender(new SimpleCompareRender());
        // Output to file
        solver.addRender(new SimpleCompareRender(new File("output-file.txt")));
        // Output to directly specified PrintStream
        solver.addRender(new SimpleCompareRender(System.out));
        solver.run();
        solver.render();*/

        /*Solver solver = new SimpleSolver(new DepthFirstSearch(), new SAT(new File("data/sat/valid-standard.cnf")));

        // Output standard info to console
        solver.addRender(new SimpleRender(SimpleRender.OUTPUT_STANDARD));
        // Output minimal info to specified PrintStream
        solver.addRender(new SimpleRender(SimpleRender.OUTPUT_MINI, System.out));
        // Output full into to file
        solver.addRender(new SimpleRender(SimpleRender.OUTPUT_FULL, new File("output-file.txt")));

        solver.addStopCondition(new TimeoutCondition(100));

        solver.run();
        solver.render();*/
        
        Solver solver = new SimpleSolver(new BreadthFirstSearch(), new Knapsack("9000 4 100 18 114 42 136 88 192 3 223"));

        solver.addRender(new XMLRender(new File("output-file.xml")));

        solver.run();
        solver.render();
    }
}
