/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.tsp;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.ProblemFormatException;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests proper behavior of {@link cz.cvut.felk.cig.jcop.problem.tsp.TSP} problem.
 *
 * @author Ondrej Skalicka
 */
public class TSPTest {
    protected TSP problem;

    @Test
    public void testInit() throws Exception {
        problem = new TSP(new Integer[][]{{0, 1}, {2, 0}});

        assert problem.cities.size() == 2 : "Expected 2 cities, " + problem.cities.size() + " found";

        problem = new TSP(new Integer[][]{
                {0, 1, 2},
                {3, 0, 4},
                {5, 6, 0},
        });

        assert problem.cities.size() == 3 : "Expected 3 cities, " + problem.cities.size() + " found";
        assert problem.cities.get(0).getDistance(problem.cities.get(1)) == 1 : "Expected distance to city be 1, " + problem.cities.get(0).getDistance(problem.cities.get(1)) + " found";
        assert problem.cities.get(0).getDistance(problem.cities.get(2)) == 2 : "Expected distance to city be 2, " + problem.cities.get(0).getDistance(problem.cities.get(2)) + " found";
        assert problem.cities.get(1).getDistance(problem.cities.get(0)) == 3 : "Expected distance to city be 3, " + problem.cities.get(1).getDistance(problem.cities.get(0)) + " found";
        assert problem.cities.get(1).getDistance(problem.cities.get(2)) == 4 : "Expected distance to city be 4, " + problem.cities.get(1).getDistance(problem.cities.get(2)) + " found";
        assert problem.cities.get(2).getDistance(problem.cities.get(0)) == 5 : "Expected distance to city be 5, " + problem.cities.get(2).getDistance(problem.cities.get(0)) + " found";
        assert problem.cities.get(2).getDistance(problem.cities.get(1)) == 6 : "Expected distance to city be 6, " + problem.cities.get(2).getDistance(problem.cities.get(1)) + " found";


        try {
            problem = new TSP(new Integer[][]{});
            assert false : "Expected exception for empty input";
        } catch (ProblemFormatException ignored) {

        }
        try {
            problem = new TSP(new Integer[][]{{1, 2}, {3}});
            assert false : "Expected exception for incorrect input";
        } catch (ProblemFormatException ignored) {

        }
        try {
            problem = new TSP(new Integer[][]{{1, 2}, {3, 4, 5}});
            assert false : "Expected exception for incorrect input";
        } catch (ProblemFormatException ignored) {

        }
        try {
            problem = new TSP(new Integer[][]{{1, 2}, {3, 4}, {5, 6}});
            assert false : "Expected exception for incorrect input";
        } catch (ProblemFormatException ignored) {

        }
    }

    @Test
    public void testInitFile() throws Exception {
        problem = new TSP(new File("data/tsp/eil51.tsp"));

        for (int i = 0; i < 6; ++i) {
            try {
                problem = new TSP(new File("data/tsp/eil51-invalid" + (i+1) + ".tsp"));
                assert false : "Expected exception for incorrect input for file data/tsp/eil51-invalid" + (i+1) + ".tsp";
            } catch (ProblemFormatException ignored) {

            }
        }

        problem = new TSP(new File("data/tsp/dsj1000.tsp"));
        problem = new TSP(new File("data/tsp/berlin52.tsp"));

    }

    @Test
    public void testIsSolution() throws Exception {
        List<Integer> list;
        Configuration configuration;

        problem = new TSP(new Integer[][]{
                {0, 1, 2},
                {3, 0, 4},
                {5, 6, 0},
        });

        list = new ArrayList<Integer>(3);
        list.add(0);
        list.add(1);
        list.add(2);
        configuration = new Configuration(list, "test");
        assert problem.isSolution(configuration) : "Configuration " + configuration + " should pass isSolution";

        list = new ArrayList<Integer>(3);
        list.add(1);
        list.add(1);
        list.add(2);
        configuration = new Configuration(list, "test");
        assert !problem.isSolution(configuration) : "Configuration " + configuration + " should not pass isSolution";

        list = new ArrayList<Integer>(3);
        list.add(2);
        list.add(1);
        list.add(0);
        configuration = new Configuration(list, "test");
        assert problem.isSolution(configuration) : "Configuration " + configuration + " should pass isSolution";

        list = new ArrayList<Integer>(3);
        list.add(5);
        list.add(2);
        list.add(0);
        configuration = new Configuration(list, "test");
        assert !problem.isSolution(configuration) : "Configuration " + configuration + " should not pass isSolution";
    }

    @Test
    public void testGetStartingConfiguration() throws Exception {
        problem = new TSP(new Integer[][]{
                {0, 1, 2, 4, 3, 2},
                {3, 0, 4, 4, 3, 2},
                {5, 6, 0, 4, 3, 2},
                {5, 6, 0, 4, 3, 2},
                {5, 6, 0, 4, 3, 2},
                {5, 6, 0, 4, 3, 2},
        });

        Configuration configuration = problem.getStartingConfiguration();

        for (int i = 0; i < problem.getDimension(); ++i)
            assert configuration.valueAt(i) == i : "Expected starting configuration to have city index " + i + " on position " + i + ", " + configuration.valueAt(i) + " found.";
    }
}
