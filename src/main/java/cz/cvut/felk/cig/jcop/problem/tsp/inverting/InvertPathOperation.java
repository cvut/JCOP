/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.tsp.inverting;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Operation;
import cz.cvut.felk.cig.jcop.problem.OperationHistory;

import java.util.List;

/**
 * Inverts part of a path using a 2-opt method.
 *
 * @author Ondrej Skalicka
 */
public class InvertPathOperation implements Operation {
    /**
     * Source index to find switched city on.
     */
    protected int sourceIndex;
    /**
     * Destination index to find switched city on.
     */
    protected int destinationIndex;

    public InvertPathOperation(int sourceIndex, int destinationIndex) {
        this.destinationIndex = destinationIndex;
        this.sourceIndex = sourceIndex;
    }

    public Configuration execute(Configuration configuration) {
        List<Integer> newConfiguration = configuration.asList();

        int si = sourceIndex;
        int di = destinationIndex;
        int dimension = configuration.getDimension();

        if (si > di) {
            di += dimension;
        }

        while (di > si) {
            newConfiguration.set(si % dimension, configuration.valueAt(di % dimension));
            newConfiguration.set(di % dimension, configuration.valueAt(si % dimension));

            di--;            
            si++;
        }

        return new Configuration(newConfiguration, new OperationHistory(this, configuration.getOperationHistory()));
    }

    @Override
    public String toString() {
        return "InvertPathOperation{" +
                "sourceIndex=" + sourceIndex +
                ", destinationIndex=" + destinationIndex +
                '}';
    }
}
