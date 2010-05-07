/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.solver;

import cz.cvut.felk.cig.jcop.result.Result;
import cz.cvut.felk.cig.jcop.result.ResultEntry;
import cz.cvut.felk.cig.jcop.result.render.SimpleCompareRender;
import cz.cvut.felk.cig.jcop.util.compare.ResultEntryFitnessComparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Median solver takes another solver, runs it repeatedly and then returns median of result entries of original solver.
 * <p/>
 * MedianSolver runs original solver (also referred as wrapped solver) several times. Every time, original solver
 * returns list of result entries. For every item in that list, it creates separate group (implemented as list) and puts
 * that item to the group. Then takes another list of results (2nd run of wrapped solver) and puts all result entries in
 * appropriate groups. This is repeated until required number of runs of wrapped solver has been made.
 * <p/>
 * Afterwards takes median from every group (key to sort is fitness) and use these medians as a result by MedianSolver
 * (wraps the medians back to a {@link cz.cvut.felk.cig.jcop.result.Result}).
 * <p/>
 * Note that wrapped entry must return identical number of result entries in result each time and their order is
 * required to be the same each run. If first condition is violated, exception will be raised. If second is violated,
 * unexpected result may occur.
 *
 * @author Ondrej Skalicka
 */
public class MedianSolver extends BaseSolver {
    /**
     * Original (wrapped) solver to be ran repeatedly.
     */
    protected Solver solver;
    /**
     * Number of times original solver has to be ran.
     */
    protected int repeatedRuns;
    /**
     * File to write additional data to.
     */
    protected File file;

    /**
     * Creates new wrapper around other solver with given number of runs.
     *
     * @param solver       which solver to run repeatedly
     * @param repeatedRuns how many times to run the solver
     * @throws IllegalArgumentException if repeatedRuns is lower than 1
     */
    public MedianSolver(Solver solver, int repeatedRuns) throws IllegalArgumentException {
        this(solver, repeatedRuns, null);
    }

    /**
     * Creates new wrapper around other solver with given number of runs. Also, writes basic information about all
     * solutions into a file.
     *
     * @param solver       which solver to run repeatedly
     * @param repeatedRuns how many times to run the solver
     * @param file         file to write additional information into
     * @throws IllegalArgumentException if repeatedRuns is lower than 1
     */
    public MedianSolver(Solver solver, int repeatedRuns, File file) throws IllegalArgumentException {
        if (repeatedRuns < 1)
            throw new IllegalArgumentException("RepeatedRuns must be greater than zero, " + repeatedRuns + " found.");
        this.repeatedRuns = repeatedRuns;
        this.solver = solver;
        this.defaultRenders.add(new SimpleCompareRender());
        this.file = file;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if two runs of wrapped solver returns results of different size
     */
    public void run() throws IllegalArgumentException {
        List<List<ResultEntry>> groups = null;

        for (int i = 0; i < this.repeatedRuns; ++i) {
            this.solver.getResult().clearResults();
            this.solver.run();
            Result result = this.solver.getResult();

            if (groups == null) {
                // create empty groups
                groups = new ArrayList<List<ResultEntry>>(result.getResultEntries().size());
                // allocate space in every group
                for (int j = 0; j < result.getResultEntries().size(); ++j) {
                    groups.add(new ArrayList<ResultEntry>(this.repeatedRuns));
                }
            }

            if (result.getResultEntries().size() != groups.size())
                throw new IllegalArgumentException("Wrapped solver must return result with identical number of elements each run, got " + result.getResultEntries().size() + " in run " + i + ", expected " + groups.size());

            for (int j = 0; j < result.getResultEntries().size(); j++) {
                ResultEntry resultEntry = result.getResultEntries().get(j);

                groups.get(j).add(resultEntry);
            }
        }

        int middleIndex = this.repeatedRuns / 2;

        for (List<ResultEntry> group : groups) {
            Collections.sort(group, new ResultEntryFitnessComparator());

            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Sorted result entries, dump:");
                for (int i = 0; i < group.size(); i++) {
                    ResultEntry resultEntry = group.get(i);
                    this.logger.debug(i + ". " + resultEntry.getBestFitness());
                }
            }

            this.getResult().addEntry(group.get(middleIndex));
        }


        if (this.file != null) {
            try {
                PrintStream printStream = new PrintStream(file);
                for (List<ResultEntry> group : groups) {
                    printStream.printf("Sorted entries for %s/%s\n", group.get(0).getAlgorithm(), group.get(0).getProblem());
                    for (int i = 0; i < group.size(); i++) {
                        printStream.printf("%03d. %.5f\n", i, group.get(i).getBestFitness());
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
