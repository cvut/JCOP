/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.sat;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Operation;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests proper behavior of {@link SetTrueOperation} in {@link SAT SAT problem}.
 *
 * @author Ondrej Skalicka
 */
public class SetTrueOperationTest {
    @Test
    public void testExecute() throws Exception {
        Operation operation = new SetTrueOperation(new Variable(1));

        List<Integer> integerList = new ArrayList<Integer>(3);
        integerList.add(0);
        integerList.add(0);
        integerList.add(0);

        Configuration originalConfiguration = new Configuration(integerList, "test");
        Configuration configuration = operation.execute(originalConfiguration);

        assert configuration.valueAt(0) == 0 : "Expected configuration.value[0] = 0, " + configuration.valueAt(0) + " found";
        assert configuration.valueAt(1) == 1 : "Expected configuration.value[1] = 1, " + configuration.valueAt(1) + " found";
        assert configuration.valueAt(2) == 0 : "Expected configuration.value[2] = 0, " + configuration.valueAt(2) + " found";

        configuration = operation.execute(configuration);

        // expect no change
        assert configuration.valueAt(0) == 0 : "Expected configuration.value[0] = 0, " + configuration.valueAt(0) + " found";
        assert configuration.valueAt(1) == 1 : "Expected configuration.value[1] = 1, " + configuration.valueAt(1) + " found";
        assert configuration.valueAt(2) == 0 : "Expected configuration.value[2] = 0, " + configuration.valueAt(2) + " found";

        operation = new SetTrueOperation(new Variable(0));

        configuration = operation.execute(configuration);

        assert configuration.valueAt(0) == 1 : "Expected configuration.value[0] = 1, " + configuration.valueAt(0) + " found";
        assert configuration.valueAt(1) == 1 : "Expected configuration.value[1] = 1, " + configuration.valueAt(1) + " found";
        assert configuration.valueAt(2) == 0 : "Expected configuration.value[2] = 0, " + configuration.valueAt(2) + " found";

        operation = new SetTrueOperation(new Variable(2));

        configuration = operation.execute(configuration);

        assert configuration.valueAt(0) == 1 : "Expected configuration.value[0] = 1, " + configuration.valueAt(0) + " found";
        assert configuration.valueAt(1) == 1 : "Expected configuration.value[1] = 1, " + configuration.valueAt(1) + " found";
        assert configuration.valueAt(2) == 1 : "Expected configuration.value[2] = 1, " + configuration.valueAt(2) + " found";
    }
}
