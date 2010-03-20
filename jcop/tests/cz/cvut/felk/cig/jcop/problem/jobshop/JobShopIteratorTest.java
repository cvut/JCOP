/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.jobshop;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Tests proper behavior of {@link JobShopIterator} iterator.
 *
 * @author Ondrej Skalicka
 */
public class JobShopIteratorTest {
    @Test
    public void testNext() throws Exception {
        JobShop jobShop = new JobShop(Arrays.asList(3, 2, 1), 3);
        Configuration configuration = new Configuration(Arrays.asList(0, 1, 2), "test");
        JobShopIterator jobShopIterator = jobShop.getOperationIterator(configuration);
        MoveOperation moveOperation;

        moveOperation = jobShopIterator.next();
        assert moveOperation.job.index == 0 : "Expected job index 0, " + moveOperation.job.index + " found.";
        assert moveOperation.machine == 1 : "Expected machine index 1, " + moveOperation.machine + " found.";

        moveOperation = jobShopIterator.next();
        assert moveOperation.job.index == 0 : "Expected job index 0, " + moveOperation.job.index + " found.";
        assert moveOperation.machine == 2 : "Expected machine index 2, " + moveOperation.machine + " found.";

        moveOperation = jobShopIterator.next();
        assert moveOperation.job.index == 1 : "Expected job index 1, " + moveOperation.job.index + " found.";
        assert moveOperation.machine == 0 : "Expected machine index 0, " + moveOperation.machine + " found.";

        moveOperation = jobShopIterator.next();
        assert moveOperation.job.index == 1 : "Expected job index 1, " + moveOperation.job.index + " found.";
        assert moveOperation.machine == 2 : "Expected machine index 2, " + moveOperation.machine + " found.";

        moveOperation = jobShopIterator.next();
        assert moveOperation.job.index == 2 : "Expected job index 2, " + moveOperation.job.index + " found.";
        assert moveOperation.machine == 0 : "Expected machine index 0, " + moveOperation.machine + " found.";

        moveOperation = jobShopIterator.next();
        assert moveOperation.job.index == 2 : "Expected job index 2, " + moveOperation.job.index + " found.";
        assert moveOperation.machine == 1 : "Expected machine index 1, " + moveOperation.machine + " found.";

        assert !jobShopIterator.hasNext() : "No more operations expected";

        try {
            jobShopIterator.next();
            assert false : "No more operations expected";
        } catch (NoSuchElementException ignored) {

        }
    }
}