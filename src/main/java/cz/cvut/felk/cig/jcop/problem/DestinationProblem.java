/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

import java.util.List;

/**
 * DestinationProblem has one or more `destination` configurations which are considered solution of a problem, eg. if
 * you are able to find your way to such attributes, you have solution to that problem.
 * <p/>
 * Example is bucket problem  - you have specified one attributes (amount of water in every bucket) as your goal.
 *
 * @author Ondrej Skalicka
 */
public interface DestinationProblem {

    /**
     * Destination attributes of problem.
     * <p/>
     * Configurations which problem recognizes as solution.
     *
     * @return list of all allowed destinations
     */
    public List<Configuration> getDestinations();

}