/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.solver;

import cz.cvut.felk.cig.jcop.algorithm.Algorithm;
import cz.cvut.felk.cig.jcop.problem.BaseObjectiveProblem;
import cz.cvut.felk.cig.jcop.problem.Problem;
import cz.cvut.felk.cig.jcop.result.render.SimpleCompareRender;

import java.util.ArrayList;
import java.util.List;

/**
 * Compares several problems with one algorithm.
 *
 * @author Ondrej Skalicka
 */
public class ProblemCompareSolver extends BaseSolver {
    /**
     * Problems to apply algorithm on.
     */
    protected List<Problem> problems;
    /**
     * Algorithm to apply on problems.
     */
    protected Algorithm algorithm;

    /**
     * Creates new solver with list of problems to be solved by single algorithm.
     *
     * @param algorithm algorithm to solve all problems
     * @param problems  list of problems to be solved
     */
    public ProblemCompareSolver(Algorithm algorithm, List<Problem> problems) {
        this.algorithm = algorithm;
        this.problems = problems;
        this.defaultRenders.add(new SimpleCompareRender());
    }

    /**
     * Creates solver without any problems.
     * <p/>
     * Problems are expected to be added later via {@link #addProblem(cz.cvut.felk.cig.jcop.problem.Problem)}.
     *
     * @param algorithm algorithm to solve problems
     */
    public ProblemCompareSolver(Algorithm algorithm) {
        this(algorithm, new ArrayList<Problem>());
    }

    /**
     * Adds new problem to solver.
     *
     * @param problem additional problem to be solved
     */
    public void addProblem(Problem problem) {
        this.problems.add(problem);
    }

    public void run() {
        logger.info("Started solver.");

        for (Problem problem : this.problems) {
            this.getResult().addEntry(this.optimize(new BaseObjectiveProblem(problem), algorithm));
        }

        logger.info("Stopped solver.");
    }
}
