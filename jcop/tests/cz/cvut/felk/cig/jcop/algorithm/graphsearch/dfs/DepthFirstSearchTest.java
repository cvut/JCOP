/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm.graphsearch.dfs;

import cz.cvut.felk.cig.jcop.algorithm.CannotContinueException;
import cz.cvut.felk.cig.jcop.algorithm.graphsearch.GraphSearch;
import cz.cvut.felk.cig.jcop.problem.BaseObjectiveProblem;
import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.ObjectiveProblem;
import cz.cvut.felk.cig.jcop.problem.knapsack.Knapsack;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Tests proper behavior of {@link DepthFirstSearch} algorithm.
 *
 * @author Ondrej Skalicka
 */
public class DepthFirstSearchTest {
    ObjectiveProblem p;

    @BeforeTest
    public void setUp() throws IOException {
        Knapsack p = new Knapsack("1 3 100 50 500 60 700 30 100");

        this.p = new BaseObjectiveProblem(p);
    }

    @Test
    public void testFullOptimize() throws Exception {
        GraphSearch gs = new DepthFirstSearch();
        gs.init(this.p);
        assertEquals(1, gs.getQueue().size()); // 000
        gs.optimize();
        assertEquals(3, gs.getQueue().size()); // 100, 010, 001
        gs.optimize();
        assertEquals(4, gs.getQueue().size()); // 110, 101, 010, 001
        gs.optimize();
        assertEquals(4, gs.getQueue().size()); // 111, 101, 010, 001
        gs.optimize();
        assertEquals(4, gs.getQueue().size()); // 011, 101, 010, 001
    }

    @Test
    public void testFindBestSolution() throws Exception {
        ObjectiveProblem p = new BaseObjectiveProblem(new Knapsack("9035 4 100 4 236 68 237 74 121 22 112")); // http://service.felk.cvut.cz/courses/X36PAA/inst/knap_4.txt

        GraphSearch gs = new DepthFirstSearch();
        gs.init(p);
        Configuration bestSolution;

        boolean b = true;
        while (b) {
            try {
                gs.optimize();
            } catch (CannotContinueException ignore) {
                b = false;
            }
        }

        bestSolution = gs.getBestConfiguration();

        /*System.out.println("Best Solution: ");
        System.out.println(bestSolution);*/

        assertEquals(new Integer(1), bestSolution.valueAt(0));
        assertEquals(new Integer(1), bestSolution.valueAt(1));
        assertEquals(new Integer(0), bestSolution.valueAt(2));
        assertEquals(new Integer(1), bestSolution.valueAt(3));
    }

    @Test
    public void testOptimizeAllSteps() throws Exception {
        GraphSearch gs = new DepthFirstSearch();
        gs.init(this.p);
        int[][][] expectations = {
                {{0, 0, 0,},}, // round 0: 000 element
                {{0, 0, 1,}, {0, 1, 0,}, {1, 0, 0,},}, // round 1: 001, 010, 100 elements
                {{0, 1, 1,}, {1, 0, 1,}, {0, 1, 0,}, {1, 0, 0,},}, // round 2: 011, 101, 010, 100 elements
                {{1, 1, 1,}, {1, 0, 1,}, {0, 1, 0,}, {1, 0, 0,},}, // round 3: 111, 101, 010, 001 elements
                {{1, 1, 0,}, {1, 0, 1,}, {0, 1, 0,}, {1, 0, 0,},}, // round 4: 110, 101, 010, 001 elements
                {{1, 0, 1,}, {0, 1, 0,}, {1, 0, 0,},}, // round 5: 101, 010, 001 elements
                {{0, 1, 0,}, {1, 0, 0,},}, // round 6: 010, 001 elements
                {{1, 0, 0,},}, // round 7: 001 element
                {}, // round 8: empty
        };
        Object[] list;
        Configuration c;
        for (int i = 0; i < expectations.length; ++i) { // for every round
            assertEquals(expectations[i].length, gs.getQueue().size());

            list = gs.getQueue().toArray();
            for (int j = 0; j < expectations[i].length; ++j) { // for every element
                assert list[j] instanceof Configuration;
                c = (Configuration) list[j];

                for (int k = 0; k < expectations[i][j].length; ++k) {
                    assertEquals("Round " + i + ", element " + j + ", index " + k + ", FP: " + c.fingerprint(), new Integer(expectations[i][j][k]), c.valueAt(k));
                }
            }

            if (gs.getQueue().size() > 0) gs.optimize();
        }
    }
}
