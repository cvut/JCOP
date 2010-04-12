/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.solver;

import cz.cvut.felk.cig.jcop.algorithm.Algorithm;
import cz.cvut.felk.cig.jcop.problem.BaseObjectiveProblem;
import cz.cvut.felk.cig.jcop.problem.ObjectiveProblem;
import cz.cvut.felk.cig.jcop.problem.Problem;

/**
 * Basic solver, applying one algorithm on one problem until one condition is met, algorithm cannot continue or
 * exception is raised.
 *
 * @author Ondrej Skalicka
 */
public class SimpleSolver extends BaseSolver {
    /**
     * Problem to be solved.
     */
    protected ObjectiveProblem problem;
    /**
     * Algorithm to be applied on problem.
     */
    protected Algorithm algorithm;

    /**
     * Creates new solver with single algorithm and single problem.
     * <p/>
     * Note that problem is converted to {@link cz.cvut.felk.cig.jcop.problem.BaseObjectiveProblem}.
     *
     * @param algorithm algorithm to be applied on problem
     * @param problem   problem to be solved by algorithm
     */
    public SimpleSolver(Algorithm algorithm, Problem problem) {
        this.algorithm = algorithm;
        this.problem = new BaseObjectiveProblem(problem);
    }

    public void run() {
        logger.info("Started solver.");

        this.getResult().addEntry(this.optimize(this.problem, this.algorithm));

        logger.info("Stopped solver.");
    }
}
