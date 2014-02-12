/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.tsp;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Operation;
import cz.cvut.felk.cig.jcop.problem.OperationIterator;
import org.testng.annotations.Test;

/**
 * Tests proper behavior of {@link cz.cvut.felk.cig.jcop.problem.tsp.TSPIterator} iterator.
 *
 * @author Ondrej Skalicka
 */
public class TSPIteratorTest {
    @Test
    public void testNext() throws Exception {
        TSP problem = new TSP(new Integer[][]{
                {0, 1, 2},
                {3, 0, 4},
                {5, 6, 0},
        });

        Configuration configuration = problem.getStartingConfiguration();
        OperationIterator operationIterator = problem.getOperationIterator(configuration);

        Operation operation;
        SwitchCityOperation switchCityOperation;

        operation = operationIterator.next();
        assert operation instanceof SwitchCityOperation : "Operation not instanceof SwitchCityOperation, " + operation.getClass().getSimpleName() + " found instead";
        switchCityOperation = (SwitchCityOperation) operation;
        assert switchCityOperation.sourceIndex == 0 : "Expected source index 0, " + switchCityOperation.sourceIndex + " found";
        assert switchCityOperation.destinationIndex == 1 : "Expected destination index 1, " + switchCityOperation.destinationIndex + " found";

        operation = operationIterator.next();
        assert operation instanceof SwitchCityOperation : "Operation not instanceof SwitchCityOperation, " + operation.getClass().getSimpleName() + " found instead";
        switchCityOperation = (SwitchCityOperation) operation;
        assert switchCityOperation.sourceIndex == 0 : "Expected source index 0, " + switchCityOperation.sourceIndex + " found";
        assert switchCityOperation.destinationIndex == 2 : "Expected destination index 2, " + switchCityOperation.destinationIndex + " found";

        operation = operationIterator.next();
        assert operation instanceof SwitchCityOperation : "Operation not instanceof SwitchCityOperation, " + operation.getClass().getSimpleName() + " found instead";
        switchCityOperation = (SwitchCityOperation) operation;
        assert switchCityOperation.sourceIndex == 1 : "Expected source index 1, " + switchCityOperation.sourceIndex + " found";
        assert switchCityOperation.destinationIndex == 2 : "Expected destination index 2, " + switchCityOperation.destinationIndex + " found";

        assert !operationIterator.hasNext() : "No more operations expected.";
    }
}
