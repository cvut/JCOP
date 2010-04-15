/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.knapsack;

import cz.cvut.felk.cig.jcop.problem.BaseReversibleOperation;
import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.OperationHistory;

import java.util.List;

/**
 * Removes item from knapsack
 *
 * @author Ondrej Skalicka
 */
public class RemoveOperation extends BaseReversibleOperation {
    /**
     * Item to be removed from knapsack.
     */
    protected KnapsackItem knapsackItem;

    /**
     * Creates new RemoveOperation associated with knapsackItem
     *
     * @param knapsackItem item to associate operation with
     */
    public RemoveOperation(KnapsackItem knapsackItem) {
        this.knapsackItem = knapsackItem;
    }

    public Configuration execute(Configuration configuration) {
        List<Integer> newConfiguration = configuration.asList();

        newConfiguration.set(this.knapsackItem.getIndex(), 0);

        return new Configuration(newConfiguration, new OperationHistory(this, configuration.getOperationHistory()));
    }

    @Override
    public String toString() {
        return "RemoveOperation{" +
                "knapsackItem=" + knapsackItem +
                '}';
    }
}
