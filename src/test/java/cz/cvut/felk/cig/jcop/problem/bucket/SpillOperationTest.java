/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.bucket;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import org.testng.annotations.Test;

/**
 * Tests proper behavior of {@link SpillOperation} in {@link Bucket} problem.
 *
 * @author Ondrej Skalicka
 */
public class SpillOperationTest {
    @Test
    public void testExecute() throws Exception {
        Bucket bucket = new Bucket(new int[]{6, 6, 6}, new int[]{0, 4, 6}, new int[]{12, 6, 4});

        Configuration c = bucket.getStartingConfiguration();

        Configuration c2 = new SpillOperation(bucket.buckets.get(0)).execute(c);

        assert c2.valueAt(0) == 0 : "Expected configuration variable 0, " + c2.valueAt(0) + " found";
        assert c2.valueAt(1) == 4 : "Expected configuration variable 4, " + c2.valueAt(1) + " found";
        assert c2.valueAt(2) == 6 : "Expected configuration variable 6, " + c2.valueAt(2) + " found";


        c2 = new SpillOperation(bucket.buckets.get(1)).execute(c);

        assert c2.valueAt(0) == 0 : "Expected configuration variable 0, " + c2.valueAt(0) + " found";
        assert c2.valueAt(1) == 0 : "Expected configuration variable 0, " + c2.valueAt(1) + " found";
        assert c2.valueAt(2) == 6 : "Expected configuration variable 6, " + c2.valueAt(2) + " found";

        c2 = new SpillOperation(bucket.buckets.get(2)).execute(c);

        assert c2.valueAt(0) == 0 : "Expected configuration variable 0, " + c2.valueAt(0) + " found";
        assert c2.valueAt(1) == 4 : "Expected configuration variable 4, " + c2.valueAt(1) + " found";
        assert c2.valueAt(2) == 0 : "Expected configuration variable 0, " + c2.valueAt(2) + " found";
    }
}
