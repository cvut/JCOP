/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.knapsack;

import cz.cvut.felk.cig.jcop.problem.BaseReversibleOperation;
import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.OperationHistory;

import java.util.List;

/**
 * Adds item to knapsack
 *
 * @author Ondrej Skalicka
 */
public class AddOperation extends BaseReversibleOperation {
    /**
     * Item to be added to knapsack.
     */
    protected KnapsackItem knapsackItem;

    /**
     * Creates new AddOperation associated with knapsackItem
     *
     * @param knapsackItem item to associate operation with
     */
    public AddOperation(KnapsackItem knapsackItem) {
        this.knapsackItem = knapsackItem;
    }

    public Configuration execute(Configuration configuration) {
        List<Integer> newConfiguration = configuration.asList();

        newConfiguration.set(this.knapsackItem.getIndex(), 1);

        return new Configuration(newConfiguration, new OperationHistory(this, configuration.getOperationHistory()));
    }

    @Override
    public String toString() {
        return "AddOperation{" +
                "knapsackItem=" + knapsackItem +
                '}';
    }
}
