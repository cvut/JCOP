/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.tsp;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.OperationIterator;
import org.testng.annotations.Test;

/**
 * Tests proper behavior of {@link cz.cvut.felk.cig.jcop.problem.tsp.SwitchCityOperation} operatoin.
 *
 * @author Ondrej Skalicka
 */
public class SwitchCityOperationTest {
    @Test
    public void testExecute() throws Exception {
        TSP problem = new TSP(new Integer[][]{
                {0, 1, 2},
                {3, 0, 4},
                {5, 6, 0},
        });

        Configuration startingConfiguration = problem.getStartingConfiguration();
        OperationIterator operationIterator = problem.getOperationIterator(startingConfiguration);
        Configuration configuration;

        // [0,1,2] -> [1,0,2]
        configuration = operationIterator.next().execute(startingConfiguration);
        assert configuration.valueAt(0) == 1 : "Expected configuration to has index 0 = 1, " + configuration.valueAt(0) + " found";
        assert configuration.valueAt(1) == 0 : "Expected configuration to has index 1 = 0, " + configuration.valueAt(1) + " found";
        assert configuration.valueAt(2) == 2 : "Expected configuration to has index 2 = 2, " + configuration.valueAt(2) + " found";

        // [0,1,2] -> [2,1,0]
        configuration = operationIterator.next().execute(startingConfiguration);
        assert configuration.valueAt(0) == 2 : "Expected configuration to has index 0 = 2, " + configuration.valueAt(0) + " found";
        assert configuration.valueAt(1) == 1 : "Expected configuration to has index 1 = 1, " + configuration.valueAt(1) + " found";
        assert configuration.valueAt(2) == 0 : "Expected configuration to has index 2 = 0, " + configuration.valueAt(2) + " found";

        // [0,1,2] -> [0,2,1]
        configuration = operationIterator.next().execute(startingConfiguration);
        assert configuration.valueAt(0) == 0 : "Expected configuration to has index 0 = 0, " + configuration.valueAt(0) + " found";
        assert configuration.valueAt(1) == 2 : "Expected configuration to has index 1 = 2, " + configuration.valueAt(1) + " found";
        assert configuration.valueAt(2) == 1 : "Expected configuration to has index 2 = 1, " + configuration.valueAt(2) + " found";
    }
}
