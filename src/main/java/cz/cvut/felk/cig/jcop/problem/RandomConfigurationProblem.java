/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

/**
 * Problem implementing RandomConfigurationProblem is able to return random configurations.
 * <p/>
 * This includes problems like SAT, where you can have each attributes attribute have random value true or false.
 *
 * @author Ondrej Skalicka
 */
public interface RandomConfigurationProblem {

    /**
     * Returns one random configuration without seed set.
     *
     * @return one random configuration
     */
    public Configuration getRandomConfiguration();

}