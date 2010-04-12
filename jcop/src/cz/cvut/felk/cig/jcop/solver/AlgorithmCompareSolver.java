/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.solver;

import cz.cvut.felk.cig.jcop.algorithm.Algorithm;
import cz.cvut.felk.cig.jcop.problem.BaseObjectiveProblem;
import cz.cvut.felk.cig.jcop.problem.ObjectiveProblem;
import cz.cvut.felk.cig.jcop.problem.Problem;
import cz.cvut.felk.cig.jcop.result.render.SimpleCompareRender;

import java.util.ArrayList;
import java.util.List;

/**
 * Compares several algorithms against one problem.
 *
 * @author Ondrej Skalicka
 */
public class AlgorithmCompareSolver extends BaseSolver {
    /**
     * Problem to apply algorithms on.
     */
    protected ObjectiveProblem problem;
    /**
     * List of algorithms to apply on problem.
     */
    protected List<Algorithm> algorithms;

    /**
     * Creates new solver with list of algorithms to be applied to single problem.
     *
     * @param problem    problem to be solved several times by algorithms
     * @param algorithms list of algorithms to be applied to problem
     */
    public AlgorithmCompareSolver(Problem problem, List<Algorithm> algorithms) {
        this.algorithms = algorithms;
        this.problem = new BaseObjectiveProblem(problem);
        this.defaultRenders.add(new SimpleCompareRender());
    }

    /**
     * Creates new AlgorithmCompareSolver with only problem specified, no algorithms.
     * <p/>
     * Algorithms are to be added later by {@link #addAlgorithm(cz.cvut.felk.cig.jcop.algorithm.Algorithm)}.
     *
     * @param problem problem to be used on all algorithms
     */
    public AlgorithmCompareSolver(Problem problem) {
        this(problem, new ArrayList<Algorithm>());
    }

    /**
     * Adds new algorithm to be used on problem.
     *
     * @param algorithm new algorithm
     */
    public void addAlgorithm(Algorithm algorithm) {
        this.algorithms.add(algorithm);
    }

    public void run() {
        logger.info("Started solver.");

        for (Algorithm algorithm : this.algorithms) {
            this.getResult().addEntry(this.optimize(this.problem, algorithm));
        }

        logger.info("Stopped solver.");
    }
}
