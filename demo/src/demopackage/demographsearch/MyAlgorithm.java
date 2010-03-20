/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package demopackage.demographsearch;

import cz.cvut.felk.cig.jcop.algorithm.graphsearch.GraphSearch;
import cz.cvut.felk.cig.jcop.algorithm.graphsearch.GraphSearchQueue;
import demopackage.demographsearch.MyQueue;

/**
 * @author Ondrej Skalicka
 */
public class MyAlgorithm extends GraphSearch {
    @Override
    protected GraphSearchQueue initQueue() {
        return new MyQueue();
    }
}
