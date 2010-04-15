/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.bucket;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Operation;
import cz.cvut.felk.cig.jcop.problem.OperationHistory;

import java.util.List;

/**
 * Fills bucket to its full capacity.
 *
 * @author Ondrej Skalicka
 */
public class FillOperation implements Operation {
    /**
     * Bucket to be filled to the brim.
     */
    protected BucketItem bucketItem;

    /**
     * Creates fill operation for given bucket item.
     *
     * @param bucketItem bucket item to be filled
     */
    public FillOperation(BucketItem bucketItem) {
        this.bucketItem = bucketItem;
    }

    public Configuration execute(Configuration configuration) {
        List<Integer> newConfiguration = configuration.asList();

        newConfiguration.set(this.bucketItem.getIndex(), this.bucketItem.getCapacity());

        return new Configuration(newConfiguration, new OperationHistory(this, configuration.getOperationHistory()));
    }

    @Override
    public String toString() {
        return "FillOperation{" +
                "bucketItem=" + bucketItem +
                '}';
    }
}
