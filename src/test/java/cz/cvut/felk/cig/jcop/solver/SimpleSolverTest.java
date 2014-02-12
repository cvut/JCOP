/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.solver;

import cz.cvut.felk.cig.jcop.algorithm.Algorithm;
import cz.cvut.felk.cig.jcop.algorithm.graphsearch.bfs.BreadthFirstSearch;
import cz.cvut.felk.cig.jcop.algorithm.graphsearch.dfs.DepthFirstSearch;
import cz.cvut.felk.cig.jcop.problem.BaseObjectiveProblem;
import cz.cvut.felk.cig.jcop.problem.ObjectiveProblem;
import cz.cvut.felk.cig.jcop.problem.knapsack.Knapsack;
import cz.cvut.felk.cig.jcop.solver.condition.IterationCondition;
import cz.cvut.felk.cig.jcop.solver.condition.TimeoutCondition;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * Tests proper behavior of {@link SimpleSolver} solver.
 *
 * @author Ondrej Skalicka
 */
public class SimpleSolverTest {
    ObjectiveProblem p;
    Algorithm a;

    @BeforeTest
    public void setUp() throws IOException {
        this.p = new BaseObjectiveProblem(new Knapsack("9035 4 100 4 236 68 237 74 121 22 112"));
        this.a = new BreadthFirstSearch();
        Logger.getRootLogger().setLevel(Level.WARN);
    }
    
    @Test
    public void testSolve() throws Exception {
        int counter = 10;
        SimpleSolver s = new SimpleSolver(this.a, this.p);
        s.addStopCondition(new IterationCondition(counter));

        s.run();

        int optimizeCounter = s.getResult().getResultEntries().get(0).getOptimizeCounter();

        assertTrue(optimizeCounter <= counter, "Expected counter at most " + counter + ", got " + optimizeCounter);


        counter = 1;
        s = new SimpleSolver(this.a, this.p);
        s.addStopCondition(new IterationCondition(counter));

        s.run();

        optimizeCounter = s.getResult().getResultEntries().get(0).getOptimizeCounter();

        assertTrue(optimizeCounter <= counter, "Expected counter at most " + counter + ", got " + optimizeCounter);

    }

    @Test
    public void testTimeout() throws Exception {
        ObjectiveProblem p = new BaseObjectiveProblem(new Knapsack("9550 40 600 14 223 38 230 3 54 1 214 13 118 4 147 15 16 2 104 5 56 49 154 40 106 24 234 18 34 33 195 7 74 10 129 12 159 42 37 41 10 11 185 6 243 45 87 32 57 20 87 9 26 16 201 39 0 23 128 39 194 21 10 46 1 8 28 30 59 26 130 35 160 22 91 34 180 19 16 31 1 17 72"));
        Algorithm a = new DepthFirstSearch();

        SimpleSolver s = new SimpleSolver(a, p);
        s.addStopCondition(new TimeoutCondition(100L)); // 100 ms


        s.run();
    }
}
