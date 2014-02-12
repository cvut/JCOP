/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.bucket;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Operation;
import org.testng.annotations.Test;

/**
 * Tests proper behavior of {@link PourOperation} in {@link Bucket} problem.
 *
 * @author Ondrej Skalicka
 */
public class PourOperationTest {
    @Test
    public void testExecute() throws Exception {
        Bucket bucket = new Bucket(new int[]{6, 6, 6}, new int[]{0, 4, 6}, new int[]{12, 6, 4});

        Configuration c = bucket.getStartingConfiguration();

        Operation o = new PourOperation(bucket.buckets.get(0), bucket.buckets.get(1));
        Configuration c2 = o.execute(c);

        assert c2.valueAt(0) == 0 : "Expected configuration variable 0, " + c2.valueAt(0) + " found";
        assert c2.valueAt(1) == 4 : "Expected configuration variable 4, " + c2.valueAt(1) + " found";
        assert c2.valueAt(2) == 6 : "Expected configuration variable 6, " + c2.valueAt(2) + " found";

        o = new PourOperation(bucket.buckets.get(1), bucket.buckets.get(0));
        c2 = o.execute(c);

        assert c2.valueAt(0) == 4 : "Expected configuration variable 4, " + c2.valueAt(0) + " found";
        assert c2.valueAt(1) == 0 : "Expected configuration variable 0, " + c2.valueAt(1) + " found";
        assert c2.valueAt(2) == 6 : "Expected configuration variable 6, " + c2.valueAt(2) + " found";

        o = new PourOperation(bucket.buckets.get(2), bucket.buckets.get(1));
        c2 = o.execute(c);

        assert c2.valueAt(0) == 0 : "Expected configuration variable 0, " + c2.valueAt(0) + " found";
        assert c2.valueAt(1) == 6 : "Expected configuration variable 6, " + c2.valueAt(1) + " found";
        assert c2.valueAt(2) == 4 : "Expected configuration variable 4, " + c2.valueAt(2) + " found";
    }
}
