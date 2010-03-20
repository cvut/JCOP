/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.bucket;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Operation;
import cz.cvut.felk.cig.jcop.problem.OperationIterator;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;

/**
 * Tests proper behavior of {@link BucketIterator}.
 *
 * @author Ondrej Skalicka
 */
public class BucketIteratorTest {
    @Test
    public void testNext() throws Exception {
        Bucket bucket = new Bucket(new int[]{14, 10, 6}, new int[]{0, 3, 6}, new int[]{12, 6, 4});

        Configuration c = bucket.getStartingConfiguration();

        OperationIterator bucketIterator = bucket.getOperationIterator(c);

        Operation o;
        FillOperation fo;
        SpillOperation so;
        PourOperation po;

        o = bucketIterator.next();
        assert o instanceof PourOperation : "Expected 1st operation PourOperation, " + o.getClass() + " found.";
        po = (PourOperation) o;
        assert po.sourceBucket.getIndex() == 1 && po.destinationBucket.getIndex() == 0 :
                "Expected 1st operation to pour bucket 1 into bucket 0, bucket " + po.sourceBucket.getIndex() + " pouring into bucket " + po.destinationBucket.getIndex() + " found.";

        o = bucketIterator.next();
        assert o instanceof PourOperation : "Expected 2nd operation PourOperation, " + o.getClass() + " found.";
        po = (PourOperation) o;
        assert po.sourceBucket.getIndex() == 2 && po.destinationBucket.getIndex() == 0 :
                "Expected 2nd operation to pour bucket 2 into bucket 0, bucket " + po.sourceBucket.getIndex() + " pouring into bucket " + po.destinationBucket.getIndex() + " found.";

        o = bucketIterator.next();
        assert o instanceof PourOperation : "Expected 3rd operation PourOperation, " + o.getClass() + " found.";
        po = (PourOperation) o;
        assert po.sourceBucket.getIndex() == 2 && po.destinationBucket.getIndex() == 1 :
                "Expected 3rd operation to pour bucket 2 into bucket 1, bucket " + po.sourceBucket.getIndex() + " pouring into bucket " + po.destinationBucket.getIndex() + " found.";

        o = bucketIterator.next();
        assert o instanceof SpillOperation : "Expected 4th operation SpillOperation, " + o.getClass() + " found.";
        so = (SpillOperation) o;
        assert so.bucketItem.getIndex() == 1 :
                "Expected 4th operation to spill bucket index 1, " + so.bucketItem.getIndex() + " found.";

        o = bucketIterator.next();
        assert o instanceof SpillOperation : "Expected 5th operation SpillOperation, " + o.getClass() + " found.";
        so = (SpillOperation) o;
        assert so.bucketItem.getIndex() == 2 :
                "Expected 5th operation to spill bucket index 2, " + so.bucketItem.getIndex() + " found.";

        o = bucketIterator.next();
        assert o instanceof FillOperation : "Expected 6th operation FillOperation, " + o.getClass() + " found.";
        fo = (FillOperation) o;
        assert fo.bucketItem.getIndex() == 0 :
                "Expected 6th operation to fill bucket index 0, " + fo.bucketItem.getIndex() + " found.";

        o = bucketIterator.next();
        assert o instanceof FillOperation : "Expected 7th operation FillOperation, " + o.getClass() + " found.";
        fo = (FillOperation) o;
        assert fo.bucketItem.getIndex() == 1 :
                "Expected 7th operation to fill bucket index 1, " + fo.bucketItem.getIndex() + " found.";

        assert !bucketIterator.hasNext() : "No more operations expected";


        try {
            bucketIterator.next();
            assert false : "No more operations expected";
        } catch (NoSuchElementException ignored) {
        }

    }

    @Test
    public void testGetRandomOperation() throws Exception {
        Bucket bucket = new Bucket(new int[]{14, 10, 6}, new int[]{0, 3, 6}, new int[]{12, 6, 4});

        Configuration c = bucket.getStartingConfiguration();

        OperationIterator bucketIterator = bucket.getOperationIterator(c);

        Operation o;
        FillOperation fo;
        SpillOperation so;
        PourOperation po;

        // test that random operations do not affect operation order
        for (int i = 0; i < 10; ++i) {
            bucketIterator.getRandomOperation();
        }

        o = bucketIterator.next();
        assert o instanceof PourOperation : "Expected 1st operation PourOperation, " + o.getClass() + " found.";
        po = (PourOperation) o;
        assert po.sourceBucket.getIndex() == 1 && po.destinationBucket.getIndex() == 0 :
                "Expected 1st operation to pour bucket 1 into bucket 0, bucket " + po.sourceBucket.getIndex() + " pouring into bucket " + po.destinationBucket.getIndex() + " found.";

        o = bucketIterator.next();
        assert o instanceof PourOperation : "Expected 2nd operation PourOperation, " + o.getClass() + " found.";
        po = (PourOperation) o;
        assert po.sourceBucket.getIndex() == 2 && po.destinationBucket.getIndex() == 0 :
                "Expected 2nd operation to pour bucket 2 into bucket 0, bucket " + po.sourceBucket.getIndex() + " pouring into bucket " + po.destinationBucket.getIndex() + " found.";

        o = bucketIterator.next();
        assert o instanceof PourOperation : "Expected 3rd operation PourOperation, " + o.getClass() + " found.";
        po = (PourOperation) o;
        assert po.sourceBucket.getIndex() == 2 && po.destinationBucket.getIndex() == 1 :
                "Expected 3rd operation to pour bucket 2 into bucket 1, bucket " + po.sourceBucket.getIndex() + " pouring into bucket " + po.destinationBucket.getIndex() + " found.";

        o = bucketIterator.next();
        assert o instanceof SpillOperation : "Expected 4th operation SpillOperation, " + o.getClass() + " found.";
        so = (SpillOperation) o;
        assert so.bucketItem.getIndex() == 1 :
                "Expected 4th operation to spill bucket index 1, " + so.bucketItem.getIndex() + " found.";

        o = bucketIterator.next();
        assert o instanceof SpillOperation : "Expected 5th operation SpillOperation, " + o.getClass() + " found.";
        so = (SpillOperation) o;
        assert so.bucketItem.getIndex() == 2 :
                "Expected 5th operation to spill bucket index 2, " + so.bucketItem.getIndex() + " found.";

        o = bucketIterator.next();
        assert o instanceof FillOperation : "Expected 6th operation FillOperation, " + o.getClass() + " found.";
        fo = (FillOperation) o;
        assert fo.bucketItem.getIndex() == 0 :
                "Expected 6th operation to fill bucket index 0, " + fo.bucketItem.getIndex() + " found.";

        o = bucketIterator.next();
        assert o instanceof FillOperation : "Expected 7th operation FillOperation, " + o.getClass() + " found.";
        fo = (FillOperation) o;
        assert fo.bucketItem.getIndex() == 1 :
                "Expected 7th operation to fill bucket index 1, " + fo.bucketItem.getIndex() + " found.";

        assert !bucketIterator.hasNext() : "No more operations expected";


        try {
            bucketIterator.next();
            assert false : "No more operations expected";
        } catch (NoSuchElementException ignored) {
        }

    }
}
