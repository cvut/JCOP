/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.sat;

import cz.cvut.felk.cig.jcop.problem.ProblemFormatException;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Tests proper behavior of {@link SAT SAT problem}.
 *
 * @author Ondrej Skalicka
 */
public class SATTest {
    @Test
    public void testInitFile() throws Exception {
        File f = new File("data/sat/invalid-2-configs.cnf");
        SAT sat;

        try {
            new SAT(f);
            assert false : "Expected ProblemFormatException";
        } catch (ProblemFormatException ignore) {

        }

        f = new File("data/sat/invalid-no-config.cnf");

        try {
            new SAT(f);
            assert false : "Expected ProblemFormatException";
        } catch (ProblemFormatException ignore) {

        }

        f = new File("data/sat/valid-standard.cnf");
        sat = new SAT(f);
        assert sat.formula.clauses.size() == 91 : "Expected 91 clauses, " + sat.formula.clauses.size() + " found";

        f = new File("data/sat/valid-multiple-clause-on-line.cnf");
        sat = new SAT(f);
        assert sat.formula.clauses.size() == 91 : "Expected 91 clauses, " + sat.formula.clauses.size() + " found";


        f = new File("data/sat/valid-mini.cnf");
        sat = new SAT(f);
        assert sat.formula.clauses.size() == 4 : "Expected 4 clauses, " + sat.formula.clauses.size() + " found";
        assert sat.formula.clauses.get(0).size() == 3 : "Expected 3 variables in 1st clause, " + sat.formula.clauses.get(0).size() + " found";
        assert sat.formula.clauses.get(1).size() == 2 : "Expected 2 variables in 2nd clause, " + sat.formula.clauses.get(1).size() + " found";
        assert sat.formula.clauses.get(2).size() == 1 : "Expected 1 variables in 3rd clause, " + sat.formula.clauses.get(2).size() + " found";
        assert sat.formula.clauses.get(3).size() == 2 : "Expected 2 variables in 4th clause, " + sat.formula.clauses.get(3).size() + " found";

        assert sat.setTrueOperations.size() == 3 : "Expected 3 SetTrueOperations, " + sat.setTrueOperations.size() + " found";
        assert sat.setFalseOperations.size() == 3 : "Expected 3 SetFalseOperations, " + sat.setFalseOperations.size() + " found";

        assert sat.formula.clauses.get(0).variables.get(0).getLabel() == 1 : "Expected variable [0,0] to be 1, " + sat.formula.clauses.get(0).variables.get(0).getLabel() + " found";
        assert sat.formula.clauses.get(0).variables.get(1).getLabel() == 2 : "Expected variable [0,1] to be 2, " + sat.formula.clauses.get(0).variables.get(1).getLabel() + " found";
        assert sat.formula.clauses.get(0).variables.get(2).getLabel() == 3 : "Expected variable [0,2] to be 3, " + sat.formula.clauses.get(0).variables.get(2).getLabel() + " found";
        assert sat.formula.clauses.get(1).variables.get(0).getLabel() == 2 : "Expected variable [1,0] to be 2 (negated), " + sat.formula.clauses.get(1).variables.get(0).getLabel() + " found";
        assert sat.formula.clauses.get(1).variables.get(1).getLabel() == 3 : "Expected variable [1,1] to be 3, " + sat.formula.clauses.get(1).variables.get(1).getLabel() + " found";
        assert sat.formula.clauses.get(2).variables.get(0).getLabel() == 1 : "Expected variable [2,0] to be 1, " + sat.formula.clauses.get(2).variables.get(0).getLabel() + " found";
        assert sat.formula.clauses.get(3).variables.get(0).getLabel() == 3 : "Expected variable [3,0] to be 3 (negated), " + sat.formula.clauses.get(3).variables.get(0).getLabel() + " found";
        assert sat.formula.clauses.get(3).variables.get(1).getLabel() == 2 : "Expected variable [3,1] to be 2 (negated), " + sat.formula.clauses.get(3).variables.get(1).getLabel() + " found";

        assert !sat.formula.clauses.get(0).negatedFlags.get(0) : "Expected variable [0,0] to be non-negated, " + (sat.formula.clauses.get(0).negatedFlags.get(0) ? "negated" : "non-negated") + " found";
        assert !sat.formula.clauses.get(0).negatedFlags.get(1) : "Expected variable [0,1] to be non-negated, " + (sat.formula.clauses.get(0).negatedFlags.get(1) ? "negated" : "non-negated") + " found";
        assert !sat.formula.clauses.get(0).negatedFlags.get(2) : "Expected variable [0,2] to be non-negated, " + (sat.formula.clauses.get(0).negatedFlags.get(2) ? "negated" : "non-negated") + " found";
        assert sat.formula.clauses.get(1).negatedFlags.get(0) : "Expected variable [1,0] to be negated, " + (sat.formula.clauses.get(0).negatedFlags.get(0) ? "negated" : "non-negated") + " found";
        assert !sat.formula.clauses.get(1).negatedFlags.get(1) : "Expected variable [1,1] to be non-negated, " + (sat.formula.clauses.get(1).negatedFlags.get(1) ? "negated" : "non-negated") + " found";
        assert !sat.formula.clauses.get(2).negatedFlags.get(0) : "Expected variable [2,0] to be non-negated, " + (sat.formula.clauses.get(2).negatedFlags.get(0) ? "negated" : "non-negated") + " found";
        assert sat.formula.clauses.get(3).negatedFlags.get(0) : "Expected variable [3,0] to be negated, " + (sat.formula.clauses.get(3).negatedFlags.get(0) ? "negated" : "non-negated") + " found";
        assert sat.formula.clauses.get(3).negatedFlags.get(1) : "Expected variable [3,1] to be negated, " + (sat.formula.clauses.get(3).negatedFlags.get(3) ? "negated" : "non-negated") + " found";
    }

    @Test
    public void testWeightInit() throws Exception {
        File f = new File("data/sat/valid-weight.cnf");
        SAT sat;

        sat = new SAT(f);

        f = new File("data/sat/invalid-weight-less.cnf");
        assert sat.variables.get(0).getWeight() == 5 : "Expected weight 5, " + sat.variables.get(0).getWeight() + " found";
        assert sat.variables.get(1).getWeight() == 8 : "Expected weight 8, " + sat.variables.get(1).getWeight() + " found";
        assert sat.variables.get(2).getWeight() == 45 : "Expected weight 45, " + sat.variables.get(2).getWeight() + " found";

        try {
            new SAT(f);
            assert false : "Expected ProblemFormatException";
        } catch (ProblemFormatException ignore) {

        }

        f = new File("data/sat/invalid-weight-more.cnf");

        try {
            new SAT(f);
            assert false : "Expected ProblemFormatException";
        } catch (ProblemFormatException ignore) {

        }
    }
}
