/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.knapsack;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Fitness;
import cz.cvut.felk.cig.jcop.problem.Operation;
import cz.cvut.felk.cig.jcop.problem.OperationIterator;
import cz.cvut.felk.cig.jcop.util.JcopRandom;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Tests proper behavior of {@link Knapsack} problem.
 *
 * @author Ondrej Skalicka
 */
public class KnapsackTest {
    @Test
    public void testInit() throws Exception {
        File f = new File("data/knapsack/knap_4.txt");
        Knapsack k = new Knapsack(f);

        Configuration c = k.getStartingConfiguration();
        assertEquals(k.getDimension(), 4);

        Configuration c2 = k.getOperationIterator(c).next().execute(c);
        Fitness fit = k.getDefaultFitness();

        assertEquals(fit.getValue(c), 0.0);
        assertEquals(fit.getValue(c2), (double) k.getKnapsackItems().get(0).getPrice());

        OperationIterator it = k.getOperationIterator(c2);
        Operation op = it.next();

        Configuration c3 = op.execute(c2);
        assertEquals(fit.getValue(c3), 0.0);
    }

    @Test
    public void testInitId() throws Exception {
        File f = new File("data/knapsack/knap_4.txt");
        Knapsack k = new Knapsack(f, "9035");

        Knapsack k2 = new Knapsack("9035 4 100 4 236 68 237 74 121 22 112");


        assertEquals(k.getDimension(), k2.getDimension());

        for (int i = 0; i < k.getDimension(); ++i) {
            assertEquals(k.knapsackItems.get(i).getPrice(), k2.knapsackItems.get(i).getPrice());
            assertEquals(k.knapsackItems.get(i).getWeight(), k2.knapsackItems.get(i).getWeight());
        }
    }

    @Test
    public void testInitFromLine() throws Exception {
        Knapsack k = new Knapsack("9000 4 100 18 114 42 136 88 192 3 223");
        Fitness fit;
        Configuration c;

        c = k.getStartingConfiguration();
        fit = k.getDefaultFitness();

        OperationIterator it = k.getOperationIterator(c);
        it.next();
        it.next();
        it.next();

        Configuration c2 = it.next().execute(c);

        assertEquals(fit.getValue(c), 0.0);
        assertEquals(fit.getValue(c2), (double) k.getKnapsackItems().get(3).getPrice());
    }

    @Test
    public void testRandomConfiguration() throws Exception {
        Knapsack k = new Knapsack("9035 4 100 4 236 68 237 74 121 22 112");

        JcopRandom.setSeed(1);
        Configuration configuration = k.getRandomConfiguration();
        JcopRandom.setSeed(1);
        assertEquals(configuration, k.getRandomConfiguration());
    }
}
