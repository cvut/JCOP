/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

/**
 * Creates new UnknownOperation for {@link OperationHistory}.
 * <p/>
 * This is mostly used in global search algorithms, which do not use operations so they have no operation history.
 *
 * @author Ondrej Skalicka
 */
public class UnknownOperation implements Operation {
    /**
     * This operation cannot be executed as it has absolutely no effect but to indicate creation of new attributes in
     * {@link OperationHistory}
     *
     * @throws UnsupportedOperationException every time called - this operation is not to be called at all.
     */
    public Configuration execute(Configuration configuration) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("UnknownOperation cannot be called directly");
    }

    @Override
    public String toString() {
        return "Unknown operation";
    }
}
