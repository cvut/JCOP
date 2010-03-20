/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm.graphsearch.bfs;

import cz.cvut.felk.cig.jcop.algorithm.graphsearch.GraphSearch;
import cz.cvut.felk.cig.jcop.algorithm.graphsearch.GraphSearchQueue;

/**
 * Breadth First Search algorithm, using LIFO queue to store new element to be expanded.
 *
 * @author Ondrej Skalicka
 */
public class BreadthFirstSearch extends GraphSearch {
    @Override
    protected GraphSearchQueue initQueue() {
        return new BreadthFirstSearchQueue();
    }
}
