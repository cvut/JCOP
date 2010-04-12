/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm;

import cz.cvut.felk.cig.jcop.problem.Configuration;

/**
 * Chain algorithms can continue work of other algorithm by giving them a configuration to work on.  
 *
 * @author Ondrej Skalicka
 */
public interface ChainAlgorithm extends Algorithm {
    /**
     * Sets configuration found previously with another algorithm (or other means) for this algorithm to continue on.
     *
     * @param activeConfiguration new active configuration
     */
    public void setActiveConfiguration (Configuration activeConfiguration);
}
