/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.sat;

import cz.cvut.felk.cig.jcop.problem.BaseReversibleOperation;
import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.OperationHistory;

import java.util.List;

/**
 * Sets variable in configuration to 1.
 *
 * @author Ondrej Skalicka
 */
public class SetTrueOperation extends BaseReversibleOperation {
    /**
     * Variable to have operation applied on.
     */
    protected Variable variable;

    /**
     * Creates new operation with variable to have the operation applied on.
     *
     * @param variable variable to have operation applied on
     */
    public SetTrueOperation(Variable variable) {
        this.variable = variable;
    }

    public Configuration execute(Configuration configuration) {
        List<Integer> newConfiguration = configuration.asList();

        newConfiguration.set(this.variable.getIndex(), 1);

        return new Configuration(newConfiguration, new OperationHistory(this, configuration.getOperationHistory()));
    }

    @Override
    public String toString() {
        return "SetTrueOperation{" +
                "index=" + variable.getIndex() +
                '}';
    }
}
