/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.solver;

import cz.cvut.felk.cig.jcop.algorithm.Algorithm;
import cz.cvut.felk.cig.jcop.algorithm.graphsearch.bfs.BreadthFirstSearch;
import cz.cvut.felk.cig.jcop.algorithm.graphsearch.dfs.DepthFirstSearch;
import cz.cvut.felk.cig.jcop.problem.knapsack.Knapsack;
import cz.cvut.felk.cig.jcop.result.ResultEntry;
import cz.cvut.felk.cig.jcop.solver.condition.TimeoutCondition;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests proper behavior of {@link cz.cvut.felk.cig.jcop.solver.AlgorithmCompareSolver} solver.
 *
 * @author Ondrej Skalicka
 */
public class AlgorithmCompareSolverTest {

    @BeforeTest
    public void setUp() {
        Logger.getRootLogger().setLevel(Level.WARN);
    }

    @Test
    public void testRun() throws Exception {
        List<Algorithm> algorithms = new ArrayList<Algorithm>();
        Knapsack problem = new Knapsack("9566 40 600 14 217 3 220 2 82 16 214 1 65 4 63 22 209 32 96 7 173 18 229 25 218 17 220 21 186 26 224 12 108 43 51 29 144 11 16 19 103 8 111 27 148 40 186 39 121 35 153 5 103 6 167 15 183 45 231 46 6 23 211 10 213 38 131 9 103 31 135 33 30 42 184 28 2 49 5 41 82 13 89");

        Algorithm DFS = new DepthFirstSearch();
        Algorithm BFS = new BreadthFirstSearch();
        algorithms.add(DFS);
        algorithms.add(BFS);

        AlgorithmCompareSolver compareSolver = new AlgorithmCompareSolver(problem, algorithms);

        compareSolver.addStopCondition(new TimeoutCondition(100));

        compareSolver.run();

        ResultEntry resultDFS = compareSolver.getResult().getResultEntries().get(0);
        ResultEntry resultBFS = compareSolver.getResult().getResultEntries().get(1);

        assert resultDFS != null;
        assert resultBFS != null;

        assert resultDFS.getOptimizeCounter() > 0;
        assert resultBFS.getOptimizeCounter() > 0;


        assert resultBFS.getBestConfiguration().getOperationHistory().getCounter() < resultDFS.getBestConfiguration().getOperationHistory().getCounter();
    }
}
