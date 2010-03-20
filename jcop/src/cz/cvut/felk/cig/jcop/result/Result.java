/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.result;

import java.util.List;

/**
 * Result contains all information about {@link cz.cvut.felk.cig.jcop.solver.Solver#run()} run of a solver.
 * <p/>
 * Result can contain multiple entries for the same problem or algorithm, entries are not required to be unique at all.
 * <p/>
 * Entries in result has 3 main parts - its algorithm, its problem and resulting data (time, number of optimizations
 * etc).
 *
 * @author Ondrej Skalicka
 */
public interface Result {
    /**
     * Adds new entry to whole result.
     *
     * @param resultEntry entry to add
     */
    public void addEntry(ResultEntry resultEntry);

    /**
     * Returns all entries in a list in order in which they were added.
     *
     * @return all entries for this result
     */
    public List<ResultEntry> getResultEntries();

    /**
     * Removes all result entries from result
     */
    public void clearResults();
}
