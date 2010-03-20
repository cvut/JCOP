/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

/**
 * This is dummy operation used mostly for {@link cz.cvut.felk.cig.jcop.result.OperationHistory} to indicate that new
 * attributes was created (either by {@link cz.cvut.felk.cig.jcop.problem.RandomConfigurationProblem} or {@link
 * cz.cvut.felk.cig.jcop.problem.StartingConfigurationProblem}.
 *
 * @author Ondrej Skalicka
 */
public final class OperationCreated implements Operation {
    protected String toString = "New configuration created";

    /**
     * Creates new operation with specified text to be displayed in {@link #toString()}.
     *
     * @param toString string returned from {@link #toString()} calls.
     */
    public OperationCreated(String toString) {
        this.toString = toString;
    }

    /**
     * Creates new operation with {@link #toString default text} displayed in {@link #toString()}.
     */
    public OperationCreated() {
    }

    /**
     * This operation cannot be executed as it has absolutely no effect but to indicate creation of new attributes in
     * {@link cz.cvut.felk.cig.jcop.result.OperationHistory}
     *
     * @throws UnsupportedOperationException every time called - this operation is not to be called at all.
     */
    public Configuration execute(Configuration configuration) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Operation Created cannot be called directly");
    }

    /**
     * Returns string stored in {@link #toString}
     */
    @Override
    public String toString() {
        return toString;
    }
}