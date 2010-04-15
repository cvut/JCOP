/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.bucket;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Operation;
import cz.cvut.felk.cig.jcop.problem.OperationHistory;

import java.util.List;

/**
 * Pours contents of one bucket into another.
 * <p/>
 * Adds contents of source bucket to destination bucket. If destination bucket will be overfilled (eg. there is not
 * enough capacity), it is filled to its capacity and rest is left in source bucket.
 *
 * @author Ondrej Skalicka
 */
public class PourOperation implements Operation {
    /**
     * Bucket to take water from.
     */
    protected BucketItem sourceBucket;
    /**
     * Bucket to pour water into.
     */
    protected BucketItem destinationBucket;

    /**
     * Creates new pour operation from source bucket to destination bucket.
     *
     * @param sourceBucket      source bucket
     * @param destinationBucket destination bucket
     */
    public PourOperation(BucketItem sourceBucket, BucketItem destinationBucket) {
        this.sourceBucket = sourceBucket;
        this.destinationBucket = destinationBucket;
    }

    public Configuration execute(Configuration configuration) {
        List<Integer> newConfiguration = configuration.asList();

        int sourceContents = configuration.valueAt(this.sourceBucket.getIndex());
        int destinationContents = configuration.valueAt(this.destinationBucket.getIndex());
        int pour = Math.min(sourceContents, destinationBucket.getCapacity() - destinationContents);

        newConfiguration.set(this.sourceBucket.getIndex(), sourceContents - pour);
        newConfiguration.set(this.destinationBucket.getIndex(), destinationContents + pour);

        return new Configuration(newConfiguration, new OperationHistory(this, configuration.getOperationHistory()));
    }

    @Override
    public String toString() {
        return "PourOperation{" +
                "sourceBucket=" + sourceBucket +
                ", destinationBucket=" + destinationBucket +
                '}';
    }
}
