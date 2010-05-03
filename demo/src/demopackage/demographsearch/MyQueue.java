/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package demopackage.demographsearch;

import cz.cvut.felk.cig.jcop.algorithm.graphsearch.GraphSearchQueue;
import cz.cvut.felk.cig.jcop.problem.Configuration;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author Ondrej Skalicka
 */
public class MyQueue implements GraphSearchQueue {
    protected LinkedList<Configuration> queue;
    protected Set<String> set;

    public MyQueue() {
        this.queue = new LinkedList<Configuration>();
        this.set = new HashSet<String>();
    }

    public Configuration fetch() {
        return queue.pop();
    }

    public void add(Configuration newElement) {
        queue.addFirst(newElement);
        set.add(newElement.fingerprint());
    }

    public boolean testPresence(Configuration testedElement) {
        return set.contains(testedElement.fingerprint());
    }

    public int size() {
        return queue.size();
    }

}
