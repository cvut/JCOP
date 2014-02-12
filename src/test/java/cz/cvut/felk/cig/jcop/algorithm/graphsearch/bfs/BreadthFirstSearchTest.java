/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm.graphsearch.bfs;

import cz.cvut.felk.cig.jcop.algorithm.CannotContinueException;
import cz.cvut.felk.cig.jcop.algorithm.graphsearch.GraphSearch;
import cz.cvut.felk.cig.jcop.problem.BaseObjectiveProblem;
import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.ObjectiveProblem;
import cz.cvut.felk.cig.jcop.problem.knapsack.Knapsack;
import org.apache.log4j.BasicConfigurator;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Tests proper behavior of {@link BreadthFirstSearch} algorithm.
 *
 * @author Ondrej Skalicka
 */
public class BreadthFirstSearchTest {
    ObjectiveProblem p;

    @BeforeTest
    public void setUp() throws IOException {
        Knapsack p = new Knapsack("1 3 100 50 500 60 700 30 100");

        this.p = new BaseObjectiveProblem(p);

        BasicConfigurator.configure();
    }

    @Test
    public void testFullOptimize() throws Exception {
        GraphSearch gs = new BreadthFirstSearch();
        gs.init(this.p);
        assertEquals(1, gs.getQueue().size()); // 000
        gs.optimize();
        assertEquals(3, gs.getQueue().size()); // 100, 010, 001
        gs.optimize();
        assertEquals(4, gs.getQueue().size()); // 110, 101, 010, 001
        gs.optimize();
        assertEquals(4, gs.getQueue().size()); // 111, 101, 010, 001
        gs.optimize();
        assertEquals(3, gs.getQueue().size()); // 011, 101, 010, 001
    }

    @Test
    public void testFindBestSolution() throws Exception {
        ObjectiveProblem p = new BaseObjectiveProblem(new Knapsack("9035 4 100 4 236 68 237 74 121 22 112")); // http://service.felk.cvut.cz/courses/X36PAA/inst/knap_4.txt

        GraphSearch gs = new BreadthFirstSearch();
        gs.init(p);
        Configuration bestSolution;

        boolean b = true;
        while (b) {
            try {
                gs.optimize();
            } catch (CannotContinueException ignored) {
                b = false;
            }
        }

        bestSolution = gs.getBestConfiguration();

        assertEquals(new Integer(1), bestSolution.valueAt(0));
        assertEquals(new Integer(1), bestSolution.valueAt(1));
        assertEquals(new Integer(0), bestSolution.valueAt(2));
        assertEquals(new Integer(1), bestSolution.valueAt(3));

        // 3 operations to get best solution, since we changed 3 bits
        assertEquals(bestSolution.getOperationHistory().getCounter(), 3);
    }

    @Test
    public void testOptimizeAllSteps() throws Exception {
        GraphSearch gs = new BreadthFirstSearch();
        gs.init(this.p);
        int[][][] expectations = {
                {{0, 0, 0,},}, // round 0: 000 element
                {{1, 0, 0,}, {0, 1, 0,}, {0, 0, 1,},}, // round 1: 100, 010, 001 elements
                {{0, 1, 0,}, {0, 0, 1,}, {1, 1, 0,}, {1, 0, 1,},}, // round 2: 010, 001, 110, 101 elements
                {{0, 0, 1,}, {1, 1, 0,}, {1, 0, 1,}, {0, 1, 1,},}, // round 3: 001, 110, 101, 011 elements
                {{1, 1, 0,}, {1, 0, 1,}, {0, 1, 1,},}, // round 4: 110, 101, 011 elements
                {{1, 0, 1,}, {0, 1, 1,}, {1, 1, 1,},}, // round 5: 101, 011, 111 elements
                {{0, 1, 1,}, {1, 1, 1,},}, // round 6: 011, 111 elements
                {{1, 1, 1,},}, // round 7: 001 element
                {}, // round 8: empty
        };
        Configuration c;

        for (int i = 0; i < expectations.length; ++i) { // for every round
            assertEquals(expectations[i].length, gs.getQueue().size());

            for (int j = 0; j < expectations[i].length; ++j) { // for every element
                c = gs.getQueue().fetch();
                assert c != null;

                for (int k = 0; k < expectations[i][j].length; ++k) {
                    assertEquals("Round " + i + ", element " + j + ", index " + k + ", FP: " + c.fingerprint(), new Integer(expectations[i][j][k]), c.valueAt(k));
                }

                gs.getQueue().add(c);
            }

            if (gs.getQueue().size() > 0) gs.optimize();
        }
    }
}
