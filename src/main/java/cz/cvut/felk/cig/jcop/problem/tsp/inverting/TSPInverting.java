/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.tsp.inverting;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.OperationIterator;
import cz.cvut.felk.cig.jcop.problem.ProblemFormatException;
import cz.cvut.felk.cig.jcop.problem.tsp.TSP;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Traveling Salesman Problem with 2-opt operations.
 * <p/>
 * TSP problem has a list of cities and every city has distance to every other city. The goal is to find order in which
 * to visit every city exactly once and minimize travel distance.
 *
 * @author Ondrej Skalicka
 */
public class TSPInverting extends TSP {
    protected List<InvertPathOperation> invertPathOperations;

    public TSPInverting(File configFile) throws IOException, ProblemFormatException {
        super(configFile);
    }

    public TSPInverting(Integer[][] distances) throws ProblemFormatException {
        super(distances);
    }

    @Override
    protected void initOperations() {
        // prepare switch operation container
        this.invertPathOperations = new ArrayList<InvertPathOperation>(this.dimension * (this.dimension - 1));

        for (int i = 0; i < this.dimension; ++i) {
            for (int j = 0; j < this.dimension; ++j) {
                if (j != i) this.invertPathOperations.add(new InvertPathOperation(i, j));
            }
        }
    }

    @Override
    public OperationIterator getOperationIterator(Configuration configuration) {
        return new TSPInvertingIterator(configuration, this);
    }
}
