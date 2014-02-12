/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.result;

import cz.cvut.felk.cig.jcop.algorithm.Algorithm;
import cz.cvut.felk.cig.jcop.algorithm.graphsearch.bfs.BreadthFirstSearch;
import cz.cvut.felk.cig.jcop.algorithm.graphsearch.dfs.DepthFirstSearch;
import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.sat.SAT;
import cz.cvut.felk.cig.jcop.util.PreciseTimestamp;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

/**
 * Tests proper behavior of {@link cz.cvut.felk.cig.jcop.result.SimpleResult} result.
 *
 * @author Ondrej Skalicka
 */
public class SimpleResultTest {
    protected SAT p1;
    protected SAT p2;
    protected Algorithm a1;
    protected Algorithm a2;
    protected Result result;
    protected Configuration c1;
    protected Configuration c2;

    @BeforeTest
    public void setUp() throws IOException {
        p1 = new SAT(new File("data/sat/valid-standard.cnf"));
        p2 = new SAT(new File("data/sat/valid-multiple-clause-on-line.cnf"));
        a1 = new DepthFirstSearch();
        a2 = new BreadthFirstSearch();

        c1 = p1.getStartingConfiguration();
        c2 = p2.getStartingConfiguration();

        result = new SimpleResult();
        result.addEntry(new ResultEntry(a1, p1, c1, 1.0, 1, new PreciseTimestamp()));
        result.addEntry(new ResultEntry(a2, p1, c1, 1.0, 1, new PreciseTimestamp(), new PreciseTimestamp()));
        result.addEntry(new ResultEntry(a1, p2, c2, 1.0, 1, new PreciseTimestamp()));
        result.addEntry(new ResultEntry(a2, p2, c2, 1.0, 1, new PreciseTimestamp(), new PreciseTimestamp()));
    }

    @Test
    public void testGetResultEntriesCount() throws Exception {
        assert result.getResultEntries().size() == 4 : "Expected 4 result entries, " + result.getResultEntries().size() + " found.";
    }
}
