/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.util;

import cz.cvut.felk.cig.jcop.algorithm.simulatedannealing.SimulatedAnnealing;
import cz.cvut.felk.cig.jcop.problem.sat.SAT;
import cz.cvut.felk.cig.jcop.result.Result;
import cz.cvut.felk.cig.jcop.result.ResultEntry;
import cz.cvut.felk.cig.jcop.solver.SimpleSolver;
import cz.cvut.felk.cig.jcop.solver.Solver;
import cz.cvut.felk.cig.jcop.solver.condition.IterationCondition;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Tests proper behavior of {@link JcopRandom} utility.
 *
 * @author Ondrej Skalicka
 */
public class JcopRandomTest {
    @Test
    public void testSetSeed() throws Exception {
        Logger.getRootLogger().setLevel(Level.WARN);
        for (int i = 0; i < 4; ++i) {
            Solver solver = new SimpleSolver(new SimulatedAnnealing(100, 0.99), new SAT(new File("data/sat/uf100-0100.cnf")));
            JcopRandom.setSeed(1);

            solver.addStopCondition(new IterationCondition(50));

            solver.run();

            Result result = solver.getResult();
            ResultEntry resultEntryFirst = result.getResultEntries().get(0);


            solver = new SimpleSolver(new SimulatedAnnealing(100, 0.99), new SAT(new File("data/sat/uf100-0100.cnf")));
            JcopRandom.setSeed(1);

            solver.addStopCondition(new IterationCondition(50));

            solver.run();

            result = solver.getResult();
            ResultEntry resultEntrySecond = result.getResultEntries().get(0);

            assert resultEntryFirst.getBestFitness() == resultEntrySecond.getBestFitness() : "Expected same results, got " + resultEntryFirst.getBestFitness() + " first and " + resultEntrySecond.getBestFitness() + " second.";
            assert resultEntryFirst.getBestConfiguration().equals(resultEntrySecond.getBestConfiguration()) : "Expected same results, got " + resultEntryFirst.getBestConfiguration() + " first and " + resultEntrySecond.getBestConfiguration() + " second.";
        }
    }
}
