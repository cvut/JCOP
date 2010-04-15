/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.bucket;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Operation;
import cz.cvut.felk.cig.jcop.problem.OperationHistory;

import java.util.List;

/**
 * Spills bucket empty.
 *
 * @author Ondrej Skalicka
 */
public class SpillOperation implements Operation {
    /**
     * Bucket to be spilled (emptied).
     */
    protected BucketItem bucketItem;

    /**
     * Creates new SpillOperation for given bucket.
     *
     * @param bucketItem bucket to spill
     */
    public SpillOperation(BucketItem bucketItem) {
        this.bucketItem = bucketItem;
    }

    public Configuration execute(Configuration configuration) {
        List<Integer> newConfiguration = configuration.asList();

        newConfiguration.set(this.bucketItem.getIndex(), 0);

        return new Configuration(newConfiguration, new OperationHistory(this, configuration.getOperationHistory()));
    }

    @Override
    public String toString() {
        return "SpillOperation{" +
                "bucketItem=" + bucketItem +
                '}';
    }
}
