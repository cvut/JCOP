/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.tsp;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Operation;
import cz.cvut.felk.cig.jcop.problem.OperationHistory;

import java.util.List;

/**
 * Switches two cities in configuration.
 * <p/>
 * Switches order in which two cities are visited in TSP route. Takes two integers as arguments - indexes of two cities
 * IN CONFIGURATION (eg. not {@link cz.cvut.felk.cig.jcop.problem.tsp.City#getIndex()}, but their actual index in
 * configuration. Switches these two.
 * <p/>
 * Example:
 * <p/>
 * Configuration = [5,2,3,4,1,0] (eg. first visited city is city with index 5, then index 2, 3, 4, 1 and last city is
 * index 0).
 * <p/>
 * new SwitchCityOperation (1,2) (switches second and third cities)
 * <p/>
 * Results in
 * <p/>
 * Configuration = [5,3,2,4,1,0]
 *
 * @author Ondrej Skalicka
 */
public class SwitchCityOperation implements Operation {
    /**
     * Source index to find switched city on.
     */
    protected int sourceIndex;
    /**
     * Destination index to find switched city on.
     */
    protected int destinationIndex;

    public SwitchCityOperation(int sourceIndex, int destinationIndex) {
        this.destinationIndex = destinationIndex;
        this.sourceIndex = sourceIndex;
    }

    public Configuration execute(Configuration configuration) {
        List<Integer> newConfiguration = configuration.asList();

        newConfiguration.set(this.sourceIndex, configuration.valueAt(this.destinationIndex));
        newConfiguration.set(this.destinationIndex, configuration.valueAt(this.sourceIndex));

        return new Configuration(newConfiguration, new OperationHistory(this, configuration.getOperationHistory()));
    }

    @Override
    public String toString() {
        return "SwitchCityOperation{" +
                "destinationIndex=" + destinationIndex +
                ", sourceIndex=" + sourceIndex +
                '}';
    }
}
