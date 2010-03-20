/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

import java.util.Iterator;

/**
 * Lazy iterator over operations in problem.
 * <p/>
 * Goal of OperationIterator is to be able to reduce the overhead of creating list of all operation for a configuration,
 * when only few will be used in near future. Instead, OperationIterator creates operation only when they are necessary,
 * thus greatly reducing memory overhead.
 *
 * @author Ondrej Skalicka
 */
public interface OperationIterator extends Iterator<Operation> {
    /**
     * Returns randomly chosen operation.
     * <p/>
     * Note that internal counter is not changed, therefore subsequent calls to next will be unaffected by
     * getRandomOperation.
     * <p/>
     * Also, two calls of getRandomOperation could possibly return the same operation.
     *
     * @return randomly chosen operation
     * @throws UnsupportedOperationException if random operation is not supported
     */
    public Operation getRandomOperation() throws UnsupportedOperationException;

    /**
     * Checks if there is more operations left in iterator.
     *
     * @return true if there is at least one more operation left
     */
    boolean hasNext();

    /**
     * Fetches next operation from iterator and increases internal counter.
     *
     * @return next operation
     */
    Operation next();

    /**
     * Removes last operation returned by {@link #next()} (optional).
     * <p/>
     * Optional, usually not implemented.
     */
    void remove();
}
