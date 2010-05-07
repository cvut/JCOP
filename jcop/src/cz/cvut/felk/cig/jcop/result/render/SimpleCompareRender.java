/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.result.render;

import cz.cvut.felk.cig.jcop.result.Result;
import cz.cvut.felk.cig.jcop.result.ResultEntry;
import cz.cvut.felk.cig.jcop.util.compare.ResultEntryFitnessComparator;
import cz.cvut.felk.cig.jcop.util.compare.ResultEntryOptimizeComparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Renders simple comparison of algorithms or problems.
 *
 * @author Ondrej Skalicka
 */
public class SimpleCompareRender implements Render {
    /**
     * Stream to print result to.
     * <p/>
     * Default is System.out, but could be replaced with a file or any other.
     */
    protected PrintStream printStream = System.out;

    /**
     * Creates new simple compare render.
     */
    public SimpleCompareRender() {
    }

    /**
     * Creates new simple compare render with print stream specified.
     *
     * @param printStream print stream to write to
     */
    public SimpleCompareRender(PrintStream printStream) {
        this.printStream = printStream;
    }

    /**
     * Creates new simple compare render with print stream set to a file.
     * <p/>
     * Note that SimpleCompareRender will overwrite contents of supplied file. If you create several renders with same
     * File, their contents will overwrite each other. In such case, create PrintStream beforehand and use {@link
     * #SimpleCompareRender(java.io.PrintStream)} instead.
     *
     * @param file file to write to
     * @throws java.io.FileNotFoundException If the given file object does not denote an existing, writable regular file
     *                                       and a new regular file of that name cannot be created, or if some other
     *                                       error occurs while opening or creating the file
     * @see java.io.PrintStream#PrintStream(java.io.File) print stream from file
     */
    public SimpleCompareRender(File file) throws FileNotFoundException {
        this.printStream = new PrintStream(file);
    }

    public void render(Result result) {
        // create a copy so we do not change order for other renders
        List<ResultEntry> resultEntries = new ArrayList<ResultEntry>(result.getResultEntries());

        Collections.sort(resultEntries, new ResultEntryFitnessComparator(false));
        this.printStream.println();
        this.printStream.println("Results sorted by fitness (DESC):");
        this.printStream.printf("%-10s %-60s %s\n", "fitness", "algorithm", "problem");
        for (ResultEntry resultEntry : resultEntries) {
            this.printStream.printf("%-10.1f %-60s %s\n", resultEntry.getBestFitness(), resultEntry.getAlgorithm(), resultEntry.getProblem());
        }

        Collections.sort(resultEntries, new ResultEntryOptimizeComparator());
        this.printStream.println();
        this.printStream.println("Results sorted by optimizations (ASC):");
        this.printStream.printf("%-10s %-60s %s\n", "optimiz.", "algorithm", "problem");
        for (ResultEntry resultEntry : resultEntries) {
            this.printStream.printf("%-10d %-60s %s\n", resultEntry.getOptimizeCounter(), resultEntry.getAlgorithm(), resultEntry.getProblem());
        }
    }
}
