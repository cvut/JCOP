/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.tsp;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Fitness;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests proper behavior of {@link cz.cvut.felk.cig.jcop.problem.tsp.TSPFitness} fitness.
 *
 * @author Ondrej Skalicka
 */
public class TSPFitnessTest {
    @Test
    public void testGetValue() throws Exception {
        TSP problem;
        Fitness fitness;
        TSPFitness tspfitness;

        problem = new TSP(new Integer[][]{
                {0, 1, 2, 5},
                {3, 0, 4, 5},
                {5, 6, 0, 5},
                {5, 6, 5, 0},
        });
        fitness = problem.getDefaultFitness();

        assert fitness instanceof TSPFitness : "Expected instance of TSPFitness, " + (fitness == null ? "null" : fitness.getClass().getSimpleName()) + " found.";
        tspfitness = (TSPFitness) fitness;

        assert tspfitness.maxDistance >= 9 : "Expected max distance in TSP fitness to be greater or equal to 9, " + tspfitness.maxDistance + " found";
        double maxDistance = tspfitness.maxDistance;

        List<Integer> list = new ArrayList<Integer>(4);
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        Configuration configuration = new Configuration(list, "test");
        // 1 + 4
        assert fitness.getValue(configuration) == maxDistance - 15.0 : "Expected fitness " + (maxDistance - 15.0) + ", got " + fitness.getValue(configuration);

        list = new ArrayList<Integer>(4);
        list.add(2);
        list.add(0);
        list.add(3);
        list.add(1);
        configuration = new Configuration(list, "test");
        // 5 + 1
        assert fitness.getValue(configuration) == maxDistance - 20.0 : "Expected fitness " + (maxDistance - 20.0) + ", got " + fitness.getValue(configuration);

        list = new ArrayList<Integer>(4);
        list.add(3);
        list.add(1);
        list.add(2);
        list.add(0);
        configuration = new Configuration(list, "test");
        // 4 + 5
        assert fitness.getValue(configuration) == maxDistance - 20.0 : "Expected fitness " + (maxDistance - 20.0) + ", got " + fitness.getValue(configuration);
    }
}
