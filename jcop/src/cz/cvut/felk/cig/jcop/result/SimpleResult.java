/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple implementation of {@link cz.cvut.felk.cig.jcop.result.Result}.
 * <p/>
 * Stores all entries in one list.
 *
 * @author Ondrej Skalicka
 */
public class SimpleResult implements Result {
    /**
     * All entries in Result. Ordered by time in which they were added.
     */
    protected List<ResultEntry> resultEntries;

    /**
     * Creates new SimpleResult with empty list of Entries.
     */
    public SimpleResult() {
        resultEntries = new ArrayList<ResultEntry>();
    }

    /**
     * Creates new SimpleResult with empty list of Entries and the specified initial capacity.
     *
     * @param initialCapacity initial capacity of list for result entries
     */
    public SimpleResult(int initialCapacity) {
        resultEntries = new ArrayList<ResultEntry>(initialCapacity);
    }

    public void addEntry(ResultEntry resultEntry) {
        this.resultEntries.add(resultEntry);
    }

    public List<ResultEntry> getResultEntries() {
        return this.resultEntries;
    }

    public void clearResults() {
        this.resultEntries.clear();
    }
}
