/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm.graphsearch;

import cz.cvut.felk.cig.jcop.problem.Configuration;

/**
 * GraphSearchQueue is used for GraphSearch algorithms to store open states (eg. configurations), add more, test if new
 * state has already been processed (either it is opened or closed) and return new state to expand.
 */
public interface GraphSearchQueue {
    /**
     * Fetches one element from queue.
     * <p/>
     * If there are no elements, returns NULL
     *
     * @return either one element or NULL
     */
    public Configuration fetch();

    /**
     * Adds new element to queue.
     * <p/>
     * Does not check for presence of that element, simply adds it.
     *
     * @param newElement new element to be added to queue
     */
    public void add(Configuration newElement);

    /**
     * Test whether tested element has already been added to queue.
     * <p/>
     * This does not mean element is still in it! Only it has been at some point in time there.
     *
     * @param testedElement element to be tested
     * @return true if element has been there, false otherwise
     */
    public boolean testPresence(Configuration testedElement);

    /**
     * Returns the number of elements in queue.
     *
     * @return number of elements in queue
     */
    public int size();
}
