/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.bucket;

import cz.cvut.felk.cig.jcop.problem.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Bucket problem.
 * <p/>
 * Bucket problem is defined by having fixed number of buckets. Each of these buckets have their maximum capacity and
 * starting capacity. Then each bucket could be fully filled or spilled empty. Furthermore each bucket's contents could
 * be poured into another one using simple rule - if all contents from target fits to destination bucket, pour it all.
 * If not, pour only what is possible and leave the remainder in source bucket.
 * <p/>
 * TODO implement loaders from file or string
 *
 * @author Ondrej Skalicka
 * @see <a href="http://service.felk.cvut.cz/courses/X36PAA/Kyble.htm"> Bucket problem on felk.cvut.fel</a>
 * @see FillOperation Fill operation
 * @see SpillOperation Spill operation
 * @see PourOperation Pour operation
 */
public class Bucket extends BaseProblem implements Problem, DestinationProblem, StartingConfigurationProblem {
    /**
     * List of buckets assigned to this problem instance
     */
    protected List<BucketItem> buckets;
    /**
     * Starting configuration for bucket problem
     */
    protected Configuration startingConfiguration;
    /**
     * Configurations considered as solution
     */
    protected List<Configuration> destinationConfigurations;
    /**
     * Fill operations - one for each bucket. Index of list is equal to {@link BucketItem#index}
     *
     * @see cz.cvut.felk.cig.jcop.problem.bucket.FillOperation
     */
    protected List<FillOperation> fillOperations;
    /**
     * Spill operations - one for each bucket. Index of list is equal to {@link BucketItem#index}
     *
     * @see cz.cvut.felk.cig.jcop.problem.bucket.SpillOperation
     */
    protected List<SpillOperation> spillOperations;
    /**
     * Pour operations - each bucket has its own list (since one bucket could be poured to all other buckets. First
     * index of list is equal to {@link BucketItem#index}. Second is equal to {@link BucketItem#index} of destination
     * bucket iff destination bucket has {@link BucketItem#index} lower than source bucket. If it is higher, index in
     * list is equal to destination's bucket {@link BucketItem#index}-1.
     */
    protected List<List<PourOperation>> pourOperations;

    /**
     * Creates new bucket problem from given capacity, starting contents and destination contents.
     * <p/>
     * Note that this problem has &Omicron;(n<sup>2</sup>) complexity just to create it (every bucket has n-1 operations
     * to other buckets).
     *
     * @param capacity            capacity for bucket 0 .. n-1
     * @param startingContents    starting contents for bucket 0 .. n-1
     * @param destinationContents target contents for bucket 0 .. n-1
     * @throws ProblemFormatException if three input arrays are not of same size or they are empty
     */
    public Bucket(int[] capacity, int[] startingContents, int[] destinationContents) throws ProblemFormatException {
        StringBuffer labelStringBufferA = new StringBuffer("start={");
        StringBuffer labelStringBufferB = new StringBuffer("},destination={");
        StringBuffer labelStringBufferC = new StringBuffer("},capacity={");

        this.dimension = capacity.length;

        // check validity of parameters
        if (this.dimension < 1)
            throw new ProblemFormatException("Invalid dimension " + this.dimension);
        if (destinationContents.length != this.dimension)
            throw new ProblemFormatException("Destination contents has wrong capacity, expected " + this.dimension + ", get " + destinationContents.length);
        if (startingContents.length != this.dimension)
            throw new ProblemFormatException("Starting contents has wrong capacity, expected " + this.dimension + ", get " + startingContents.length);

        // create buckets
        this.buckets = new ArrayList<BucketItem>(this.dimension);
        List<Integer> startingConfigurationAttributes = new ArrayList<Integer>(this.dimension);
        List<Integer> destinationConfigurationAttributes = new ArrayList<Integer>(this.dimension);
        for (int i = 0; i < this.dimension; i++) {

            this.buckets.add(new BucketItem(i, capacity[i]));
            startingConfigurationAttributes.add(startingContents[i]);
            destinationConfigurationAttributes.add(destinationContents[i]);

            // add starting/destination/contents to label
            labelStringBufferA.append(i == 0 ? "" : ",").append(startingContents[i]);
            labelStringBufferB.append(i == 0 ? "" : ",").append(destinationContents[i]);
            labelStringBufferC.append(i == 0 ? "" : ",").append(capacity[i]);
        }

        labelStringBufferA.append(labelStringBufferB);
        labelStringBufferA.append(labelStringBufferC);
        labelStringBufferA.append("}");
        this.setLabel(labelStringBufferA.toString());

        // create starting configuration
        this.startingConfiguration = new Configuration(startingConfigurationAttributes, "Bucket starting configuration");
        // create destination configuration
        this.destinationConfigurations = new ArrayList<Configuration>(1);
        this.destinationConfigurations.add(new Configuration(destinationConfigurationAttributes, "Bucket starting configuration"));

        // create fill & spill operations
        this.fillOperations = new ArrayList<FillOperation>(this.dimension);
        this.spillOperations = new ArrayList<SpillOperation>(this.dimension);
        for (int i = 0; i < this.dimension; i++) {
            this.fillOperations.add(new FillOperation(this.buckets.get(i)));
            this.spillOperations.add(new SpillOperation(this.buckets.get(i)));
        }

        // create pour operations
        this.pourOperations = new ArrayList<List<PourOperation>>(this.dimension);
        for (int i = 0; i < this.dimension; ++i) {
            List<PourOperation> tmp = new ArrayList<PourOperation>(this.dimension - 1);
            for (int j = 0; j < this.dimension; ++j) {
                if (i != j)
                    tmp.add(new PourOperation(this.buckets.get(i), this.buckets.get(j)));
            }
            this.pourOperations.add(tmp);
        }
    }

    /* Problem interface */

    public boolean isSolution(Configuration configuration) {
        return configuration.equals(this.destinationConfigurations.get(0));
    }

    public BucketIterator getOperationIterator(Configuration configuration) {
        return new BucketIterator(configuration, this);
    }

    public Fitness getDefaultFitness() {
        return new BucketFitness(this);
    }

    /* DestinationProblem interface */

    public List<Configuration> getDestinations() {
        return this.destinationConfigurations;
    }

    /* StartingConfigurationProblem interface */

    public Configuration getStartingConfiguration() {
        return this.startingConfiguration;
    }


}
