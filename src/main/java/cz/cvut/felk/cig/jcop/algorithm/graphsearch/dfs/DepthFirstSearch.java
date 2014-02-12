/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm.graphsearch.dfs;

import cz.cvut.felk.cig.jcop.algorithm.graphsearch.GraphSearch;
import cz.cvut.felk.cig.jcop.algorithm.graphsearch.GraphSearchQueue;

/**
 * Depth First Search algorithm, using FIFO queue to store new element to be expanded.
 *
 * @author Ondrej Skalicka
 */
public class DepthFirstSearch extends GraphSearch {
    @Override
    protected GraphSearchQueue initQueue() {
        return new DepthFirstSearchQueue();
    }
}
