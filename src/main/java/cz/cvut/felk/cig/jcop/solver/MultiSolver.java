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
 * Multiple solver is used to run several algorithms each on several problems.
 * <p/>
 * Each algorithm is applied on every problem, so the result is cartesian product.
 *
 * @author Ondrej Skalicka
 */
public class MultiSolver extends BaseSolver {
    /**
     * Problems to apply algorithms on.
     */
    protected List<Problem> problems;
    /**
     * Algorithms to apply on problems
     */
    protected List<Algorithm> algorithms;

    /**
     * Creates solver with given lists both of algorithms and problems.
     *
     * @param algorithms list of algorithms to be used
     * @param problems   list of problems to be solved
     */
    public MultiSolver(List<Algorithm> algorithms, List<Problem> problems) {
        this.algorithms = algorithms;
        this.problems = problems;
        this.defaultRenders.add(new SimpleCompareRender());
    }

    /**
     * Creates solver with no algorithms nor problems, just empty lists and to be added later.
     */
    public MultiSolver() {
        this(new ArrayList<Algorithm>(), new ArrayList<Problem>());
    }

    /**
     * Adds new algorithm to the solver.
     *
     * @param algorithm algorithm to be used
     */
    public void addAlgorithm(Algorithm algorithm) {
        this.algorithms.add(algorithm);
    }

    /**
     * Adds new problem to the solver.
     *
     * @param problem problem to be solved
     */
    public void addProblem(Problem problem) {
        this.problems.add(problem);
    }

    public void run() {
        logger.info("Started solver.");

        for (Problem problem : this.problems) {
            ObjectiveProblem objectiveProblem = new BaseObjectiveProblem(problem);
            for (Algorithm algorithm : this.algorithms) {
                this.getResult().addEntry(this.optimize(objectiveProblem, algorithm));
            }
        }

        logger.info("Stopped solver.");
    }
}
