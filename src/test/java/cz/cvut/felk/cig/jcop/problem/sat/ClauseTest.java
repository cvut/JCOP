/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.sat;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests proper behavior of {@link Clause} in {@link SAT SAT problem}.
 *
 * @author Ondrej Skalicka
 */
public class ClauseTest {
    @Test
    public void testIsTrue() throws Exception {
        List<Variable> variableList = new ArrayList<Variable>(3);
        List<Boolean> negatedList = new ArrayList<Boolean>(3);

        variableList.add(new Variable(0));
        negatedList.add(false);
        variableList.add(new Variable(1));
        negatedList.add(true);
        variableList.add(new Variable(3));
        negatedList.add(false);

        // eg 1, -2, 3 (labels) or 0, -1, 2 (indexes)

        Clause clause = new Clause(variableList, negatedList);

        List<Integer> integerList = new ArrayList<Integer>(4);
        integerList.add(0);
        integerList.add(1);
        integerList.add(0);
        integerList.add(0);
        Configuration configuration = new Configuration(integerList, "test");

        assert !clause.isTrue(configuration) : "Clause should not be satisfied";

        integerList.set(1, 0);
        configuration = new Configuration(integerList, "test");

        assert clause.isTrue(configuration) : "Clause should be satisfied";
    }
}
