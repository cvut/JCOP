/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm.graphsearch.dfs;

import cz.cvut.felk.cig.jcop.algorithm.graphsearch.GraphSearchQueue;
import cz.cvut.felk.cig.jcop.problem.Configuration;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * Queue for DFS algorithm.
 * <p/>
 * Uses Set&lt;List&lt;Integer&gt;&gt; to keep track of visited elements ({@link cz.cvut.felk.cig.jcop.problem.Configuration#asList()
 * is used as key}).
 * <p/>
 * Active elements are stored in {@link java.util.LinkedList}, new elements puts to front of queue, element to be
 * expanded pops from start of queue.
 *
 * @see DepthFirstSearch DFS uses this queue
 */
public class DepthFirstSearchQueue implements GraphSearchQueue {
    /**
     * Stack to store opened configurations in.
     */
    protected LinkedList<Configuration> queue;
    /**
     * Set to store all configurations ever found in.
     * <p/>
     * Note that only configuration's attributes are stored in order to reduce memory overhead (configurations points to
     * operation history which cannot be cleared until configuration itself is removed).
     */
    protected Set<List<Integer>> set;

    public DepthFirstSearchQueue() {
        this.queue = new LinkedList<Configuration>();
        this.set = new HashSet<List<Integer>>();
    }

    public Configuration fetch() {
        return queue.pop();
    }

    public void add(Configuration newElement) {
        queue.addFirst(newElement);
        set.add(newElement.asList());
    }

    public boolean testPresence(Configuration testedElement) {
        return set.contains(testedElement.asList());
    }

    public int size() {
        return queue.size();
    }

}
