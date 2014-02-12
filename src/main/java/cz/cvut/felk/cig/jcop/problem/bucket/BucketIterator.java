/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.bucket;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Operation;
import cz.cvut.felk.cig.jcop.problem.OperationIterator;
import cz.cvut.felk.cig.jcop.util.JcopRandom;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Iterator over bucket operations.
 * <p/>
 * Operations are iterated in this way: <ol> <li>{@link PourOperation Pour operations} <li>{@link SpillOperation Spill
 * operations} <li>{@link FillOperation Fill operations} </ol>
 * <p/>
 * Each operation type is sorted by source bucket. Eg. first filled bucket is bucket with index 0, then index 1 etc.
 * Pouring is first pour bucket with index 0 to buckets 1 ... n, then pour bucket 1 to other etc.
 *
 * @author Ondrej Skalicka
 */
public class BucketIterator implements OperationIterator {
    /**
     * Main counter. For fill and spill operations used to indicate which bucket to fill/spill. For pour operations
     * indicates destination bucket.
     */
    protected int counter = 0;
    /**
     * Secondary counter. Not used for fill/spill operations, for pour operations indicates source bucket.
     */
    protected int counter2 = 0;
    /**
     * Stage of iterator.
     * <p/>
     * 0 = prepare stage (move counter not yet called), 1 = pour operations, 2 = spill operations, 3 = fill operations,
     * .
     */
    protected int stage = 0;
    /**
     * Configuration to iterate operations over.
     */
    protected Configuration configuration;
    /**
     * Problem for configuration, required to get bucket capacities.
     */
    protected Bucket problem;

    /**
     * Creates new bucket iterator for certain configuration
     *
     * @param configuration configuration to create iterator for
     * @param problem       problem that configuration is part of
     */
    public BucketIterator(Configuration configuration, Bucket problem) {
        this.configuration = configuration;
        this.problem = problem;
        this.moveCounter();
    }

    /**
     * Moves internal counter to next value (can change counter or both counter and stage).
     */
    protected void moveCounter() {
        // Prepare stage, just init
        if (this.stage == 0) {
            this.stage = 1;
            this.counter = -1;
            this.counter2 = 0;
        }
        // First stage. Try to pour every non-empty bucket to every non-full
        if (this.stage == 1) {
            List<PourOperation> sourceBucketOperations;
            // iterating over source buckets
            while (this.counter2 < this.problem.pourOperations.size()) {
                sourceBucketOperations = this.problem.pourOperations.get(this.counter2);

                // source bucket is empty, skip it right away. fetch info from
                // first operation in list
                if (sourceBucketOperations.size() > 0 && this.configuration.valueAt(sourceBucketOperations.get(0).sourceBucket.getIndex()) > 0) {
                    // move to next destination bucket
                    this.counter++;
                    while (this.counter < sourceBucketOperations.size()) {
                        // non-full destination
                        if (this.configuration.valueAt(sourceBucketOperations.get(counter).destinationBucket.getIndex()) < sourceBucketOperations.get(counter).destinationBucket.getCapacity())
                            return;
                        // try next bucket
                        this.counter++;
                    }
                }
                // depleted all possibilities for this source bucket (or source
                // is empty), move to next source bucket
                this.counter2++;
                this.counter = -1;
            }
            this.stage = 2;
            this.counter = -1;
        }
        // Second stage. Try to spill any non-empty bucket
        if (this.stage == 2) {
            // move to next bucket
            this.counter++;
            while (this.counter < this.problem.getDimension()) {
                // non-empty bucket
                if (this.configuration.valueAt(this.problem.fillOperations.get(counter).bucketItem.getIndex()) > 0)
                    return;
                // try next bucket
                this.counter++;
            }
            // tried all buckets, move to next stage
            this.stage = 3;
            this.counter = -1;
        }
        // Third stage. Try to fill any non-full bucket.
        if (this.stage == 3) {
            // move to next bucket
            this.counter++;
            while (this.counter < this.problem.getDimension()) {
                // non-full bucket
                if (this.configuration.valueAt(this.problem.fillOperations.get(counter).bucketItem.getIndex()) < this.problem.buckets.get(counter).getCapacity())
                    return;
                // try next bucket
                this.counter++;
            }
            // tried all buckets, move to next stage
            this.stage = 4;
            this.counter = -1;
        }
    }

    public boolean hasNext() {
        return this.stage < 4 && this.stage > 0;
    }

    public Operation next() throws NoSuchElementException {
        int oldCounter = this.counter;
        int oldCounter2 = this.counter2;
        int oldStage = this.stage;
        this.moveCounter();

        // stage one - pour operations
        if (oldStage == 1)
            return this.problem.pourOperations.get(oldCounter2).get(oldCounter);
        // stage two - spill operations
        if (oldStage == 2) return this.problem.spillOperations.get(oldCounter);
        // stage three - fill operations
        if (oldStage == 3) return this.problem.fillOperations.get(oldCounter);

        throw new NoSuchElementException("Bucket iterator has no more operations");
    }

    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("BucketIterator does not support remove()");
    }

    public Operation getRandomOperation() {
        int backupCounter = this.counter;
        int backupStage = this.stage;
        int backupCounter2 = this.counter2;

        this.stage = JcopRandom.nextInt(4);
        this.counter = JcopRandom.nextInt(this.problem.getDimension());
        this.counter2 = JcopRandom.nextInt(this.problem.getDimension());

        this.moveCounter();
        if (!this.hasNext()) {
            this.stage = 0;
            this.moveCounter();
        }

        Operation operation;

        try {
            operation = this.next();
        } finally {
            this.counter = backupCounter;
            this.stage = backupStage;
            this.counter2 = backupCounter2;
        }

        return operation;
    }
}
