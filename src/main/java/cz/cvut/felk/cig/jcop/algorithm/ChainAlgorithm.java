/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.ObjectiveProblem;

/**
 * Chain algorithms can continue work of other algorithm by giving them a configuration to work on.  
 *
 * @author Ondrej Skalicka
 */
public interface ChainAlgorithm extends Algorithm {
    /**
     * Initializes new Algorithm on a problem.
     * <p/>
     * Algorithm is expected to prepare its fitness at this point, since solver can override it afterwards.
     * <p/>
     * Also, keep in mind that one algorithm could be used repeatedly on several different problems, so init should
     * reset all local settings.
     *
     * @param problem problem to be initialized on
     * @param activeConfiguration new active configuration
     * @throws InvalidProblemException if supplied problem cannot be solved by this algorithm
     */
    public void init(ObjectiveProblem problem, Configuration activeConfiguration) throws InvalidProblemException;
}
